package com.pasi.pasilu_api.controllers;

import com.pasi.pasilu_api.dtos.goals.*;
import com.pasi.pasilu_api.services.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets/{walletId}/goal")
@RequiredArgsConstructor
public class WalletGoalController {

    private final GoalService goalService;

    /* ---- GET ---- */
    @GetMapping
    public GoalResponse get(@PathVariable UUID walletId) {
        return goalService.findByWallet(walletId);
    }

    /* ---- CREATE ---- */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoalResponse create(@PathVariable UUID walletId,
                               @Valid @RequestBody GoalCreateRequest body) {
        return goalService.create(walletId, body);
    }

    /* ---- UPDATE ---- */
    @PutMapping
    public GoalResponse update(@PathVariable UUID walletId,
                               @Valid @RequestBody GoalUpdateRequest body) {
        return goalService.update(walletId, body);
    }

    /* ---- DELETE ---- */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID walletId) {
        goalService.delete(walletId);
    }
}
