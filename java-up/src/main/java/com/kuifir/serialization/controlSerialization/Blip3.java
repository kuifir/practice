package com.kuifir.serialization.controlSerialization;

import java.io.*;

/**
 * 字段s,i仅在第二个构造器中进行了初始化，在无参构造器没有初始化。
 * 这意味着，若果你没有在readExternal()中 初始化s和i,那么s就是null,i就是零（对象的存储在其创建的第一步被擦除为零）。
 * 如果注释掉 readExternal()中的内容，则s为null、i为零。
 * 如果你继承了一个Externalizable对象，通常需要调用writeExternal()和readExternal()的基类版本，
 * 从而提供基类组建的正确存储和恢复。
 * 因此为了使序列化正常，不仅需要在 writeExternal()方法里写入对象的重要数据（序列化机制不会默认为Externalizable对象写入任何成员对象），
 * 而且需要在readExternal()方法中恢复该数据。起初这可能有点令人困惑，因为Externalizable对象的默认构造器行为有可能
 * 使它看起来像存在某种自动存储和恢复行为，而这实际上是没有的。
 * @author kuifir
 * @date 2023/7/4 23:00
 */
public class Blip3 implements Externalizable {
    private int i;
    private String s;
    /**
     * 没有执行初始化
     * 没有无参构造器，会报错
     */
    public Blip3() {
        System.out.println("Blip3 Constructor");
    }



    public Blip3(int i, String s) {
        System.out.println("Blip3(int i, String s)");
        this.i = i;
        this.s = s;
    }

    @Override
    public String toString() {
        return s + i;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        System.out.println("Blip3.writeExternal");
        // 必须这样做
        out.writeObject(s);
        out.writeInt(i);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        System.out.println("Blip3.readExternal");
        // 必须这样做
        s = (String) in.readObject();
        i = in.readInt();
    }

    public static void main(String[] args) {
        System.out.println("Constructing objects: ");
        Blip3 b3 = new Blip3(47, "A String ");
        System.out.println(b3);
        try (
                ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("Blip3.serialized"))
        ) {
            System.out.println("Saving object:");
            o.writeObject(b3);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 现在进行反序列化
        System.out.println("Recovering b3");
        try (
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("Blip3.serialized"))
        ) {
            b3 = (Blip3) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(b3);
    }
}
