package com.pasi.pasilu_api.Exceptions;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {

        super("El rol " + message + " no existe");
    }
}
