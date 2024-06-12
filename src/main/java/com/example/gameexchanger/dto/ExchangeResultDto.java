package com.example.gameexchanger.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExchangeResultDto {

    private String exchangedCurrency;
    private BigDecimal resultExchangeAmount;
    private BigDecimal exchangeRate;
}
