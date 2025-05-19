package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.dtos.goals.*;
import com.pasi.pasilu_api.entities.Goal;
import com.pasi.pasilu_api.entities.Wallet;
import com.pasi.pasilu_api.exceptions.*;
import com.pasi.pasilu_api.repositories.GoalRepository;
import com.pasi.pasilu_api.repositories.WalletRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoalService {

    private final WalletRepository walletRepo;
    private final GoalRepository   goalRepo;

    /* ---------- mapper ---------- */
    private GoalResponse toResponse(Goal g) {
        return new GoalResponse(
                g.getId(),
                g.getWallet().getId(),
                g.getGoalDate(),
                g.getTargetAmount(),
                g.getIsAchieved(),
                g.getDescription(),
                g.getUpdatedAt()
        );
    }

    /* ---------- CREATE ---------- */
    @Transactional
    public GoalResponse create(UUID walletId, GoalCreateRequest dto) {

        if (goalRepo.existsByWallet_Id(walletId))
            throw new GoalAlreadyExistsException(walletId.toString());

        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId.toString()));

        Goal g = new Goal();
        g.setWallet(wallet);
        g.setGoalDate(dto.goalDate());
        g.setTargetAmount(dto.targetAmount());
        g.setDescription(dto.description());

        Goal saved = goalRepo.save(g);

        /* vincular desde Wallet (columna goal_id) */
        wallet.setGoal(saved);

        return toResponse(saved);
    }

    /* ---------- READ ---------- */
    @Transactional
    public GoalResponse findByWallet(UUID walletId) {
        return goalRepo.findByWallet_Id(walletId)
                .map(this::toResponse)
                .orElseThrow(() -> new GoalNotFoundException(walletId.toString()));
    }

    /* ---------- UPDATE ---------- */
    @Transactional
    public GoalResponse update(UUID walletId, GoalUpdateRequest dto) {

        Goal g = goalRepo.findByWallet_Id(walletId)
                .orElseThrow(() -> new GoalNotFoundException(walletId.toString()));

        if (dto.goalDate()     != null) g.setGoalDate(dto.goalDate());
        if (dto.targetAmount() != null) g.setTargetAmount(dto.targetAmount());
        if (dto.description()  != null) g.setDescription(dto.description());

        return toResponse(g);   // flush al commit
    }

    /* ---------- DELETE ---------- */
    @Transactional
    public void delete(UUID walletId) {
        Goal g = goalRepo.findByWallet_Id(walletId)
                .orElseThrow(() -> new GoalNotFoundException(walletId.toString()));

        /* romper vínculo en Wallet */
        Wallet w = g.getWallet();
        w.setGoal(null);

        goalRepo.delete(g);
    }
}
