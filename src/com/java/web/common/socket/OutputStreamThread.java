package com.java.web.common.socket;

import com.java.web.common.interceptor.Before;
import com.java.web.common.request.dto.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class OutputStreamThread implements Runnable {

    private final Socket CLIENT_SOCKET;
    private final int BUFFER_SIZE = 1024;
    private final ServerService serverService;
    private final HttpRequest httpRequest;
    private OutputStream outputStream;

    public OutputStreamThread(Socket clientSocket, HttpRequest httpRequest) {
        this.CLIENT_SOCKET = clientSocket;
        this.httpRequest = httpRequest;
        serverService = new ServerService();
    }

    @Override
    public void run() {
        try {
            outputStream = CLIENT_SOCKET.getOutputStream();
            byte[] response = Before.HTTP_ROUTE.getController(httpRequest).getBytes();
            outputStream.write(response);
            outputStream.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(outputStream != null) outputStream.close();
            } catch (IOException e) {
                System.out.println("outputStream close");
            }
        }
    }
}
