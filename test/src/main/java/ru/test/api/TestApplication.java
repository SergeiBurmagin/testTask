package ru.test.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.UnknownHostException;

@EnableScheduling
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) throws UnknownHostException {
        new SpringApplicationBuilder(TestApplication.class)
                .listeners(new ApplicationPidFileWriter("pid"))
                .run();
    }

}
