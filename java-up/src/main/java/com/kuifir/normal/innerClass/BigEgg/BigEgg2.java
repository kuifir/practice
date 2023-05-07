package com.kuifir.normal.innerClass.BigEgg;

/**
 * @author kuifir
 * @date 2023/5/7 23:49
 */
public class BigEgg2 extends Egg2{
    /**
     * BigEgg2.Yolk明确地继承了Egg2.Yolk,而且重写了其方法。
     */
    public class Yolk extends Egg2.Yolk{
        public Yolk(){
            System.out.println("BigEgg2.Yolk()");
        }

        @Override
        public void f() {
            System.out.println("BigEgg2.Yolk.f()");
        }
    }
    public BigEgg2(){
        insertYolk(new Yolk());
    }

    public static void main(String[] args) {
        Egg2 egg2 = new BigEgg2();
        egg2.g();
        //Egg2.Yolk
        //new Egg2()
        //Egg2.Yolk 第二次调用是BigEgg2.Yolk调用基类构造器时触发的。
        //BigEgg2.Yolk()
        //BigEgg2.Yolk.f()
    }
}

class Egg2 {
    private Yolk y = new Yolk();

    Egg2() {
        System.out.println("new Egg2()");
    }

    public void insertYolk(Yolk yy) {
        y = yy;
    }

    public void g() {
        y.f();
    }

    protected class Yolk {
        public Yolk() {
            System.out.println("Egg2.Yolk");
        }

        public void f() {
            System.out.println("Egg2.Yolk.f()");
        }
    }
}
