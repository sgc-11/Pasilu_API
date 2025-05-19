package com.pasi.pasilu_api.repositories;

import com.pasi.pasilu_api.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {

    boolean existsByWallet_Id(UUID walletId);

    Optional<Goal> findByWallet_Id(UUID walletId);
}