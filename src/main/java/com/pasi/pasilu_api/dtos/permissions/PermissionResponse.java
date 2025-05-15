package com.pasi.pasilu_api.dtos.permissions;

import java.util.UUID;

public record PermissionResponse(
        UUID id,
        String name,
        String description
) {
}
