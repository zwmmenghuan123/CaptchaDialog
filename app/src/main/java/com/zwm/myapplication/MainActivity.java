package com.zwm.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;
import com.tencent.mars.xlog.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//            Log.d("ZWM","MainActivity!!!!!!!!");
//            android.util.Log.d("zwm", "MainActivity");
//            android.util.Log.d("zwm", Log.getStackTraceString(new Throwable()));
//            for(StackTraceElement j : Thread.currentThread().getStackTrace()) {
//                Log.i("zwm222", j.toString());
//            }
//            android.util.Log.d("zwm333333", String.valueOf(Thread.currentThread().getStackTrace()));
        StackTraceElement[] s = Thread.currentThread().getStackTrace();
        if (s != null) {
            for (int i = 0; i < s.length; i++) {
                LogUtils.d(s[i].getClassName() + "/t");
                LogUtils.d(s[i].getFileName() + "/t");
                LogUtils.d(s[i].getLineNumber() + "/t");
                LogUtils.d(s[i].getMethodName());
                LogUtils.d("-----------------------------------");
            }
        }
//            for (int i = 0; i < 3; i++) {
////          LogUtils.d("zwm","格式参数$的%1$d,%2$s使用",66668888,"abc");
//            Logger.json(getJson());
//
//        }

    }

    public String getJson() {
        String s = "{\n" +
                "    \"name\": \"BeJson\",\n" +
                "    \"url\": \"http://www.bejson.com\",\n" +
                "    \"page\": 88,\n" +
                "    \"isNonProfit\": true,\n" +
                "    \"address\": {\n" +
                "        \"street\": \"科技园路.\",\n" +
                "        \"city\": \"江苏苏州\",\n" +
                "        \"country\": \"中国\"\n" +
                "    },\n" +
                "    \"links\": [\n" +
                "        {\n" +
                "            \"name\": \"Google\",\n" +
                "            \"url\": \"http://www.google.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Baidu\",\n" +
                "            \"url\": \"http://www.baidu.com\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"SoSo\",\n" +
                "            \"url\": \"http://www.SoSo.com\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        return s;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.appenderClose();
    }
}
