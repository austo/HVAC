package com.hvac.main;

import com.hvac.EnvironmentController;
import com.hvac.EnvironmentControllerImpl;
import vendor.hvac.HVAC;

import java.util.Map;

public class Runner {

    ArgumentValidationState validationState;

    Runner() {

    }

    public void run(String[] args) {
        Map<String, String> arguments = new ArgumentParser().parse(args).getArguments();

        validationState = validate(arguments);

        if (validationState == ArgumentValidationState.INVALID) {
            System.err.println("invalid arguments");
            System.exit(-1);
        }

        System.out.printf("Arguments are : %s\n", arguments);

        EnvironmentController controller = new EnvironmentControllerImpl(new HVAC() {
            @Override
            public void heat(boolean on) {

            }

            @Override
            public void cool(boolean on) {

            }

            @Override
            public void fan(boolean on) {

            }

            @Override
            public int temp() {
                return 0;
            }
        });

        if (validationState == ArgumentValidationState.PRESENT) {
            controller.setMinTemp(Integer.parseInt(arguments.get("min")));
            controller.setMaxTemp(Integer.parseInt(arguments.get("max")));
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static ArgumentValidationState validate(Map<String, String> arguments) {
        return ArgumentValidator.validate(arguments);
    }

    public static void main(String[] args) {
        new Runner().run(args);
    }
}
