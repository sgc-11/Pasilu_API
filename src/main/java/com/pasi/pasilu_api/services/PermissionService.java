package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.Entities.Permission;
import com.pasi.pasilu_api.Exceptions.PermissionNameAlreadyUsedException;
import com.pasi.pasilu_api.Exceptions.PermissionNotFoundException;
import com.pasi.pasilu_api.dtos.permissions.*;
import com.pasi.pasilu_api.repositories.PermissionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepo;

    /* ---------- Mapper manual ---------- */
    private PermissionResponse toResponse(Permission p) {
        return new PermissionResponse(
                p.getId(),
                p.getName(),
                p.getDescription()
        );
    }

    /* ---------- CRUD ---------- */

    @Transactional
    public PermissionResponse create(PermissionCreateRequest dto) {
        if (permissionRepo.existsByName(dto.name()))
            throw new PermissionNameAlreadyUsedException(dto.name());

        Permission p = new Permission();
        p.setName(dto.name());
        p.setDescription(dto.description());

        return toResponse(permissionRepo.save(p));
    }

    @Transactional
    public PermissionResponse update(UUID id, PermissionUpdateRequest dto) {
        Permission p = permissionRepo.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id.toString()));

        if (dto.name() != null && !dto.name().equals(p.getName())) {
            if (permissionRepo.existsByName(dto.name()))
                throw new PermissionNameAlreadyUsedException(dto.name());
            p.setName(dto.name());
        }
        if (dto.description() != null) p.setDescription(dto.description());

        return toResponse(p);   // entidad gestionada, flush al commit
    }

    @Transactional
    public void delete(UUID id) {
        if (!permissionRepo.existsById(id))
            throw new PermissionNotFoundException(id.toString());
        permissionRepo.deleteById(id);
    }

    @Transactional
    public PermissionResponse findById(UUID id) {
        return permissionRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new PermissionNotFoundException(id.toString()));
    }

    @Transactional
    public List<PermissionResponse> findAll() {
        return permissionRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
