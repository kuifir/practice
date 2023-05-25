package com.kuifir.normal.generic;

import java.util.*;

/**
 * 类型擦除
 * 泛型代码内部并不存在有关泛型参数类型的可用信息
 * Java泛型有些问题看起来并不合理，例如：
 * 1. 虽然声明{@code ArrayList.class}是合法的，但声明{@code ArrayList<Integer>.class}却不行；
 * 2. {@code ArrayList<String>}和{@code ArrayList<Integer>}应该是不同的类型,而不同的类型具有不同的行为。然而程序会认为这两者是相同的类型。;
 * 3. 根据JDK文档的描述，Class.getTypeParameters()会"返回一个由TypeVariable"对象组成的数组，代表由泛型声明的类型变量。。。“。
 * 这似乎在暗示可以发现参数的类型信息。然而，你只能发现作为参数占位符的标识符。
 *<p></p>
 *
 * @author kuifir
 * @date 2023/5/25 23:22
 */
public class LostInformation {
    public static void main(String[] args) {
        // 虽然声明ArrayList.class是合法的，但声明ArrayList<Integer>.class却不行
        Class<ArrayList> arrayListClass = ArrayList.class;
        // Class<ArrayList<Integer>> arrayListClass1 = ArrayList<Integer>.class;


        // ArrayList<String> 和 ArrayList<Integer>应该是不同的类型,而不同的类型具有不同的行为。
        // 然而程序会认为这两者是相同的类型。
        Class<? extends ArrayList> aClass = new ArrayList<String>().getClass();
        Class<? extends ArrayList> aClass1 = new ArrayList<Integer>().getClass();
        // true
        System.out.println(aClass == aClass1);


        List<Frob> list = new ArrayList<>();
        Map<Frob, Fnorkle> map = new HashMap<>(10);
        Quark<Fnorkle> quark = new Quark<>();
        Particle<Long, Double> p = new Particle<>();
        System.out.println(Arrays.toString(list.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(map.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(quark.getClass().getTypeParameters()));
        System.out.println(Arrays.toString(p.getClass().getTypeParameters()));
        // [E]
        // [K, V]
        // [Q]
        // [POSITION, MOMENTUM]
    }
}

class Frob {
}

class Fnorkle {
}

class Quark<Q> {
}

class Particle<POSITION, MOMENTUM> {
}