package com.pasi.pasilu_api.repositories;

import com.pasi.pasilu_api.entities.WalletMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletMemberRepository extends JpaRepository<WalletMember, UUID> {

    boolean existsByWallet_IdAndUser_Id(UUID walletId, UUID userId);

    List<WalletMember> findAllByWallet_Id(UUID walletId);

    Optional<WalletMember> findByIdAndWallet_Id(UUID memberId, UUID walletId);
}
