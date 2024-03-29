package com.arpc.sotnim;

import com.arpc.sotnim.account.component_tests.endpoints.AccountEndpoint;
import com.arpc.sotnim.account.component_tests.endpoints.PaymentEndpoint;
import com.arpc.sotnim.exchange.control.ExchangeRateServiceTestDouble;
import com.arpc.sotnim.exchange.control.ExchangeService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.arpc.sotnim.TestPaymentApplication.POSTGRES_DOCKER_IMAGE;

/**
 * Component test as defined by <a href="https://martinfowler.com/articles/microservice-testing/#testing-component-in-process-diagram">Microservice testing article</a>
 * Use for all tests if possible to take advantage of Spring context reuse
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@Import({ExchangeRateServiceTestDouble.class, AccountEndpoint.class, PaymentEndpoint.class})
@NoArgsConstructor
public abstract class ComponentTest {

    @ServiceConnection
    static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_DOCKER_IMAGE);

    static {
        postgreSQLContainer.start();
    }

    public ComponentTest(ExchangeService exchangeServiceTestDouble) {
        this.exchangeServiceTestDouble = (ExchangeRateServiceTestDouble) exchangeServiceTestDouble;
    }

    @Autowired
    protected AccountEndpoint accountSystem;

    @Autowired
    protected ExchangeRateServiceTestDouble exchangeServiceTestDouble;


}

