package com.java.web.common.dto;

import com.java.web.common.route.HttpMethod;

public class HttpRequestHeaderDto {

    HttpMethod httpMethod;
    String requestPath;
    String httpVersion;
    String host;
    String connection;
    String cacheControl;
    String upgradeInsecureRequests;
    String accept;
    String userAgent;
    String origin;
    String secFetchSite;
    String secFetchMode;
    String secFetchUser;
    String secFetchDest;
    String acceptEncoding;
    String acceptLanguage;
    String ContentLength;
    String ContentType;

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setStartHeader(String startHeader) {
        String[] startLine = startHeader.split(" ");
        this.httpMethod = HttpMethod.valueOf(startLine[0]);
        this.requestPath = startLine[1];
        this.httpVersion = startLine[2];
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public String getUpgradeInsecureRequests() {
        return upgradeInsecureRequests;
    }

    public void setUpgradeInsecureRequests(String upgradeInsecureRequests) {
        this.upgradeInsecureRequests = upgradeInsecureRequests;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSecFetchSite() {
        return secFetchSite;
    }

    public void setSecFetchSite(String secFetchSite) {
        this.secFetchSite = secFetchSite;
    }

    public String getSecFetchMode() {
        return secFetchMode;
    }

    public void setSecFetchMode(String secFetchMode) {
        this.secFetchMode = secFetchMode;
    }

    public String getSecFetchUser() {
        return secFetchUser;
    }

    public void setSecFetchUser(String secFetchUser) {
        this.secFetchUser = secFetchUser;
    }

    public String getSecFetchDest() {
        return secFetchDest;
    }

    public void setSecFetchDest(String secFetchDest) {
        this.secFetchDest = secFetchDest;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getContentLength() {
        return ContentLength;
    }

    public void setContentLength(String contentLength) {
        ContentLength = contentLength;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(String contentType) {
        ContentType = contentType;
    }

}
