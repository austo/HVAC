package com.hvac.main;

import com.hvac.EnvironmentControllerSpy;
import com.hvac.network.InputHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TemperatureRequestHandlerTest {

    @Test
    public void ShouldReturnEmptyStringIfInputIsBlank() {
        InputHandler handler = new TemperatureRequestHandler(new EnvironmentControllerSpy());
        assertStringEquals("", handler.handle(""));
    }

    @Test
    public void ShouldReturnEmptyStringIfInputIsNull() {
        InputHandler handler = new TemperatureRequestHandler(new EnvironmentControllerSpy());
        assertStringEquals("", handler.handle(null));
    }

    @Test
    public void ShouldReturnUpdateResponseIfInputIsValid() {
        EnvironmentControllerSpy spy = new EnvironmentControllerSpy();
        InputHandler handler = new TemperatureRequestHandler(spy);

        int newMinTemp = 63, newMaxTemp = 77;

        assertStringEquals(
                TemperatureRequestHandler.getUpdateResponse(newMinTemp, newMaxTemp),
                handler.handle(String.format("-min %d -max %d", newMinTemp, newMaxTemp)));

        assertEquals(spy.getMinTemp(), newMinTemp);
        assertEquals(spy.getMaxTemp(), newMaxTemp);
    }

    void assertStringEquals(String expected, String actual) {
        assertTrue(expected.equals(actual));
    }
}