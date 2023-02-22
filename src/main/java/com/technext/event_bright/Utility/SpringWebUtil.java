package com.technext.event_bright.Utility;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


public class SpringWebUtil {
    public static Object currentRequestAttribute(String attributeName) {
        return RequestContextHolder.currentRequestAttributes().getAttribute(attributeName, RequestAttributes.SCOPE_REQUEST);
    }

    public static Object currentSessionAttribute(String attributeName) {
        return RequestContextHolder.currentRequestAttributes().getAttribute(attributeName, RequestAttributes.SCOPE_SESSION);
    }

    public static void setCurrentRequestAttribute(String attributeName, Object attributeValue) {
        RequestContextHolder.currentRequestAttributes().setAttribute(attributeName, attributeValue, RequestAttributes.SCOPE_REQUEST);
    }

    public static void setCurrentSessionAttribute(String attributeName, Object attributeValue) {
        RequestContextHolder.currentRequestAttributes().setAttribute(attributeName, attributeValue, RequestAttributes.SCOPE_SESSION);
    }
}
