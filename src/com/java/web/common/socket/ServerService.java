package com.java.web.common.socket;

import com.java.web.common.dto.HttpRequest;
import com.java.web.sample.controller.SampleController;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ServerService {

    private static String OS = System.getProperty("os.name").toLowerCase();

    InputStream inputStream;
    InputStreamReader inputStreamReader;
    BufferedReader bufferedReader;
    OutputStream outputStream = null;
    byte[] bytes = null;

    SampleController sampleController = new SampleController();

    /* temp */
    public boolean allowIp(Socket client, String... array) {
        System.out.println("socket accepted!!! " + "client port="+client.getPort() + " client ip="+client.getInetAddress() +"\n");

        for(String ip : array) {
            if(client.getInetAddress().toString().equals(ip)) {
                return true;
            }
        }
        System.out.println("other socket closed\n\n");
        return false;
    }

    public void openStream(Socket clientSocket) throws IOException {
//        this.inputStream = clientSocket.getInputStream();
//        this.inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
//        this.bufferedReader = new BufferedReader(inputStreamReader);
        this.outputStream = clientSocket.getOutputStream();

        bytes = new byte[clientSocket.getReceiveBufferSize()];
    }

    public void closeStream(Socket clientSocket) {
        try { if(this.outputStream != null) this.outputStream.close(); } catch (IOException ignored) {}
        try { if(this.bufferedReader != null) this.bufferedReader.close(); } catch (IOException ignored) { }
        try { if(this.inputStreamReader != null) this.inputStreamReader.close(); } catch (IOException ignored) { }
        try { if(this.inputStream != null) this.inputStream.close(); } catch (IOException ignored) { }
    }

    public void socketInputStream() {

    }

    public HttpRequest getRequest() throws IOException {
        HttpRequest httpRequest = null;
        Map<String, String> headerMap = new HashMap<>();

        // header
//        String str = null;
//        while(true) {
//            System.out.println(test);
//            if(test == 0) break;
//            int readCount = inputStream.read(bytes);
//            System.out.println("===================================readCount: "+readCount);
//            str = new String(bytes, 0, readCount);
//            System.out.println(str);
//        }

        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            // header 끝 공백라인
            if ("".equals(str)) {
                break;
            }
            // get http httpRequestHeaderMap
            headerMap = getRequestHeaderMap(headerMap, str);
        }
        if(headerMap != null) {
            httpRequest = getRequestHeader(headerMap);

            if(httpRequest.getContentLength() != null) {
                StringBuffer sb = new StringBuffer();

                str = bufferedReader.readLine();
                sb.append(str).append("\n");

                httpRequest.setBody(sb.toString());
            }
        }
        System.out.println(httpRequest.toString());

        return  httpRequest;
    }

    public Map<String, String> getRequestHeaderMap(String header) {
        Map<String, String> headerMap = new HashMap<>();

        String[] headerLine = null;
        if(OS.contains("win")) {
            headerLine = header.split("\r\n");
        } else {
            headerLine = header.split("\n");
        }

        for(String line : headerLine) {
            if(line.contains(":")) {
                int i = line.indexOf(":");
                String name = line.substring(0, i).toLowerCase().trim();
                String value = line.substring(i+1).trim();
                System.out.println(name+": "+value);
                headerMap.put(name, value);
            } else if(line.contains("HTTP/")) {
                System.out.println(line);
                headerMap.put("startline", line);
            }
        }

        return headerMap;
    }

    public Map<String, String> getRequestHeaderMap(Map<String, String> headerMap, String readLine) {
        if(readLine.contains(":")) {
            int i = readLine.indexOf(":");
            String name = readLine.substring(0, i).toLowerCase().trim();
            String value = readLine.substring(i+1).trim();
            System.out.println(name+": "+value);
            headerMap.put(name, value);
        } else if(readLine.contains("HTTP/")) {
            System.out.println(readLine);
            headerMap.put("startline", readLine);
        }

        return headerMap;
    }

    public HttpRequest getRequestHeader(Map<String, String> headerMap) {
        HttpRequest httpRequestHeader = new HttpRequest();
        httpRequestHeader.setStartHeader(headerMap.get("startline"));
        httpRequestHeader.setHost(headerMap.get("host"));
        httpRequestHeader.setConnection(headerMap.get("connection"));
        httpRequestHeader.setCacheControl(headerMap.get("cache-control"));
        httpRequestHeader.setUpgradeInsecureRequests(headerMap.get("upgrade-insecure-requests"));
        httpRequestHeader.setContentType(headerMap.get("content-type"));
        httpRequestHeader.setContentLength(headerMap.get("content-length"));
        httpRequestHeader.setAccept(headerMap.get("accept"));
        httpRequestHeader.setUserAgent(headerMap.get("user-agent"));
        httpRequestHeader.setOrigin(headerMap.get("origin"));
        httpRequestHeader.setSecFetchSite(headerMap.get("sec-fetch-site"));
        httpRequestHeader.setSecFetchMode(headerMap.get("sec-fetch-mode"));
        httpRequestHeader.setSecFetchUser(headerMap.get("sec-fetch-user"));
        httpRequestHeader.setSecFetchDest(headerMap.get("sec-fetch-dest"));
        httpRequestHeader.setAcceptEncoding(headerMap.get("accept-encoding"));
        httpRequestHeader.setAcceptLanguage(headerMap.get("accept-language"));

        return httpRequestHeader;
    }

    public void sendSuccessResponse() throws IOException {
        String str = "HTTP/1.1 200 OK\n";
        byte[] bytes = str.getBytes();
        outputStream.write(bytes);
        outputStream.flush();
    }

    public void sendResponse() throws IOException {
        byte[] sendResult = getResponse();
        outputStream.write(sendResult);
        outputStream.flush();
    }

    public byte[] getResponse() {
        return sampleController.sampleData().getBytes();
    }
}
