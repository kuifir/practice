package com.kuifir.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextLoaderListener implements ServletContextListener {
    private static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
    private WebApplicationContext context;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        initWebApplicationContext(event.getServletContext());
    }

    private void initWebApplicationContext(ServletContext servletContext) {
        String contextConfigLocation = servletContext.getInitParameter(CONFIG_LOCATION_PARAM);
        System.out.println("contextConfigLocation-----------" + contextConfigLocation);
        WebApplicationContext wac = new XmlWebApplicationContext(contextConfigLocation);
        wac.setServletContext(servletContext);
        this.context = wac;
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
    }
}
