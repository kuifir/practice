package com.kuifir.serialization.controlSerialization;

import java.io.*;

/**
 * 默认的序列化机制使用起来很简单。但是如果有特殊需求呢？
 * 也许你有特殊的安全问题，不想序列化对象的某些部分；也许某个子对象在反序列化时需要重新创建，这样的话，序列化这个子对象就没有意义了。
 * 你可以通过实现{@link java.io.Externalizable}接口而不是Serializable接口来控制序列化过程。
 * {@link java.io.Externalizable}接口扩展可Serializable接口
 * 并添加了两个方法{@link Externalizable#writeExternal(ObjectOutput)}和{@link Externalizable#readExternal(ObjectInput)}.
 * 它们在序列化和反序列化期间会自动调用，这样你就可以执行自己的特殊操作了。
 * <p></p>
 * 注意{@link Blips} 和{@link Blip2} 的细微区别，Blip2 的构造器是非public的，这在恢复时导致了异常。
 * 当回复b1时，Blip1的无参构造器被调用。这与恢复Serializable对象不同,后者完全从其存储的字节位里构建对象，没有调用构造器。
 * 在使用{@link Externalizable}对象时，所有正常的默认构造行为都会发生(包括在字段定义出的初始化)，之后readExternal()被调用。
 * 请注意这一点，特别是所有默认的构造总是会发生，这样才能在Externalizable对象中产生正确的行为。
 * 下个示例显示了完全存储和恢复Externalizable对象必须执行的操作 {@link Blip3}
 *
 * @author kuifir
 * @date 2023/7/4 22:31
 */
public class Blips {
    public static void main(String[] args) throws RuntimeException {
        System.out.println("Constructor objects");
        Blip1 b1 = new Blip1();
        Blip2 b2 = new Blip2();
        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Blips.serialized"))
        ) {
            System.out.println("Saving objects: ");
            out.writeObject(b1);
            out.writeObject(b2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 现在恢复它们
        System.out.println("Recovering b1");
        try (
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("Blips.serialized"))
        ) {
            b1 = (Blip1) in.readObject();
            // 抛出异常:Blip2 的构造器是非public的，这在恢复时导致了异常。
            System.out.println("Recovering b2");
            b2 = (Blip2) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

class Blip1 implements Externalizable {

    public Blip1() {
        System.out.println("Bilp1 Constructor");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip1.writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blips.readExternal");
    }
}

class Blip2 implements Externalizable {
    Blip2() {
        System.out.println("Blips2 Constructor");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blips2.writeExternal");
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blips2.readExternal");
    }
}