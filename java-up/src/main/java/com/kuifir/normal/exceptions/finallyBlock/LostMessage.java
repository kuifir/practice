package com.kuifir.normal.exceptions.finallyBlock;

/**
 * 特殊的finally子句可能导致异常丢失
 *
 * @author kuifir
 * @date 2023/5/12 22:25
 */
public class LostMessage {
    void f() throws VeryImportantException {
        throw new VeryImportantException();
    }

    void dispose() throws HoHumException {
        throw new HoHumException();
    }

    public static void main(String[] args) {
        try {
            LostMessage lm = new LostMessage();
            try {
                lm.f();
            } finally {
                lm.dispose();
            }
        } catch (VeryImportantException | HoHumException e) {
            System.out.println(e);
        }
    }
}

class VeryImportantException extends Exception {
    @Override
    public String toString() {
        return "A very important exception";
    }
}

class HoHumException extends Exception {
    @Override
    public String toString() {
        return "a trivial exception";
    }
}