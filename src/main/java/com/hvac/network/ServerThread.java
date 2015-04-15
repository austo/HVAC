package com.hvac.network;

import com.hvac.EnvironmentController;
import com.hvac.main.TemperatureRequestHandler;

import java.io.IOException;

public class ServerThread implements Runnable {

    SocketWrapper wrapper;
    EnvironmentController controller;
    final Object mutex;
    boolean shouldRun = true;

    public ServerThread(SocketWrapper wrapper, EnvironmentController controller, Object mutex) {
        this.wrapper = wrapper;
        this.controller = controller;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        synchronized (mutex) {
            try {
                wrapper.start(new TemperatureRequestHandler(controller));
            } catch(IOException e) {
                throw new RuntimeException(e);
            } finally {
                mutex.notify();
            }
        }
    }

    public void stop() {

    }
}
