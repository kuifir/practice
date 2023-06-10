package com.kuifir.normal.reference;

import java.lang.ref.*;
import java.util.LinkedList;

/**
 * {@link java.lang.ref}库包含一组类，给垃圾收集提供了更大的灵活性。
 * 当存在可能耗尽内存的大对象时，这些类特别有用。
 * 有3个继承自抽象类{@link java.lang.ref.Reference}的类:
 * <ul>
 *     <li>{@link java.lang.ref.SoftReference}</li>
 *     <li>{@link java.lang.ref.WeakReference}</li>
 *     <li>{@link java.lang.ref.PhantomReference}</li>
 * </ul>
 * 如果讨论中的对象<b>只能</b>通过这些Reference对象中的某一个来访问，那么他们可以为垃圾收集器提供不同层次的间接控制。
 * <p></p>
 * 如果某个对象是可达的，就意味着可以在程序中的某个地方找到它。这可能意味着：
 * <ul>
 *     <li>在栈上有一个普通的引用，直接指向这个对象；</li>
 *     <li>我们有一个指向某个对象的引用，而被指向的对象中又有一个引用，指向我们讨论的对象；</li>
 *     <li>也许存在多个中间链接</li>
 * </ul>
 * 如果某个对象是可达的，垃圾收集器就不能释放它，因为它还在被我们的程序所使用。
 * 如果某个对象不可达，我们的程序没有办法使用它，那么对这个对象执行垃圾收集就是安全的。
 * <p></p>
 * 因为我们可以使用Reference对象继续持有一个指向那个对象的引用，所以那个对象就是可达的。
 * 不过我们也允许垃圾收集器释放该对象。因此，我们有办法使用这个对象，但是如果内存即将耗尽，也可以释放它。
 * <p></p>
 * 我们是这么做的：使用一个Reference对象作为我们和普通引用之间的中介（一个代理）。
 * 另外，不能存在指向该对象的其他普通引用，如果垃圾收集器发现某个对象可以通过一个普通引用访问到，它就不会释放这个对象，
 * <p></p>
 * SoftReference、WeakReference和PhantomReference的顺序对应不同级别的可达性，后面的比前面的"更弱"。
 * <ul>
 *     <li>软引用用于实现对内存敏感的缓存。</li>
 *     <li>弱引用用于实现规范映射(canonicalizing mapping)——为节省存储空间，
 *     对象的实例可以同时在程序中的多个位置使用——这不会妨碍它们的键（或值）被回收</li>
 *     <li>与Java终结机制相比，虚引用用于以更灵活的方式安排事后（post-mortem）清理动作。</li>
 * </ul>
 * 而且请注意，从Java9开始，Object.finalize()已被废弃。事实证明，从Java诞生之初，他就是一个糟糕的、容易引起误解的想法。
 * <p></p>
 * 在使用SoftReference和 WeakReference时，我们可以选择是否将其放到一个{@link java.lang.ref.ReferenceQueue}中（该队列用于事后清理动作）、
 * 但是PhantomReference只能放到ReferenceQueue中。
 *
 * @author kuifir
 * @date 2023/6/10 16:15
 * @see java.lang.ref.Reference
 * @see java.lang.ref.SoftReference
 * @see java.lang.ref.WeakReference
 * @see java.lang.ref.PhantomReference
 * @see java.lang.ref.ReferenceQueue
 */
public class References {
    private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<>();

    private static void checkQueue() {
        Reference<? extends VeryBig> inq = rq.poll();
        if (inq != null) {
            System.out.println("In que " + inq.get());
        }
    }

    public static void main(String[] args) {
        int size = 10;
        // 或者通过命令选择大小：
        if (args.length > 0) {
            size = Integer.valueOf(args[0]);
        }
        LinkedList<SoftReference<VeryBig>> sa = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            sa.add(new SoftReference<>(new VeryBig("Soft " + i), rq));
            System.out.println("Just created:　" + sa.getLast());
            checkQueue();
        }

        LinkedList<WeakReference<VeryBig>> wa = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            wa.add(new WeakReference<>(new VeryBig("Weak " + i), rq));
            System.out.println("Just created: " + wa.getLast());
            checkQueue();
        }
        SoftReference<VeryBig> s = new SoftReference<>(new VeryBig("Soft"));
        WeakReference<VeryBig> w = new WeakReference<>(new VeryBig("Weak"));
        System.gc();
        System.out.println(s.get());
        System.out.println(w.get());
        LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            pa.add(new PhantomReference<>(new VeryBig("Phantom " + i),rq));
            System.out.println("Just created: " + pa.getLast());
            checkQueue();
        }
    }
}

class VeryBig {
    private static final int SIZE = 10000;
    private long[] la = new long[SIZE];
    private String ident;

    VeryBig(String ident) {
        this.ident = ident;
    }

    @Override
    public String toString() {
        return ident;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected void finalize() {
        System.out.println("Finalizing " + ident);
    }
}
