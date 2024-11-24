package com.kuifir.classloading.notInitialization;

public class SubClass extends SuperClass{
    static {
        System.out.println("Subclass init");
    }
}
