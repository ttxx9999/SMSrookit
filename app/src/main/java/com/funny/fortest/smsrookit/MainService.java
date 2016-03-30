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

public class MainService extends Service {
    public MainService() {
    }
    private IntentFilter smsreceiveFilter;
    private SMSReceiver SMSReceiver;
    private IntentFilter netChangereceiveFilter;
    private NetChangeReceiver NetChangeReceiver;

    private StartBinder mBinder = new StartBinder();

    class StartBinder extends Binder {

        public void startSMSReceiver1() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // start downloading
                }
            }).start();
            Log.d("MyService", "startDownload executed");
        }

        public int startSMSReceiver() {
            Log.d("MyService", "startSMSReceiver executed");

            return 0;
        }

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("MainService", "onBind executed");
        return mBinder;
    }

    public void onCreate(){
        super.onCreate();
        Log.d("MainService", "onCreate executed");
        SMSDatabaseHelper dbHelper = new SMSDatabaseHelper(this, "SMSrookit.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MainService", "onStartCommand executed");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do something here
            }
        }).start();

        startSMSReceiver();
        startNetChangeReceiver();

        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.e("MainService", "onDestroy executed");
    }

    public int startNetChangeReceiver(){
        Log.d("MainService", "startNetChangeReceiver executed");
        netChangereceiveFilter = new IntentFilter();
        netChangereceiveFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        NetChangeReceiver = new NetChangeReceiver();
        registerReceiver(NetChangeReceiver, netChangereceiveFilter);
        return 0;
    }
    public int startSMSReceiver() {
        Log.d("MainService", "startSMSReceiver executed");
        smsreceiveFilter = new IntentFilter();
        smsreceiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        smsreceiveFilter.setPriority(100);
        SMSReceiver = new SMSReceiver();
        registerReceiver(SMSReceiver, smsreceiveFilter);
        return 0;
    }
}
