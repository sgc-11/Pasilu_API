package com.pasi.pasilu_api.exceptions;

public class PermissionNameAlreadyUsedException extends RuntimeException {
    public PermissionNameAlreadyUsedException(String message) {
        super("El permiso '" + message + "' ya existe");
    }
}
