package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.dtos.wallets.*;
import com.pasi.pasilu_api.entities.User;
import com.pasi.pasilu_api.entities.Wallet;
import com.pasi.pasilu_api.exceptions.*;
import com.pasi.pasilu_api.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository   walletRepo;
    private final UserRepository     userRepo;

    /* ----- mapper ----- */
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

    /* ----- CREATE ----- */
    @Transactional
    public WalletResponse create(WalletCreateRequest dto) {

        if (walletRepo.existsByNameAndCreatedBy_Id(dto.name(), dto.createdById()))
            throw new WalletNameAlreadyUsedException(dto.name());

        User creator = userRepo.findById(dto.createdById())
                .orElseThrow(() -> new UserNotFoundException(dto.createdById()));

        Wallet w = new Wallet();
        w.setName(dto.name());
        w.setCreatedBy(creator);
        w.setNotes(dto.notes());

        return toResponse(walletRepo.save(w));
    }

    /* ----- UPDATE ----- */
    @Transactional
    public WalletResponse update(UUID id, WalletUpdateRequest dto) {

        Wallet w = walletRepo.findById(id)
                .orElseThrow(() -> new WalletNotFoundException(id.toString()));

        if (dto.name() != null && !dto.name().equals(w.getName())) {
            // evitar duplicados por creador
            if (walletRepo.existsByNameAndCreatedBy_Id(dto.name(), w.getCreatedBy().getId()))
                throw new WalletNameAlreadyUsedException(dto.name());
            w.setName(dto.name());
        }

        if (dto.notes() != null)    w.setNotes(dto.notes());
        if (dto.isActive() != null) w.setIsActive(dto.isActive());

        return toResponse(w);      // flush automático al commit
    }

    /* ----- DELETE ----- */
    @Transactional
    public void delete(UUID id) {
        if (!walletRepo.existsById(id))
            throw new WalletNotFoundException(id.toString());
        walletRepo.deleteById(id);
    }

    /* ----- READ ----- */
    @Transactional
    public WalletResponse findById(UUID id) {
        return walletRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new WalletNotFoundException(id.toString()));
    }

    @Transactional
    public List<WalletResponse> findAll() {
        return walletRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
