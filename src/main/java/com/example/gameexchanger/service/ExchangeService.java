package com.example.gameexchanger.service;

import com.example.gameexchanger.client.NbpApiClient;
import com.example.gameexchanger.dto.ExchangeParamsDto;
import com.example.gameexchanger.dto.ExchangeRateDto;
import com.example.gameexchanger.dto.ExchangeResultDto;
import com.example.gameexchanger.dto.UpdateAccountDto;
import com.example.gameexchanger.enums.CountryCurrencyCode;
import com.example.gameexchanger.exceptions.AccountNotFoundException;
import com.example.gameexchanger.exceptions.ExchangeSameCurrenciesException;
import com.example.gameexchanger.exceptions.InsufficientFundsException;
import com.example.gameexchanger.model.Account;
import com.example.gameexchanger.model.Currency;
import com.example.gameexchanger.repository.AccountRepository;
import com.example.gameexchanger.util.PeselUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import static com.example.gameexchanger.enums.CountryCurrencyCode.PLN;

@Service
@RequiredArgsConstructor
public class ExchangeService implements IExchangeService {

    private final AccountRepository accountRepository;
    private final NbpApiClient nbpApiClient;

    @Override
    public ExchangeResultDto exchangeAccountCurrency(ExchangeParamsDto exchangeParameters) {
        if (exchangeParameters.getCurrencyFrom().equals(exchangeParameters.getCurrencyTo())) {
            throw new ExchangeSameCurrenciesException();
        }
        Account account = retriveAccount(exchangeParameters.getAccountPesel());
        return exchangeByGivenParams(exchangeParameters, account);
    }

    private Account retriveAccount(String pesel) {
        PeselUtils.assertIsPeselValid(pesel);
        Account account = accountRepository.findByPesel(pesel);
        if (account == null) {
            throw new AccountNotFoundException();
        }
        return account;
    }

    private ExchangeResultDto exchangeByGivenParams(ExchangeParamsDto exchangeParameters, Account account) {
        BigDecimal fundsForExchange = account.getCurrencySubAccount()
                .get(exchangeParameters.getCurrencyFrom()).getAmount();
        verifyPossibilityToExchange(fundsForExchange, exchangeParameters.getAmount());

        ExchangeRateDto exchangeRateDto = nbpApiClient.getUsdCurrencyRate();

        ExchangeResultDto exchangeResultDto = calculateResultAmount(exchangeParameters, exchangeRateDto);

        updateAccount(new UpdateAccountDto(
                account,
                exchangeParameters.getCurrencyFrom(),
                exchangeParameters.getCurrencyTo(),
                exchangeParameters.getAmount(),
                exchangeResultDto.getResultExchangeAmount()
        ));

        return exchangeResultDto;
    }

    private void updateAccount(UpdateAccountDto updateAccountDto) {
        Map<String, Currency> currencySubAccount = updateAccountDto.getAccount().getCurrencySubAccount();
        Currency currencyFrom = currencySubAccount.get(updateAccountDto.getCurrencyCodeFrom());

        BigDecimal originalAmountFrom = currencyFrom.getAmount();
        BigDecimal subtractedAmount = originalAmountFrom.subtract(updateAccountDto.getAmount());

        Currency updatedCurrencyFrom = Currency.builder()
                .amount(subtractedAmount)
                .code(CountryCurrencyCode.fromString(updateAccountDto.getCurrencyCodeFrom()))
                .build();
        currencySubAccount.put(updateAccountDto.getCurrencyCodeFrom(), updatedCurrencyFrom);

        BigDecimal originalAmountTo = currencySubAccount.get(updateAccountDto.getCurrencyCodeTo()).getAmount();
        BigDecimal addedAmount = originalAmountTo.add(updateAccountDto.getResultExchangeAmount());

        Currency updatedCurrencyTo = Currency.builder()
                .code(CountryCurrencyCode.fromString(updateAccountDto.getCurrencyCodeTo()))
                .amount(addedAmount)
                .build();
        currencySubAccount.put(updateAccountDto.getCurrencyCodeTo(), updatedCurrencyTo);

        accountRepository.saveAccount(updateAccountDto.getAccount());
    }

    private void verifyPossibilityToExchange(BigDecimal fundsToExchange, BigDecimal exchangeAmount) {
        if (fundsToExchange.compareTo(exchangeAmount) < 0) {
            throw new InsufficientFundsException();
        }
    }

    private ExchangeResultDto calculateResultAmount(ExchangeParamsDto exchangeParameters, ExchangeRateDto exchangeRateDto) {
        BigDecimal usdExchangeRate = exchangeRateDto.getRates().get(0).getMid();

        BigDecimal exchangeRate;
        if (exchangeParameters.getCurrencyFrom().equals(PLN.toString())) {
            exchangeRate = calculateInverseRate(usdExchangeRate);
        } else {
            exchangeRate = usdExchangeRate;
        }

        BigDecimal amountToExchange = exchangeParameters.getAmount();
        BigDecimal resultAmount = amountToExchange.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);

        return ExchangeResultDto.builder()
                .resultExchangeAmount(resultAmount)
                .exchangedCurrency("From: " + exchangeParameters.getCurrencyFrom() + " to: " + exchangeParameters.getCurrencyTo())
                .exchangeRate(exchangeRate)
                .build();
    }

    private BigDecimal calculateInverseRate(BigDecimal usdExchangeRate) {
        return BigDecimal.ONE.divide(usdExchangeRate, 6, RoundingMode.HALF_UP);
    }

}
