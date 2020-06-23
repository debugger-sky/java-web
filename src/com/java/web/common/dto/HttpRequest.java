package com.java.web.common.dto;

public class HttpRequest extends HttpRequestHeaderDto {

    String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpRequestDto{" +
                "body='" + body + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", requestPath='" + requestPath + '\'' +
                ", httpVersion='" + httpVersion + '\'' +
                ", host='" + host + '\'' +
                ", connection='" + connection + '\'' +
                ", cacheControl='" + cacheControl + '\'' +
                ", upgradeInsecureRequests='" + upgradeInsecureRequests + '\'' +
                ", accept='" + accept + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", origin='" + origin + '\'' +
                ", secFetchSite='" + secFetchSite + '\'' +
                ", secFetchMode='" + secFetchMode + '\'' +
                ", secFetchUser='" + secFetchUser + '\'' +
                ", secFetchDest='" + secFetchDest + '\'' +
                ", acceptEncoding='" + acceptEncoding + '\'' +
                ", acceptLanguage='" + acceptLanguage + '\'' +
                ", ContentLength='" + ContentLength + '\'' +
                ", ContentType='" + ContentType + '\'' +
                '}';
    }
}
