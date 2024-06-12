package com.example.gameexchanger.enums;

public enum CountryCurrencyCode {
    PLN("PLN"),
    USD("USD");

    private final String value;

    CountryCurrencyCode(String value) {
        this.value = value;
    }

    public static CountryCurrencyCode fromString(String text) {
        for (CountryCurrencyCode b : CountryCurrencyCode.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
