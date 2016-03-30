package com.funny.fortest.smsrookit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.os.IBinder;
import android.content.Intent;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private MainService.StartBinder startBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            startBinder = (MainService.StartBinder) service;

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //启动主服务
        Intent startIntent = new Intent(this, MainService.class);
        startService(startIntent);

    }
}
