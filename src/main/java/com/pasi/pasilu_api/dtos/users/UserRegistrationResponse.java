package com.pasi.pasilu_api.dtos.users;

import java.util.UUID;

public record UserRegistrationResponse(
        UUID id,
        String name,
        String lastname,
        String mail,
        String token) {}
