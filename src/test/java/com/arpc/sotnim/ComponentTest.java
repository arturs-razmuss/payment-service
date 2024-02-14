package com.arpc.sotnim;

import com.arpc.sotnim.account.component_tests.endpoints.AccountEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.arpc.sotnim.TestPaymentApplication.POSTGRES_DOCKER_IMAGE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Configuration
public abstract class ComponentTest {

    @ServiceConnection
    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_DOCKER_IMAGE).withReuse(true);

    static {
        postgreSQLContainer.start();
    }

    @Autowired
    protected AccountEndpoint accountSystem;

}

