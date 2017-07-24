package com.zwm.myapplication;

import android.app.Application;
import android.os.Environment;

import com.orhanobut.logger.Logger;
import com.tencent.mars.xlog.Log;
import com.tencent.mars.xlog.Xlog;


/**
 * Created by weiming.zeng on 2017/4/17.
 */

public class MyApplication extends Application {
    final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    final String logPath = SDCARD + "/max_Zeng/log";
    @Override
    public void onCreate() {
        System.loadLibrary("stlport_shared");
        System.loadLibrary("marsxlog");
//init xlog
        if (BuildConfig.DEBUG) {
            Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, "", logPath, "max_Zeng");
            Xlog.setConsoleLogOpen(true);
        } else {
            Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, "", logPath, "MarsSample");
            Xlog.setConsoleLogOpen(false);
        }
        Log.setLogImp(new Xlog());
        Logger.init("zwm");
    }
}
