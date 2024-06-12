package com.example.gameexchanger.exceptions;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException() {
        super("Account with same PESEL already exists.");
    }
}
