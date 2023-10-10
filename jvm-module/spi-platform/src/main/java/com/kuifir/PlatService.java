package com.kuifir;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author kuifir
 * @date 2023/10/10 23:12
 */
public class PlatService {
    public static void main(String[] args) {
        ServiceLoader<Runnable> services = ServiceLoader.load(Runnable.class);
        System.out.println(Runnable.class.getClassLoader());
        Iterator<Runnable> iterator = services.iterator();
        while (iterator.hasNext()){
            Runnable service = iterator.next();
            System.out.println(service.getClass());
            System.out.println(service.getClass().getClassLoader());
            service.run();

        }
    }
}
