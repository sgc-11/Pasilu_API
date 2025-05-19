package com.pasi.pasilu_api.repositories;

import com.pasi.pasilu_api.entities.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ApprovalRepository extends JpaRepository<Approval, UUID> {
    Optional<Approval> findByTransaction_IdAndMember_Id(UUID txId, UUID memberId);
    List<Approval> findAllByTransaction_Id(UUID txId);
}