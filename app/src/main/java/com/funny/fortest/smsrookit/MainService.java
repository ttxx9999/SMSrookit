package com.funny.fortest.smsrookit;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RemoteViews;

public class MainService extends Service {
    public MainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("MainService", "onBind executed");
        return null;
    }

    public void onCreate(){
        super.onCreate();
        Log.d("zhaochengyu", "MainService onCreate executed");
        SMSDatabaseHelper dbHelper = new SMSDatabaseHelper(this, "SMSrookit.db", null, 1);

        addNotification();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("zhaochengyu", "MainService onStartCommand executed");
        super.onStartCommand(intent, flags, startId);
        // TODO: 2016/4/1  开启网络receiver
        startNetChangeReceiver();

        return START_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e("zhaochengyu", "MainService onDestroy: executed");
    }

    public void addNotification(){
        Notification notification = new Notification(R.mipmap.ic_launcher,
                "", System.currentTimeMillis());
        notification.contentView = new RemoteViews(this.getPackageName(),R.layout.notification_template_lines);
        notification.contentIntent = PendingIntent.getActivity(this, 0, new Intent(), 0);
        startForeground(345, notification);
    }
    public int startNetChangeReceiver(){
        Log.d("zhaochengyu", "startNetChangeReceiver: executed");
        IntentFilter netChangereceiveFilter;
        NetChangeReceiver NetChangeReceiver;
        netChangereceiveFilter = new IntentFilter();
        netChangereceiveFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        NetChangeReceiver = new NetChangeReceiver();
        registerReceiver(NetChangeReceiver, netChangereceiveFilter);
        return 0;
    }

}
