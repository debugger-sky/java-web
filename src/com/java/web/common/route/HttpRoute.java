package com.java.web.common.route;

import com.java.web.common.commoninterface.NotFoundController;
import com.java.web.common.interceptor.Before;
import com.java.web.common.request.dto.HttpRequest;
import com.java.web.sample.controller.SampleController;

public class HttpRoute {

    public String getController(HttpRequest httpRequest) throws ClassNotFoundException {
        String path = httpRequest.getRequestPath();
        HttpMethod httpMethod = httpRequest.getHttpMethod();

        String response = null;

        try {
            Class<?> clazz = Class.forName("com.java.web.sample.controller.SampleController");
//            Constructor<?> constructor = clazz.getConstructor();
//            Controller controller = (Controller) constructor.newInstance();

            //test
            SampleController sampleController = new SampleController();

            switch (httpMethod) {
                case GET: response = sampleController.get(httpRequest.getBody());
//                case POST: controller.post();
//                case PUT: controller.put();
//                case DELETE: controller.delete();
//                case OPTION: controller.options();
//                default: controller.notFound();
            }

        } catch (ClassNotFoundException e) {
            NotFoundController controller = Before.NOT_FOUND_CONTROLLER;
            response = controller.notFound();
        }

        return response;
    }

}
