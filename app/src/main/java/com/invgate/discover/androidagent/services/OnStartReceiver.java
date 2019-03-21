package com.invgate.discover.androidagent.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.invgate.discover.androidagent.utils.Constants;

public class OnStartReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_MY_PACKAGE_REPLACED.equals(intent.getAction())) {
            Log.i(Constants.LOG_TAG, "App was updated. Scheduling inventory again");
            ServiceScheduler.schedule(1L, context);
        }
    }
}