package com.kuifir.serialization.findClass.xfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 要想让示例正常运行。JVM就必须能够找到相应的.class文件。
 *
 * @author kuifir
 * @date 2023/7/4 22:19
 */
public class ThawAlien {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("X.file")));
        Object mystery = in.readObject();
        System.out.println(mystery.getClass());
    }
}
