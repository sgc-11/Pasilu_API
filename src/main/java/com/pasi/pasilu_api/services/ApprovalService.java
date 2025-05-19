package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.dtos.approvals.ApprovalDecisionRequest;
import com.pasi.pasilu_api.dtos.approvals.ApprovalResponse;
import com.pasi.pasilu_api.entities.Approval;
import com.pasi.pasilu_api.entities.Transaction;
import com.pasi.pasilu_api.exceptions.ApprovalAlreadyDecidedException;
import com.pasi.pasilu_api.exceptions.ApprovalNotFoundException;
import com.pasi.pasilu_api.exceptions.TransactionNotFoundException;
import com.pasi.pasilu_api.repositories.ApprovalRepository;
import com.pasi.pasilu_api.repositories.TransactionRepository;
import com.pasi.pasilu_api.repositories.WalletMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApprovalService {

    private final ApprovalRepository apprRepo;
    private final TransactionRepository txRepo;
    private final WalletMemberRepository wmRepo;

    private ApprovalResponse toResponse(Approval a) {
        return new ApprovalResponse(
                a.getId(),
                a.getTransaction().getId(),
                a.getMember().getId(),
                a.getIsApproved(),
                a.getDecidedAt()
        );
    }

    @Transactional
    public ApprovalResponse decide(UUID walletId, UUID txId, UUID memberId,
                                   ApprovalDecisionRequest dto) {

        // asegurar que la transaccion pertenece a la wallet
        Transaction tx = txRepo.findById(txId)
                .filter(t -> t.getWallet().getId().equals(walletId))
                .orElseThrow(() -> new TransactionNotFoundException(txId.toString()));

        Approval appr = apprRepo.findByTransaction_IdAndMember_Id(txId, memberId)
                .orElseThrow(() -> new ApprovalNotFoundException(txId.toString(), memberId.toString()));

        if (appr.getIsApproved() != null)
            throw new ApprovalAlreadyDecidedException();

        appr.setIsApproved(dto.approved());
        appr.setDecidedAt(LocalDateTime.now());

        return toResponse(appr);      // flush al commit
    }

    @Transactional(readOnly = true)
    public List<ApprovalResponse> list(UUID txId) {
        return apprRepo.findAllByTransaction_Id(txId)
                .stream().map(this::toResponse).toList();
    }
}
