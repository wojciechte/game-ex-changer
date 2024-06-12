package com.example.gameexchanger.exceptions;

public class InvalidPeselNumberException extends RuntimeException {
    public InvalidPeselNumberException() {
        super("Given PESEL is invalid");
    }
}
