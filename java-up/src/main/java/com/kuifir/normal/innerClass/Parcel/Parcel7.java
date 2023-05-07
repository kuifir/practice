package com.kuifir.normal.innerClass.Parcel;

import com.kuifir.normal.innerClass.Contents;

/**
 * 匿名内部类的基类 是无参构造器
 * @author kuifir
 * @date 2023/5/7 11:56
 */
public class Parcel7 {
    public Contents contents(){
        return new Contents() {
            private int i = 11;
            @Override
            public int value() {
                return i;
            }
        };
    }
    public static void main(String[] args) {
        Parcel7 parcel7 = new Parcel7();
        Contents contents = parcel7.contents();
        // Parcel7$1@277050dc
        System.out.println(contents);
        System.out.println(contents.value());
    }
}
