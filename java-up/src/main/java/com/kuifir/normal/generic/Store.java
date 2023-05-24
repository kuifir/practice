package com.kuifir.normal.generic;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

/**
 * 用泛型集合构建复杂模型
 *
 * @author kuifir
 * @date 2023/5/24 23:28
 */
public class Store extends ArrayList<Aisle> {
    private ArrayList<CheckoutStand> checkouts = new ArrayList<>();
    private Office office = new Office();

    public Store(int nAisles,int nShelves, int nProduces) {
        for (int i = 0; i < nAisles; i++) {
            add(new Aisle(nAisles,nProduces));
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Aisle shelves : this) {
            result.append("=======\n");
            for (Shelf shelf : shelves) {
                result.append("-----\n");
                for (Produce produce : shelf) {
                    result.append(produce);
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Store(5,4,3));
    }
}

class Produce {
    public static Supplier<Produce> generator = new Supplier<>() {
        private Random rand = new Random(47);

        @Override
        public Produce get() {
            return new Produce(rand.nextInt(1000), "Test", Math.round(rand.nextDouble() * 1000.0) + 0.99);
        }
    };
    private final int id;
    private String description;
    private double price;

    public Produce(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return id + " : " + description + ", price: $" + price;
    }

    public void priceChange(double change) {
        price += change;
    }
}

class Shelf extends ArrayList<Produce> {
    public Shelf(int nProduces) {
        Suppliers.fill(this, Produce.generator, nProduces);
    }
}

class Aisle extends ArrayList<Shelf> {
    public Aisle(int nShelf, int nProduces) {
        for (int i = 0; i < nShelf; i++) {
            add(new Shelf(nProduces));
        }
    }
}

class CheckoutStand {
}

class Office {
}
