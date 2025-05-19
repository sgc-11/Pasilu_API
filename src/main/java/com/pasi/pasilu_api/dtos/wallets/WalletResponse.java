package com.pasi.pasilu_api.dtos.wallets;

import java.time.LocalDateTime;
import java.util.UUID;

public record WalletResponse(
        UUID id,
        String         name,
        UUID           createdById,
        String         createdByName,
        String         notes,
        Boolean        isActive,
        Double         currentBalance,
        LocalDateTime createdAt
) {}
