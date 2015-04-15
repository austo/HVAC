package com.hvac.network;

import com.hvac.EnvironmentController;
import com.hvac.main.TemperatureRequestHandler;

public class ServerThread implements Runnable {

    SocketWrapper wrapper;
    EnvironmentController controller;
    final Object mutex;

    public ServerThread(SocketWrapper wrapper, EnvironmentController controller, Object mutex) {
        this.wrapper = wrapper;
        this.controller = controller;
        this.mutex = mutex;
    }

    @Override
    public void run() {
        synchronized (mutex) {
            wrapper.start(new TemperatureRequestHandler(controller));
            notify();
        }
    }
}
