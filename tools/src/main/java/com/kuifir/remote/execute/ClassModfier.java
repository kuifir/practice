package com.kuifir.remote.execute;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改class文件常量池常量
 */
public class ClassModfier {
    /**
     * Class文件中常量池的起始偏移
     */
    private static final int CONSTANT_POOL_COUNT_INDEX = 8;
    /**
     * CONSTANT_utf8_info常量的tag标志
     */
    private static final int CONSTANT_utf8_info = 1;
    /**
     * 常量池中常量所占的长度，因为不是定长除外
     * 创建一个 Map 来存储 tag 和长度的对应关系
     */
    private static final Map<Integer, Integer> constantPoolMap = new HashMap<>();

    static {
        // 填充定长常量类型及其长度
        constantPoolMap.put(3, 5);  // CONSTANT_Integer: tag = 3, length = 5 bytes
        constantPoolMap.put(4, 5);  // CONSTANT_Float: tag = 4, length = 5 bytes
        constantPoolMap.put(5, 9);  // CONSTANT_Long: tag = 5, length = 9 bytes
        constantPoolMap.put(6, 9);  // CONSTANT_Double: tag = 6, length = 9 bytes
        constantPoolMap.put(7, 3);  // CONSTANT_Class: tag = 7, length = 3 bytes
        constantPoolMap.put(8, 3);  // CONSTANT_String: tag = 8, length = 3 bytes
        constantPoolMap.put(9, 5);  // CONSTANT_Fieldref: tag = 9, length = 5 bytes
        constantPoolMap.put(10, 5); // CONSTANT_Methodref: tag = 10, length = 5 bytes
        constantPoolMap.put(11, 5); // CONSTANT_InterfaceMethodref: tag = 11, length = 5 bytes
        constantPoolMap.put(12, 5); // CONSTANT_NameAndType: tag = 12, length = 5 bytes
        constantPoolMap.put(15, 4); // CONSTANT_MethodHandle: tag = 15, length = 4 bytes
        constantPoolMap.put(16, 3); // CONSTANT_MethodType: tag = 16, length = 3 bytes
        constantPoolMap.put(17, 5); // CONSTANT_Dynamic: tag = 17, length = 5 bytes
        constantPoolMap.put(18, 5); // CONSTANT_InvokeDynamic: tag = 18, length = 5 bytes
        constantPoolMap.put(19, 3); // CONSTANT_Module: tag = 19, length = 3 bytes
        constantPoolMap.put(20, 3); // CONSTANT_Package: tag = 20, length = 3 bytes
    }

    private static final int u1 = 1;
    private static final int u2 = 2;
    private byte[] classBytes;

    public ClassModfier(byte[] classBytes) {
        this.classBytes = classBytes;
    }

    // 修改常量池中CONSTANT_utf8_info常量的内容


    public byte[] midifyUTF8Constant(String oldStr, String newStr) {
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for (int i = 0; i < cpc; i++) {
            int tag = ByteUtils.bytes2Int(classBytes, offset, u1);
            if (tag == CONSTANT_utf8_info) {
                int len = ByteUtils.bytes2Int(classBytes, offset + u1, u2);
                offset += (u1 + u2);
                String str = ByteUtils.bytes2String(classBytes, offset, len);
                if (str.equals(oldStr)) {
                    byte[] strBytes = ByteUtils.string2Bytes(newStr);
                    byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
                    classBytes = ByteUtils.bytesReplace(classBytes, offset - u2, u2, strLen);
                    classBytes = ByteUtils.bytesReplace(classBytes, offset, len, strBytes);
                    return classBytes;
                } else {
                    offset += len;
                }
            } else {
                offset += constantPoolMap.getOrDefault(tag, -1);
            }
        }
        return classBytes;
    }

    /**
     * 获取常量重中常量的数量
     */
    public int getConstantPoolCount() {
        return ByteUtils.bytes2Int(classBytes, CONSTANT_POOL_COUNT_INDEX, u2);
    }
}
