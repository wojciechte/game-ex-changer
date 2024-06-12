package com.example.gameexchanger.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


@Configuration
public class RestClientConfig {

    @Value("${nbp.api.base.uri}")
    private String nbpApiBaseUri;

    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl(nbpApiBaseUri)
                .build();
    }
}