package com.kuifir.basic.completableFuture;

import com.kuifir.basic.task.Nap;

/**
 * 下面这个类通过静态的work()方法对该类对象执行了某些操作。
 * 这其实是个<b>有限状态机</b>，不过它是随便写的，因为并没有分支。
 * 它只是从一条路径的头部移动到尾部。work()方法使状态机移动到下一个状态，冰球求了100毫秒来执行该"work"。
 *
 * @author kuifir
 * @date 2023/6/25 22:39
 */
public class Machina {
    private State state = State.START;
    private final int id;

    public Machina(int id) {
        this.id = id;
    }

    public static Machina work(Machina m) {
        if (!m.state.equals(State.END)) {
            new Nap(0.1);
            m.state = m.state.step();
        }
        System.out.println(m);
        return m;
    }

    @Override
    public String toString() {
        return "Machina" + id + ": " + (state.equals(State.END) ? "complete" : state);
    }

    public enum State {
        /**
         * 状态机
         */
        START, ONE, TWO, THREE, END;

        State step() {
            if (equals(END)) {
                return END;
            }
            return values()[ordinal() + 1];
        }
    }
}
