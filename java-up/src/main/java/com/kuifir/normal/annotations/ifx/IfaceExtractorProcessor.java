package com.kuifir.normal.annotations.ifx;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 基于javac的注解处理
 * 下面的示例是一个编译期处理器，他会提取出public方法,并创建新接口的源代码文件(该源文件之后会作为”编译阶段“的一部分，被自动编译)
 * <p></p>
 * Filer（processingEnv.getFiler()）是一种创建新文件的PrintWriter
 * 之所以用Filer对象而非某个普通的PrintWriter,
 * 是因为Filer对象允许javac持续跟踪你创建的所有新文件，从而可以检查它们的注解，并在额外的"编译阶段"中编译它们。
 * <p></p>
 * 如下代码是使用处理器来编译的命令行指令：
 * <pre>
 *     {@code javac -processor com.kuifir.normal.annotations.ifx.IfaceExtractorProcessor Multiplier.java}
 * </pre>
 *
 * @author kuifir
 * @date 2023/6/14 22:33
 * @see Filer
 */
@SupportedAnnotationTypes("com.kuifir.normal.annotations.ifx.ExtractInterface")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class IfaceExtractorProcessor extends AbstractProcessor {
    private final ArrayList<Element> interfaceMethods = new ArrayList<>();
    Elements elementUtils;
    private ProcessingEnvironment processingEnv;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(ExtractInterface.class)) {
            String interfaceName = element.getAnnotation(ExtractInterface.class).interfaceName();
            for (Element enclosedElement : element.getEnclosedElements()) {
                if (enclosedElement.getKind().equals(ElementKind.METHOD)
                        && enclosedElement.getModifiers().contains(Modifier.PUBLIC)
                        && !enclosedElement.getModifiers().contains(Modifier.STATIC)
                ) {
                    interfaceMethods.add(enclosedElement);
                }
            }
            if (interfaceMethods.size() > 0) {
                writeInterfaceFile(interfaceName);
            }
        }
        return false;
    }

    private void writeInterfaceFile(String interfaceName) {
        String packageName = elementUtils.getPackageOf(interfaceMethods.get(0)).toString();
        System.out.println(packageName);
        System.out.println(interfaceName);
        try (
                // Filer（processingEnv.getFiler()）是一种创建新文件的PrintWriter
                // 之所以用Filer对象而非某个普通的PrintWriter,
                // 是因为Filer对象允许javac持续跟踪你创建的所有新文件，从而可以检查它们的注解，并在额外的"编译阶段"中编译它们。
                Writer writer = processingEnv.getFiler().createSourceFile(packageName + "." + interfaceName).openWriter()
        ) {
            writer.write("package " + packageName + ";\n");
            writer.write("public interface " + interfaceName + "{\n");
            for (Element interfaceMethod : interfaceMethods) {
                ExecutableElement method = (ExecutableElement) interfaceMethod;
                String signature = " public ";
                signature += method.getReturnType() + " ";
                signature += method.getSimpleName();
                signature += createArgList(method.getParameters());
                writer.write(signature + ";\n");
            }
            writer.write("}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String createArgList(List<? extends VariableElement> parameters) {
        String args = parameters.stream()
                .map(p -> p.asType() + " " + p.getSimpleName())
                .collect(Collectors.joining(","));
        return "(" + args + ")";
    }
}
