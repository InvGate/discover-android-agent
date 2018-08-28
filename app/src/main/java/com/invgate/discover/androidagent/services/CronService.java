package com.invgate.discover.androidagent.services;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.invgate.discover.androidagent.R;
import com.invgate.discover.androidagent.models.InventoryResponse;

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
        } catch (PackageManager.NameNotFoundException ex) {
            Log.e("AppVersion","not found", ex);
        }

        // TODO: This comment will be used when QR is implemented
        // String apiurl = Preferences.Instance().getString("apiurl", "");
        String apiurl = getString(R.string.apiurl);
        Api.configure(apiurl);

        context = getApplicationContext();
        Preferences.configure(context);

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

        Log.d("App", "Running background task");

        if (inventoryTask == null) {
            this.initializeServices();
        }

        inventoryTask.getJSON(new InventoryTask.OnTaskCompleted() {
            @Override
            public void onTaskSuccess(String data) {
                Log.d("getJSON", data);
                String inventoryId = Preferences.Instance().getString("uuid", "");
                Log.d("Inventory ID", inventoryId);
                inventoryService.send(data).subscribe(
                        dataObj -> {
                            InventoryResponse inventoryResponse = (InventoryResponse) dataObj;
                            Log.i("InventoryResult", inventoryResponse.getStatus());
                            ServiceScheduler.schedule(inventoryResponse.getInventoryInterval() * 60, context);
                        }
                );
            }

            @Override
            public void onTaskError(Throwable error) {
                Log.e("getJSON error", error.getMessage());
            }
        });

        return GcmNetworkManager.RESULT_SUCCESS;

    }
}
