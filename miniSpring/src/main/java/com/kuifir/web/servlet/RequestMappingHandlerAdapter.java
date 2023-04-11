package com.kuifir.web.servlet;

import com.kuifir.beans.BeansException;
import com.kuifir.http.converter.HttpMessageConverter;
import com.kuifir.web.WebApplicationContext;
import com.kuifir.web.WebBindingInitializer;
import com.kuifir.web.WebDataBinder;
import com.kuifir.web.WebDataBinderFactory;
import com.kuifir.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class RequestMappingHandlerAdapter implements HandlerAdapter {
    private WebApplicationContext wac;
    private WebBindingInitializer webBindingInitializer;
    private HttpMessageConverter messageConverter;

    public RequestMappingHandlerAdapter() {
    }

    public RequestMappingHandlerAdapter(WebApplicationContext wac) {
        this.wac = wac;
        try {
            this.webBindingInitializer = (WebBindingInitializer) this.wac.getBean("webBindingInitializer");
        } catch (BeansException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return handleInternal(request, response, (HandlerMethod) handler);
    }

    private ModelAndView handleInternal(HttpServletRequest request, HttpServletResponse response, HandlerMethod handler) {
        Method method = handler.getMethod();
        Object obj = handler.getBean();
        Object objResult = null;
        try {
            return invokeHandlerMethod(request, response, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response,
                                       HandlerMethod handlerMethod) throws Exception {
        ModelAndView mav = null;
        WebDataBinderFactory binderFactory = new WebDataBinderFactory();
        Parameter[] methodParameters = handlerMethod.getMethod().getParameters();
        Object[] methodParamObjs = new Object[methodParameters.length];
        int i = 0;
        for (Parameter methodParameter : methodParameters) {
            Object methodParamObj = methodParameter.getType().getDeclaredConstructor().newInstance();
            WebDataBinder webDataBinder = binderFactory.createBinder(request, methodParamObj, methodParameter.getName());
            webBindingInitializer.initBinder(webDataBinder);
            webDataBinder.bind(request);
            methodParamObjs[i] = methodParamObj;
            i++;
        }
        Method invocableMethod = handlerMethod.getMethod();
        Object returnObj = invocableMethod.invoke(handlerMethod.getBean(), methodParamObjs);
        //如果是ResponseBody注解，仅仅返回值，则转换数据格式后直接写到response
        if (invocableMethod.isAnnotationPresent(ResponseBody.class)) { //ResponseBody
            this.messageConverter.write(returnObj, response);
        }else {
            //返回的是前端页面
            if (returnObj instanceof ModelAndView) {
                mav = (ModelAndView) returnObj;
            } else if (returnObj instanceof String sTarget) {
                //字符串也认为是前端页面
                mav = new ModelAndView();
                mav.setViewName(sTarget);
            }
        }
        return mav;

    }

    public WebBindingInitializer getWebBindingInitializer() {
        return webBindingInitializer;
    }

    public void setWebBindingInitializer(WebBindingInitializer webBindingInitializer) {
        this.webBindingInitializer = webBindingInitializer;
    }

    public HttpMessageConverter getMessageConverter() {
        return messageConverter;
    }

    public void setMessageConverter(HttpMessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }
}
