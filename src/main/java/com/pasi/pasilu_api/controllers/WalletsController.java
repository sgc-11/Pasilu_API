package com.pasi.pasilu_api.controllers;

import com.pasi.pasilu_api.dtos.wallets.*;
import com.pasi.pasilu_api.services.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletsController {

    private final WalletService walletService;

    /* ---- LIST ---- */
    @GetMapping
    public List<WalletResponse> list() {
        return walletService.findAllForCurrentUser();
    }

    /* ---- GET ---- */
    @GetMapping("/{id}")
    public WalletResponse get(@PathVariable UUID id) {
        return walletService.findById(id);
    }

    /* ---- CREATE ---- */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WalletResponse create(@Valid @RequestBody WalletCreateRequest body) {
        return walletService.create(body);
    }

    /* ---- UPDATE ---- */
    @PutMapping("/{id}")
    public WalletResponse update(@PathVariable UUID id,
                                 @Valid @RequestBody WalletUpdateRequest body) {
        return walletService.update(id, body);
    }

    /* ---- DELETE ---- */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        walletService.delete(id);
    }
}
