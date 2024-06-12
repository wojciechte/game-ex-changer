package com.example.gameexchanger.service;

import com.example.gameexchanger.client.NbpApiClient;
import com.example.gameexchanger.dto.ExchangeParamsDto;
import com.example.gameexchanger.dto.ExchangeRateDto;
import com.example.gameexchanger.dto.ExchangeResultDto;
import com.example.gameexchanger.dto.RateDto;
import com.example.gameexchanger.exceptions.ExchangeSameCurrenciesException;
import com.example.gameexchanger.exceptions.InsufficientFundsException;
import com.example.gameexchanger.fixtures.AccountFixtures;
import com.example.gameexchanger.model.Account;
import com.example.gameexchanger.model.Currency;
import com.example.gameexchanger.repository.InMemoryAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.example.gameexchanger.enums.CountryCurrencyCode.PLN;
import static com.example.gameexchanger.enums.CountryCurrencyCode.USD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExchangeServiceTest implements AccountFixtures {

    @Mock
    NbpApiClient nbpApiClient;

    @Spy
    InMemoryAccountRepository inMemoryAccountRepository = new InMemoryAccountRepository();

    @InjectMocks
    ExchangeService service;

    @Test
    void shouldSuccessfullyExchangeFundsFromPLNToUSD() {
        //given
        Account account = getAccount();
        inMemoryAccountRepository.saveAccount(account);
        ExchangeParamsDto exchangeParamsDto = ExchangeParamsDto.builder()
                .currencyFrom(PLN.toString())
                .currencyTo(USD.toString())
                .amount(new BigDecimal(50))
                .accountPesel(account.getPesel())
                .build();
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto(
                USD.toString(),
                List.of(new RateDto(new BigDecimal("4.044293"))));

        when(nbpApiClient.getUsdCurrencyRate()).thenReturn(exchangeRateDto);

        //when
        ExchangeResultDto exchangeResultDto = service.exchangeAccountCurrency(exchangeParamsDto);

        //then
        assertEquals(exchangeResultDto.getResultExchangeAmount(), new BigDecimal("12.36"));
    }

    @Test
    void shouldSuccessfullyExchangeFundsFromUSDToPLN() {
        //given
        Account account = getAccount();
        inMemoryAccountRepository.saveAccount(account);
        ExchangeParamsDto exchangeParamsDto = ExchangeParamsDto.builder()
                .currencyFrom(USD.toString())
                .currencyTo(PLN.toString())
                .amount(new BigDecimal(50))
                .accountPesel(account.getPesel())
                .build();
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto(
                USD.toString(),
                List.of(new RateDto(new BigDecimal("4.044293"))));

        when(nbpApiClient.getUsdCurrencyRate()).thenReturn(exchangeRateDto);

        //when
        ExchangeResultDto exchangeResultDto = service.exchangeAccountCurrency(exchangeParamsDto);

        //then
        assertEquals(exchangeResultDto.getResultExchangeAmount(), new BigDecimal("202.21"));
    }

    @Test
    void shouldSuccessfullyUpdateAccountAfterExchange() {
        //given
        Account account = getAccount();
        inMemoryAccountRepository.saveAccount(account);
        ExchangeParamsDto exchangeParamsDto = ExchangeParamsDto.builder()
                .currencyFrom(USD.toString())
                .currencyTo(PLN.toString())
                .amount(new BigDecimal(50))
                .accountPesel(account.getPesel())
                .build();
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto(
                USD.toString(),
                List.of(new RateDto(new BigDecimal("4.044293"))));

        when(nbpApiClient.getUsdCurrencyRate()).thenReturn(exchangeRateDto);

        //when
        ExchangeResultDto exchangeResultDto = service.exchangeAccountCurrency(exchangeParamsDto);

        //then
        Account updatedAccount = inMemoryAccountRepository.findByPesel(account.getPesel());
        Map<String, Currency> currencySubAccount = updatedAccount.getCurrencySubAccount();
        BigDecimal USDAmount = currencySubAccount.get(USD.toString()).getAmount();
        BigDecimal PLNAmount = currencySubAccount.get(PLN.toString()).getAmount();

        assertEquals(USDAmount, new BigDecimal(200).subtract(new BigDecimal(50)));
        assertEquals(PLNAmount, new BigDecimal(500).add(exchangeResultDto.getResultExchangeAmount()));

    }

    @Test
    void shouldThrowExceptionWhenInsufficientFunds() {
        //given
        Account account = getAccount();
        inMemoryAccountRepository.saveAccount(account);
        ExchangeParamsDto exchangeParamsDto = ExchangeParamsDto.builder()
                .currencyFrom(USD.toString())
                .currencyTo(PLN.toString())
                .amount(new BigDecimal(5000))
                .accountPesel(account.getPesel())
                .build();

        //then
        assertThrows(InsufficientFundsException.class, () -> service.exchangeAccountCurrency(exchangeParamsDto));
    }

    @Test
    void shouldThrowExceptionWhenExchangeSameCurrencies() {
        //given
        Account account = getAccount();
        inMemoryAccountRepository.saveAccount(account);
        ExchangeParamsDto exchangeParamsDto = ExchangeParamsDto.builder()
                .currencyFrom(PLN.toString())
                .currencyTo(PLN.toString())
                .amount(new BigDecimal(5))
                .accountPesel(account.getPesel())
                .build();

        //then
        assertThrows(ExchangeSameCurrenciesException.class, () -> service.exchangeAccountCurrency(exchangeParamsDto));
    }

}