package com.pasi.pasilu_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "roles_permissions",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_role_permission",
                columnNames = { "role_id", "permission_id" }))
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_permission_id", nullable = false, updatable = false)
    private UUID id;

    /* ---- Relaciones ---- */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "permission_id", nullable = false)
    private Permission permission;

    /* ---- Campo opcional (alias) ---- */
    @Size(max = 100)
    private String name;   // puedes rellenarlo con permission.getName() o dejarlo null
}
