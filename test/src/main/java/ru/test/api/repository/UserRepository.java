package ru.test.api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.test.api.model.dto.email.Email;
import ru.test.api.model.dto.phone.Phone;
import ru.test.api.model.entity.User;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;


public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    Page<User> findAll(Predicate predicate, Pageable pageable);

    Optional<User> findByEmailsContaining(Email email);

    boolean existsByPhonesIsContaining(Set<Phone> phones);

    boolean existsByEmailsIsContaining(Email email);

}
