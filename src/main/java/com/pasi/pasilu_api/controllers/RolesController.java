package com.pasi.pasilu_api.controllers;

import com.pasi.pasilu_api.dtos.roles.*;
import com.pasi.pasilu_api.services.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolesController {

    private final RoleService roleService;

    /* ---------- Listar ---------- */
    @GetMapping
    public List<RoleResponse> list() {
        return roleService.findAll();
    }

    /* ---------- Obtener por ID ---------- */
    @GetMapping("/{id}")
    public RoleResponse get(@PathVariable UUID id) {
        return roleService.findById(id);
    }

    /* ---------- Crear ---------- */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleResponse create(@Valid @RequestBody RoleCreateRequest body) {
        return roleService.create(body);
    }

    /* ---------- Actualizar ---------- */
    @PutMapping("/{id}")
    public RoleResponse update(@PathVariable UUID id,
                               @Valid @RequestBody RoleUpdateRequest body) {
        return roleService.update(id, body);
    }

    /* ---------- Eliminar ---------- */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        roleService.delete(id);
    }
}
