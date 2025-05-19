package com.pasi.pasilu_api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@Entity
@Table(name = "goals",
        uniqueConstraints = @UniqueConstraint(name = "uk_goal_wallet", columnNames = "wallet_id"))
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "goal_id", nullable = false, updatable = false)
    private UUID id;

    /* --- Relación 1-a-1 inversa (Wallet es quien tiene la FK goal_id) --- */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @NotNull
    @Column(name = "goal_date", nullable = false)
    private LocalDate goalDate;

    @NotNull
    @Column(name = "target_amount", nullable = false)
    private Double targetAmount;

    private String description;

    @NotNull
    @Column(name = "is_achieved", nullable = false)
    private Boolean isAchieved = false;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
