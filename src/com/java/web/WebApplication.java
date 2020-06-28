package com.java.web;

import com.java.web.common.socket.StreamThread;
import com.java.web.common.socket.Server;
import com.java.web.common.socket.ServerService;

import java.io.*;
import java.net.Socket;

public class WebApplication {

    public static void main(String[] args) {

        Server serverSocket = new Server();
        ServerService serverService = new ServerService();

        while(true) {
            Socket client = serverSocket.accept();
            Runnable inputStreamRunnable = new StreamThread(client);

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



        }
    }
}
