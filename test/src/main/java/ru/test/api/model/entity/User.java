package ru.test.api.model.entity;

import ru.test.api.model.dto.email.Email;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import ru.test.api.model.dto.phone.Phone;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Builder
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String name;

    @ToString.Include
    @EqualsAndHashCode.Include
    private LocalDate dateOfBirth;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "email_data", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Email> emails;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "R", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Phone> phones;

    private String password;



    public void addEmail(@NonNull Email emailAddress) { this.emails.add(emailAddress); }
    public void addPhone(@NonNull Phone phoneNumber) { this.phones.add(phoneNumber); }

    public boolean removeEmail(@NonNull Email email) { return this.emails.remove(email);}
    public boolean removePhone(@NonNull Phone phone) { return this.phones.remove(phone);}

    public void updateEmail(@NonNull Email currentEmail, @NonNull Email newEmail) {
        if (removeEmail(currentEmail)) addEmail(newEmail);
    }
    public void updatePhone(@NonNull Phone currentPhone, @NonNull Phone newPhone) {
        if (removePhone(currentPhone)) addPhone(newPhone);
    }

}
