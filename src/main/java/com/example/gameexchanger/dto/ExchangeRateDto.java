package com.example.gameexchanger.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {

    private String code;
    private List<RateDto> rates;
}
