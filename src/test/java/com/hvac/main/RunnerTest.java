package com.hvac.main;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class RunnerTest {

    @Test()
    public void ShouldReturnAbsentIfMapDoesNotContainDesiredKeys() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("unbound", "");
        assertTrue(Runner.validate(arguments) ==
                Runner.ArgumentValidationState.ABSENT);
    }

    @Test
    public void ShouldReturnPresentIfBothDesiredKeysArePresentAndValid() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("unbound", "");
        arguments.put("min", "65");
        arguments.put("max", "75");
        assertTrue(Runner.validate(arguments) ==
                Runner.ArgumentValidationState.PRESENT);
    }

    @Test
    public void ShouldReturnInvalidIfMapContainsInvalidDesiredKeys() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("unbound", "");
        arguments.put("min", "aardvark");
        assertTrue(Runner.validate(arguments) ==
                Runner.ArgumentValidationState.INVALID);
    }

    @Test
    public void ShouldReturnInvalidIfMaxIsLessThanMin() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("unbound", "");
        arguments.put("min", "70");
        arguments.put("max", "65");
        assertTrue(Runner.validate(arguments) ==
                Runner.ArgumentValidationState.INVALID);
    }

    @Test
    public void ShouldReturnInvalidIfOnlyOneDesiredKeyIsPresent() {
        Map<String, String> arguments = new HashMap<>();
        arguments.put("unbound", "");
        arguments.put("min", "65");
        assertTrue(Runner.validate(arguments) ==
                Runner.ArgumentValidationState.INVALID);
    }
}