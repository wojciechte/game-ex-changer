package com.example.gameexchanger.api;

import com.example.gameexchanger.dto.ExchangeParamsDto;
import com.example.gameexchanger.dto.ExchangeResultDto;
import com.example.gameexchanger.service.ExchangeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("exchange")
@AllArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<ExchangeResultDto> exchangeCurrency(@RequestBody ExchangeParamsDto params) {
        ExchangeResultDto exchangeResultDto = exchangeService.exchangeAccountCurrency(params);
        return ResponseEntity.ok(exchangeResultDto);
    }
}
