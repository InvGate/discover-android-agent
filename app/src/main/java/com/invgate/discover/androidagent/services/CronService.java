package com.invgate.discover.androidagent.services;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.invgate.discover.androidagent.R;
import com.invgate.discover.androidagent.models.InventoryResponse;
import com.invgate.discover.androidagent.utils.Constants;

import org.flyve.inventory.InventoryTask;

public class CronService extends GcmTaskService {


    public static InventoryTask inventoryTask;
    public static Inventory inventoryService;
    private Context context;

    /**
     * Create the inventory task collector and the inventory service
     */
    public void initializeServices() {
        String appVersion = "not-found";
        try {
            appVersion = this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.d(Constants.LOG_TAG, "App version: " + appVersion);
        } catch (PackageManager.NameNotFoundException ex) {
            Log.e(Constants.LOG_TAG,"App version not found", ex);
        }

        context = getApplicationContext();
        Preferences.configure(context);

        String apiurl = Preferences.Instance().getString("apiurl", "");
        Api.configure(apiurl);

        inventoryTask = new InventoryTask(context, appVersion, false);
        inventoryService = new Inventory();
    }

    /**
     * This will be invoked in the configured scheduler period, getting the inventory and sending to the API
     * @param params Task Params
     * @return Network Manager Result
     */
    @Override
    public int onRunTask(TaskParams params) {

        Log.i(Constants.LOG_TAG, "Running background get inventory");

        if (inventoryTask == null) {
            this.initializeServices();
        }

        inventoryTask.getJSON(new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String data) {
                Log.d(Constants.LOG_TAG, "Inventory string result: " + data);

                String inventoryId = Preferences.Instance().getString("uuid", "");
                inventoryService.send(data).subscribe(
                        dataObj -> {
                            InventoryResponse inventoryResponse = (InventoryResponse) dataObj;
                            Log.d(Constants.LOG_TAG, "Insight Api inventory stored: " + inventoryResponse.getStatus());
                            ServiceScheduler.schedule(inventoryResponse.getInventoryInterval() * 60, context);
                        }
                );
            }

            @Override
            public void onTaskError(Throwable error) {
                Log.e(Constants.LOG_TAG, "Getting Inventory JSON Error", error);
            }
        });

        return GcmNetworkManager.RESULT_SUCCESS;

    }
}
