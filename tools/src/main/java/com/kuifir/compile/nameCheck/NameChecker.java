package com.kuifir.compile.nameCheck;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner14;
import javax.tools.Diagnostic;
import java.util.EnumSet;

/**
 * 程序名称规范的编译器插件：
 * 如果程序命名不合规范，将会输出一个编译器的warning信息
 *
 * <ul>
 *     <li>类或接口：符合驼式命名法，首字母大写</li>
 *     <li>方法：符合驼式命名法，首字母小写</li>
 *     <li>字段：符合驼式命名法，首字母小写</li>
 *     <ul>
 *         <li>类、实例变量：符合驼式命名法，首字母小写
 *         <li>常量：要求全部大写
 *     </ul>
 * </ul>
 */
public class NameChecker {

    private final Messager messager;

    private NameCheckScanner nameCheckScanner = new NameCheckScanner();

    public NameChecker(ProcessingEnvironment processingEnv) {
        messager = processingEnv.getMessager();
    }

    public void checkNames(Element element) {
        nameCheckScanner.scan(element);
    }

    class NameCheckScanner extends ElementScanner14<Void, Void> {
        /**
         * 检查java类
         *
         * @param e the element to visit
         * @param p a visitor-specified parameter
         * @return
         */
        @Override
        public Void visitType(TypeElement e, Void p) {
            scan(e.getTypeParameters(), p);
            checkCameCase(e, true);
            super.visitType(e, p);
            return null;
        }

        /**
         * 检查方法命名是否合法
         *
         * @param e      the element to visit
         * @param unused a visitor-specified parameter
         * @return
         */
        @Override
        public Void visitExecutable(ExecutableElement e, Void unused) {
            if (e.getKind() == ElementKind.METHOD) {
                Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "一个普通方法 “" + name + "” 不应当与类名重复，避免与构造函数产生混淆", e);
                    System.out.println("一个普通方法 “" + name + "” 不应当与类名重复，避免与构造函数产生混淆");
                    checkCameCase(e, false);
                }
            }
            super.visitExecutable(e, unused);
            return null;
        }

        /**
         * 检查变量命名是否合法
         *
         * @param e      the element to visit
         * @param unused a visitor-specified parameter
         * @return
         */
        @Override
        public Void visitVariable(VariableElement e, Void unused) {
            // 如果这个Variable是枚举或常量，则按大写命名检查，否则按照驼式命名法规则检查
            if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null || heuristicallyConstant(e)) {
                checkAllCaps(e);
            } else {
                checkCameCase(e, false);
            }
            return null;
        }


        /**
         * 判断一个变量是否是常量
         */
        private boolean heuristicallyConstant(VariableElement e) {
            if (e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
                return true;
            } else if (e.getKind() == ElementKind.FIELD
                    && e.getModifiers().containsAll(EnumSet.of(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL))) {
                return true;
            }
            return false;
        }

        /**
         * 检查传入的element是否符合驼峰命名法，如果不符合，则输出警告信息
         */
        private void checkCameCase(Element e, boolean initialCaps) {
            String name = e.getSimpleName().toString();
            boolean previourUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);

            if (Character.isUpperCase(firstCodePoint)) {
                previourUpper = true;
                if (!initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称 “" + name + "”应当以小写字母开头", e);
                    return;
                }
            } else if (Character.isLowerCase(firstCodePoint)) {
                if (initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称 “" + name + "”应当以大写字母开头", e);
                    return;
                }
            } else {
                conventional = false;
            }

            if (conventional) {
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (Character.isUpperCase(cp)) {
                        if (previourUpper) {
                            conventional = false;
                            break;
                        }
                        previourUpper = true;
                    } else {
                        previourUpper = false;
                    }
                }
            }

            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING, "名称“" + name + "”应当符合驼式命名法(Camel Case Names)", e);
            }
        }

        /**
         * 大写命名检查，要求第一个字母必须是大写的英文字母，其余部分可以是下划线或大写字母
         */
        private void checkAllCaps(Element e) {
            String name = e.getSimpleName().toString();

            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);

            if (!Character.isUpperCase(firstCodePoint)) {
                conventional = false;
            } else {
                boolean previousUnderscore = false;
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (cp == (int) '_') {
                        if (previousUnderscore) {
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    } else {
                        previousUnderscore = false;
                        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                            conventional = false;
                            break;
                        }
                    }
                }
            }
            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING, "常量“" + name + "” 应当全部以大写字母或下划线命名，并且以字母开头", e);
            }
        }

    }

}
