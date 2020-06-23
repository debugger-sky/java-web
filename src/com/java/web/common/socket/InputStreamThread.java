package com.java.web.common.socket;

import com.java.web.common.dto.HttpRequest;
import com.java.web.common.route.MethodRoute;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class InputStreamThread implements Runnable {

    private final Socket CLIENT_SOCKET;
    private final int BUFFER_SIZE = 1024;
    private InputStream inputStream;
    private BufferedInputStream bufferedInputStream;
    private InputStreamReader inputStreamReader;
    private final MethodRoute methodRoute;
    private final ServerService serverService;

    public InputStreamThread(Socket clientSocket) {
        this.CLIENT_SOCKET = clientSocket;
        methodRoute = new MethodRoute();
        serverService = new ServerService();
    }

    @Override
    public void run() {
        try {
            openInputStream();
        } catch (IOException e) {
            System.out.println("fail open socket's inputStream");
            e.printStackTrace();
            closeInputStream();
            return;
        }


        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while(true) {
            try {
                i = bufferedInputStream.read();

                if(i == -1) { break; }

                stringBuffer.append((char)i);
                System.out.print((char)i);

                if(stringBuffer.toString().contains("\r\n\r\n") || stringBuffer.toString().contains("\n\n")) {

                    Map<String, String> headerMap =  serverService.getRequestHeaderMap(stringBuffer.toString());
                    HttpRequest httpRequestHeader = serverService.getRequestHeader(headerMap);
                    if(stringBuffer.toString().toLowerCase().contains("content-length")) { //todo: content-length > 0 일 경우도 추가필요

                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
                closeInputStream();
                break;
            }
        }


    }

    private void openInputStream() throws IOException {
            this.inputStream = this.CLIENT_SOCKET.getInputStream();
            this.bufferedInputStream = new BufferedInputStream(this.inputStream, BUFFER_SIZE);
            this.inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    }

    private void closeInputStream() {
        try {
            if(this.inputStreamReader!=null) this.inputStreamReader.close();
        } catch (IOException e) {
            System.out.println("fail close inputStream:: inputStreamReader");
        }

        try {
            if(this.bufferedInputStream!=null) this.bufferedInputStream.close();
        } catch (IOException e) {
            System.out.println("fail close inputStream:: bufferedInputStream");
        }

        try {
            if(this.inputStream!=null) this.inputStream.close();
            System.out.println("close inputStream and close socket");
        } catch (IOException e) {
            System.out.println("fail close inputStream:: inputStream");
        }
    }

}
