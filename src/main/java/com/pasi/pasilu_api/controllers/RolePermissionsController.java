package com.pasi.pasilu_api.controllers;

import com.pasi.pasilu_api.dtos.roles_permissions.*;
import com.pasi.pasilu_api.services.RolePermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles/{roleId}/permissions")
@RequiredArgsConstructor
public class RolePermissionsController {

    private final RolePermissionService rpService;

    /* ---- LIST ---- */
    @GetMapping
    public List<RolePermissionResponse> list(@PathVariable UUID roleId) {
        return rpService.listPermissions(roleId);
    }

    /* ---- ADD ---- */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RolePermissionResponse add(@PathVariable UUID roleId,
                                      @Valid @RequestBody RolePermissionCreateRequest body) {
        return rpService.addPermissionToRole(roleId, body);
    }

    /* ---- DELETE ---- */
    @DeleteMapping("/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID roleId,
                       @PathVariable UUID permissionId) {
        rpService.removePermission(roleId, permissionId);
    }
}
