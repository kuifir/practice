package com.kuifir.web.servlet;

public interface ViewResolver {
    View resolveViewName(String viewName) throws Exception;
}
