package com.java.web.common.request.service;

import com.java.web.common.request.dto.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestService {

    private static String OS = System.getProperty("os.name").toLowerCase();

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
}
