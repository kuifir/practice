package com.kuifir.serialization.persistent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * @author kuifir
 * @date 2023/7/6 0:15
 */
public class RecoverCADState {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("CADState.dat"))) {
            // 按照他们的写入顺序读取:
            List<Class<? extends Shape>> shapeTypes = (List<Class<? extends Shape>>) in.readObject();
            Line.deserializedStaticState(in);
            List<Shape> shapes = (List<Shape>) in.readObject();
            System.out.println(shapes);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
