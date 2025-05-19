package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.dtos.wallets_members.*;
import com.pasi.pasilu_api.entities.*;
import com.pasi.pasilu_api.exceptions.*;
import com.pasi.pasilu_api.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletMemberService {

    private final WalletRepository      walletRepo;
    private final UserRepository        userRepo;
    private final RoleRepository        roleRepo;
    private final WalletMemberRepository memberRepo;

    /* ---- mapper ---- */
    private WalletMemberResponse toResponse(WalletMember wm) {
        return new WalletMemberResponse(
                wm.getId(),
                wm.getWallet().getId(),
                wm.getUser().getId(),
                wm.getUser().getName(),
                wm.getRole().getId(),
                wm.getRole().getName()
        );
    }

    /* ---- ADD ---- */
    @Transactional
    public WalletMemberResponse addMember(UUID walletId, WalletMemberCreateRequest dto) {

        if (memberRepo.existsByWallet_IdAndUser_Id(walletId, dto.userId()))
            throw new WalletMemberAlreadyExistsException(walletId.toString(), dto.userId().toString());

        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId.toString()));

        User user = userRepo.findById(dto.userId())
                .orElseThrow(() -> new UserNotFoundException(dto.userId()));

        Role role = roleRepo.findById(dto.roleId())
                .orElseThrow(() -> new RoleNotFoundException(dto.roleId().toString()));

        WalletMember wm = new WalletMember();
        wm.setWallet(wallet);
        wm.setUser(user);
        wm.setRole(role);

        return toResponse(memberRepo.save(wm));
    }

    /* ---- LIST ---- */
    @Transactional
    public List<WalletMemberResponse> listMembers(UUID walletId) {

        if (!walletRepo.existsById(walletId))
            throw new WalletNotFoundException(walletId.toString());

        return memberRepo.findAllByWallet_Id(walletId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* ---- UPDATE ROLE ---- */
    @Transactional
    public WalletMemberResponse updateRole(UUID walletId, UUID memberId, WalletMemberUpdateRequest dto) {

        WalletMember wm = memberRepo.findByIdAndWallet_Id(memberId, walletId)
                .orElseThrow(() -> new WalletMemberNotFoundException(memberId.toString()));

        if (dto.roleId() != null && !dto.roleId().equals(wm.getRole().getId())) {
            Role role = roleRepo.findById(dto.roleId())
                    .orElseThrow(() -> new RoleNotFoundException(dto.roleId().toString()));
            wm.setRole(role);
        }
        return toResponse(wm);
    }

    /* ---- REMOVE ---- */
    @Transactional
    public void removeMember(UUID walletId, UUID memberId) {

        WalletMember wm = memberRepo.findByIdAndWallet_Id(memberId, walletId)
                .orElseThrow(() -> new WalletMemberNotFoundException(memberId.toString()));

        memberRepo.delete(wm);
    }
}
