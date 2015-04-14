package com.hvac.main;

import com.hvac.EnvironmentController;
import com.hvac.EnvironmentControllerImpl;
import vendor.hvac.HVAC;

import java.util.Map;

public class Runner {

    public enum ArgumentValidationState {
        INVALID,
        ABSENT,
        PRESENT
    }

    private static class TemperatureSettings {
        private int minTemp;
        private int maxTemp;
    }

    Runner() {
    }

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

    public static void main(String[] args) {
        Map<String, String> arguments = new ArgumentParser().parse(args).getArguments();

        ArgumentValidationState validationState = validate(arguments);
        if (validationState == ArgumentValidationState.INVALID) {
            System.err.println("invalid arguments");
            System.exit(-1);
        }



//        arrayPrint(args);
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
    }


//    private static void arrayPrint(String[] args) {
//       for (int i = 0; i < args.length; i++) {
//           if (i > 0) {
//               System.out.print(", ");
//           }
//           System.out.print(args[i]);
//       }
//        System.out.println();
//    }

}
