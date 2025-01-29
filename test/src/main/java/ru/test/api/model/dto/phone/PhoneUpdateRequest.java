package ru.test.api.model.dto.phone;


import jakarta.validation.constraints.NotNull;

public record PhoneUpdateRequest(
        @NotNull Long userId,
        @NotNull Phone currentPhoneNumber,
        @NotNull Phone newPhoneNumber
) {
}
