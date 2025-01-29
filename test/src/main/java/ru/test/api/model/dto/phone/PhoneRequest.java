package ru.test.api.model.dto.phone;


import jakarta.validation.constraints.NotNull;



public record PhoneRequest(@NotNull Long userId, @NotNull Phone phone) {
}
