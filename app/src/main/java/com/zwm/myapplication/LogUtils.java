package com.zwm.myapplication;

import android.os.Environment;
import android.os.Process;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by weiming.zeng on 2017/4/19.
 */

public class LogUtils {
    private static final String TAG = "PhiComm"; //默认TAG
    private static boolean IS_PRINT_STACK = false;
    private static final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String logPath = SDCARD + "/max_Zeng/log";

    public static final int LEVEL_VERBOSE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARNING = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;
    public static final int LEVEL_NONE = 6;

    private static final int JSON_INDENT = 2;    //the number of spaces to indent for each level of nesting.
    private LogUtils() {
        throw new UnsupportedOperationException("can not instantiate LogUtils...");
    }

    public static void init(boolean isPrintStack) {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("marsxlog");
        IS_PRINT_STACK = isPrintStack;
//init xlog
        if (BuildConfig.DEBUG) {
            Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "max_Zeng");
            Xlog.setConsoleLogOpen(true);
        } else {
            Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, "", logPath, "MarsSample");
            Xlog.setConsoleLogOpen(false);
        }
        Log.setLogImp(new Xlog());
    }

    public static String getLogLevel() {
        String level;
        switch (Log.getLogLevel()) {
            case 0:
                level = "LEVEL_VERBOSE";
                break;
            case 1:
                level = "LEVEL_DEBUG";
                break;
            case 2:
                level = "LEVEL_INFO";
                break;
            case 3:
                level = "LEVEL_WARNING";
                break;
            case 4:
                level = "LEVEL_ERROR";
                break;
            case 5:
                level = "LEVEL_FATAL";
                break;
            case 6:
                level = "LEVEL_NONE";
                break;
            default:
                level = "";
        }
        return level;
    }
    /**
     * 生成格式化的日志
     * @param obj
     * @return 格式化后的日志内容
     */
    private static String createMessage(Object obj) {
        String message;
        if (obj.getClass().isArray()) {
            message = Arrays.deepToString((Object[]) obj);
        }else {
            message = obj.toString();
        }
        StringBuilder content = new StringBuilder("PID:" + Process.myPid() + "\t" + "Thread:" + Thread.currentThread().getName() + "---");
        content.append(message);
        return content.toString();
    }

    public static void v(Object message) {
        log(LEVEL_VERBOSE, "", message, null);
    }
    public static void v(String tag,final String message, final Object... args) {
        log(LEVEL_VERBOSE, tag, message, null, args);
    }

    public static void d(Object message) {
        d(TAG,message);
    }
    public static void d(String tag, Object message) {
        log(LEVEL_DEBUG, tag, message, null);
    }
    public static void d(String tag,final String message, final Object... args) {
        log(LEVEL_DEBUG, tag, message, null, args);

    }
    public static void i(Object contents) {
    }
    public static void i(String tag, Object... contents) {
    }
    public static void w(Object contents) {
    }
    public static void w(String tag, Object... contents) {
    }
    public static void e(Object contents) {
    }
    public static void e(String tag, Object... contents) {
    }

    /**
     * 打印json日志
     * @param json
     */
    public static void json(String json) {
        if (TextUtils.isEmpty(json)) {
            log(LEVEL_INFO, TAG, "Empty/Null json content",null);
        }
        try {
            if(json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                log(LEVEL_INFO, TAG, message, null);
                return;
            }
            if(json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                log(LEVEL_INFO, TAG, message, null);
                return;
            }
            log(LEVEL_ERROR, TAG, "Invalid json", null);
        }catch (JSONException e) {
            log(LEVEL_ERROR, TAG, "error Json", e.getCause());
        }
    }

    /**
     * 获取堆栈信息
     * @param tr
     * @return
     */
    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        // This is to reduce the amount of log spew that apps do in the non-error
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    public static void log(int priority, String tag, Object message, @Nullable Throwable throwable, Object...args) {
        if (Log.getLogLevel() == Log.LEVEL_NONE) {
            return;
        }
        if (null == message || message.toString().length() == 0) {
            Log.e(TAG, "Empty/null log content");
            return;
        }
        String contents = createMessage(message);
        if (throwable != null) {
            contents += ":" + getStackTraceString(throwable);
        }
        switch (priority) {
            case LEVEL_DEBUG:
                Log.d(tag, contents, args);
                break;
            case LEVEL_INFO:
                Log.i(tag, contents, args);
                break;
            case LEVEL_WARNING:
                Log.w(tag, contents, args);
                break;
            case LEVEL_ERROR:
                Log.e(tag, contents, args);
                break;
            case LEVEL_FATAL:
                Log.f(tag, contents, args);
                break;
            default:
                Log.i(tag, contents, args);
                break;
        }
    }

}
