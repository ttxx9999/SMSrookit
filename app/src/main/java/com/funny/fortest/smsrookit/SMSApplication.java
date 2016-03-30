package com.funny.fortest.smsrookit;
import android.app.Application;
import android.content.Context;
/**
 * Created by zhaochengyu on 2016/3/30.
 */
public class SMSApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        context = getApplicationContext();
    }
    public static Context getContext() {
        return context;
    }
}
