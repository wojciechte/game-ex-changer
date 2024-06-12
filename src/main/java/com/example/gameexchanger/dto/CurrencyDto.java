package com.example.gameexchanger.dto;

import com.example.gameexchanger.enums.CountryCurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CurrencyDto {

    private CountryCurrencyCode code;
    private BigDecimal amount;
}
