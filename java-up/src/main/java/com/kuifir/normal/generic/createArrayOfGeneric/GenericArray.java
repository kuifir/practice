package com.kuifir.normal.generic.createArrayOfGeneric;

/**
 * 正如{@link com.kuifir.normal.generic.ErasedProblem}所述，
 * 我们无法声明{@code T[] array = new T[size]}.因此我们可以创建一个对象数组，并对它进行转型。
 * {@link GenericArray#rep()}方法返回一个{@code T[]}，
 * 对应到main()中的{@code gai.rep()}则应该返回Integer[].
 * 但如果你调用了rep()并试图将结果作为Integer[]的引用来获取，便会抛出{@link ClassCastException}异常，这仍然是因为运行时类型实际时Object[]
 *<p></p>
 * 由于泛型擦除的缘故，数组的运行时类型只能是Object[]。
 * 如果我们立刻将其转型为T[]，那么在编译时，数组的实际类型便会丢失，编译器就可能会错过对某些潜在错误的检查。
 * 因此，更好的办法是在集合内使用Object[]，并在使用某个数组元素的时候增加转型为T的操作。{@link GenericArray2}
 *
 * @author kuifir
 * @date 2023/5/27 0:49
 * @see com.kuifir.normal.generic.ErasedProblem
 */
public class GenericArray<T> {
    private T[] array;

    public GenericArray(int size) {
        array = (T[]) new Object[size];
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    /**
     * 暴漏了潜在表现形式的方法
     *
     * @return
     */
    public T[] rep() {
        return array;
    }

    public static void main(String[] args) {
        GenericArray<Integer> gai = new GenericArray<>(10);
        try {
            Integer[] rep = gai.rep();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // 这没问题
        Object[] rep = gai.rep();
    }
}
