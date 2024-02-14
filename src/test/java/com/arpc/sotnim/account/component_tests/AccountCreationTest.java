package com.arpc.sotnim.account.component_tests;

import com.arpc.sotnim.ComponentTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountCreationTest extends ComponentTest {

    @Test
    public void shouldCreateAccountWhenValidResponse() {
        var invocationResult = accountSystem.createAccount(5L, Map.of(
                "name", "John Doe",
                "currency", "USD")
        );
        var account = invocationResult.getBody();
        assertThat(account.accountId()).containsOnlyDigits();
        assertThat(account.createdAt()).matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d+Z");
    }

}
