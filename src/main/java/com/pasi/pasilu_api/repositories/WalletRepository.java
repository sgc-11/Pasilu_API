package com.pasi.pasilu_api.repositories;

import com.pasi.pasilu_api.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    boolean existsByNameAndCreatedBy_Id(String name, UUID userId);
}