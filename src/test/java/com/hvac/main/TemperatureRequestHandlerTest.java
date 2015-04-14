package com.hvac.main;

import com.hvac.network.InputHandler;
import org.junit.Test;

import static org.junit.Assert.*;

public class TemperatureRequestHandlerTest {

    @Test
    public void ShouldReturnEmptyStringIfInputIsBlank() {
        InputHandler handler = new TemperatureRequestHandler();
        assertStringEquals("", handler.handle(""));
    }

    @Test
    public void ShouldReturnEmptyStringIfInputIsNull() {
        InputHandler handler = new TemperatureRequestHandler();
        assertStringEquals("", handler.handle(null));
    }

    void assertStringEquals(String expected, String actual) {
        assertTrue(expected.equals(actual));
    }

}