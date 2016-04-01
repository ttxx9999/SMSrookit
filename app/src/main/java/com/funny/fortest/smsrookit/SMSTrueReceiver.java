package com.funny.fortest.smsrookit;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSTrueReceiver extends BroadcastReceiver {
    public SMSTrueReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Bundle bundle = intent.getExtras();
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[pdus.length];
        for (int i = 0; i < messages.length; i++) {
            messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
        }
        String address = messages[0].getOriginatingAddress();
        String fullMessage = "";
        for (SmsMessage message : messages) {
            fullMessage += message.getMessageBody();
        }
        Log.d("zhaochengyu", "SMSTrueReceiver onReceive: " + address);
        Log.d("zhaochengyu", "SMSTrueReceiver onReceive: " + fullMessage);

        Uri uri = Uri.parse("content://com.funny.fortest.provider/SMS");
        ContentValues values = new ContentValues();
        values.put("sender", address);
        values.put("message", fullMessage);
        values.put("flag", 0);


        try {
            Uri newUri = SMSApplication.getContext().getContentResolver().insert(uri, values);
            //newId = newUri.getPathSegments().get(1);
        }
        catch (Exception e)
        {
            Log.e("zhaochengyu", "SMSTrueReceiver onReceive: Excpetion "+e);
        }
        finally {
            Log.d("zhaochengyu", "SMSTrueReceiver onReceive: finish");
            Intent startIntent = new Intent(SMSApplication.getContext(), MainService.class);
            SMSApplication.getContext().startService(startIntent);
            Log.d("zhaochengyu", "SMSTrueReceiver MainService: Start");
    }
    }
}
