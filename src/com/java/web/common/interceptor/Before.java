package com.java.web.common.interceptor;

import com.java.web.common.commoninterface.NotFoundController;
import com.java.web.common.route.HttpRoute;

public class Before {

    public final static HttpRoute HTTP_ROUTE = new HttpRoute();
    public final static NotFoundController NOT_FOUND_CONTROLLER = new NotFoundController() {};


}
