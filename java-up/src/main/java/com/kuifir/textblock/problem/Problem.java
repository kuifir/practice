package com.kuifir.textblock.problem;

/**
 * 对于复杂的文本
 * 需要处理好文本对齐、换行字符、连接符以及双引号的转义字符。这就使得这段代码既不美观、也不简约，一点都不自然。
 * 这样的字符串不好写，不好看，也不好读。
 * 更糟糕的是，我们有时候需要从别的地方拷贝一段 HTML 或者 SQL 语句，
 * 然后再转换成类似于上面的字符串。是不是出力多，收效少
 */
public class Problem {
    public static void main(String[] args) {

        String stringBlock =
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "    <body>\n" +
                        "        <h1>\"Hello World!\"</h1>\n" +
                        "    </body>\n" +
                        "</html>\n";
        System.out.println(stringBlock);

    }
}
