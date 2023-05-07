package com.kuifir.normal.innerClass.Parcel;

import com.kuifir.normal.innerClass.Destination;
import com.kuifir.normal.innerClass.Wrapping;

/**
 * 可以在定义匿名内部类的时候执行初始化
 * @author kuifir
 * @date 2023/5/7 11:56
 */
public class Parcel9 {
    /**
     *
     * @param dest: 要在匿名内部类中使用，参数必须是最终变量，或者"实际上的最终变量"(初始化之后它永远不会改变，可以被视为final的)
     * @return
     */
   public Destination destination(final String dest){
       return new Destination() {
           private String label = dest;
           @Override
           public String readLabel() {
               return label;
           }
       };
   }
    public static void main(String[] args) {
        Parcel9 parcel9 = new Parcel9();
        Destination destination = parcel9.destination("Tasmania");
        // Parcel9$1@277050dc
        System.out.println(destination);
        System.out.println(destination.readLabel());
    }
}
