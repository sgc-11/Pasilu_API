package com.pasi.pasilu_api.dtos.users;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String lastname,
        String mail,
        String cellphone,
        String location,
        LocalDateTime createdAt
) {}

