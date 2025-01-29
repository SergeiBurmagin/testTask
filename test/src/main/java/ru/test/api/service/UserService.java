package ru.test.api.service;

import ru.test.api.model.dto.email.Email;
import ru.test.api.model.dto.email.EmailRequest;
import ru.test.api.model.dto.email.EmailUpdateRequest;
import ru.test.api.model.dto.phone.PhoneRequest;
import ru.test.api.model.dto.phone.PhoneUpdateRequest;
import ru.test.api.model.dto.phone.Phone;
import ru.test.api.model.dto.request.UserSearchRequest;
import ru.test.api.model.dto.respomse.UserResponse;
import ru.test.api.model.entity.User;
import ru.test.api.model.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.test.api.model.querydsl.QPredicates;
import ru.test.api.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void addUserEmail(@NonNull @Valid EmailRequest request) {
        User user = findById(request.userId());
        user.addEmail(request.email());

        userRepository.save(user);
    }

    public void removeUserEmail(@NonNull @Valid EmailRequest request) {
        User user = findById(request.userId());
        user.removeEmail(request.email());

        userRepository.save(user);
    }

    public void updateUserEmail(@NonNull @Valid EmailUpdateRequest request) {
        Email currentEmail = request.currentEmail();
        Email newEmail = request.newEmail();

        if (currentEmail.equals(newEmail) || userRepository.existsByEmailsIsContaining(newEmail)) {
            log.warn("*** E-mail {} уже используется пользователем", request.newEmail());
            throw new RuntimeException("*** Ошибка!!! Отказано в изменении почты.");
        }

        User user = findById(request.userId());
        user.updateEmail(currentEmail, newEmail);

        this.userRepository.save(user);
    }

    // Phone methods

    public void addUserPhone(@NonNull @Valid PhoneRequest request) {
        User user = findById(request.userId());
        user.addPhone(request.phone());

        userRepository.save(user);
    }

    public void removeUserPhone(@NonNull @Valid PhoneRequest request) {
        User user = findById(request.userId());
        user.removePhone(request.phone());

        userRepository.save(user);
    }

    public void updateUserPhone(@NonNull @Valid PhoneUpdateRequest request) {
        Phone currentPhoneNumber = request.currentPhoneNumber();
        Phone newPhoneNumber = request.newPhoneNumber();

        if (currentPhoneNumber.equals(newPhoneNumber) || userRepository.existsByPhonesIsContaining(Collections.singleton(newPhoneNumber))) {
            log.warn("*** Номер {} уже используется пользователем", request.newPhoneNumber());
            throw new RuntimeException("*** ОШИБКА!!! Отаказано в изменении номера.");
        }

        User user = findById(request.userId());
        user.updatePhone(currentPhoneNumber, newPhoneNumber);

        this.userRepository.save(user);
    }

    // Other methods

    public User findById(@NonNull Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("*** Пользователь не найден"));
    }

    public List<UserResponse> search(UserSearchRequest request, Integer page, Integer pageSize) {
        var predicate = QPredicates.builder()
                .add(request.name(), QUser.user.name::startsWith)
                .add(request.dateOfBirth(), QUser.user.dateOfBirth::after)
                .add(request.phone().phone(), QUser.user.phones.any().phone::eq)
                .add(request.email().email(), QUser.user.emails.any().email::eq)
                .buildOr();

        return userRepository
                .findAll((Predicate) predicate,
                        PageRequest.of(
                                Optional.ofNullable(page).orElse(0),
                                Optional.ofNullable(pageSize).orElse(10)
                        )
                )
                .getContent()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public User findByEmail(Email email) {
        return userRepository.findByEmailsContaining(email)
                .orElseThrow(() -> new UsernameNotFoundException("*** Ошибка!!! Пользователь с e-mail не найден."));
    }
}