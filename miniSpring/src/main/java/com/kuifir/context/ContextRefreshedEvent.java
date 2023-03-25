package com.kuifir.context;


public class ContextRefreshedEvent extends ApplicationEvent{
    private static final long serialVersionUID = 1L;
    public ContextRefreshedEvent(Object arg0) {
        super(arg0);
    }

    public String toString() {
        return this.msg;
    }
}
