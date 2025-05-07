package com.pasi.pasilu_api.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegistrationRequest(
        @NotBlank @Size(max = 50) String name,
        @NotBlank @Size(max = 50) String lastname,
        @Email @NotBlank @Size(max = 100) String mail,
        @Size(max = 20) String cellphone,
        String location,
        @NotBlank @Size(min = 8, max = 72) String password   // bcrypt ≤ 72 car.
) {}
