package com.example.gameexchanger.service;

import com.example.gameexchanger.dto.ExchangeParamsDto;
import com.example.gameexchanger.dto.ExchangeResultDto;

public interface IExchangeService {

    ExchangeResultDto exchangeAccountCurrency(ExchangeParamsDto params);
}
