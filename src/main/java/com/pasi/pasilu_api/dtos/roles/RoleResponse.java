package com.pasi.pasilu_api.dtos.roles;

import java.time.LocalDateTime;
import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
