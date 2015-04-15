package com.hvac.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class SocketWrapper implements java.lang.AutoCloseable {
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;

    public SocketWrapper(int port) {
        this.port = port;
    }

    public void start(InputHandler handler) {
        try {
            serverSocket = new ServerSocket(this.port);

            while (true) {
                socket = serverSocket.accept();

                // read input
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String input = in.readLine();

                // write output
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(handler.handle(input));
                out.flush();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            System.out.println("closing");
            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

