package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.dtos.roles_permissions.*;
import com.pasi.pasilu_api.entities.Permission;
import com.pasi.pasilu_api.entities.Role;
import com.pasi.pasilu_api.entities.RolePermission;
import com.pasi.pasilu_api.exceptions.*;
import com.pasi.pasilu_api.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RolePermissionService {

    private final RoleRepository roleRepo;
    private final PermissionRepository permRepo;
    private final RolePermissionRepository rpRepo;

    /* -------- Helpers -------- */
    private RolePermissionResponse toResponse(RolePermission rp) {
        return new RolePermissionResponse(
                rp.getId(),
                rp.getRole().getId(),
                rp.getRole().getName(),
                rp.getPermission().getId(),
                rp.getPermission().getName(),
                rp.getName()
        );
    }

    /* -------- ADD -------- */
    @Transactional
    public RolePermissionResponse addPermissionToRole(UUID roleId, RolePermissionCreateRequest dto) {

        if (rpRepo.existsByRole_IdAndPermission_Id(roleId, dto.permissionId()))
            throw new RolePermissionAlreadyExistsException(roleId, dto.permissionId());

        Role role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId.toString()));

        Permission perm = permRepo.findById(dto.permissionId())
                .orElseThrow(() -> new PermissionNotFoundException(dto.permissionId().toString()));

        RolePermission rp = new RolePermission();
        rp.setRole(role);
        rp.setPermission(perm);
        rp.setName(dto.name() != null ? dto.name() : perm.getName());

        return toResponse(rpRepo.save(rp));
    }

    /* -------- LIST -------- */
    @Transactional
    public List<RolePermissionResponse> listPermissions(UUID roleId) {
        if (!roleRepo.existsById(roleId))
            throw new RoleNotFoundException(roleId.toString());

        return rpRepo.findAllByRole_Id(roleId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /* -------- DELETE -------- */
    @Transactional
    public void removePermission(UUID roleId, UUID permId) {
        RolePermission rp = rpRepo.findByRole_IdAndPermission_Id(roleId, permId)
                .orElseThrow(() -> new RolePermissionNotFoundException(roleId, permId));

        rpRepo.delete(rp);
    }
}

