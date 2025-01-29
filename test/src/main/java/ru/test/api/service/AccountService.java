package ru.test.api.service;


import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test.api.model.dto.request.AccountTransferRequest;
import ru.test.api.model.entity.Account;
import ru.test.api.repository.AccountRepository;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void transfer(@NonNull @Valid AccountTransferRequest request) {
        Account from = findAccountBy(request.fromUser());
        Account to = findAccountBy(request.toUser());
        from.subtractFromBalance(request.amount());

        to.addBalance(request.amount());

        accountRepository.save(from);
        accountRepository.save(to);
    }

    public Account findAccountBy(@NonNull Long userId) {
        return accountRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("У пользователя не существует аккаунт"));
    }

}
