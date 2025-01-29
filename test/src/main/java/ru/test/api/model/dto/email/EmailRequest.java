package ru.test.api.model.dto.email;

import jakarta.validation.constraints.NotNull;


public record EmailRequest(@NotNull Long userId, @NotNull Email email) {
}
