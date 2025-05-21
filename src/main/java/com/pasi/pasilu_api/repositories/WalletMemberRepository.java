package com.pasi.pasilu_api.repositories;

import com.pasi.pasilu_api.entities.Wallet;
import com.pasi.pasilu_api.entities.WalletMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletMemberRepository extends JpaRepository<WalletMember, UUID> {

    /* wallets donde el usuario es miembro */
    @Query("SELECT wm.wallet FROM WalletMember wm WHERE wm.user.id = :uid")
    List<Wallet> findWalletsByUserId(@Param("uid") UUID uid);

    /* ---------- MÉTODOS QUE FALTABAN ---------- */
    List<WalletMember> findAllByWallet_Id(UUID walletId);

    Optional<WalletMember> findByIdAndWallet_Id(UUID memberId, UUID walletId);

    /* ---------- PERMISOS ---------- */
    boolean existsByWallet_IdAndUser_IdAndRole_Name(UUID walletId, UUID userId, String roleName);

    boolean existsByWallet_IdAndUser_Id(UUID walletId, UUID userId);
}
