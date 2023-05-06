package com.kuifir.normal.innerClass.Parcel;

import com.kuifir.normal.innerClass.Contents;
import com.kuifir.normal.innerClass.Destination;

/**
 * 当需要向上转型为基类，特别是接口时，内部类就更有吸引力了
 * （从实现某个接口的对象生成一个该接口类型的引用，其效果和 向上转型为某基类 在本质上是一样的。）
 * 这是因为内部类（接口的实现）对外部而言是不可见、不可用的，这便于隐藏实现。
 * <p/>
 * 外部获得的只是一个指向基类过接口的引用。
 *
 * @author kuifir
 * @date 2023/5/6 22:05
 */
public class Parcel4 {

    public Destination destination(String s){
        return new DestinationImpl(s);
    }
    public Contents contents(){
        return new ContentsImpl();
    }

    private class ContentsImpl implements Contents{
        private int i = 11;

        @Override
        public int value() {
            return i;
        }
    }
    protected final class DestinationImpl implements Destination{
        private String label;
        private DestinationImpl(String whereTo){
            label = whereTo;
        }

        @Override
        public String readLabel() {
            return label;
        }
    }
    public static void main(String[] args) {
        Parcel4 parcel4 = new Parcel4();
        Contents contents = parcel4.contents();
        Destination tasmania = parcel4.destination("Tasmania");
        // 其他类不能访问private类
        ContentsImpl contents1 = parcel4.new ContentsImpl();
        System.out.println(contents.value());
        System.out.println(tasmania.readLabel());
        System.out.println(contents1.value());
    }
}
