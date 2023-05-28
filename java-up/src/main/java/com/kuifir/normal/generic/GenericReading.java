package com.kuifir.normal.generic;

import java.util.Arrays;
import java.util.List;

/**
 * 泛型协变性和通配符
 * redExact()使用精确的类型。
 * 如果在不使用通配符的情况下使用精确的类型，便可以在List上对该精确对象进行写入或读出操作。
 * 另外，对于返回值来说，静态泛型方法{@link GenericReading#readExact(List)}对每个方法调用都可以有效地兼容,
 * 如在f1()中所见，从{@code List<apple>}中返回Apple,以及从{@code List<Fruit>}中返回Fruit。
 * 因此，如果你可以避开使用静态泛型方法，只需要只读的情况下，你并不一定需要协变。
 * <p></p>
 * 不过，如果使用泛型类的话，在你为该类实例化一个对象的时候，其参数即被确定下来。
 * 如f2()所示，因为fruitReader的具体类型是Fruit,所以它可以从{@code List<Fruit>}中读取单个Fruit.
 * 但是{@code List<Apple>}应该也能生成Fruit对象，而fruitReader并不允许这样。
 * <p></p>
 * 为了解决这个问题，CovariantReader.readCovariant()方法接收了{@code List<? extends T>}作为参数。
 * 从该list中读取T是安全的，因为你知道里面的所有元素都至少是T，也可能是T的某种子类。
 * 在f3()中，你可以看到现在可以中{@code List<Apple>}中读取fruit了。
 * @author kuifir
 * @date 2023/5/27 15:34
 */
public class GenericReading {
    static List<Apple> apples = Arrays.asList(new Apple());
    static List<Fruit> fruits = Arrays.asList(new Fruit());

    static <T> T readExact(List<T> list) {
        return list.get(0);
    }

    /**
     * 兼容每种调用的静态方法
     */
    static void f1() {
        Apple apple = readExact(apples);
        Fruit fruit = readExact(fruits);
        fruit = readExact(apples);
    }

    /**
     * 类被实例化后，其类型即被确定
     *
     * @param <T>
     */
    static class Reader<T> {
        T readExact(List<T> list) {
            return list.get(0);
        }
    }
    static void f2(){
        Reader<Fruit> fruitReader = new Reader<>();
        Fruit fruit = fruitReader.readExact(fruits);
        // error:  不兼容的类型: java.util.List<Apple>无法转换为java.util.List<Fruit>
        // Fruit fruit1 = fruitReader.readExact(apples);
    }
    static class CovariantReader<T>{
        T readCovariant(List<? extends T> list){
            return list.get(0);
        }
    }
    static void f3(){
        CovariantReader<Fruit> fruitReader = new CovariantReader<>();
        Fruit fruit = fruitReader.readCovariant(fruits);
        Fruit fruit1 = fruitReader.readCovariant(apples);
    }
    public static void main(String[] args) {
        f1();f2();f3();
    }
}

class Fruit {
}

class Apple extends Fruit {
}

class Jonathan extends Apple {
}

class Orange extends Fruit {
}
