package com.pasi.pasilu_api.dtos.transactions;

import com.pasi.pasilu_api.entities.enums.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionCreateRequest(
        @NotNull BigDecimal amount,
        @NotNull UUID memberId,       // wallet_member que la solicita
        @NotNull TransactionType type
) {}
