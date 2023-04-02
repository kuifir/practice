package com.kuifir.web;

import com.kuifir.web.servlet.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DispatcherServlet extends HttpServlet {

    private List<String> packageNames = new ArrayList<>();
    private String sContextConfigLocation;
    private WebApplicationContext webApplicationContext;
    private WebApplicationContext parentApplicationContext;
    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;
    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        this.parentApplicationContext = (WebApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        URL xmlPath;
        try {
            xmlPath = config.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
        this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation, this.parentApplicationContext);
        Refresh();
    }


    //对所有的mappingValues中注册的类进行实例化，默认构造函数
    protected void Refresh() {
        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapters(this.webApplicationContext);
    }

    protected void initHandlerMappings(WebApplicationContext wac) {
        this.handlerMapping = new RequestMappingHandlerMapping(wac);
    }

    protected void initHandlerAdapters(WebApplicationContext wac) {
        this.handlerAdapter = new RequestMappingHandlerAdapter(wac);
    }


    protected void service(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try {
            doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerMethod handlerMethod;

        handlerMethod = this.handlerMapping.getHandler(request);
        if (handlerMethod == null) {
            return;
        }

        HandlerAdapter ha = this.handlerAdapter;

        ha.handle(request, response, handlerMethod);
    }
}
