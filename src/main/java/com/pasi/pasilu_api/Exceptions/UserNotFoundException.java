package com.pasi.pasilu_api.Exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID message) {
        super("usuario no encontrado" + message);
    }
}
