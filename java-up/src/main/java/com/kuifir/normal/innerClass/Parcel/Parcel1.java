package com.kuifir.normal.innerClass.Parcel;

/**
 * @author kuifir
 */
public class Parcel1 {
    public static void main(String[] args) {
        Parcel1 p = new Parcel1();
        p.ship("Tasmania");
    }

    /**
     * 在Parcel1内，使用内部类看上去就和使用任何其它类一样
     */
    public void ship(String dest){
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
    class Destination{
        private String label;
        Destination(String whereTo){
            label = whereTo;
        }
        String readLabel(){
            return label;
        }
    }
}
