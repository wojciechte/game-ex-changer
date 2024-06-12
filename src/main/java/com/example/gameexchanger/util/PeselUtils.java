package com.example.gameexchanger.util;

import com.example.gameexchanger.exceptions.InvalidPeselNumberException;

import java.time.DateTimeException;
import java.time.LocalDate;


// Code taken form parts of this repo https://github.com/viepovsky/PESEL
public class PeselUtils {

    private static final int[] CONTROL_WEIGHTS = new int[]{1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1};

    public static LocalDate decodeBirthDate(String pesel) {
        int year = 1900;
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        int monthFirstDigit = Character.getNumericValue(pesel.charAt(2));
        switch (monthFirstDigit) {
            case 8, 9 -> {
                month -= 80;
                year = 1800;
            }
            case 2, 3 -> {
                month -= 20;
                year = 2000;
            }
            case 4, 5 -> {
                month -= 40;
                year = 2100;
            }
            case 6, 7 -> {
                month -= 60;
                year = 2200;
            }
        }

        year += Integer.parseInt(pesel.substring(0, 2));
        return LocalDate.of(year, month, day);
    }

    public static void assertIsPeselValid(String pesel) {
        assertIsNotNull(pesel);
        assertIsLengthValid(pesel);
        assertIsOnlyDigits(pesel);
        assertIsControlDigitValid(pesel);
        assertIsBirthDateValid(pesel);
    }

    protected static void assertIsNotNull(String pesel) {
        if (pesel == null) {
            throw new InvalidPeselNumberException();
        }
    }

    protected static void assertIsLengthValid(String pesel) {
        if (pesel.length() != 11) {
            throw new InvalidPeselNumberException();
        }
    }

    protected static void assertIsOnlyDigits(String pesel) {
        if (!pesel.matches("[0-9]*")) {
            throw new InvalidPeselNumberException();
        }
    }

    protected static void assertIsControlDigitValid(String pesel) {
        if (!isControlDigitValid(pesel)) {
            throw new InvalidPeselNumberException();
        }
    }

    protected static boolean isControlDigitValid(String pesel) {
        int sum = 0;
        for (int i = 0; i <= 10; i++) {
            int multipliedNumber = CONTROL_WEIGHTS[i] * Character.getNumericValue(pesel.charAt(i));
            sum += multipliedNumber;
        }
        String controlSum = String.valueOf(sum);
        int lastDigit = Integer.parseInt(controlSum.substring(controlSum.length() - 1));
        return lastDigit == 0;
    }


    protected static void assertIsBirthDateValid(String pesel) {
        if (!isBirthDateValid(pesel)) {
            throw new InvalidPeselNumberException();
        }
    }

    protected static boolean isBirthDateValid(String pesel) {
        try {
            decodeBirthDate(pesel);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
