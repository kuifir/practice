package com.kuifir.serialization.persistent;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 保存一个虚构的CAD系统的状态
 * 下面的实例演示了一个虚构的计算机辅助设计(computer-aided design,CAD)系统.此外,它还抛出了static字段的问题.
 * 如果查看JDK文档,你会看到Class也实现了Serializable接口,因此应该很容易通过序列化Class对象来存储static字段.
 * 无论如何,这似乎是一个合理的做法.
 *
 * @author kuifir
 * @date 2023/7/5 23:39
 */
enum Color {RED, BLUE, GREEN}

abstract class Shape implements Serializable {
    private int xPos, yPos, dimension;

    Shape(int xPos, int yPos, int dimension) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.dimension = dimension;
    }

    public abstract void setColor(Color newColor);

    public abstract Color getColor();

    @Override
    public String toString() {
        return "\n" + getClass() + " " + getColor() +
                " xPos[" + xPos + "] yPos[" + yPos + "] dim[" + dimension + "]";
    }

    private static Random rand = new Random(47);
    private static int counter = 0;

    public static Shape randomFactory() {
        int xVal = rand.nextInt(100);
        int yVal = rand.nextInt(100);
        int dim = rand.nextInt(100);
        switch (counter++ % 2) {
            case default -> {
                return null;
            }
            case 0 -> {
                return new Circle(xVal, yVal, dim);
            }
            case 1 -> {
                return new Line(xVal, yVal, dim);
            }
        }
    }
}

class Circle extends Shape {
    private static Color color = Color.RED;

    public Circle(int xPos, int yPos, int dimension) {
        super(xPos, yPos, dimension);
    }

    @Override
    public void setColor(Color newColor) {
        color = newColor;
    }

    @Override
    public Color getColor() {
        return color;
    }
}

class Line extends Shape {
    private static Color color = Color.RED;

    public Line(int xPos, int yPos, int dimension) {
        super(xPos, yPos, dimension);
    }

    @Override
    public void setColor(Color newColor) {
        color = newColor;
    }

    @Override
    public Color getColor() {
        return color;
    }

    public static void serializedStaticState(ObjectOutputStream os) throws IOException {
        os.writeObject(color);
    }

    public static void deserializedStaticState(ObjectInputStream os) throws IOException, ClassNotFoundException {
        color = (Color) os.readObject();
    }
}

/**
 * @author kuifir
 */
public class AStoreCADState {
    public static void main(String[] args) {
        List<Class<? extends Shape>> shapeTypes = Arrays.asList(Circle.class, Line.class);
        List<Shape> shapes = IntStream.range(0, 5)
                .mapToObj(i -> Shape.randomFactory())
                .toList();
        // 将所有的静态字段color设置为GREEN:
        shapes.forEach(s -> s.setColor(Color.GREEN));
        // 将所有信息序列化到CADState.dat
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("CADState.dat"))) {
            out.writeObject(shapeTypes);
            Line.serializedStaticState(out);
            out.writeObject(shapes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 展示形状
        System.out.println(shapes);
    }
}
