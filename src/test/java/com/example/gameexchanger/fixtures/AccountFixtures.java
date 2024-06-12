package com.example.gameexchanger.fixtures;

import com.example.gameexchanger.dto.AccountRegisterDto;
import com.example.gameexchanger.enums.CountryCurrencyCode;
import com.example.gameexchanger.model.Account;
import com.example.gameexchanger.model.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.gameexchanger.enums.CountryCurrencyCode.PLN;
import static com.example.gameexchanger.enums.CountryCurrencyCode.USD;

public interface AccountFixtures {

    default AccountRegisterDto getAccountRegisterDto() {
        return AccountRegisterDto.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .pesel("71050273959")
                .initialFunds(new BigDecimal(100))
                .build();
    }

    default AccountRegisterDto getAccountRegisterDto(String pesel) {
        return AccountRegisterDto.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .pesel(pesel)
                .initialFunds(new BigDecimal(100))
                .build();
    }

    default Account getAccount() {
        Map<String, Currency> currencySubAccount = new HashMap<>();
        currencySubAccount.put(USD.toString(), Currency.builder().code(USD).amount(new BigDecimal(200)).build());
        currencySubAccount.put(PLN.toString(), Currency.builder().code(PLN).amount(new BigDecimal(500)).build());
        return Account.builder()
                .firstName("Jan")
                .lastName("Kowalski")
                .pesel("71050273959")
                .currencySubAccount(currencySubAccount)
                .build();

    }
}
