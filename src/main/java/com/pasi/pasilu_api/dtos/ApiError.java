package com.pasi.pasilu_api.dtos;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiError(
        int            status,      // 404
        String         code,        // USER_NOT_FOUND
        String         message,     // descripción legible
        LocalDateTime timestamp
) {
    public ApiError(HttpStatus status, String code, String message) {
        this(status.value(), code, message, LocalDateTime.now());
    }
}
