package com.kuifir.serialization;

import java.io.*;
import java.util.Random;

/**
 * @author kuifir
 * @date 2023/7/4 21:43
 */
class Data implements Serializable {
    private final int n;

    Data(int n) {
        this.n = n;
    }

    @Override
    public String toString() {
        return Integer.toString(n);
    }
}

/**
 * @author kuifir
 */
public class Worm implements Serializable {
    private static final Random RAND = new Random(47);
    private final Data[] d = {
            new Data(RAND.nextInt(10)),
            new Data(RAND.nextInt(10)),
            new Data(RAND.nextInt(10)),
    };
    private Worm next;
    private char c;

    /**
     * Value of i == number of segments
     */
    public Worm(int i, char x) {
        System.out.println("Worm constructor: " + i);
        c = x;
        if (--i > 0) {
            next = new Worm(i, (char) (x + 1));
        }
    }

    public Worm() {
        System.out.println("Zero-argument constructor");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(":");
        result.append(c);
        result.append("(");
        for (Data dat : d) {
            result.append(dat);
        }
        result.append(")");
        if (next != null) {
            result.append(next);
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Worm w = new Worm(6, 'a');
        System.out.println("w = " + w);
        try (
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("worm.dat"))
        ) {
            out.writeObject("Worm storage\n");
            out.writeObject(w);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("worm.dat"))
        ) {
            String s = (String) in.readObject();
            Worm w2 = (Worm) in.readObject();
            System.out.println(s + "w2 = " + w2);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                ObjectOutputStream out2 = new ObjectOutputStream(bout);
        ) {
            out2.writeObject("Worm storage\n");
            out2.writeObject(w);
            out2.flush();
            try (ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()))) {
                String s = (String) in2.readObject();
                Worm w3 = (Worm) in2.readObject();
                System.out.println(s + "w3= " + w3);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}