package com.hvac.main;

import com.hvac.network.InputHandler;

public class TemperatureRequestHandler implements InputHandler {
    @Override
    public String handle(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }
        return null;
    }
}
