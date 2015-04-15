package com.hvac.network;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.Socket;
import java.net.InetAddress;
import java.net.ConnectException;


public class SocketWrapperTest {

    @Test
    public void ItCanStartASocketAtAPort() throws Exception {
        try(SocketWrapper socket = new SocketWrapper(5000)) {
            StartSocket(socket);

            // Try to connect
            boolean connected = false;
            int retries = 0;
            while (!connected && retries < 5) {
                InetAddress host = InetAddress.getLocalHost();
                try(Socket client = new Socket(host.getHostName(), 5000)) {
                    connected = true;

                    String dataWritten = WriteToSocket(client, "Test\n");

                    assertEquals("Test", dataWritten);
                } catch(ConnectException e) {
                    Thread.sleep(100);
                    retries++;
                }
            }

            // Throw exception if we couldn't retry
            if (retries >= 5) {
                throw new Exception();
            }
        }
    }

    private void StartSocket(final SocketWrapper socket) {
        new Thread() {
            public void run() {
                try {
                    socket.start(new InputHandler() {
                        @Override
                        public String handle(String input) {
                            return input;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private String WriteToSocket(Socket client, String data) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        PrintWriter out = new PrintWriter(client.getOutputStream());
        out.println(data);
        out.flush();

        // should echo back back value
        return in.readLine();
    }

}