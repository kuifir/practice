package com.kuifir.normal.annotations.simplest;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * 已被废弃的旧apt(即Annotation Processing Tool,编译时注解处理器)版本的注解处理器
 * 需要额外的方法来确定哪些注解和Java版本可以被支持。
 * 而现在你可以简单通过{@link SupportedAnnotationTypes}和{@link SupportedSourceVersion}注解来达到相同的目的
 * （这个很好地缺诠释了注解如何做到简化代码）
 * <p></p>
 * 此处唯一需要实现的方法是{@link #process(Set, RoundEnvironment)}，其中包含了所有的逻辑。
 * 第一个参数会告诉你有哪些注解，第二个参数则包含余下的所有信息。
 * 此处我们做的只是把注解（只有一个）都打印了出来，要了解其他功能，请参考TypeElement文档。
 * 通过process()方法的第二个参数，我们遍历所有被@Simple注解的元素，并且对每个元素都调用了{@link #display(Element)}方法。‘
 * 每个Element 都可以携带自身的基本信息，例如{@link Element#getModifiers()}能够告诉我们它是否是public和static的。
 * <p></p>
 * 如果你只是正常地编译SimpleTest.java，不归得到任何结果。想要得到注解的输出，就需要加上-processor标识和注解处理器类。
 * <pre>{@code javac -processor com.kuifir.normal.annotations.simplest.SimpleProcessor SimpleTest.java}</pre>
 * <pre>{@code
 * com.kuifir.normal.annotations.simplest.Simple
 * =====com.kuifir.normal.annotations.simplest.SimpleTest========
 * CLASS : [public] : SimpleTest : com.kuifir.normal.annotations.simplest.SimpleTest
 * com.kuifir.normal.annotations.simplest.SimpleTest
 * java.lang.Object
 * i,SimpleTest(),foo(),bar(java.lang.String,int,float),main(java.lang.String[])
 * =====i========
 * FIELD : [] : i : int
 * =====SimpleTest()========
 * CONSTRUCTOR : [public] : <init> : ()void
 * =====foo()========
 * METHOD : [public] : foo : ()void
 * void
 * foo(
 * )
 * =====bar(java.lang.String,int,float)========
 * METHOD : [public] : bar : (java.lang.String,int,float)void
 * void
 * bar(
 * s,i,f)
 * =====main(java.lang.String[])========
 * METHOD : [public, static] : main : (java.lang.String[])void
 * void
 * main(
 * args)
 * }</pre>
 * @author kuifir
 * @date 2023/6/13 23:46
 */
@SupportedAnnotationTypes("com.kuifir.normal.annotations.simplest.Simple")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class SimpleProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement t : annotations) {
            System.out.println(t);
        }
        for (Element el : roundEnv.getElementsAnnotatedWith(Simple.class)) {
            display(el);

        }
        return false;
    }

    private void display(Element el) {
        System.out.println("=====" + el + "========");
        System.out.println(el.getKind() +
                " : " + el.getModifiers() +
                " : " + el.getSimpleName() +
                " : " + el.asType()
        );
        if (el.getKind().equals(ElementKind.CLASS)) {
            TypeElement te = (TypeElement) el;
            System.out.println(te.getQualifiedName());
            System.out.println(te.getSuperclass());
            System.out.println(te.getEnclosedElements());
        }
        if (el.getKind().equals(ElementKind.METHOD)) {
            ExecutableElement ex = (ExecutableElement) el;
            System.out.println(ex.getReturnType() + " ");
            System.out.println(ex.getSimpleName() + "(");
            System.out.println(ex.getParameters() + ")");
        }
    }
}
