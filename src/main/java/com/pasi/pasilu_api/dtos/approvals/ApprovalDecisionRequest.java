package com.pasi.pasilu_api.dtos.approvals;

import jakarta.validation.constraints.NotNull;

public record ApprovalDecisionRequest(
        @NotNull Boolean approved        // true = aprueba, false = rechaza
) {}
