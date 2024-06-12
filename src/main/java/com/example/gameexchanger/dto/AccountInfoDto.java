package com.example.gameexchanger.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AccountInfoDto {

    private String firstName;
    private String lastName;
    private String pesel;
    private Map<String, CurrencyDto> currencySubAccounts;
}
