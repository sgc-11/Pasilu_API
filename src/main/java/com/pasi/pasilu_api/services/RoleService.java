package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.Entities.Role;
import com.pasi.pasilu_api.Exceptions.RoleNameAlreadyUsedException;
import com.pasi.pasilu_api.Exceptions.RoleNotFoundException;
import com.pasi.pasilu_api.dtos.roles.*;
import com.pasi.pasilu_api.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepo;

    /* ---------- Helpers ---------- */
    private RoleResponse toResponse(Role r) {
        return new RoleResponse(
                r.getId(),
                r.getName(),
                r.getCreatedAt(),
                r.getUpdatedAt()
        );
    }

    /* ---------- CRUD ---------- */

    @Transactional
    public RoleResponse create(RoleCreateRequest dto) {
        if (roleRepo.existsByName(dto.name()))
            throw new RoleNameAlreadyUsedException(dto.name());

        Role role = new Role();
        role.setName(dto.name());

        return toResponse(roleRepo.save(role));
    }

    @Transactional
    public RoleResponse update(UUID id, RoleUpdateRequest dto) {
        Role role = roleRepo.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(id.toString()));

        if (dto.name() != null && !dto.name().equals(role.getName())) {
            if (roleRepo.existsByName(dto.name()))
                throw new RoleNameAlreadyUsedException(dto.name());
            role.setName(dto.name());
        }
        return toResponse(role);        // la entidad está gestionada; se flushea al commit
    }

    @Transactional
    public void delete(UUID id) {
        if (!roleRepo.existsById(id))
            throw new RoleNotFoundException(id.toString());
        roleRepo.deleteById(id);
    }

    @Transactional
    public RoleResponse findById(UUID id) {
        return roleRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RoleNotFoundException(id.toString()));
    }

    @Transactional
    public List<RoleResponse> findAll() {
        return roleRepo.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
