package com.java.web.common.route;

import com.java.web.common.interceptor.Before;
import com.java.web.common.request.dto.HttpRequest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

public class HttpRoute {

    public String getController(HttpRequest httpRequest) throws Exception {
        Object response = null;

        String path = httpRequest.getRequestPath();
        HttpMethod httpMethod = httpRequest.getHttpMethod();

        Map<Object, Object> requestMap = Before.CONTROLLER_MAP.get(path);
        if(requestMap != null) {
            Class<?> requestClass = (Class<?>) Before.CONTROLLER_MAP.get(path).get("class");
            Method requestMethod = (Method) Before.CONTROLLER_MAP.get(path).get(httpMethod);

            if(requestMethod != null) {
                Constructor constructor = requestClass.getConstructor();
                Object object = constructor.newInstance();
                response = requestMethod.invoke(object, httpRequest.getBody());

            } else {
                System.out.println("METHOD IS NULL!!!!!");
            }

        }

        return (String) response;
    }

}
