package com.pasi.pasilu_api.dtos.transactions;

import com.pasi.pasilu_api.entities.enums.TransactionStatus;
import com.pasi.pasilu_api.entities.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionResponse(
        UUID id,
        UUID                walletId,
        BigDecimal amount,
        TransactionType type,
        TransactionStatus status,
        Integer             requiredApprovals,
        UUID                memberRequiringId,
        LocalDateTime createdAt
) {}
