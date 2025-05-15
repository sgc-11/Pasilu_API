package com.pasi.pasilu_api.Exceptions;

public class PermissionNameAlreadyUsedException extends RuntimeException {
    public PermissionNameAlreadyUsedException(String message) {
        super("El permiso '" + message + "' ya existe");
    }
}
