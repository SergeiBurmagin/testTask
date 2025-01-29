package ru.test.api.model.dto.request;

import ru.test.api.model.dto.email.Email;
import ru.test.api.model.dto.phone.Phone;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


public record UserSearchRequest(
        LocalDate dateOfBirth,
        Phone phone,
        String name,
        Email email
) {
    public UserSearchRequest(String name, String email) {
        this(null, null, name, email == null ? null : new Email(email));
    }
}