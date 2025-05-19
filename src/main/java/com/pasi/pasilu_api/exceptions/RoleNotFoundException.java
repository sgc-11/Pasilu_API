package com.pasi.pasilu_api.exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {

        super("El rol " + message + " no existe");
    }
}
