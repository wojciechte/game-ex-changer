package com.example.gameexchanger.client;

import com.example.gameexchanger.dto.ExchangeRateDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NbpApiClient {

    private final RestClient restClient;

    @Value("${nbp.api.exchange.uri.usd}")
    private String usdExchangeRatesUri;

    public ExchangeRateDto getUsdCurrencyRate() {
        return restClient.get()
                .uri(usdExchangeRatesUri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ExchangeRateDto.class);
    }

}
