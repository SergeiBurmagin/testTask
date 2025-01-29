package ru.test.api.model.dto.email;

import jakarta.validation.constraints.NotNull;


public record EmailUpdateRequest(
        @NotNull Long userId,
        @NotNull Email currentEmail,
        @NotNull Email newEmail
) {}
