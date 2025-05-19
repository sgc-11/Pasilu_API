package com.pasi.pasilu_api.dtos.wallets;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record WalletCreateRequest(
        @NotBlank @Size(max = 100) String name,
        @NotNull UUID createdById,   // id del usuario creador
        String     notes
) {}
