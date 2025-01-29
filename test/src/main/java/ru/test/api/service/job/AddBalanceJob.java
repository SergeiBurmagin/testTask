package ru.test.api.service.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.test.api.model.entity.Account;
import ru.test.api.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class AddBalanceJob {

    private static final BigDecimal MAX_LIMIT = BigDecimal.valueOf(2.07);
    private static final BigDecimal PERCENT_BONUS = BigDecimal.valueOf(1.1);

    private final AccountRepository accountRepository;

    @Scheduled(fixedDelay = 30000)
    public void jobIncreasing() {

        List<Account> accounts = accountRepository.findAll();

        accounts.forEach(account -> {
                    BigDecimal maxBalance = account.getInitialBalance().multiply(MAX_LIMIT);
                    BigDecimal balance = account.getBalance().multiply(PERCENT_BONUS);
                    if (balance.compareTo(maxBalance) <= 0) account.setBalance(balance);
                }
        );

        accountRepository.saveAll(accounts);
    }
}