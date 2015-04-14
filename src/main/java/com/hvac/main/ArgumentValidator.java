package com.hvac.main;

import java.util.Map;

public class ArgumentValidator {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static ArgumentValidationState validate(Map<String, String> arguments) {
        if (arguments.size() == 0) {
            return ArgumentValidationState.ABSENT;
        }
        String minStr = arguments.get("min");
        String maxStr = arguments.get("max");

        if (minStr == null && maxStr == null) {
            return ArgumentValidationState.ABSENT;
        }

        try {
            int min = Integer.parseInt(minStr);
            int max = Integer.parseInt(maxStr);

            if (min >= max) {
                throw new RuntimeException(
                        "Min cannot be greater than or equal to max");
            }
        } catch (RuntimeException e) {
            return ArgumentValidationState.INVALID;
        }
        return ArgumentValidationState.PRESENT;
    }
}
