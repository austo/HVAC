package com.hvac.main;

import com.hvac.EnvironmentController;
import com.hvac.EnvironmentControllerImpl;
import com.hvac.network.ServerThread;
import com.hvac.network.SocketWrapper;
import vendor.hvac.HVAC;

import java.util.Map;

public class Runner {

    static final int SERVER_PORT = 5000;

    ArgumentValidationState validationState;
    EnvironmentController controller;
    SocketWrapper socketWrapper;
    final Object mutex = new Object();

    public void run(String[] args) {
        Map<String, String> arguments = new ArgumentParser().parse(args).getArguments();

        validationState = validate(arguments);

        if (validationState == ArgumentValidationState.INVALID) {
            System.err.println("invalid arguments");
            System.exit(-1);
        }

        System.out.printf("Arguments are : %s\n", arguments);

        if (validationState == ArgumentValidationState.PRESENT) {
            initializeController(
                    Integer.parseInt(arguments.get("min")), Integer.parseInt(arguments.get("max")));
        } else {
            initializeController();
        }

        this.socketWrapper = new SocketWrapper(SERVER_PORT);
        ServerThread serverThread = runServer();

        synchronized (mutex) {
            try {
                mutex.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeController() {
        this.controller = new EnvironmentControllerImpl(new HVAC() {
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

    private void initializeController(int min, int max) {
        this.controller = new EnvironmentControllerImpl(new HVAC() {
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

        this.controller.setMinTemp(min);
        this.controller.setMaxTemp(max);
    }


    ServerThread runServer() {
        final ServerThread serverThread = new ServerThread(this.socketWrapper, this.controller, this.mutex);
        synchronized (mutex) {
            new Thread(serverThread).start();
        }
        return serverThread;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    static ArgumentValidationState validate(Map<String, String> arguments) {
        return ArgumentValidator.validate(arguments);
    }

    public static void main(String[] args) {
        new Runner().run(args);
    }
}
