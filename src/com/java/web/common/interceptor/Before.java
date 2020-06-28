package com.java.web.common.interceptor;

import com.java.web.common.annotation.*;
import com.java.web.common.route.HttpMethod;
import com.java.web.common.route.HttpRoute;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Before {

    public final static HttpRoute HTTP_ROUTE = new HttpRoute();
    public static Map<String, Map<Object, Object>> CONTROLLER_MAP = new HashMap<>();
    public final String basePackageName = "com.java.web";

    public Before() throws ClassNotFoundException {
        init(getBasePackageUrl());
    }

    public void init(String rootPackage) throws ClassNotFoundException {
        File[] files = new File(rootPackage).listFiles();
//        File[] files = new File(rootPath.getFile()).listFiles((dir, name) -> name.endsWith(".class"));
        if (files == null) return;

        for(File file : files) {
            if(file.isDirectory()) {
                init(file.getPath());
            } else if(file.getName().contains(".class")) {
                String className = file.getPath().replaceAll("/", ".").split(basePackageName)[1].replaceAll(".class", "");
                Class<?> clazz = Class.forName(basePackageName + className);

                if(!clazz.isAnnotationPresent(Controller.class)) continue;

                String requestPath = clazz.isAnnotationPresent(RequestMapping.class)? clazz.getAnnotation(RequestMapping.class).value() : "";

                Method[] methods = clazz.getDeclaredMethods();
                for(Method method : methods) {
                    String subRequestPath;
                    HttpMethod requestMethod;

                    if(method.isAnnotationPresent(GetMapping.class)) {
                        GetMapping mapping = method.getAnnotation(GetMapping.class);
                        subRequestPath = mapping.value();
                        requestMethod = mapping.method();

                    } else if(method.isAnnotationPresent(PostMapping.class)) {
                        PostMapping mapping = method.getAnnotation(PostMapping.class);
                        subRequestPath = mapping.value();
                        requestMethod = mapping.method();

                    } else if(method.isAnnotationPresent(PutMapping.class)) {
                        PutMapping mapping = method.getAnnotation(PutMapping.class);
                        subRequestPath = mapping.value();
                        requestMethod = mapping.method();

                    } else if(method.isAnnotationPresent(DeleteMapping.class)) {
                        DeleteMapping mapping = method.getAnnotation(DeleteMapping.class);
                        subRequestPath = mapping.value();
                        requestMethod = mapping.method();

                    } else if(method.isAnnotationPresent(PatchMapping.class)) {
                        PatchMapping mapping = method.getAnnotation(PatchMapping.class);
                        subRequestPath = mapping.value();
                        requestMethod = mapping.method();

                    } else {
                        continue;
                    }

                    String fullRequestPath = (!"/".equals(subRequestPath))? requestPath+subRequestPath : requestPath;
                    setControllerMap(fullRequestPath, clazz, method, requestMethod);

                }

            }
        }

    }

    public String getBasePackageUrl() {
        URL url = Thread.currentThread().getContextClassLoader().getResource(basePackageName.replace(".", "/"));
        return url.getFile();
    }

    public void setControllerMap(String fullRequestPath, Class<?> clazz, Method method, HttpMethod httpMethod) {
        Map<Object, Object> controllerMap = CONTROLLER_MAP.get(fullRequestPath);
        if(controllerMap == null) {
            controllerMap = new HashMap<>();
            controllerMap.put("class", clazz);
            controllerMap.put(httpMethod, method);
            CONTROLLER_MAP.put(fullRequestPath, controllerMap);
        } else {
            controllerMap.put(httpMethod, method);
        }
    }
}
