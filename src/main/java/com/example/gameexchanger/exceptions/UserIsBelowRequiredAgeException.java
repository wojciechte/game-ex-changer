package com.example.gameexchanger.exceptions;

public class UserIsBelowRequiredAgeException extends RuntimeException {

    public UserIsBelowRequiredAgeException() {
        super("The user must be above 18 years old to register.");
    }
}
