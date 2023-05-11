package com.kuifir.normal.exceptions;

/**
 * 一个可以动态添加数据项的类，用来演示异常链
 * <p/>
 * 又是我们会捕捉一个异常并抛出一个，但仍保留原始异常的信息，这称为异常链。
 * 在Java 1.4之前，程序员要自己编写代码来保留原始异常信息，
 * 但现在所有的Throwable子类都可以选择在构造器中接受一个cause对象（Throwable(String message,Throwable cause)）。
 * 这个cause意在作为原始的异常，尽管我们正在创建和抛出一个新异常，但是通过将它传进去，我们可以维护能追溯到源头的栈轨迹。
 * <p/>
 * 在Throwable 的子类中，只有三种基本的异常类提供了带cause参数的构造器，
 * 它们是Error(JVM使用它来报告系统错误)、Exception和RuntimeException。
 * 要链接任何其他的异常类型，请使用initCause()方法而不是构造器，
 *
 * @author kuifir
 * @date 2023/5/11 22:46
 * @see Throwable
 * @see DynamicFieldsException
 */
public class DynamicFields {
    private Object[][] fields;

    public DynamicFields(int initialSize) {
        fields = new Object[initialSize][2];
        for (int i = 0; i < initialSize; i++) {
            fields[i] = new Object[]{null, null};
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Object[] field : fields) {
            result.append(field[0]);
            result.append(":");
            result.append(field[1]);
            result.append("\n");
        }
        return result.toString();
    }

    private int hasField(String id) {
        for (int i = 0; i < fields.length; i++) {
            if (id.equals(fields[i][0])) {
                return i;
            }
        }
        return -1;
    }

    private int getFieldNumber(String id) throws NoSuchFieldException {
        int fieldNum = hasField(id);
        if (fieldNum == -1) {
            throw new NoSuchFieldException();
        }
        return fieldNum;
    }

    private int makeField(String id) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i][0] == null) {
                fields[i][0] = id;
                return i;
            }
        }
        // 没有空的数据项，则添加一个
        Object[][] tmp = new Object[fields.length + 1][2];
        for (int i = 0; i < fields.length; i++) {
            tmp[i] = fields[i];
        }
        for (int i = fields.length; i < tmp.length; i++) {
            tmp[i] = new Object[]{null, null};
        }
        fields = tmp;
        // 在拓展后的fields上递归调用
        return makeField(id);
    }

    public Object getField(String id) throws NoSuchFieldException {
        return fields[getFieldNumber(id)][1];
    }

    public Object setField(String id, Object value) throws DynamicFieldsException {
        if (null == value) {
            // 大部分异常没有支持cause参数的构造器，这种情况下必须使用initCause(),Throwable 的所有子类都支持这个方法。
            DynamicFieldsException dfe = new DynamicFieldsException();
            dfe.initCause(new NullPointerException());
            throw dfe;
        }
        int fieldNumber = hasField(id);
        if (fieldNumber == -1) {
            fieldNumber = makeField(id);
        }
        Object result = null;
        try {
            // 得到原来的值
            result = getField(id);
        } catch (NoSuchFieldException e) {
            // 使用接受cause的构造器：
            throw new RuntimeException(e);
        }
        fields[fieldNumber][1] = value;
        return result;
    }

    public static void main(String[] args) {
        DynamicFields df = new DynamicFields(3);
        System.out.println(df);
        try {
            df.setField("d", "A value for d");
            df.setField("number", 47);
            df.setField("number2", 48);
            System.out.println(df);
            df.setField("d", "A new value for d");
            df.setField("number", 11);
            System.out.println("df: " + df);
            System.out.println("""
                    df.getField("d") :""" + df.getField("d"));

            Object field = df.setField("d", null);// 异常

        } catch (NoSuchFieldException | DynamicFieldsException e) {
            e.printStackTrace(System.out);
        }
    }
}

class DynamicFieldsException extends Exception {
}