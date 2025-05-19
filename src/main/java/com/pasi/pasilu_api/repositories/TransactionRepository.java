package com.pasi.pasilu_api.repositories;

import com.pasi.pasilu_api.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByWallet_Id(UUID walletId);
}