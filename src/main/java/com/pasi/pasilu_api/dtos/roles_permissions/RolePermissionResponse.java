package com.pasi.pasilu_api.dtos.roles_permissions;

import java.util.UUID;

public record RolePermissionResponse(
        UUID rolePermissionId,
        UUID roleId,
        String roleName,
        UUID permissionId,
        String permissionName,
        String alias         // el campo name de la tabla (puede ser null)
) {}
