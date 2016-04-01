package com.funny.fortest.smsrookit;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class DeviceMyReceiver extends DeviceAdminReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        // Intent i = new Intent(context, MainActivity.class);
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // context.startActivity(i);
        Log.e("autosetting", "receiver onReceive");
    }


    @Override
    public CharSequence onDisableRequested(Context context, Intent intent) {
        // 这里处理 不可编辑设备。
        //Intent intent2 = new Intent(context, NoticeSetting.class);
        //intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.startActivity(intent2);
        context.stopService(intent);// 是否可以停止
        Toast.makeText(context, "Success!!!", Toast.LENGTH_SHORT).show();
        return ""; // "这是一个可选的消息，警告有关禁止用户的请求";
    }

    public static SharedPreferences getDevicePreference(Context context) {
        return context.getSharedPreferences(
                DeviceAdminReceiver.class.getName(), 0);
    }

    // 密码的特点
    public static String PREF_PASSWORD_QUALITY = "password_quality";
    // 密码的长度
    public static String PREF_PASSWORD_LENGTH = "password_length";

    public static String PREF_MAX_FAILED_PW = "max_failed_pw";

    void showToast(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        // TODO Auto-generated method stub
        showToast(context, "设备管理：可用");
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        // TODO Auto-generated method stub
        showToast(context, "设备管理：不可用");
    }


    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        // TODO Auto-generated method stub
        showToast(context, "设备管理：密码己经改变");
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        // TODO Auto-generated method stub
        showToast(context, "设备管理：改变密码失败");
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        // TODO Auto-generated method stub
        showToast(context, "设备管理：改变密码成功");
    }
}
