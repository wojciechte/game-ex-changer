package com.example.gameexchanger.model;

import com.example.gameexchanger.enums.CountryCurrencyCode;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Currency {

    private CountryCurrencyCode code;
    private BigDecimal amount;
}
