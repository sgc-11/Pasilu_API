package com.pasi.pasilu_api.exceptions;

import java.util.UUID;

// no encontrada (para delete)
public class RolePermissionNotFoundException extends RuntimeException {
    public RolePermissionNotFoundException(UUID roleId, UUID permId) {
        super("El rol " + roleId + " no posee el permiso " + permId);
    }
}
