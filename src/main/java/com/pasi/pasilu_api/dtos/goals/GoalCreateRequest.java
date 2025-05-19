package com.pasi.pasilu_api.dtos.goals;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GoalCreateRequest(
        @NotNull LocalDate goalDate, // YYYY-MM-DD
        @NotNull Double    targetAmount,
        String             description
) {}
