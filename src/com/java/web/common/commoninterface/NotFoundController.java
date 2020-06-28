package com.java.web.common.commoninterface;

public interface NotFoundController {

    default String notFound() {
        return "";
    }
}
