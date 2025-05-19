package com.pasi.pasilu_api.dtos.wallets;

import jakarta.validation.constraints.Size;

public record WalletUpdateRequest(
        @Size(max = 100) String name,
        String           notes,
        Boolean          isActive        // opcional activar/desactivar
) {}
