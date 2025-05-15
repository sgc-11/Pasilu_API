package com.pasi.pasilu_api.Exceptions;

public class RoleNameAlreadyUsedException extends RuntimeException {
    public RoleNameAlreadyUsedException(String message)
    {
        super("El rol '" + message + "' ya existe");
    }
}
