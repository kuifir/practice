package com.kuifir.normal.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * 将捕获的异常记录到日志中
 * {@link LoggingException} 用到的方法非常方便，
 * 因为它把所有的日志基础设施都构建在了异常本身之中，因此他可以自动工作，无需客户程序员干预。
 * 然而更常见的情况是捕捉别人的异常，并将其记录到日志中，所以我们必须在异常处理程序中生成日志信息。
 *
 * @author kuifir
 * @date 2023/5/8 23:20
 * @see LoggingException
 * @see LoggingExceptions
 */
public class LoggingExceptions2 {
    private static Logger logger = Logger.getLogger("LoggingExceptions2");
    static void logException(Exception e){
        StringWriter trace = new StringWriter();
        e.printStackTrace(new PrintWriter(trace));
        logger.severe(trace.toString());
    }

    public static void main(String[] args) {
        try {
            throw new NullPointerException();
        }catch (NullPointerException e){
            logException(e);
        }
    }
}
