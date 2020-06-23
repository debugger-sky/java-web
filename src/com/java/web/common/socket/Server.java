package com.java.web.common.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static ServerSocket SERVER_SOCKET;

    public Server() {
        this.setServerSocket();
    }

    public void setServerSocket() {
        try {
            SERVER_SOCKET = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ServerSocket getServerSocket() {
        return SERVER_SOCKET;
    }

    public Socket accept() {
        Socket socket = null;

        try {
            socket = SERVER_SOCKET.accept();
            socket.setSoTimeout(10000);

        } catch (IOException e) {
            System.out.println("socket accept failure");
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return socket;
    }

}
