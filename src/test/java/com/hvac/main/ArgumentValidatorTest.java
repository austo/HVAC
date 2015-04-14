package com.hvac.main;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ArgumentValidatorTest {

    @Test()
    public void ShouldReturnAbsentIfMapDoesNotContainDesiredKeys() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("unbound", "");
        assertTrue(ArgumentValidator.validate(arguments) ==
                ArgumentValidationState.ABSENT);
    }

    @Test
    public void ShouldReturnPresentIfBothDesiredKeysArePresentAndValid() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("unbound", "");
        arguments.put("min", "65");
        arguments.put("max", "75");
        assertTrue(ArgumentValidator.validate(arguments) ==
                ArgumentValidationState.PRESENT);
    }

    @Test
    public void ShouldReturnInvalidIfMapContainsInvalidDesiredKeys() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("unbound", "");
        arguments.put("min", "aardvark");
        assertTrue(ArgumentValidator.validate(arguments) ==
                ArgumentValidationState.INVALID);
    }

    @Test
    public void ShouldReturnInvalidIfMaxIsLessThanMin() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("unbound", "");
        arguments.put("min", "70");
        arguments.put("max", "65");
        assertTrue(ArgumentValidator.validate(arguments) ==
                ArgumentValidationState.INVALID);
    }

    @Test
    public void ShouldReturnInvalidIfOnlyOneDesiredKeyIsPresent() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("unbound", "");
        arguments.put("min", "65");
        assertTrue(ArgumentValidator.validate(arguments) ==
                ArgumentValidationState.INVALID);
    }

}