package com.arpc.sotnim;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.arpc.sotnim.TestPaymentApplication.POSTGRES_DOCKER_IMAGE;

@SpringBootTest
@Testcontainers
public class ComponentTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_DOCKER_IMAGE);

}

