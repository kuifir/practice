package com.kuifir.normal.funtional.methodReference;

/**
 * 未绑定对象的方法引用：有方法有更多参数的未绑定方法
 * 有方法有更多参数，只要遵循第一个参数取得是this这种模式{@link UnboundMethodReference}
 * @see UnboundMethodReference
 * @author kuifir
 * @date 2023/5/13 15:33
 */
public class MultiUnbound {
    public static void main(String[] args) {
        TowArgs towArgs = This::two;
        ThreeArgs threeArgs = This::three;
        FourArgs fourArgs = This::four;
        This athis = new This();
        towArgs.call2(athis,11,3.14);
        threeArgs.call3(athis,11,3.14,"Three");
        fourArgs.call4(athis,11,3.14,"Four",'z');
    }
}
class This{
    void two(int i, double d){}
    void three(int i, double d,String s){}
    void four(int i, double d, String s,char c){}
}
interface TowArgs{
    void call2(This athis, int i,double d);
}
interface ThreeArgs{
    void call3(This athis, int i,double d,String s);
}
interface FourArgs{
    void call4(This athis, int i,double d,String s,char c);
}
