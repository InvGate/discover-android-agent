package com.invgate.discover.androidagent.services;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class ServiceScheduler {
    private static final String TAG = "inventory-service";

    public static void schedule(Long seconds, Context context) {

        GcmNetworkManager gcmNetworkManager = GcmNetworkManager.getInstance(context);

        // First cancel a previuos taks
        // gcmNetworkManager.cancelTask(ServiceScheduler.TAG, CronService.class);

        PeriodicTask myTask = new PeriodicTask.Builder()
                .setService(CronService.class)
                .setPeriod(seconds)
                .setTag(ServiceScheduler.TAG)
                .setUpdateCurrent(true)
                .setPersisted(true) // To be executed on start up and kill app
                // .setRequiredNetwork(Task.NETWORK_STATE_CONNECTED) // Only with internet connection
                .build();

        gcmNetworkManager.schedule(myTask);
    }
}
