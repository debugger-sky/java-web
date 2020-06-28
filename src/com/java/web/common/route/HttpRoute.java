package com.java.web.common.route;

import com.java.web.common.commoninterface.NotFoundController;
import com.java.web.common.interceptor.Before;
import com.java.web.common.request.dto.HttpRequest;
import com.java.web.sample.controller.SampleController;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class HttpRoute {

    public String getController(HttpRequest httpRequest) throws Exception {
        Object response = null;

        String path = httpRequest.getRequestPath();
        HttpMethod httpMethod = httpRequest.getHttpMethod();

        Map<Object, Object> requestMap = Before.CONTROLLER_MAP.get(httpRequest.getRequestPath());
        if(requestMap != null) {
            Class<?> requestClass = (Class<?>) Before.CONTROLLER_MAP.get(httpRequest.getRequestPath()).get("class");
            Method requestMethod = (Method) Before.CONTROLLER_MAP.get(httpRequest.getRequestPath()).get(httpMethod);

            if(requestMethod != null) {
                Constructor constructor = requestClass.getConstructor();
                Object object = constructor.newInstance();
                response = requestMethod.invoke(object, httpRequest.getBody());
                System.out.println("METHOD IS NOT NULL!!!!!");
            } else {
                System.out.println("METHOD IS NULL!!!!!");
            }

        } else {
            System.out.println("why??????");
        }





//        try {
//            Class<?> clazz = Class.forName("com.java.web.sample.controller.SampleController");
//            Constructor<?> constructor = clazz.getConstructor();
//            Controller controller = (Controller) constructor.newInstance();

            //test
//            SampleController sampleController = new SampleController();

//            switch (httpMethod) {
//                case GET: response = sampleController.get(httpRequest.getBody());
//                case POST: controller.post();
//                case PUT: controller.put();
//                case DELETE: controller.delete();
//                case OPTION: controller.options();
//                default: controller.notFound();
//            }

//        } catch (ClassNotFoundException e) {
//            NotFoundController controller = Before.NOT_FOUND_CONTROLLER;
//            response = controller.notFound();
//        }

        return (String) response;
    }

}
