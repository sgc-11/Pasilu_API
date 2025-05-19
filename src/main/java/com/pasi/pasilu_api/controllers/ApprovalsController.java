package com.pasi.pasilu_api.controllers;

import com.pasi.pasilu_api.dtos.approvals.ApprovalDecisionRequest;
import com.pasi.pasilu_api.dtos.approvals.ApprovalResponse;
import com.pasi.pasilu_api.services.ApprovalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets/{walletId}/transactions/{txId}/approvals")
@RequiredArgsConstructor
public class ApprovalsController {

    private final ApprovalService apprService;

    @GetMapping
    public List<ApprovalResponse> list(@PathVariable UUID txId) {
        return apprService.list(txId);
    }

    @PutMapping("/{memberId}")
    public ApprovalResponse decide(@PathVariable UUID walletId,
                                   @PathVariable UUID txId,
                                   @PathVariable UUID memberId,
                                   @Valid @RequestBody ApprovalDecisionRequest body) {
        return apprService.decide(walletId, txId, memberId, body);
    }
}
