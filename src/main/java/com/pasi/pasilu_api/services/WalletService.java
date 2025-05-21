package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.dtos.wallets.*;
import com.pasi.pasilu_api.entities.*;
import com.pasi.pasilu_api.exceptions.*;
import com.pasi.pasilu_api.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository       walletRepo;
    private final WalletMemberRepository memberRepo;
    private final UserRepository         userRepo;
    private final RoleRepository         roleRepo;

    /* ---------- helpers de usuario ---------- */
    private UUID currentUserId() {
        return (UUID) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
    private User currentUser() {
        return userRepo.getReferenceById(currentUserId());
    }

    /* ---------- helpers de autorización ---------- */

    private boolean isAdmin(UUID walletId) {
        UUID uid = currentUserId();
        return walletRepo.existsByIdAndCreatedBy_Id(walletId, uid) ||      // creador
                memberRepo.existsByWallet_IdAndUser_IdAndRole_Name(walletId, uid, "ADMIN");
    }

    private void verifyCanEdit(UUID walletId) {
        if (!isAdmin(walletId))
            throw new AccessDeniedException("No tienes permisos sobre la wallet " + walletId);
    }

    private void verifyParticipant(UUID walletId) {
        UUID uid = currentUserId();
        boolean isCreator  = walletRepo.existsByIdAndCreatedBy_Id(walletId, uid);
        boolean isMember   = memberRepo.existsByWallet_IdAndUser_Id(walletId, uid);
        if (!(isCreator || isMember))
            throw new AccessDeniedException("No estás autorizado para ver la wallet " + walletId);
    }

    /* ---------- DASHBOARD ---------- */
    @Transactional
    public List<WalletResponse> findAllForCurrentUser() {
        UUID uid = currentUserId();
        // consulta única (creador + miembro)
        return walletRepo.findAllParticipating(uid)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ---------- CREATE ---------- */
    @Transactional
    public WalletResponse create(WalletCreateRequest dto) {

        User creator = currentUser();

        if (walletRepo.existsByNameAndCreatedBy_Id(dto.name(), creator.getId()))
            throw new WalletNameAlreadyUsedException(dto.name());

        Wallet w = new Wallet();
        w.setName(dto.name());
        w.setNotes(dto.notes());
        w.setCreatedBy(creator);
        walletRepo.save(w);

        /* añade al creador como ADMIN */
        WalletMember member = new WalletMember();
        member.setWallet(w);
        member.setUser(creator);
        member.setRole(roleRepo.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no existe")));
        memberRepo.save(member);

        return toResponse(w);
    }

    /* ---------- UPDATE ---------- */
    @Transactional
    public WalletResponse update(UUID id, WalletUpdateRequest dto) {
        verifyCanEdit(id);

        Wallet w = walletRepo.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id.toString()));

        if (dto.name() != null && !dto.name().equals(w.getName())) {
            if (walletRepo.existsByNameAndCreatedBy_Id(dto.name(), w.getCreatedBy().getId()))
                throw new WalletNameAlreadyUsedException(dto.name());
            w.setName(dto.name());
        }
        if (dto.notes() != null)    w.setNotes(dto.notes());
        if (dto.isActive() != null) w.setIsActive(dto.isActive());

        return toResponse(w);   // el commit hace flush
    }

    /* ---------- DELETE ---------- */
    @Transactional
    public void delete(UUID id) {
        verifyCanEdit(id);
        if (!walletRepo.existsById(id))
            throw new WalletNotFoundException(id.toString());
        walletRepo.deleteById(id);
    }

    /* ---------- READ ---------- */
    @Transactional
    public WalletResponse findById(UUID id) {
        verifyParticipant(id);
        return walletRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new WalletNotFoundException(id.toString()));
    }

    /* ---------- Mapper ---------- */
    private WalletResponse toResponse(Wallet w) {
        return new WalletResponse(
                w.getId(),
                w.getName(),
                w.getCreatedBy().getId(),
                w.getCreatedBy().getName(),
                w.getNotes(),
                w.getIsActive(),
                w.getCurrentBalance(),
                w.getCreatedAt()
        );
    }
}
