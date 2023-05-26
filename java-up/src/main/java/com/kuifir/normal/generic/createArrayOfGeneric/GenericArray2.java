package com.kuifir.normal.generic.createArrayOfGeneric;

/**
 * 乍一看，这好像没多大变化，只是改变了类型转换的地方。
 * 然而现在内部表达所用的是Object[],而不是T[].
 * 在调用get()方法时，该方法会将对象转型为T,这实际上是正确的类型，因此是安全的。
 * 不过如果调用rep(),该方法会再次试图将Object[]转型为T[]，这仍然是错误的，并且会产生编译时警告和运行时异常。
 * 因此，没有任何办法可以推翻底层的数组类型，该类型只能是Object[]。
 * 在内部将array当作Object[]而不是T[]来处理，这样做的好处是，可以减少由于你忘记了数组的运行时类型，而意外产生bug的可能性。
 * <p></p>
 * 对于新代码，应该传入一个类型表及，这种情况下的GenericArray看起来应该是这样：{@link GenericArrayWithTypeToken}
 *
 * @see GenericArrayWithTypeToken
 * @see GenericArray
 * @author kuifir
 * @date 2023/5/27 1:19
 */
public class GenericArray2<T> {
    private Object[] array;

    public GenericArray2(int size) {
        array = new Object[size];
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return (T) array[index];
    }

    public T[] rep() {
        return (T[]) array;
    }

    public static void main(String[] args) {
        GenericArray2<Integer> gai = new GenericArray2<>(10);
        for (int i = 0; i < 10; i++) {
            gai.put(i, i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.print(gai.get(i) + " ");
        }
        System.out.println();
        try {
            Integer[] rep = gai.rep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
