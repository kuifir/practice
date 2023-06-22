package com.kuifir.normal.annotations.atunit;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link AtUnit} 在寻找类文件时有个必须解决的问题：从类文件名无法却确切地得知限定的类名（包括包名）。
 * 要获取这个信息，就必须分析类文件。这并非易事，但也并非做不到。
 * 当找到一个.class文件时，程序会打开文件，读取它的二进制数据，并传给ClassNameFinder.thisClass().
 * 此处我们将进入"字节码工程"的领域，因为我们实际上已经在分析类文件的内容了。
 *
 * @author kuifir
 * @date 2023/6/22 22:07
 * @see AtUnit
 */
public class ClassNameFinder {
    public static String thisClass(byte[] classBytes) {
        Map<Integer, Integer> offsetTable = new HashMap<>();
        Map<Integer, String> classNameTable = new HashMap<>();
        try {
            DataInputStream data = new DataInputStream(new ByteArrayInputStream(classBytes));
            // 0xcafebabe
            int magic = data.readInt();
            int minorVersion = data.readShort();
            int majorVersion = data.readShort();
            int constantPoolCount = data.readShort();
            int[] constantPool = new int[constantPoolCount];
            for (int i = 1; i < constantPoolCount; i++) {
                int tag = data.read();
                // int tableSize
                switch (tag) {
                    //UTF
                    case 1:
                        int length = data.readShort();
                        char[] bytes = new char[length];
                        for (int k = 0; k < bytes.length; k++) {
                            bytes[k] = (char) data.read();
                        }
                        String className = new String(bytes);
                        classNameTable.put(i, className);
                        break;
                    case 5:
                    case 6:
                        // LONG
                        // DOUBLE
                        // 丢弃8字节
                        data.readLong();
                        // 必要的特殊处理，跳过此处。
                        i++;
                        break;
                    case 7:
                        // CLASS
                        int offset = data.readShort();
                        offsetTable.put(i, offset);
                        break;
                    case 8:
                        // String
                        // 抛弃两字节
                        data.readShort();
                        break;
                    case 3:
                    case 4:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 18:
                        // INTEGER
                        // FLOAT
                        // FIELD_REF
                        // METHOD_REF
                        // INTERFACE_METHOD_REF
                        // NAME_ADD_TYPE
                        // Invoke Dynamic(动态代理调用指令)

                        // 抛弃4字节
                        data.readInt();
                        break;
                    case 15:
                        // Method Handle 方法句柄
                        data.readByte();
                        data.readShort();
                        break;
                    case 16:
                        // Method Type 方法类型
                        data.readShort();
                        break;
                    default:
                        throw new RuntimeException("Bad tag " + tag);
                }
            }
            short accessFlags = data.readShort();
            String access = (accessFlags & 0x0001) == 0 ? "nonpublic:" : "public:";
            int thisClass = data.readShort();
            int superClass = data.readShort();
            return access + classNameTable.get(offsetTable.get(thisClass)).replace('/', '.');
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.class");
        // 遍历整个树
        Files.walk(Paths.get("."))
                .filter(matcher::matches)
                .filter(p-> !p.endsWith("module-info.class"))
                .map(p -> {
                    try {
                        return thisClass(Files.readAllBytes(p));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(s -> s.startsWith("public:"))
//                .filter(s -> s.indexOf('$') >= 0)
                .map(s -> s.split(":")[1])
                .filter(s -> !s.startsWith("enums."))
                .filter(s -> s.contains("."))
                .forEach(System.out::println);
        ;
    }
}
