package com.kuifir.normal.cleanup;

/**
 * 清理共享对象
 * Forg对象拥有它的成员对象.
 * 它创建它们,并且直到只要自己还存活着,这些成员对象就业要能正常工作,所以它知道什么时候对成员对象进行dispose()
 * 但是,如果其中某个成员对象被其它对象多共享,则问题会变得更加复杂,此时就不能简单的使用dispose().
 * 在这里可能需要使用 引用计数 来跟踪访问共享对象的对象数量
 *
 * @author kuifir
 * @date 2023/5/15 0:19
 */
public class ReferenceCounting {
    public static void main(String[] args) {
        Shared shared = new Shared();
        Composing[] composings = {
                new Composing(shared),
                new Composing(shared),
                new Composing(shared),
                new Composing(shared),
                new Composing(shared),
        };
        for (Composing composing : composings) {
            composing.dispose();
        }
    }
}

class Shared {
    private int refcount = 0;
    private static int counter = 0;
    private final long id = counter++;

    Shared() {
        System.out.println("Creating" + this);
    }

    public void addRef() {
        refcount++;
    }

    protected void dispose() {
        if (--refcount == 0) {
            System.out.println("Disposing" + this);
        }
    }

    @Override
    public String toString() {
        return "Shared" + id;
    }
}

class Composing {
    private Shared shared;
    private static long counter = 0;
    private final long id = counter++;

    Composing(Shared shared) {
        System.out.println("Creating" + this);
        this.shared = shared;
        this.shared.addRef();
    }

    protected void dispose() {
        System.out.println("disposing" + this);
        shared.dispose();
    }

    @Override
    public String toString() {
        return "Composing" + id;
    }
}
