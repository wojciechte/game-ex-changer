package com.example.gameexchanger.converter;

import com.example.gameexchanger.dto.AccountInfoDto;
import com.example.gameexchanger.dto.AccountRegisterDto;
import com.example.gameexchanger.dto.CurrencyDto;
import com.example.gameexchanger.fixtures.AccountFixtures;
import com.example.gameexchanger.model.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.example.gameexchanger.enums.CountryCurrencyCode.PLN;
import static com.example.gameexchanger.enums.CountryCurrencyCode.USD;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountConverterTest implements AccountFixtures {

    @Test
    void shouldCorrectlyMapAccountRegisterDtoToAccount() {
        //given
        AccountRegisterDto accountRegisterDto = getAccountRegisterDto();

        //when
        Account convertedAccount = AccountConverter.from(accountRegisterDto);

        //then
        assertEquals(accountRegisterDto.getPesel(), convertedAccount.getPesel());
        assertEquals(accountRegisterDto.getFirstName(), convertedAccount.getFirstName());
        assertEquals(accountRegisterDto.getLastName(), convertedAccount.getLastName());
        BigDecimal currencyAmount = convertedAccount.getCurrencySubAccount().get(PLN.toString()).getAmount();
        assertEquals(accountRegisterDto.getInitialFunds(), currencyAmount);
    }

    @Test
    void shouldCorrectlyMapAccountInfoDtoToAccountInfoDto() {
        //given
        Account exampleAccount = getAccount();

        //when
        AccountInfoDto convertedInfo = AccountConverter.from(exampleAccount);

        //then
        assertEquals(exampleAccount.getPesel(), convertedInfo.getPesel());
        assertEquals(exampleAccount.getFirstName(), convertedInfo.getFirstName());
        assertEquals(exampleAccount.getLastName(), convertedInfo.getLastName());

        CurrencyDto currencyDto0 = convertedInfo.getCurrencySubAccounts().get(USD.toString());
        assertEquals(currencyDto0.getAmount(), exampleAccount.getCurrencySubAccount().get(USD.toString()).getAmount());
        assertEquals(currencyDto0.getCode(), exampleAccount.getCurrencySubAccount().get(USD.toString()).getCode());

        CurrencyDto currencyDto1 = convertedInfo.getCurrencySubAccounts().get(PLN.toString());
        assertEquals(currencyDto1.getAmount(), exampleAccount.getCurrencySubAccount().get(PLN.toString()).getAmount());
        assertEquals(currencyDto1.getCode(), exampleAccount.getCurrencySubAccount().get(PLN.toString()).getCode());
    }

}