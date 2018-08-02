package com.invgate.discover.androidagent.services;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import org.flyve.inventory.InventoryTask;

public class CronService extends GcmTaskService {


    public static InventoryTask inventoryTask;
    public static Inventory inventoryService;


    /**
     * Create the inventory task collector and the inventory service
     */
    public void initializeServices() {
        String appVersion = "not-found";
        try {
            appVersion = this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            Log.e("AppVersion","not found", ex);
        }
        Context cx = getApplicationContext();
        inventoryTask = new InventoryTask(cx, appVersion, true);
        inventoryService = new Inventory(cx);
    }

    @Override
    public int onRunTask(TaskParams params) {

        Log.d("App", "Running background task");

        if (inventoryTask == null) {
            this.initializeServices();
        }

        inventoryTask.getJSON(new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String data) {
                Log.d("getJSON", data);
                inventoryService.send(data);
            }

            @Override
            public void onTaskError(Throwable error) {
                Log.e("getJSON error", error.getMessage());
            }
        });

        return GcmNetworkManager.RESULT_SUCCESS;

    }
}
