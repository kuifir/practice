package com.kuifir.normal.initialization;

/**
 * 带参数的构造器
 * 如果基类没有无参构造器,或者如果你必须调用具有参数的基类构造器.
 * 那么就要使用super关键字和相应的参数列表,来显示调用基类构造器
 * 如果不在BoardGame 的构造器中显示调用基类的构造器,编译器会报错,表示它找不到形式为Game()的构造器.
 * 另外,对基类构造器的调用必须时子类构造器的第一个操作(否则编译器会通过报错来提示)
 *
 * @see Cartoon
 * @author kuifir
 * @date 2023/5/14 20:05
 */
public class Chess extends BoardGame{
    Chess() {
        super(11);
        System.out.println("Chess constructor");
    }

    public static void main(String[] args) {
        Chess x = new Chess();
    }
}
class Game{
    Game(int i){
        System.out.println("Game constructor");
    }
}
class BoardGame extends Game{

    BoardGame(int i) {
        super(i);
        System.out.println("BoardGame constructor");
    }
}
