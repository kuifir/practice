package com.kuifir.normal.innerClass;

/**
 * 更普遍的情况是，外部类有一个方法，该方法返回一个只想内部类的引用，
 * 正如在{@link Parcel2#to(String)}
 * {@link Parcel2#contents()}方法中看到的那样
 *
 * @author kuifir
 */
public class Parcel2 {
    public static void main(String[] args) {
        Parcel2 p = new Parcel2();
        p.ship("Tasmania");
        Parcel2 q = new Parcel2();
        // 定义指向内部类的引用
        Parcel2.Contents contents = q.contents();
        Parcel2.Destination borneo = q.to("Borneo");
    }

    public Destination to(String s) {
        return new Destination(s);
    }

    public Contents contents() {
        return new Contents();
    }

    /**
     * 使用内部类看上去就和使用任何其它类一样
     */
    public void ship(String dest) {
        Contents c = new Contents();
        Destination d = new Destination(dest);
        System.out.println(d.readLabel());
    }

    class Contents {
        private int i = 11;

        public int value() {
            return i;
        }
    }

    class Destination {
        private String label;

        Destination(String whereTo) {
            label = whereTo;
        }

        String readLabel() {
            return label;
        }
    }
}
