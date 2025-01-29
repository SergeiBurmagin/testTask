package ru.test.api.repository;


import java.util.List;
import java.util.Optional;

import ru.test.api.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;


public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findByUserId(Long userId);


    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Override
    List<Account> findAll();
}
