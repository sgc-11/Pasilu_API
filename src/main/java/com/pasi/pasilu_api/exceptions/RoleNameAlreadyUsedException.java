package com.pasi.pasilu_api.exceptions;

public class RoleNameAlreadyUsedException extends RuntimeException {
    public RoleNameAlreadyUsedException(String message)
    {
        super("El rol '" + message + "' ya existe");
    }
}
