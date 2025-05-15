package com.pasi.pasilu_api.dtos.roles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleCreateRequest (
        @NotBlank @Size(max = 50) String name
)
{ }
