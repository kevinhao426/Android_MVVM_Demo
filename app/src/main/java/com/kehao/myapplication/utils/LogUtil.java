package com.kehao.myapplication.utils;

import com.orhanobut.logger.Logger;

public class LogUtil {

    private static final int VERBOSE = 1;

    private static final int DEBUG = 2;

    private static final int INFO = 3;

    private static final int WARN = 4;

    private static final int ERROR = 5;

    private static final int NOTHING = 6;

    //    private static final int level = NOTHING;
    private static final int level = VERBOSE;

    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            Logger.v(tag, msg);
            Logger.v(msg);
        }
    }

    public static void d(String msg) {
        if (level <= DEBUG) {
            Logger.d(msg);
        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            Logger.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            Logger.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WARN) {
            Logger.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            Logger.e(tag, msg);
        }
    }


}

