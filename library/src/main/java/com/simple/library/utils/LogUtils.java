package com.simple.library.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * package：    com.simple.library.utils
 * author：     XuShuai
 * date：       2017/9/20  10:00
 * version:     v1.0
 * describe：   日志输出工具类
 */
public class LogUtils {
    private static boolean LOG_DEBUG = false;
    private static final String SEPARATOR = ",";
    private static final String TAG = "LogUtils:";

    private LogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void isDebug(boolean debug) {
        LOG_DEBUG = debug;
    }

    public static void v(String msg) {
        if (LOG_DEBUG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(traceElement);
            Log.v(TAG + tag, getLogInfo(traceElement, msg));
        }
    }

    public static void d(String msg) {
        if (LOG_DEBUG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(traceElement);
            Log.d(TAG + tag, getLogInfo(traceElement, msg));
        }
    }

    public static void i(String msg) {
        if (LOG_DEBUG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(traceElement);
            Log.i(TAG + tag, getLogInfo(traceElement, msg));
        }
    }

    public static void w(String msg) {
        if (LOG_DEBUG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(traceElement);
            Log.w(TAG + tag, getLogInfo(traceElement, msg));
        }
    }


    public static void e(String msg) {
        if (LOG_DEBUG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(traceElement);
            Log.e(TAG + tag, getLogInfo(traceElement, msg));
        }
    }

    public static void e(String msg, Throwable e) {
        if (LOG_DEBUG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(traceElement);
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer, true);
            pw.append(msg);
            e.printStackTrace(pw);
            Log.e(TAG + tag, getLogInfo(traceElement, writer.toString()));
            pw.close();
        }
    }

    public static void e(Throwable e) {
        if (LOG_DEBUG) {
            StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
            String tag = getDefaultTag(traceElement);
            StringWriter writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer, true);
            e.printStackTrace(pw);
            Log.e(TAG + tag, getLogInfo(traceElement, writer.toString()));
            pw.close();
        }
    }

    /**
     * 获取默认的TAG名称.
     * 比如在MainActivity.java中调用了日志输出.
     * 则TAG为MainActivity
     */
    private static String getDefaultTag(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        String stringArray[] = fileName.split("\\.");
        return stringArray[0];
    }

    /**
     * 输出日志所包含的信息
     */
    private static String getLogInfo(StackTraceElement stackTraceElement, String msg) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // 获取线程名
        String threadName = Thread.currentThread().getName();
        // 获取线程ID
        long threadID = Thread.currentThread().getId();
        // 获取文件名.即xxx.java
        String fileName = stackTraceElement.getFileName();
        // 获取类名.即包名+类名
        String className = stackTraceElement.getClassName();
        // 获取方法名称
        String methodName = stackTraceElement.getMethodName();
        // 获取日志输出行数
        int lineNumber = stackTraceElement.getLineNumber();
        logInfoStringBuilder.append
                ("==================================================================================================\n");
        logInfoStringBuilder.append("********");
        logInfoStringBuilder.append(" threadID = ").append(threadID).append(SEPARATOR);
        logInfoStringBuilder.append(" threadName = ").append(threadName).append(SEPARATOR);
        logInfoStringBuilder.append(" fileName = ").append(fileName).append(SEPARATOR);
        logInfoStringBuilder.append(" className = ").append(className).append(SEPARATOR);
        logInfoStringBuilder.append(" methodName = ").append(methodName).append(SEPARATOR);
        logInfoStringBuilder.append(" lineNumber = ").append(lineNumber);
        logInfoStringBuilder.append(" ********\n");
        logInfoStringBuilder.append(msg).append("\n");
        logInfoStringBuilder.append
                ("===================================================================================================\n");
        return logInfoStringBuilder.toString();
    }
}
