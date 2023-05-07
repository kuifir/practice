package com.kuifir.normal.innerClass;

/**
 * 嵌套类可以是接口的一部分。放到接口中的任何类都会自动成为 public和 static的。
 * 因为类是static的，所以被嵌套的类只是放在了这个接口的命名空间内。
 * 甚至可以在内部类内实现包围它的这个接口。
 *
 * @author kuifir
 * @date 2023/5/7 16:22
 */
public interface ClassInInterface {
    /**
     * 接口
     */
    void howdy();

    class Test implements ClassInInterface{

        @Override
        public void howdy() {
            System.out.println("Howdy");
        }
    }

    /**
     * 内部类实现外部接口
     * @param args
     */
    static void main(String[] args) {
        new Test().howdy();
    }

}
