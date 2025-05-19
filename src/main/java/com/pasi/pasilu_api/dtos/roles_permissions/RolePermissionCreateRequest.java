package com.pasi.pasilu_api.dtos.roles_permissions;

import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RolePermissionCreateRequest(
        UUID permissionId,
        @Size(max = 100) String name    // opcional
) {}
