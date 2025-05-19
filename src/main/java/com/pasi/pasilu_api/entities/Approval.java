package com.pasi.pasilu_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "approvals",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_trans_member",
                columnNames = {"transaction_id", "member_id"}))
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "approval_id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private WalletMember member;

    private Boolean isApproved;                 // null = pendiente

    private LocalDateTime decidedAt;
}
