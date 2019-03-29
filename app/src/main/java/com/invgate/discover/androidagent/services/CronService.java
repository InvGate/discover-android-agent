package com.invgate.discover.androidagent.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.invgate.discover.androidagent.R;
import com.invgate.discover.androidagent.models.request.InventoryResponse;
import com.invgate.discover.androidagent.models.MainActivityModel;
import com.invgate.discover.androidagent.utils.Constants;

import org.flyve.inventory.InventoryTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;

public class CronService extends GcmTaskService {


    public static InventoryTask inventoryTask;
    public static Inventory inventoryService;

    /**
     * Create the inventory task collector and the inventory service
     */
    public void initializeServices(Context context) {
        String appVersion = "not-found";

        try {
            appVersion = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            Log.d(Constants.LOG_TAG, "App version: " + appVersion);
        } catch (PackageManager.NameNotFoundException ex) {
            Log.e(Constants.LOG_TAG,"App version not found", ex);
        }


        Preferences.configure(context);

        String apiurl = Preferences.Instance().getString("apiurl", "");
        Api.configure(apiurl);

        inventoryTask = new InventoryTask(context, appVersion, false) {
            @Override
            public String[] getCategories() {
                return new String[]{"Hardware", "User", "Storage", "OperatingSystem", "Bios", "Memory", "Inputs", "Sensors", "Drives", "Cpus", "Simcards", "Videos", "Networks", "Envs", "Jvm", "Software", "Usb", "Battery", "Controllers", "Modems"};
            }
        };
        inventoryService = new Inventory(new InheritedMobileDevice(context));
    }

    /**
     * This will be invoked in the configured scheduler period, getting the inventory and sending to the API
     * @param params Task Params
     * @return Network Manager Result
     */
    @Override
    public int onRunTask(TaskParams params) {

        Log.i(Constants.LOG_TAG, "Running scheduled send inventory");

        Context context = getApplicationContext();

        this.sendInventory(context).subscribe(response -> {
            InventoryResponse inventoryResponse = (InventoryResponse) response;
            Long interval = inventoryResponse.getInventoryInterval();
            Log.d(Constants.LOG_TAG, "Scheduling in " + interval.toString() + " seconds");
            ServiceScheduler.schedule(interval, context);
        }, error -> {
            String message = getString(R.string.error_sending_inventory);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        });

        return GcmNetworkManager.RESULT_SUCCESS;

    }

    public Observable sendInventory(Context context) {
        if (inventoryTask == null) {
            this.initializeServices(context);
        }

        Observable observable = Observable.create(emitter -> {

            inventoryTask.getJSON(new InventoryTask.OnTaskCompleted() {
                @Override
                public void onTaskSuccess(String data) {

                    inventoryService.send(data).subscribe(
                        dataObj -> {
                            InventoryResponse inventoryResponse = (InventoryResponse) dataObj;
                            Log.d(Constants.LOG_TAG, "Insight Api inventory stored: " + inventoryResponse.getStatus());
                            saveLastReported();
                            emitter.onNext(inventoryResponse);
                        },
                        e -> {
                            String errorMsg = context.getString(R.string.error_sending_inventory);
                            Log.e(Constants.LOG_TAG, errorMsg, (Throwable) e);
                            emitter.onError((Throwable) e);
                        }

                    );
                }

                @Override
                public void onTaskError(Throwable error) {
                    Log.e(Constants.LOG_TAG, "Getting Inventory JSON Error", error);
                }
            });

        });

        return observable;



    }

    protected void saveLastReported() {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String lastReported = format.format(new Date()).toString();
        MainActivityModel.Instance().setLastReport(lastReported);

        SharedPreferences.Editor editor = Preferences.Instance().edit();
        editor.putString("last_reported", lastReported);
        editor.commit();
    }
}
