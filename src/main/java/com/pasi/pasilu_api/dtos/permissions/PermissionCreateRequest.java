package com.pasi.pasilu_api.dtos.permissions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PermissionCreateRequest(
        @NotBlank @Size(max = 50) String name,
        @Size(max = 255)          String description
) {
}
