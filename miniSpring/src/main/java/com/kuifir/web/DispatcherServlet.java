package com.kuifir.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {

    private Map<String, MappingValue> mappingValues;
    private final Map<String, Class<?>> mappingClz = new HashMap<>();
    private final Map<String, Object> mappingObjs = new HashMap<>();
    private String sContextConfigLocation;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sContextConfigLocation = config.getInitParameter("contextConfigLocation");
        URL xmlPath = null;
        try {
            xmlPath = config.getServletContext().getResource(sContextConfigLocation);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Resource rs = new ClassPathXmlResource(xmlPath);
        XmlConfigReader xmlConfigReader = new XmlConfigReader();
        mappingValues = xmlConfigReader.loadConfig(rs);
        Refresh();
    }


    //对所有的mappingValues中注册的类进行实例化，默认构造函数
    protected void Refresh() {
        for (Map.Entry<String,MappingValue> entry : mappingValues.entrySet()) {
            String id = entry.getKey();
            String className = entry.getValue().getClz();
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(className);
                obj = clz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mappingClz.put(id, clz);
            mappingObjs.put(id, obj);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String servletPath = request.getServletPath(); //获取请求的path
        String msg ="http.method_get_not_found";
        if(!this.mappingValues.containsKey(servletPath)){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, msg);
        }
        Class<?> clz = this.mappingClz.get(servletPath); // 获取bean类定义
        Object obj = this.mappingObjs.get(servletPath); //获取bean实例
        String methodName = this.mappingValues.get(servletPath).getMethod(); //获取调用方法名

        Object objResult;
        try {
            Method method = clz.getMethod(methodName);
            objResult = method.invoke(obj);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        //将方法返回值写入response
         response.getWriter().append(objResult.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
