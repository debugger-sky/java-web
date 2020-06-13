package com.java.web.sample.controller;

import java.util.Date;

public class SampleController {

    private String header(String length) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("HTTP/1.1 200 OK\n");
        stringBuffer.append("Date: ").append(new Date()).append("\n");
        stringBuffer.append("Content-Type: text/html; charset=utf-8\n");
        stringBuffer.append("Content-Length: ").append(length).append("\n");
        stringBuffer.append("Access-Control-Allow-Origin: *").append("\n");
        stringBuffer.append("\n"); // lastLine
        return stringBuffer.toString();
    }

    public String sampleData() {
        String body = "Sample Test";
        String sampleData = header(String.valueOf(body.length()));
        sampleData += body;
        return sampleData;
    }
}
