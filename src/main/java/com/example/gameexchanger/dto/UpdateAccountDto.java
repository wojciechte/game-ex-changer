package com.example.gameexchanger.dto;

import com.example.gameexchanger.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class UpdateAccountDto {

    private Account account;
    private String currencyCodeFrom;
    private String currencyCodeTo;
    private BigDecimal amount;
    private BigDecimal resultExchangeAmount;
}
