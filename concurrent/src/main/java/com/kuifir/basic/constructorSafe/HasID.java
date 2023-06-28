package com.kuifir.basic.constructorSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 构造器并不是线程安全的：
 * 想象一下对象的构造过程，很容易认为其是线程安全的。
 * 毕竟，在新对象完成初始化之前，甚至都没人能看到这个对象，又怎么可能去竞争该对象呢？
 * 的确，Java语言规范(Java Language Specification,JLS)明确地说过：
 * <p>
 *     将构造器设为同步并没有实际意义，因为这样做会阻塞正在构造的对象。在对象所有构造器完成工作之前，其他线程通常无法使用该对象。
 * </p>
 * 不幸的是，对象的构造过程和其他任何场景一样，面对共享内存的并发问题时都是脆弱的，然而，其机制可能更加微妙。
 * 思考一下用一个静态字段为每个对象自动创建一个唯一标识的过程，为了测试不同的实现，从一个接口开始：{@link HasID},
 * 然后简单实现该接口{@link StaticIDField}:
 * 这大概是你能想到的最简单稳妥的类了。它甚至都没有一个可能带来麻烦的显示构造器。
 * 为了看看生成多个创建对象的并发任务时会发生什么，接下来写了一个测试工具：{@link IDChecker}
 * 测试类:{@link TestStaticIDField}
 * <p></p>
 * 重复的id数量相当多。显然，单纯的static int对于构造过程来说并不安全。下面通过{@link AtomicInteger}来让过程变为线程安全的。
 * 实现类{@link GuardedIDField}
 * <p></p>
 * 构造器甚至有种更巧妙的办法来共享状态，即构造器参数{@link ShardedConstructorArgument}
 * 虽然语言层面并不支持synchronized修饰的构造器，但是可以通过synchronized语句块，来创建自己的（同步）构造器。
 * 虽然JLS声明"...这会阻塞正在创建的对象"，但这并不是真的——构造器事实上是个静态方法，
 * 因此synchronized的构造器实际上会阻塞Class对象。
 * 我们可以通过创建自己的静态对象并对它上锁来复现该过程：{@link SyncConstructor}
 * 对Unsafe类的共享现在是安全的了。
 * <p></p>
 * 另一种方法是将构造器设为私有的(因此会阻止继承)，并实现一个静态的<b>工厂方法</b>来生成新对象{@link SynchronizedFactory}
 * 通过将静态工厂方法设为同步的，你在构造过程中对Class对象上了锁。
 * 以上这些事例强调了在并发Java程序中，发现和管理共享状态是多么难以捉摸。即使你采用了"什么都不共享"的策略，意外的共享还是格外容易发生。
 * @author kuifir
 * @date 2023/6/28 22:54
 */
public interface HasID {
    int getID();
}
