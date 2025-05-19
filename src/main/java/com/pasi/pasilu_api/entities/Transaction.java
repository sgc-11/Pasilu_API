package com.pasi.pasilu_api.entities;

import com.pasi.pasilu_api.entities.enums.TransactionStatus;
import com.pasi.pasilu_api.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id", nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    /* miembro que solicita la transacción */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_requiring", nullable = false)
    private WalletMember memberRequiring;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "transaction_type")
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "transaction_status")
    private TransactionStatus status = TransactionStatus.IN_PROGRESS;

    @Column(name = "required_approvals", nullable = false)
    private Integer requiredApprovals;
}

