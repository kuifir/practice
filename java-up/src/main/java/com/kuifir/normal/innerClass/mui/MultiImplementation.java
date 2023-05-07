package com.kuifir.normal.innerClass.mui;

/**
 * 如果必须以某种方式实现多个抽象类或具体类（`多重实现继承`），只能使用内部类。
 * 内部类提供了事实上能继承多个具体类或抽象类的能力
 * <p/>
 * 内部类额外功能：
 * 1. 内部类可以有多个实例，每个实例都有自己的状态信息，独立于外围类对象的信息
 * 2. 一个外围类中可以有多个内部类，他们可以以不同的方式实现同一个接口，或者继承同一个类。
 * 3. 内部类对象的创建时机不予外围类对象的创建捆绑到一起。
 * 4. 内部类不存在可能引起混淆的"is-a"关系；他是对立的实体
 *
 * @author kuifir
 * @date 2023/5/7 18:18
 */
public class MultiImplementation {
    static void takesD(D d) {
    }

    static void takesE(E e) {
    }

    public static void main(String[] args) {
        Z z = new Z();
        takesD(z);
        takesE(z.makeE());
    }
}

class D {
}

abstract class E {
}

class Z extends D {
    E makeE() {
        return new E() {
        };
    }
}

