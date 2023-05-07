package com.kuifir.normal.innerClass.Parcel;

import com.kuifir.normal.innerClass.Contents;
import com.kuifir.normal.innerClass.Wrapping;

/**
 * 匿名内部类的基类是有参构造器
 * @author kuifir
 * @date 2023/5/7 11:56
 */
public class Parcel8 {
   public Wrapping wrapping(int x){
       // 基类构造器调用：
       // 将适当的参数传给基类构造器
       // 尽管Wrapping 是一个带有实现类的普通类，但是它也被用作其子类的公共"接口"
       return new Wrapping(x){
           @Override
           public int value() {
               return super.value()*47;
           }
       };
   }
    public static void main(String[] args) {
        Parcel8 parcel8 = new Parcel8();
        Wrapping wrapping = parcel8.wrapping(10);
        // Parcel8$1@277050dc
        System.out.println(wrapping);
        System.out.println(wrapping.value());
    }
}
