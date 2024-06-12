package com.example.gameexchanger.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeParamsDto {

    private String accountPesel;
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal amount;

}
