package com.java.web.common.interceptor;

import com.java.web.common.annotation.Controller;
import com.java.web.common.annotation.GetMapping;
import com.java.web.common.annotation.RequestMapping;
import com.java.web.common.commoninterface.NotFoundController;
import com.java.web.common.request.dto.HttpRequest;
import com.java.web.common.route.HttpMethod;
import com.java.web.common.route.HttpRoute;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Before {

    public final static HttpRoute HTTP_ROUTE = new HttpRoute();
    public final static NotFoundController NOT_FOUND_CONTROLLER = new NotFoundController() {};
    public static Map<String, Map<Object, Object>> CONTROLLER_MAP = new HashMap<>();

    public String basePackageName = "com.java.web";

    public Before() throws ClassNotFoundException, NoSuchMethodException {
        init(getBasePackageUrl());
    }

    public void init(String rootPackage) throws ClassNotFoundException {
        File[] files = new File(rootPackage).listFiles();
//        File[] files = new File(rootPath.getFile()).listFiles((dir, name) -> name.endsWith(".class"));

        if (files != null) {
            
            for(File file : files) {
                if(file.isDirectory()) {
                    init(file.getPath());
                } else if(file.getName().contains(".class")) {
                    String className = file.getPath().replaceAll("/", ".").split(basePackageName)[1].replaceAll(".class", "");
                    Class<?> clazz = Class.forName(basePackageName + className);

                    if(clazz.isAnnotationPresent(Controller.class)) {
                        String requestPath = "";
                        if(clazz.isAnnotationPresent(RequestMapping.class)) {
                            RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                            requestPath += requestMapping.value();
                        }

                        Method[] methods = clazz.getDeclaredMethods();
                        for(Method method : methods) {
                            if(method.isAnnotationPresent(GetMapping.class)) {
                                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                                String subRequestPath = getMapping.value();

                                if("/".equals(subRequestPath)) {
                                    Map<Object, Object> controllerMap = CONTROLLER_MAP.get(requestPath);
                                    if(controllerMap == null) {
                                        controllerMap = new HashMap<>();
                                        controllerMap.put("class", clazz);
                                        controllerMap.put(HttpMethod.GET, method);
                                        CONTROLLER_MAP.put(requestPath, controllerMap);
                                    } else {
                                        controllerMap.put(HttpMethod.GET, method);
                                    }
                                    
                                } else {
                                    Map<Object, Object> controllerMap = CONTROLLER_MAP.get(requestPath + subRequestPath);
                                    if(controllerMap == null) {
                                        controllerMap = new HashMap<>();
                                        controllerMap.put("class", clazz);
                                        controllerMap.put(HttpMethod.GET, method);
                                        CONTROLLER_MAP.put(requestPath + subRequestPath, controllerMap);
                                    } else {
                                        controllerMap.put(HttpMethod.GET, method);
                                    }
                                }
                            }
                        }

                    }

                }
            }
        }

    }

    public String getBasePackageUrl() {
        URL url = Thread.currentThread().getContextClassLoader().getResource(basePackageName.replace(".", "/"));
        return url.getFile();
    }
}
