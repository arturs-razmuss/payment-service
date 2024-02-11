package com.arpc.sotnim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestPaymentApplication {

    public static final String POSTGRES_DOCKER_IMAGE = "postgres:16";
    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_DOCKER_IMAGE));
    }

    public static void main(String[] args) {
        SpringApplication.from(PaymentServiceApplication::main).with(TestPaymentApplication.class).run(args);
    }

}
