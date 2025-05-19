package com.pasi.pasilu_api.exceptions;

public class PermissionNotFoundException extends RuntimeException {
    public PermissionNotFoundException(String message) {
        super("Permiso con id " + message + " no encontrado");
    }
}
