package com.kuifir.normal.innerClass.Parcel;

/**
 * 将.new 应用于 Parcel
 * 非静态内部类的创建:除非已经有了一个外部类的对象，否则创建内部类是不可能的。
 * 这是因为内部类的对象会暗中连接到用于创建它的外部类对象。
 * 然而，如果创建的是嵌套类（static 修饰的内部类），它就不需要指向外部类对象的引用。
 *
 * @author kuifir
 * @date 2023/5/6 21:40
 */
public class Parcel3 {
    public static void main(String[] args) {
        Parcel3 parcel3 = new Parcel3();
        // 必须使用外部类的实例来创建内部类的实例
        Parcel3.Contents contents = parcel3.new Contents();
        Parcel3.Destination tasmania = parcel3.new Destination("Tasmania");
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
