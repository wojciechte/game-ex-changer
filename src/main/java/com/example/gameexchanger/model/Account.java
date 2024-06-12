package com.example.gameexchanger.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class Account {

    private String firstName;
    private String lastName;
    private String pesel;
    private Map<String, Currency> currencySubAccount;
}
