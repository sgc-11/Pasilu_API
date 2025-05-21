package com.pasi.pasilu_api.repositories;

import com.pasi.pasilu_api.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    List<Wallet> findByCreatedBy_Id(UUID userId);

    @Query("""
      SELECT DISTINCT w
      FROM Wallet w
      WHERE w.createdBy.id = :uid
         OR w.id IN (SELECT wm.wallet.id FROM WalletMember wm WHERE wm.user.id = :uid)
    """)
    List<Wallet> findAllParticipating(@Param("uid") UUID uid);

    boolean existsByNameAndCreatedBy_Id(String name, UUID id);

    boolean existsByIdAndCreatedBy_Id(UUID walletId, UUID uid);
}