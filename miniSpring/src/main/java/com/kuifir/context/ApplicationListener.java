package com.kuifir.context;

import java.util.EventListener;

public class ApplicationListener<E extends ApplicationEvent> implements EventListener {
    void onApplicationEvent(E event) {
        System.out.println(event.toString());
    }
}
