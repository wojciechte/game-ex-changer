package com.example.gameexchanger.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountRegisterDto {

    private String firstName;
    private String lastName;
    private String pesel;
    private BigDecimal initialFunds;
}