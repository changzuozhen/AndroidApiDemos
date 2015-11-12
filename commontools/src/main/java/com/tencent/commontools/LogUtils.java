package com.tencent.commontools;

import android.util.Log;

/**
 * 日志控制
 */
public class LogUtils {
    private static boolean sEnable = true;

    private static void enableLog(boolean enable) {
        sEnable = enable;
    }

    /**
     * 打印debug级别的log
     *
     * @param tag tag标签
     * @param msg 内容
     */
    public static void d(String tag, String msg) {
        if (sEnable) {
            Log.d(tag, "[" + Thread.currentThread().getId() + "]" + msg);
        }
    }

    /**
     * 打印warning级别的log
     *
     * @param tag tag标签
     * @param msg 内容
     */
    public static void w(String tag, String msg) {
        if (sEnable) {
            Log.w(tag, "[" + Thread.currentThread().getId() + "]" + msg);
        }
    }

    /**
     * 打印error级别的log
     *
     * @param tag tag标签
     */
    public static void e(String tag, Throwable tr) {
        if (sEnable) {
            Log.e(tag, "[" + Thread.currentThread().getId() + "]" + tr.getMessage(), tr);
        }
    }

    /**
     * 打印error级别的log
     *
     * @param tag tag标签
     */
    public static void e(String tag, String msg) {
        if (sEnable) {
            Log.e(tag, msg);
        }
    }

    /**
     * 打印error级别的log
     *
     * @param tag tag标签
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (sEnable) {
            Log.e(tag, msg, tr);
        }
    }

    /**
     * 打印info级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void i(String tag, String str) {
        if (sEnable) {
            Log.i(tag, "[" + Thread.currentThread().getId() + "]" + str);
        }
    }

    /**
     * 打印verbose级别的log
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void v(String tag, String str) {
        if (sEnable) {
            Log.v(tag, "[" + Thread.currentThread().getId() + "]" + str);
        }
    }

    /**
     * 打印调用栈信息
     *
     * @param tag tag标签
     * @param str 内容
     */
    public static void t(String tag, String str) {
        if (sEnable) {
            LogUtils.i(tag, "DebugInfo: " + str);
            Exception e = new Exception(tag);
            e.printStackTrace();
        }
    }
}
