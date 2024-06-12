package com.example.gameexchanger.converter;

import com.example.gameexchanger.dto.AccountInfoDto;
import com.example.gameexchanger.dto.AccountRegisterDto;
import com.example.gameexchanger.dto.CurrencyDto;
import com.example.gameexchanger.model.Account;
import com.example.gameexchanger.model.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.gameexchanger.enums.CountryCurrencyCode.PLN;
import static com.example.gameexchanger.enums.CountryCurrencyCode.USD;

public class AccountConverter {

    public static Account from(AccountRegisterDto registrationData) {
        Map<String, Currency> subAccount = new HashMap<>();
        Currency initialPlnCurrency = Currency.builder()
                .amount(registrationData.getInitialFunds())
                .code(PLN)
                .build();
        subAccount.put(PLN.toString(), initialPlnCurrency);
        Currency initialUsdCurrency = Currency.builder()
                .amount(BigDecimal.ZERO)
                .code(USD)
                .build();
        subAccount.put(USD.toString(), initialUsdCurrency);
        return Account.builder()
                .pesel(registrationData.getPesel())
                .firstName(registrationData.getFirstName())
                .lastName(registrationData.getLastName())
                .currencySubAccount(subAccount)
                .build();
    }

    public static AccountInfoDto from(Account account) {
        return AccountInfoDto.builder()
                .pesel(account.getPesel())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .currencySubAccounts(from(account.getCurrencySubAccount()))
                .build();
    }

    private static Map<String, CurrencyDto> from(Map<String, Currency> subAccount) {
        return subAccount.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> new CurrencyDto(e.getValue().getCode(), e.getValue().getAmount())
                ));
    }
}
