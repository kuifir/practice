package com.kuifir.normal.generic.createArrayOfGeneric;

/**
 * 有时候需要创建泛型类型的数组（例如ArrayList,其内部实现使用了数组）。
 * 你可以定一个泛型引用，指向一个数组，以满足编译器的要求：
 * <pre>{@code
 * public class ArrayOfGenericReference {
 *     static Generic<Integer>[] gia;
 * }
 * class Generic<T>{}
 * }
 * 编译器接受了这种方式，没有产生警告。但是你永远无法创建该确切类型（包括类型参数）的数组，因此这有点让人疑惑。
 * 所有的数组不论持有的是什么类型，都有着相同的结构（包括每个数组元素的大小和布局），
 * 因此你似乎可以创建一个Object数组，并将其转换为目标数组类型。
 * 这确实可以通过编译，但会在运行时抛出{@link ClassCastException}
 * <p></p>
 * 问题在于数组时刻都掌握着他们的实际类型信息，而该类型是在创建数组时刻确定的。
 * 因此尽管gia被转型为{@code Generic<Integer>[]},该信息也只会存在于编译时。
 * 在运行时，他仍然还是Object数组，二这会导致问题。
 * 唯一可以成功创建泛型类型数组的方法就是创建一个类型为被擦除类型的新数组，然后再对其进行类型转换。{@link GenericArray}
 *
 * @see com.kuifir.normal.generic.ErasedProblem
 * @see GenericArray
 * @see ClassCastException
 * @author kuifir
 * @date 2023/5/26 23:30
 */
public class ArrayOfGenericReference {
    private static final int SIZE = 100;
    static Generic<Integer>[] gia;

    public static void main(String[] args) {
        try {
            gia= (Generic<Integer>[]) new Object[SIZE];        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // 运行时的类型是原始类型（已被擦除）
        gia = (Generic<Integer>[]) new Generic[SIZE];
        // Generic[]
        System.out.println(gia.getClass().getSimpleName());
        gia[0] = new Generic<>();
        // 编译时错误
        // gia[1] = new Object();
        // 在编译时发现类型不匹配
        // gia[2] = new Generic<Double>();
    }
}

class Generic<T> {
}