package com.pasi.pasilu_api.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID message) {
        super("usuario no encontrado" + message);
    }
}
