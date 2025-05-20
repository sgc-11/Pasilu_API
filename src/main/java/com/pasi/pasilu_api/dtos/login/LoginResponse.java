package com.pasi.pasilu_api.dtos.login;

import java.util.UUID;

public record LoginResponse(
        UUID id,
        String name,
        String lastname,
        String mail) {}
