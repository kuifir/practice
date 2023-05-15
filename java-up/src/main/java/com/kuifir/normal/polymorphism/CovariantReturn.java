package com.kuifir.normal.polymorphism;

/**
 * 协变返回类型
 * Java 5添加了协变返回类型(covariant return type),这表示子类中重写方法的返回值可以是基类方法返回值的子类型
 * <p></p>
 * Java 5与之前的版本主要区别在于，其之前版本强制要求process()的重写版本返回Grain,而不能是Wheat.
 * 即使 (Wheat继承自Grain,因而也是一个合法的返回类型)。
 * 协变返回类型允许更具体的Wheat返回类型。
 * @author kuifir
 * @date 2023/5/15 21:08
 */
public class CovariantReturn {
    public static void main(String[] args) {
        Mill m = new Mill();
        Grain g = m.process();
        System.out.println(g);
        m = new WheatMill();
        g = m.process();
        System.out.println(g);
    }

}
class Grain{
    @Override
    public String toString() {
        return "Grain";
    }
}
class Wheat extends Grain{
    @Override
    public String toString() {
        return "Wheat";
    }
}
class Mill{
    Grain process(){
        return new Grain();
    }
}
class WheatMill extends Mill{
    @Override
    Wheat process() {
        return new Wheat();
    }
}