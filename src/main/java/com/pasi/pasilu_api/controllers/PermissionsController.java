package com.pasi.pasilu_api.controllers;

import com.pasi.pasilu_api.dtos.permissions.*;
import com.pasi.pasilu_api.services.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
public class PermissionsController {

    private final PermissionService permissionService;

    /* -------- LIST -------- */
    @GetMapping
    public List<PermissionResponse> list() {
        return permissionService.findAll();
    }

    /* -------- GET -------- */
    @GetMapping("/{id}")
    public PermissionResponse get(@PathVariable UUID id) {
        return permissionService.findById(id);
    }

    /* -------- CREATE -------- */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionResponse create(@Valid @RequestBody PermissionCreateRequest body) {
        return permissionService.create(body);
    }

    /* -------- UPDATE -------- */
    @PutMapping("/{id}")
    public PermissionResponse update(@PathVariable UUID id,
                                     @Valid @RequestBody PermissionUpdateRequest body) {
        return permissionService.update(id, body);
    }

    /* -------- DELETE -------- */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        permissionService.delete(id);
    }
}
