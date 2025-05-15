package com.pasi.pasilu_api.dtos.permissions;

import jakarta.validation.constraints.Size;

public record PermissionUpdateRequest(
        @Size(max = 50)  String name,
        @Size(max = 255) String description
) {
}
