package ru.test.api.model.dto.respomse;

import java.time.LocalDate;
import java.util.Set;

import ru.test.api.model.dto.email.Email;
import ru.test.api.model.dto.phone.Phone;

public record UserResponse(
        Long id,
        String name,
        LocalDate dateOfBirth,
        Set<Email> emails,
        Set<Phone> phones
) {}