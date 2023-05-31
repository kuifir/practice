package com.kuifir.normal.enums;

import java.util.Random;
import java.util.function.Supplier;

/**
 * 实现而不是继承
 * 我们已经确认所有的enum对象都继承自{@link java.lang.Enum}.
 * Java 不支持多继承，这意味着你无法通过以下这样的继承方式创建一个枚举对象。
 * <pre>{@code
 * enum NotPossible extends Pet{
 *     ..../
 * }
 * }</pre>
 * 不过可以创建实现了一个或多个接口的枚举类型。{@link CartoonCharacter}
 *
 * @see CartoonCharacter
 * @author kuifir
 * @date 2023/5/31 22:45
 */
public class EnumImplementation {
    public static <T> void printNext(Supplier<T> rg){
        System.out.print(rg.get()+",");
    }
    public static void main(String[] args) {
        // 选择任一实例
        CartoonCharacter cartoonCharacter = CartoonCharacter.BOB;
        for (int i = 0; i < 10; i++) {
            printNext(cartoonCharacter);
        }
    }
}

/**
 * 枚举实现接口
 */
enum CartoonCharacter implements Supplier<CartoonCharacter> {
    /**
     * 草率的
     */
    SLAPPY,
    /**
     * 浮夸
     */
    SPANKY,
    /**
     * 言简意赅的
     */
    PUNCHY,
    /**
     * 愚蠢的
     */
    SILLY,
    /**
     * 生气勃勃的
     */
    BOUNCY,
    /**
     * 疯癫的
     */
    NUTTY,
    BOB
    ;

    private Random rand = new Random(47);
    @Override
    public CartoonCharacter get() {
        return values()[rand.nextInt(values().length)];
    }
}