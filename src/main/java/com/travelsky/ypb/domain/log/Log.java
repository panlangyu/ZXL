package com.travelsky.ypb.domain.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log 日志输出
 */
public class Log {

    private static final Logger Logger = LoggerFactory.getLogger("LogProxy");

    public static final ThreadLocal<StringBuilder> threadLocals = new ThreadLocal<StringBuilder>();

    public static final ThreadLocal<Long> threadTimeLocals = new ThreadLocal<Long>();

    public static void beforeInvoke(Class targetClass, String method, Object[] args) {
        long startTime = System.currentTimeMillis();
        threadTimeLocals.set(startTime);
        StringBuilder sb = threadLocals.get();
        if (sb == null) {
            sb = new StringBuilder();
            threadLocals.set(sb);
        }
        sb.delete(0, sb.length());

        StringBuffer argBuffer = new StringBuffer();
        if (null != args) {
            for (int i = 0; i < args.length; i++) {
                String value = null == args[i] ? "null" : String.valueOf(args[i]);
                argBuffer.append("[arg " + i + "=" + value + "],");
            }
        }
        noticeLog("事件[%s] invoke method[%s] \n\t[beforeRed args [%s]]....\n",targetClass.getSimpleName(), method,argBuffer.toString());

    }

    public static void returnInvoke(Object result) {
        long endTime = System.currentTimeMillis();
        long startTime = threadTimeLocals.get();
        StringBuilder sb = threadLocals.get();
        if (sb != null) {
            noticeLog("\t[consuming time cast[%s] return[%s].... ]", (endTime - startTime),
                    null != result ? result.toString() : "null");
            Logger.info(sb.toString());
        }
    }

    public static void throwableInvoke(Object... args) {
        StringBuilder sb = threadLocals.get();
        if (sb != null) {
            noticeLog("\t[exception [%s] invoke method[%s]  message[%s]  information[%s]]....",args);
            Logger.info(String.valueOf(sb));
        }
    }

    public static void noticeLog(String fmt, Object... args) {
        StringBuilder sb = threadLocals.get();
        if (sb != null) {
            sb.append(" " + String.format(fmt, args));
        }
    }


    /**
     * 日志集中输出
     * @param targetClass 输出类
     * @param method 输出方法
     * @param args 输出参数
     */
    public static void i(Class targetClass, String method, Object... args) {
        StringBuilder sb = threadLocals.get();
        if (sb == null) {
            sb = new StringBuilder();
            threadLocals.set(sb);
        }
        StringBuffer argBuffer = new StringBuffer();
        if (null != args) {
            for (int i = 0; i < args.length; i++) {
                String value = null == args[i] ? "null" : String.valueOf(args[i]);
                argBuffer.append("[arg" + i + "=" + value + "],");
            }
        }
        noticeLog("\t[processor [%s]  [method [%s]]  [args [%s]]]....\n", targetClass.getSimpleName(), method,
                argBuffer.toString());
    }


}
