package com.pasi.pasilu_api.dtos.login;

import jakarta.validation.constraints.Email;

public record LoginRequest(
        @Email String mail,
        String password) {}
