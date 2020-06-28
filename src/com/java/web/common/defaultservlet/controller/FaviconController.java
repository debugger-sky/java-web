package com.java.web.common.defaultservlet.controller;

import com.java.web.common.annotation.Controller;
import com.java.web.common.annotation.GetMapping;
import com.java.web.common.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Controller
@RequestMapping(value = "/favicon.ico")
public class FaviconController {

    @GetMapping
    public String get(String body) {
        return favicon();
    }

    private String header(String length) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("HTTP/1.1 200 OK\n");
        stringBuffer.append("Date: ").append(new Date()).append("\n");
        stringBuffer.append("Content-Type: text/html; charset=utf-8\n");
        stringBuffer.append("Content-Length: ").append(length).append("\n");
        stringBuffer.append("Access-Control-Allow-Origin: *").append("\n");
        stringBuffer.append("Access-Control-Allow-Headers: *").append("\n");
        stringBuffer.append("\n"); // lastLine
        return stringBuffer.toString();
    }

    public String favicon() {
        String body = "Sample Test";
        StringBuilder content = new StringBuilder();

        File file = new File("/Users/sky/Downloads/favicon.ico");
        if(file.exists()) {
            try {
                InputStream inputStream = new FileInputStream(file);

                int i=0;
                while((i=inputStream.read()) != -1) {
                    content.append((char)i);
                }


            } catch(Exception ignored) { }
        }

        String sampleData = header(String.valueOf(content.toString().getBytes(StandardCharsets.UTF_8).length));
        sampleData += content.toString();
        return sampleData;
    }
}
