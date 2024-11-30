package com.kuifir.remote.execute;

public class ByteUtils {

    public static int bytes2Int(byte[] bytes, int start, int len) {
        if (start < 0 || len < 0 || start + len > bytes.length) {
            throw new IllegalArgumentException("Invalid start or length");
        }
        int sum = 0;
        int end = start + len;
        for (int i = 0; i < end; i++) {
            int n = ((int) bytes[i]) & 0Xff;
            n <<= (--len) * 8;
            sum += n;
        }
        return sum;
    }

    public static byte[] int2Bytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte) ((value >> 8 * i) & 0xff);
        }
        return b;
    }

    public static String bytes2String(byte[] classBytes, int start, int len) {
        return new String(classBytes, start, len);
    }

    public static byte[] string2Bytes(String str) {
        return str.getBytes();
    }

    public static byte[] bytesReplace(byte[] originalBytes, int offset, int len, byte[] replaceBytes) {
        byte[] newBytes = new byte[originalBytes.length + (replaceBytes.length - len)];
        System.arraycopy(originalBytes, 0, newBytes, 0, offset);
        System.arraycopy(replaceBytes, 0, newBytes, offset, replaceBytes.length);
        System.arraycopy(originalBytes, offset + len, newBytes, offset + replaceBytes.length, originalBytes.length - offset - len);
        return newBytes;
    }
}
