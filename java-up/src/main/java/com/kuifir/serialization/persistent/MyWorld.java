package com.kuifir.serialization.persistent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用持久性：
 * 我们可以使用序列化技术来存储程序的一些状态，方便以后轻松地将程序恢复到当前状态，这很有吸引力。
 * 但在执行操作之前，你必须回答一些问题。
 * 如果序列化的两个对象都具有对第三个对象的引用，会发生什么呢？
 * 当你从两个对象的序列化状态恢复它们时，第三个对象是否只生成一次？
 * 如果将两个对象序列化为单独的文件，并在代码的不同部分反序列化它们，又会怎么样？
 * 下面是一个演示了这些问题的示例:{@link MyWorld}
 * <p></p>
 * 我们将对象序列化到byte数组或从byte数组中反序列化,这也昂就可以对任意Serializable对象进行<b>深拷贝</b>(deep copy)了.
 * 深拷贝意味着要复制整个对象网络,而不仅仅是基本对象及其引用.
 * <p>
 * {@link Animal} 对象包含类型为{@link House}类型的字段.在main()中,我们创建并序列化了含有这些Animal的List,
 * 两次序列化到同一个流,然后序列化到另一个单独的流.
 * 当它们被反序列化并打印时,你可以看到某次运行时显示的输出(每次运行时对象位于不同的内存位置)
 * <p>
 * 你可能期望反序列化的对象与原始对象具有不同的地址.
 * 但是请注意,在animals1和animals2中出现了相同的地址,包括两者共享的House对象引用.
 * 另外,当animals3恢复时,系统无法知道这个流中的对象是第一个流中对象的别名,因此它构建了一个完全不同的对象网络.
 * <p>
 * 只要将所有内容序列化到同一个流,你就可以恢复自己序列化时的对象网络,而不会出现意外的重复对象.
 * 可以在序列化第一个和最后一个对象之间更改对象的状态,但这就要你自己负责了,
 * 对象在序列化它们时是以其当前状态(以及它们与其他对象的任何连接)写入的.
 * <p>
 * 保存系统状态的最安全方法是"原子"地序列化该状态.
 * 如果你序列化了一些东西,然后做一些其他的工作,在序列化一些,等等,那么就不能安全地存储系统.
 * 相反,要将构成系统状态的所有对象放在一个容器中,然后再一次操作中序列化该容器.这样,你就可以通过一个方法调用来恢复它了.
 *
 * @author kuifir
 * @date 2023/7/5 23:00
 */
public class MyWorld {
    public static void main(String[] args) {
        House house = new House();
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("Bosco the dog ", house));
        animals.add(new Animal("Ralph the hamster ", house));
        animals.add(new Animal("Molly the cat ", house));
        System.out.println("animals: " + animals);
        try (
                ByteArrayOutputStream buf1 = new ByteArrayOutputStream();
                ObjectOutputStream o1 = new ObjectOutputStream(buf1)
        ) {
            o1.writeObject(animals);
            // 再次序列化
            o1.writeObject(animals);
            // 写到另一个流里
            try (
                    ByteArrayOutputStream buf2 = new ByteArrayOutputStream();
                    ObjectOutputStream o2 = new ObjectOutputStream(buf2)
            ) {
                o2.writeObject(animals);
                // 下面进行反序列化
                try (
                        ObjectInputStream in1 = new ObjectInputStream(new ByteArrayInputStream(buf1.toByteArray()));
                        ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(buf2.toByteArray()));
                ) {
                    List animals1 = (List) in1.readObject();
                    List animals2 = (List) in1.readObject();
                    List animals3 = (List) in2.readObject();
                    System.out.println("animals1: " + animals1);
                    System.out.println("animals2: " + animals2);
                    System.out.println("animals3: " + animals3);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class House implements Serializable {
}

class Animal implements Serializable {
    private String name;
    private House preferredHouse;

    public Animal(String name, House preferredHouse) {
        this.name = name;
        this.preferredHouse = preferredHouse;
    }

    @Override
    public String toString() {
        return name + "[" + super.toString() + "]" + preferredHouse + "\n";
    }
}