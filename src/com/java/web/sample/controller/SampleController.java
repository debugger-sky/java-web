package com.java.web.sample.controller;

import com.java.web.common.annotation.Controller;
import com.java.web.common.annotation.RequestMapping;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Controller
@RequestMapping(value = "/sample")
public class SampleController {

    public String get(String requestBody) {
        return sampleData();
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

    public String sampleData() {
        String body = "Sample Test";
        String iframe = "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/POdypraDi6g\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
        StringBuilder content = new StringBuilder();
        content.append("<!DOCTYPE html>").append("\n")
                .append("<html lang=\"ko\">").append("\n")
                .append("<head>").append("\n")
                .append("<meta charset=\"utf-8\">").append("\n")
                .append("<title>TEST!!</title>").append("\n")
                .append("</head>").append("\n")
                .append("<body>").append("\n")
                .append("<div style=\"color: blue\">졸리 너무 재밌어요!</div>").append("\n")
                .append(iframe).append("\n")
                .append("</body>").append("\n")
                .append("</html>");

        String sampleData = header(String.valueOf(content.toString().getBytes(StandardCharsets.UTF_8).length));
        sampleData += content.toString();
        return sampleData;
    }
}
