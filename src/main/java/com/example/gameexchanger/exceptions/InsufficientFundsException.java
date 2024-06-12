package com.example.gameexchanger.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("Insufficient funds to make exchange");
    }
}
