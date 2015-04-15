package com.hvac.network;

import com.hvac.EnvironmentControllerSpy;
import org.junit.Test;

public class ServerThreadTest {

    @Test(expected = RuntimeException.class)
    public void ShouldThrowRuntimeExceptionIfPortIsNotAvailable() {
        final int badPort = -1;
        final Object mutex = new Object();
        final ServerThread serverThread = new ServerThread(
                new SocketWrapper(badPort), new EnvironmentControllerSpy(), mutex);
        serverThread.run();
    }

    @Test
    public void ShouldBindToValidPort() {
        final int goodPort = 9998;
        final Object mutex = new Object();
        final SocketWrapper wrapper = new SocketWrapper(goodPort);
        final ServerThread serverThread = new ServerThread(wrapper, new EnvironmentControllerSpy(), mutex);

        Thread server = new Thread(serverThread);
        server.start();

        try {
            Thread.sleep(2000);
            server.interrupt();
        } catch (InterruptedException ignored) {
            System.out.println("caught interrupted exception");
        }
    }
}