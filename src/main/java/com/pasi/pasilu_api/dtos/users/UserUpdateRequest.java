package com.pasi.pasilu_api.dtos.users;

import jakarta.validation.constraints.*;

public record UserUpdateRequest(
        @Size(max = 50) String name,
        @Size(max = 50) String lastname,
        @Size(max = 20)            String cellphone,
        String                      location,
        @Size(min = 8, max = 72)   String password   // opcional
) {}
