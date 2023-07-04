package com.kuifir.serialization.findClass;

import com.kuifir.serialization.findClass.xfiles.ThawAlien;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 创建一个序列化输出文件
 * {@link ThawAlien}
 *
 * @author kuifir
 * @date 2023/7/4 22:14
 */
public class FreezeAlien {
    public static void main(String[] args) {
        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("X.file"))
        ) {
            Alien quellek = new Alien();
            out.writeObject(quellek);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
