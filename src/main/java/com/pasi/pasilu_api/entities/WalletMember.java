package com.pasi.pasilu_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "wallet_members",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_wallet_user", columnNames = { "wallet_id", "user_id" }))
public class WalletMember {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_member_id", nullable = false, updatable = false)
    private UUID id;

    /* ---- Relaciones ---- */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
