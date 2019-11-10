package com.math_riddles.common;


import com.math_riddles.BuildConfig;

/**
 * @author phuocns on 26/11/2018.
 */

public class LoggerUtil {
	LoggerUtil() {
        // Do nothing
    }
    private static final boolean IS_PRINT_LOG = BuildConfig.DEBUG;
    private static final String APP_NAME = "ESOC";
    private static final String INFO_CLASS = "INFO[ CLASS: ";
    private static final String METHOD = ". METHOD: ";
    private static final String MESSAGE = ". MESSAGE: ";
    private static final String CLOSE = "]";

    public static void i(String className, String methodName, String... message) {
        if (IS_PRINT_LOG) {
            android.util.Log.i(APP_NAME, INFO_CLASS + className + METHOD + methodName + MESSAGE + (message.length > 0 ? message[0] : "") + CLOSE);
        }
    }

    public static void e(String className, String methodName, Exception e) {
        if (IS_PRINT_LOG) {
            android.util.Log.e(APP_NAME, INFO_CLASS + className + METHOD + methodName + CLOSE, e);
        }
    }

    public static void e(String className, String methodName, String msg) {
        if (IS_PRINT_LOG) {
            android.util.Log.e(APP_NAME, INFO_CLASS + className + METHOD + methodName + MESSAGE + msg + CLOSE);
        }
    }

    public static void e(String className, String methodName, String msg, Throwable cause) {
        if (IS_PRINT_LOG) {
            android.util.Log.e(APP_NAME, INFO_CLASS + className + METHOD + methodName + MESSAGE + msg + CLOSE, cause);
        }
    }

    public static void w(String className, String methodName, String... message) {
        if (IS_PRINT_LOG) {
            android.util.Log.w(APP_NAME, INFO_CLASS + className + METHOD + methodName + MESSAGE + (message.length > 0 ? message[0] : "") + CLOSE);
        }
    }

    public static void d(String className, String methodName, String... message) {
        if (IS_PRINT_LOG) {
            if(message.length > 0)
                android.util.Log.d(APP_NAME, INFO_CLASS + className + METHOD + methodName + MESSAGE + message[0] + CLOSE);
            else
                android.util.Log.d(APP_NAME, INFO_CLASS + className + MESSAGE + methodName + CLOSE);
        }
    }
}

