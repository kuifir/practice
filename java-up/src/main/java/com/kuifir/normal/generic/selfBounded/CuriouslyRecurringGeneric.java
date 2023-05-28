package com.kuifir.normal.generic.selfBounded;

/**
 * 泛型参数是无法直接继承的。不过，可以继承一个在自身定义中用到了该泛型参数的类。
 * 继Jim Coplien 在C++领域提出奇异递归模板模式(Curiously Recurring Template Pattern)后,
 * 这种方式可以称为奇异递归泛型(curiously recurring generics,CGR)。
 * 其中，“奇异递归”指的是你的类奇怪地在自身的基类中出现的现象。
 *
 * @see SelfBounded
 * @author kuifir
 * @date 2023/5/28 15:22
 */
public class CuriouslyRecurringGeneric extends GenericType<CuriouslyRecurringGeneric>{
}

class GenericType<T> {
}
