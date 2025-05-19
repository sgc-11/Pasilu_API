package com.pasi.pasilu_api.dtos.approvals;

import java.time.LocalDateTime;
import java.util.UUID;

public record ApprovalResponse(
        UUID          approvalId,
        UUID          transactionId,
        UUID memberId,
        Boolean       isApproved,
        LocalDateTime decidedAt
) {}
