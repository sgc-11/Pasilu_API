package com.pasi.pasilu_api.dtos.goals;

import java.time.LocalDate;

public record GoalUpdateRequest(
        LocalDate goalDate,
        Double    targetAmount,
        String    description
) {}