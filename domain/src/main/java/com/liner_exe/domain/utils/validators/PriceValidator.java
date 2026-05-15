package com.liner_exe.domain.utils.validators;

import java.util.Locale;

public class PriceValidator {
    public static boolean isValid(String value) {
        return value.matches("^\\d+(\\.d{2})?$");
    }

    public static double parse(String value) {
        value = value.trim().replace(",", ".");
        return Double.parseDouble(value);
    }

    public static String format(double value) {
        return String.format(Locale.US, "%s.2f", value);
    }
}
