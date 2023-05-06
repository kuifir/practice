package com.kuifir.normal.innerClass.Parcel;

/**
 * @author kuifir
 * @date 2023/5/6 23:22
 */
public class Parcel6 {
    private void internalTracking(boolean b) {
        // TrackingSlip类被嵌入一个if语句的作用域内。这并不是说该类的创建是由田间的，它会和其他所有代码一起编译。
        // 然而它在定义它的作用域之外是不可用的，除此之外，它看上去就像普通的类一样。
        if (b) {
            class TrackingSlip {
                private String id;

                TrackingSlip(String s) {
                    id = s;
                }
                String getSlip(){return id;}
            }
            TrackingSlip ts = new TrackingSlip("slip");
            // Parcel6$1TrackingSlip@4b85612c
            System.out.println(ts);
            String slip = ts.getSlip();
            System.out.println(slip);
        }
        // 这里不能使用,已经出了作用域
        // - TrackingSlip ts = new TrackingSlip("slip");
    }
    public void track(){
        internalTracking(true);
    }

    public static void main(String[] args) {
        Parcel6 parcel6 = new Parcel6();
        parcel6.track();
    }
}
