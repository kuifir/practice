package com.kuifir.compile.nameCheck;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

import static javax.lang.model.SourceVersion.RELEASE_17;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(RELEASE_17)
public class NameCheckProcessor extends AbstractProcessor {
    private NameChecker nameChecker;

    /**
     * 初始化名称检查插件
     *
     * @param processingEnv environment to access facilities the tool framework
     *                      provides to the processor
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        System.out.println("aaa");
        super.init(processingEnv);
        nameChecker = new NameChecker(processingEnv);
    }

    /**
     * 对输入的语法树各个节点进行名称检查
     *
     * @param annotations the annotation interfaces requested to be processed
     * @param roundEnv    environment for information about the current and prior round
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                nameChecker.checkNames(element);
            }
        }
        return false;
    }
}
