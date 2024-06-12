package com.example.gameexchanger.exceptions;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException() {
        super("Could not find Account with provided PESEL");
    }
}
