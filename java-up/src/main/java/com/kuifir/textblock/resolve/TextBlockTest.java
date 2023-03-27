package com.kuifir.textblock.resolve;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 所见即所得的文字块
 * <p>
 * 文字块由零个或多个内容字符组成，从开始分隔符开始，到结束分隔符结束。
 * 开始分隔符是由三个双引号字符 (“”“) ，
 * 后面跟着的零个或多个空格，
 * 以及行结束符组成的序列。
 * 结束分隔符是一个由三个双引号字符 (”“”) 组成的序列。
 * <p>
 * 需要注意的是，开始分隔符必须单独成行；三个双引号字符后面的空格和换行符都属于开始分隔符。
 * 所以，一个文字块至少有两行代码。即使是一个空字符，结束分隔符也不能和开始分隔符放在同一行代码里。
 * <p>
 * <p>
 * jshell> String s = """""";
 * |  Error:
 * |  illegal text block open delimiter sequence, missing line terminator
 * |  String s = """""";
 * <p>
 * jshell> String s = """
 * ...> """;
 * s ==> ""
 * <p>
 */
public class TextBlockTest {
    public static void main(String[] args) {

        String textBlock = """
                <!DOCTYPE html>
                <html>
                    <body>
                        <h1>"Hello World!"</h1>
                    </body>
                </html>
                """;
        System.out.println(
                "Here is the text block:\n" + textBlock);
    }

    @BeforeAll
    public static void beforeAll(){
        System.out.println("TextBlockTest begin test =====================================");
    }
    @AfterAll
    public static void afterAll(){
        System.out.println("TextBlockTest end test =====================================");
    }
    @BeforeEach
    public void beforeEach(){
        System.out.println("test begin ...");
    }
    @AfterEach
    public void afterEach(){
        System.out.println("test finish ...");
    }

    /**
     * 需要注意的是，开始分隔符必须单独成行；三个双引号字符后面的空格和换行符都属于开始分隔符。
     * 所以，一个文字块至少有两行代码。即使是一个空字符，结束分隔符也不能和开始分隔符放在同一行代码里。
     */
    @Test
    public void test() {
        System.out.println("开始分隔符必须单独成行；三个双引号字符后面的空格和换行符都属于开始分隔符。" +
                "所以，一个文字块至少有两行代码。即使是一个空字符，结束分隔符也不能和开始分隔符放在同一行代码里。");
        String s = """
                OneLine""";
        assertEquals(s, "OneLine");
    }

    @Test
    public void testTwoLines() {
        System.out.println("结束分隔符只有一个由三个双引号字符组成的序列。" +
                "结束分隔符之前的字符，包括换行符，都属于文字块的有效内容。");
        String s = """
                TwoLines
                """;
        assertEquals(s, "TwoLines\n");
    }

    @Test
    public void isSameObjectWithString() {
        System.out.println("文字块是在编译期处理的，并且在编译期被转换成了常量字符串，然后就被当作常规的字符串了。所以，如果文字块代表的内容，和传统字符串代表的内容一样，那么这两个常量字符串变量就指向同一内存地址，代表同一个对象");
        String stringBlock =
                "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <body>\n"
                + "        <h1>\"Hello World!\"</h1>\n"
                + "    </body>\n"
                + "</html>\n";
        String textBlock = """
                 <!DOCTYPE html>
                 <html>
                     <body>
                         <h1>"Hello World!"</h1>
                     </body>
                 </html>
                 """;
        assertEquals(stringBlock,textBlock);
        assertTrue(stringBlock.equals(textBlock));
    }
    /**
     * 不同于传统字符串的是，在编译期，文字块要顺序通过如下三个不同的编译步骤：
     * <p>
     * 为了降低不同平台间换行符的表达差异，编译器把文字内容里的换行符统一转换成 LF（\u000A）；
     * 为了能够处理 Java 源代码里的缩进空格，要删除所有文字内容行和结束分隔符共享的前导空格，以及所有文字内容行的尾部空格；
     * 最后处理转义字符，这样开发人员编写的转义序列就不会在第一步和第二步被修改或删除。
     * <p>
     * 通过合理地安排共享的前导空格，我们可以实现文字的编排和缩进
     */
    @Test
    public void testIndentation(){
        System.out.println("测试结束分隔符位置的影响");
        // 测试缩进
        // There are 8 leading white spaces in common
        String textBlock = """
        <!DOCTYPE html>
        <html>
            <body>
                <h1>"Hello World!"</h1>    
            </body>
        </html>
        """;
        System.out.println("把结束分隔符单独放在一行，和文本内容左边对齐。这时候，共享的前导空格就是文本内容本身共享的前导空格；结束分隔符仅仅是用来结束文字块的。这个例子里，我还加入了文字内容行的尾部空格，它们在编译期会被删除掉。");
        System.out.println(textBlock);
        String text =
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <body>\n" +
                "        <h1>\"Hello World!\"</h1>\n" +
                "    </body>\n" +
                "</html>\n";
        assertEquals(textBlock, text);

// There are 4 leading white spaces in common
        String textBlock2 = """
        <!DOCTYPE html>
        <html>
            <body>
                <h1>"Hello World!"</h1>    
            </body>
        </html>
    """;
        System.out.println("把结束分隔符单独放在一行，但是放在比文本内容更靠左的位置。这时候，结束分隔符除了用来结束文字块之外，还参与界定共享的前导空格。");
        System.out.println(textBlock2);
        assertFalse(textBlock2.equals(text));


// There are 8 leading white spaces in common
        String textBlock3 = """
        <!DOCTYPE html>
        <html>
            <body>
                <h1>"Hello World!"</h1>    
            </body>
        </html>
            """;
        System.out.println("把结束分隔符单独放在了一行，但是放在文本内容左对齐位置的右侧。这时候，结束分隔符的左侧，除了共享的前导空格之外，还有多余的空格。这些多余的空格，就成了文字内容行的尾部空格，它们在编译期会被删除掉。");
        System.out.println(textBlock3);
        assertEquals(text,textBlock3);
    }

    @Test
    public void testEscapeCharacter(){
        System.out.println("测试转义字符 '\\s");
        System.out.println("为了能够支持尾部附带的空格，文字块还引入了另外一个新的转义字符，‘\\s’，空格转义符。空格转义符表示一个空格。我们前面说过的文字块的编译器处理顺序，空格转义符不会在文字块的编译期被删除，因此空格转义符之前的空格也能被保留。所以，每一行使用一个空格转义符也就足够了");

// There are 8 leading white spaces in common
        String textBlock = """
        <!DOCTYPE html>    \s    
        <html>             \s
            <body>          
                <h1>"Hello World!"</h1>
            </body>
        </html>
        """;
        String text ="<!DOCTYPE html>     \n" +
                "<html>              \n" +
                "    <body>\n" +
                "        <h1>\"Hello World!\"</h1>\n" +
                "    </body>\n" +
                "</html>\n";
        System.out.println(textBlock);
        assertEquals(text, textBlock);
    }
    @Test
    public void testLineTerminationCharacter(){

        System.out.println("换行转义符的意思是，如果转移符号出现在一个行的结束位置，这一行的换行符就会被取缔");
        String textBlock = """
        <!DOCTYPE html>
        <html>
            <body>
                <h1>"Hello \
        World!"</h1>
            </body>
        </html>
        """;
        String text ="<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <body>\n" +
                "        <h1>\"Hello World!\"</h1>\n" +
                "    </body>\n" +
                "</html>\n";
        System.out.println("空格处理先于转义字符处理。因此，换行转义符之前的空格不算是文字块的尾部空格，因此会得到保留。");
        System.out.println(textBlock);
        assertEquals(textBlock,text);
    }

    @Test
    public void testCenter(){
        String textBlock = """
                        No man is an island,
                         Entire of itself,
                Every man is a piece of the continent,
                        A part of the main        
                """;
        String poetryHtml =
                """
                        <html>
                        <head></head>
                        <body>
                        <div align="center">
                            No man is an island,<br />
                            Entire of itself,<br />
                            Every man is a piece of the continent,<br />
                            A part of the main.
                        </div>
                        </body>
                        </html>""";
        System.out.println(poetryHtml);
        System.out.println(textBlock);
    }
}
