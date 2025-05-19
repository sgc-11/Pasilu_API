package com.pasi.pasilu_api.repositories;

import com.pasi.pasilu_api.entities.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID> {

    boolean existsByRole_IdAndPermission_Id(UUID roleId, UUID permissionId);

    Optional<RolePermission> findByRole_IdAndPermission_Id(UUID roleId, UUID permissionId);

    List<RolePermission> findAllByRole_Id(UUID roleId);
}
