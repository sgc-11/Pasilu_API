package com.pasi.pasilu_api.services;

import com.pasi.pasilu_api.dtos.transactions.TransactionCreateRequest;
import com.pasi.pasilu_api.dtos.transactions.TransactionResponse;
import com.pasi.pasilu_api.entities.Transaction;
import com.pasi.pasilu_api.entities.Wallet;
import com.pasi.pasilu_api.entities.WalletMember;
import com.pasi.pasilu_api.exceptions.InsufficientBalanceException;
import com.pasi.pasilu_api.exceptions.TransactionNotFoundException;
import com.pasi.pasilu_api.exceptions.WalletMemberNotFoundException;
import com.pasi.pasilu_api.exceptions.WalletNotFoundException;
import com.pasi.pasilu_api.repositories.TransactionRepository;
import com.pasi.pasilu_api.repositories.WalletMemberRepository;
import com.pasi.pasilu_api.repositories.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository txRepo;
    private final WalletRepository walletRepo;
    private final WalletMemberRepository wmRepo;

    /* mapper */
    private TransactionResponse toResponse(Transaction t) {
        return new TransactionResponse(
                t.getId(),
                t.getWallet().getId(),
                t.getAmount(),
                t.getType(),
                t.getStatus(),
                t.getRequiredApprovals(),
                t.getMemberRequiring().getId(),
                t.getCreatedAt()
        );
    }

    @Transactional
    public TransactionResponse create(UUID walletId, TransactionCreateRequest dto) {

        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException(walletId.toString()));

        WalletMember member = wmRepo.findById(dto.memberId())
                .orElseThrow(() -> new WalletMemberNotFoundException(dto.memberId().toString()));

        Transaction tx = new Transaction();
        tx.setWallet(wallet);
        tx.setAmount(dto.amount());
        tx.setType(dto.type());
        tx.setMemberRequiring(member);

        try {
            Transaction saved = txRepo.save(tx);           // triggers calculan approvals
            return toResponse(saved);
        } catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause().getMessage().contains("Saldo insuficiente"))
                throw new InsufficientBalanceException(e.getMostSpecificCause().getMessage());
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> listByWallet(UUID walletId) {
        return txRepo.findAllByWallet_Id(walletId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public TransactionResponse find(UUID walletId, UUID txId) {
        Transaction tx = txRepo.findById(txId)
                .filter(t -> t.getWallet().getId().equals(walletId))
                .orElseThrow(() -> new TransactionNotFoundException(txId.toString()));
        return toResponse(tx);
    }
}
