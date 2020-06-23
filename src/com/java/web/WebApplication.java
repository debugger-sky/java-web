package com.java.web;

import com.java.web.common.socket.InputStreamThread;
import com.java.web.common.socket.Server;
import com.java.web.common.socket.ServerService;
import com.java.web.sample.controller.SampleController;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class WebApplication {

    public static void main(String[] args) {

        Server serverSocket = new Server();
        ServerService serverService = new ServerService();

        while(true) {
            Socket client = serverSocket.accept();
            Runnable inputStreamRunnable = new InputStreamThread(client);

            // 로컬 테스트만 가능하도록 IP제한
            String[] strings = new String[]{"/0:0:0:0:0:0:0:1", "/127.0.0.1"};
            if(!serverService.allowIp(client, strings)) {
                try { client.close(); } catch (IOException e) {
                    System.out.println("not allowed");
                }
                continue;
            }

            Thread inputStreamThread = new Thread(inputStreamRunnable);
            inputStreamThread.start();


            // socket, inputStream, outputStream open
//            try {
//                serverService.openStream(client);
//            } catch (IOException e) {
//                System.out.println(client.getInetAddress() + " stream open fail");
//                try { client.close(); } catch (IOException ignored) { }
//                continue;
//            }
//
//            try {
//                serverService.getRequest();
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println(client.getInetAddress() + " stream read fail : first get request");
//                try { client.close(); } catch (IOException ignored) { }
//                continue;
//            }

//            try {
//                serverService.openStream(client);
//                serverService.sendSuccessResponse();
//            } catch (IOException e) {
//                System.out.println("sendSuccessResponse");
//            }

//            try {
//                serverService.getRequest();
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println(client.getInetAddress() + " stream read fail : second get request");
//                try { client.close(); } catch (IOException ignored) { }
//                continue;
//            }

//            try {
//                serverService.sendResponse();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                // socket, inputStream, outputStream close
//                serverService.closeStream(client);
//                try {
//                    client.close();
//                    System.out.println("client closed");
//                } catch (IOException ignored) { }
//            }
        }
    }
}
