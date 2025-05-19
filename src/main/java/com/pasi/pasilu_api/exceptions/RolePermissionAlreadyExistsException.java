package com.pasi.pasilu_api.exceptions;

import java.util.UUID;

// ya existe la asociación
public class RolePermissionAlreadyExistsException extends RuntimeException {
    public RolePermissionAlreadyExistsException(UUID roleId, UUID permId) {
        super("El rol " + roleId + " ya contiene el permiso " + permId);
    }
}

