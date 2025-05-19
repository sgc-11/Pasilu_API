package com.pasi.pasilu_api.controllers;

import com.pasi.pasilu_api.dtos.transactions.TransactionCreateRequest;
import com.pasi.pasilu_api.dtos.transactions.TransactionResponse;
import com.pasi.pasilu_api.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets/{walletId}/transactions")
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionService txService;

    @GetMapping
    public List<TransactionResponse> list(@PathVariable UUID walletId) {
        return txService.listByWallet(walletId);
    }

    @GetMapping("/{txId}")
    public TransactionResponse get(@PathVariable UUID walletId,
                                   @PathVariable UUID txId) {
        return txService.find(walletId, txId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(@PathVariable UUID walletId,
                                      @Valid @RequestBody TransactionCreateRequest body) {
        return txService.create(walletId, body);
    }
}
