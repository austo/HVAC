package com.hvac.main;

import com.hvac.EnvironmentController;
import com.hvac.network.InputHandler;

import java.util.Map;

public class TemperatureRequestHandler implements InputHandler {

    private static final String UPDATED_RESPONSE_FORMAT = "Updated EnvironmentController temperature range (min: %d, max: %d)";

    private EnvironmentController controller;

    public TemperatureRequestHandler(EnvironmentController controller) {
        this.controller = controller;
    }

    @Override
    public String handle(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        final Map<String, String> arguments = new ArgumentParser().parse(input.split(" ")).getArguments();
        ArgumentValidationState validationState = ArgumentValidator.validate(arguments);

        return upDateEnvironmentControllerIfNecessary(arguments, validationState);
    }

    private String upDateEnvironmentControllerIfNecessary(
            Map<String, String> arguments, ArgumentValidationState validationState) {

        if (validationState == ArgumentValidationState.PRESENT) {
            int newMin = Integer.parseInt(arguments.get("min"));
            int newMax = Integer.parseInt(arguments.get("max"));

            controller.setMinTemp(newMin);
            controller.setMaxTemp(newMax);
            return getUpdateResponse(newMin, newMax);
        }

        return validationState.toString();
    }

    static String getUpdateResponse(int min, int max) {
        return String.format(UPDATED_RESPONSE_FORMAT, min, max);
    }
}
