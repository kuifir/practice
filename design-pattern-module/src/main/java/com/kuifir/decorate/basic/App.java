package com.kuifir.decorate.basic;

public class App {
    public static void main(String[] args) {
        IDecorator decorator = new Decorate();
        CurtainDecorator curtainDecorator = new CurtainDecorator(decorator);
        curtainDecorator.decorator();
    }
}
