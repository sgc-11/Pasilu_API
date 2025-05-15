package com.pasi.pasilu_api.dtos.roles;

import jakarta.validation.constraints.Size;

public record RoleUpdateRequest(
        @Size(max = 50) String name
) {
}
