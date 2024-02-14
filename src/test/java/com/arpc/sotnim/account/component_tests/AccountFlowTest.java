package com.arpc.sotnim.account.component_tests;

import com.arpc.sotnim.ComponentTest;
import com.arpc.sotnim.account.component_tests.endpoints.AccountEndpoint;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountFlowTest extends ComponentTest {

    @Test
    public void shouldCreateAccountWhenValidRequest() {
        var clientId = "5";
        assertThat(accountSystem.getAccounts(clientId).getResponseData()).isEmpty();

        var addedAccount = accountSystem.createAccount(clientId, Map.of(
                "name", "John Doe",
                "currency", "USD")
        )
                .getResponseData();

        assertThat(addedAccount.accountId()).containsOnlyDigits();
        assertThat(addedAccount.createdAt()).matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d+Z");

        List<AccountEndpoint.AccountDto> accountList = accountSystem.getAccounts(clientId).getResponseData();

        assertThat(accountList).hasSize(1);
        var accountWithBalance = accountList.stream().findFirst().orElseThrow();
        assertThat(accountWithBalance.accountId()).isEqualTo(addedAccount.accountId());
        assertThat(accountWithBalance.balance().amount()).isEqualTo("0");
    }

}
