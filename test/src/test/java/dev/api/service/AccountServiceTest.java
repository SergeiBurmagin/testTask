package dev.api.service;

import dev.api.ApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.test.api.model.dto.request.AccountTransferRequest;
import ru.test.api.model.entity.Account;
import ru.test.api.service.AccountService;
import ru.test.api.service.job.AddBalanceJob;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;


@AutoConfigureMockMvc
@ExtendWith(Extension.class)
public class AccountServiceTest extends ApplicationTests {


    @MockBean
    private AddBalanceJob addBalanceJob;


    @Autowired
    private AccountService accountService;

    @Test
    void transferWithValidRequestUpdatesBalances() {
        AccountTransferRequest request = new AccountTransferRequest(1L, 2L, new BigDecimal("100.00"));
        Account fromAccount = new Account(1L, new BigDecimal("500.00"));
        Account toAccount = new Account(2L, new BigDecimal("300.00"));

        when(accountService.findAccountBy(1L)).thenReturn(fromAccount);
        when(accountService.findAccountBy(2L)).thenReturn(toAccount);

        accountService.transfer(request);

        Assertions.assertEquals(new BigDecimal("400.00"), fromAccount.getBalance());
        Assertions.assertEquals(new BigDecimal("400.00"), toAccount.getBalance());
    }

    @Test
    void transferWithNonExistentFromAccountThrowsException() {
        AccountTransferRequest request = new AccountTransferRequest(1L, 2L, new BigDecimal("100.00"));

        when(accountService.findAccountBy(1L)).thenReturn(null);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            accountService.transfer(request);
        });

        Assertions.assertEquals("У пользователя не существует аккаунт", exception.getMessage());
    }

    @Test
    void transferWithNonExistentToAccountThrowsException() {
        AccountTransferRequest request = new AccountTransferRequest(1L, 2L, new BigDecimal("100.00"));
        Account fromAccount = new Account(1L, new BigDecimal("500.00"));

        when(accountService.findAccountBy(1L)).thenReturn(fromAccount);
        when(accountService.findAccountBy(2L)).thenReturn(null);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            accountService.transfer(request);
        });

        Assertions.assertEquals("У пользователя не существует аккаунт", exception.getMessage());
    }

    @Test
    void transferWithInsufficientBalanceThrowsException() {
        AccountTransferRequest request = new AccountTransferRequest(1L, 2L, new BigDecimal("600.00"));
        Account fromAccount = new Account(1L, new BigDecimal("500.00"));
        Account toAccount = new Account(2L, new BigDecimal("300.00"));

        when(accountService.findAccountBy(1L)).thenReturn(fromAccount);
        when(accountService.findAccountBy(2L)).thenReturn(toAccount);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            accountService.transfer(request);
        });

        Assertions.assertEquals("Insufficient balance", exception.getMessage());
    }

}
