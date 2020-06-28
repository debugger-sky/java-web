package com.java.web.common.socket;

import com.java.web.common.interceptor.Before;
import com.java.web.common.request.dto.HttpRequest;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class StreamThread implements Runnable {

    private final Socket CLIENT_SOCKET;
    private final int BUFFER_SIZE = 1024;
    private InputStream inputStream;
    private BufferedInputStream bufferedInputStream;
    private InputStreamReader inputStreamReader;
    private OutputStream outputStream;
    private final ServerService serverService;

    public StreamThread(Socket clientSocket) {
        this.CLIENT_SOCKET = clientSocket;
        serverService = new ServerService();
    }

    @Override
    public void run() {
        try {
            openStream();
        } catch (IOException e) {
            System.out.println("fail open socket's inputStream");
            closeStream();
            return;
        }

        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        try {
            while((i = bufferedInputStream.read()) != -1) {

                stringBuffer.append((char) i);

                if (stringBuffer.toString().contains("\r\n\r\n") || stringBuffer.toString().contains("\n\n")) {
                    Map<String, String> headerMap = serverService.getRequestHeaderMap(stringBuffer.toString());
                    HttpRequest httpRequest = serverService.getRequestHeader(headerMap);

                    if(httpRequest.getContentLength() != null && Integer.parseInt(httpRequest.getContentLength()) > 0) {
                        byte[] body = new byte[Integer.parseInt(httpRequest.getContentLength())];
                        bufferedInputStream.read(body);
                        httpRequest.setBody(new String(body));
                    }

                    sendResponse(httpRequest);
                    break;

                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeStream();
        }

    }

    public void sendResponse(HttpRequest httpRequest) throws IOException, ClassNotFoundException {
        outputStream = CLIENT_SOCKET.getOutputStream();
        byte[] response = Before.HTTP_ROUTE.getController(httpRequest).getBytes();
        outputStream.write(response);
        outputStream.flush();
    }

    private void openStream() throws IOException {
            this.inputStream = this.CLIENT_SOCKET.getInputStream();
            this.bufferedInputStream = new BufferedInputStream(this.inputStream, BUFFER_SIZE);
            this.inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    }

    private void closeStream() {
        try { if(this.inputStreamReader!=null) this.inputStreamReader.close(); } catch (IOException ignored) { }
        try { if(this.bufferedInputStream!=null) this.bufferedInputStream.close(); } catch (IOException ignored) { }
        try { if(this.inputStream!=null) this.inputStream.close(); } catch (IOException ignored) { }
        try { if(this.outputStream!=null) this.outputStream.close(); } catch (IOException ignored) { }

        System.out.println("================= close socket!!!!!! =================");
    }

}
