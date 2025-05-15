package com.pasi.pasilu_api.repositories;

import com.pasi.pasilu_api.Entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    boolean existsByName(String name);
}