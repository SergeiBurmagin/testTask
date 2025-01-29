package dev.api;

import dev.api.initializer.Postgres;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;


@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(initializers = {Postgres.Initializer.class})
@Transactional
public class ApplicationTests {

    @BeforeAll
    static void init() {
        Postgres.container.start();
    }

    @AfterAll
    static void afterAll() {
        Postgres.container.close();
    }
}
