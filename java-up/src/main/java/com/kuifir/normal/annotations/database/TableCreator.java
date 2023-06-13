package com.kuifir.normal.annotations.database;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 基于反射的注解处理器
 * 下面这个示例演示了注解处理器如何读取文件，检查其数据库注解，并生成SQL命令来创建数据库。
 *
 * @author kuifir
 * @date 2023/6/13 22:30
 */
public class TableCreator {
    public static void main(String[] args) throws ClassNotFoundException {
        if (args.length < 1) {
            System.out.println("arguments: annotated classes");
            System.exit(0);
        }
        for (String className : args) {
            Class<?> cl = Class.forName(className);
            DBTable dbTable = cl.getAnnotation(DBTable.class);
            if (dbTable == null) {
                System.out.println("No DBTable annotations in class " + className);
                continue;
            }
            String tableName = dbTable.name();
            if (tableName.length() < 1) {
                tableName = cl.getName().toUpperCase();
            }
            List<String> columnDefs = new ArrayList<>();
            for (Field field : cl.getDeclaredFields()) {
                String columnName = null;
                Annotation[] anns = field.getDeclaredAnnotations();
                if (anns.length < 1) {
                    continue;
                }
                if (anns[0] instanceof SQLInteger sInt) {
                    // 如果name未指定，使用字段名
                    if (sInt.name().length() < 1) {
                        columnName = field.getName().toUpperCase();
                    } else {
                        columnName = sInt.name();
                    }
                    columnDefs.add(columnName + " INT" + getConstraints(sInt.constraints()));
                }
                if (anns[0] instanceof SQLString sString) {
                    if (sString.name().length() < 1) {
                        columnName = field.getName().toUpperCase();
                    } else {
                        columnName = sString.name();
                    }
                    columnDefs.add(columnName + " VARCHAR("
                            + sString.value() + ")"
                            + getConstraints(sString.constrains()));
                }
            }
            StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
            for (String columnDef : columnDefs) {
                createCommand.append("\n " + columnDef + ",");
            }
            // 移除尾部的逗号
            String tableCreate = createCommand.substring(0, createCommand.length() - 1) + ")";
            System.out.println("Table Creation SQL for " + className + " is: \n" + tableCreate);
        }
    }

    private static String getConstraints(Constraints con) {
        String constrains = "";
        if (!con.allowNull()) {
            constrains += " NOT NULL";
        }
        if (con.primaryKey()) {
            constrains += " PRIMARY KEY";
        }
        if (con.unique()) {
            constrains += " UNIQUE";
        }
        return constrains;
    }
}
