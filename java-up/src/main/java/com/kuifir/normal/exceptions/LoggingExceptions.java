package com.kuifir.normal.exceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * 可以使用java.util.logging 工具将输出记录到日志中。基本的日志操作很简单。
 *
 * @author kuifir
 * @date 2023/5/8 22:56
 */
class LoggingException extends Exception {
    /**
     * 创建了一个与String参数（通常是错误像滚的包和类的名字）关联的Logger对象，这个对象会将其输出发送到System.err.
     */
    private static Logger logger = Logger.getLogger("LoggingException");

    LoggingException() {
        // 为了生成用于日志记录消息的String, 我们想让栈轨迹出现在异常被抛出的地方，
        // 但是printStackTrace()默认情况下不会生成一个String。
        // 为了获得String,使用了重载的printStackTrace()，他接受一个java.io.PrintWriter对象最为参数。
        // 如果我们把一个java.io.StringWriter对象传给PrintWriter的构造器，
        // 它可以通过调用toString()把输出当作一个String提取出来
        StringWriter trace = new StringWriter();
        printStackTrace(new PrintWriter(trace));
        // 最简单的写入Logger的方式是直接调用 与日志记录消息级别关联 的方法，这里调用了severe()
        logger.severe(trace.toString());
    }

}

/**
 * @author kuifir
 */
public class LoggingExceptions {
    public static void main(String[] args) {
        try {
            throw new LoggingException();
        } catch (LoggingException e) {
            System.err.println("Caught" + e);
        }
        try {
            throw new LoggingException();
        } catch (LoggingException e) {
            System.err.println("Caught" + e);
        }
    }
}
