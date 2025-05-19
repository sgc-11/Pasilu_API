package com.pasi.pasilu_api.dtos.goals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record GoalResponse(
        UUID id,
        UUID          walletId,
        LocalDate goalDate,
        Double        targetAmount,
        Boolean       isAchieved,
        String        description,
        LocalDateTime updatedAt
) {}