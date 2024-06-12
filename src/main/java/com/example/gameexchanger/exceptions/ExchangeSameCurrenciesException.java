package com.example.gameexchanger.exceptions;

public class ExchangeSameCurrenciesException extends RuntimeException {
    public ExchangeSameCurrenciesException() {
        super("Could not exchange to same currency");
    }
}
