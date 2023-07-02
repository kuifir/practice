package com.kuifir.normal.equalsHashCode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 子类型之间的相等性：
 * 继承的特性告诉我们，两个不同子类型的对象在向上强制转型后可以是"相同的"。
 * 假设现在有一个Animal对象的集合，这个集合只能接受Animal的子类型——本例中为Dog和Pig。
 * 每个Animal都有一个name和一个size,以及一个唯一的内部id。
 * <p></p>
 * {@link java.util.Objects}提供了equals()和hashCode()的经典形式定义来供我们使用。
 * 我们可以通过Objects类来定义经典形式的equals()和hashCode()方法，
 * 但本例中我们只在基类Animal中定义它们，并且这两个方法都不包含唯一的id。
 * 从equals()的角度来看，这意味着我们只关心某物是否为Animal,而不在乎它是否是特定的Animal:
 *
 * @author kuifir
 * @date 2023/7/2 23:01
 */
enum Size {SMALL, MEDIUM, LARGE}

class Animal {
    private static int counter = 0;
    private final int id = counter++;
    private final String name;
    private final Size size;

    public Animal(String name, Size size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public boolean equals(Object rval) {
        return rval instanceof Animal other
//                && Objects.equals(id,other.id)
                && Objects.equals(name, other.name)
                && Objects.equals(size, other.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size);
//        return Objects.hash(name,size,id);
    }

    @Override
    public String toString() {
        return String.format("%s[%d]: %s %s %x", getClass().getSimpleName(), id, name, size, hashCode());
    }

}

class Dog extends Animal {
    public Dog(String name, Size size) {
        super(name, size);
    }
}

class Pig extends Animal {

    public Pig(String name, Size size) {
        super(name, size);
    }
}

public class SubtypeEquality {
    public static void main(String[] args) {
        Set<Animal> pets = new HashSet<>();
        pets.add(new Dog("Ralph", Size.MEDIUM));
        pets.add(new Pig("Ralph", Size.MEDIUM));
        pets.forEach(System.out::println);
    }
}
