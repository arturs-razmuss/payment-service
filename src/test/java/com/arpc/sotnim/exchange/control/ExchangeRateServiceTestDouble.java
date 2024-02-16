package com.arpc.sotnim.exchange.control;

import com.arpc.sotnim.currency.ExchangeRateHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.money.CurrencyUnit;
import javax.money.convert.ExchangeRate;
import java.math.BigDecimal;
import java.util.Optional;


@Configuration
public class ExchangeRateServiceTestDouble extends ExchangeService {

    private BigDecimal rate = new BigDecimal("2");
    public void setRate(String rate) {
        this.rate = new BigDecimal(rate);
    }
    @Override
    public Optional<ExchangeRate> getExchangeRate(CurrencyUnit sourceCurrency, CurrencyUnit targetCurrency) {
        return Optional.of(ExchangeRateHelper.getExchangeRate(sourceCurrency.getCurrencyCode(), sourceCurrency.getCurrencyCode(), rate));
    }

    @Bean
    @Primary
    public ExchangeService exchangeService() {
        return new ExchangeRateServiceTestDouble();
    }
}
