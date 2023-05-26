package com.kuifir.normal.generic.createArrayOfGeneric;

import java.lang.reflect.Array;

/**
 * 这里需要将类型标记Class<T>传入了构造器，以用于擦除后的类型回复，
 * 这样就能够创建实际所需类型的数组了，尽管还是必须通过{@link SuppressWarnings}禁用类型转换导致的警告。
 * 如在main()中所见，一旦得到了实际的类型，就可以将其放回，并生成想要的结果。数组的运行时类型是精确的T[]类型。
 *
 * @author kuifir
 * @date 2023/5/27 1:37
 * @see GenericArray2
 */
public class GenericArrayWithTypeToken<T> {
    private T[] array;

    public GenericArrayWithTypeToken(Class<T> type, int size) {
        array = (T[]) Array.newInstance(type, size);
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    /**
     * 漏潜在的表达方式
     */
    public T[] rep() {
        return array;
    }

    public static void main(String[] args) {
        GenericArrayWithTypeToken<Integer> gai = new GenericArrayWithTypeToken<>(Integer.class, 10);
        // 现在可以正常运行了
        Integer[] rep = gai.rep();
    }

}
