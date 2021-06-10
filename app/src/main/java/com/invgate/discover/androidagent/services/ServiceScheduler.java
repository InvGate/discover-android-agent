package com.invgate.discover.androidagent.services;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;
import com.invgate.discover.androidagent.models.request.InventoryResponse;
import com.invgate.discover.androidagent.utils.Constants;

import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class ServiceScheduler extends Worker {
    private static final String TAG = "inventory-service";
    public static Long intervalTime = 3600L;

    public ServiceScheduler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {

        Log.i(Constants.LOG_TAG, "Running scheduled send inventory");

        CronService cronService = new CronService();

        cronService.sendInventory(this.getApplicationContext()).subscribe(response -> {
            InventoryResponse inventoryResponse = (InventoryResponse) response;
            Long interval = inventoryResponse.getInventoryInterval();
            Log.d(Constants.LOG_TAG, "Scheduling in " + interval.toString() + " seconds");

            if (interval.compareTo(intervalTime) != 0) {
                intervalTime = interval;
                ServiceScheduler.schedule();
            }
        }, error -> {
            Log.e(Constants.LOG_TAG, "Error sending inventory scheduled");
        });
        return ListenableWorker.Result.success();
    }


    public static void schedule() {

        final PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(ServiceScheduler.class, intervalTime, TimeUnit.SECONDS)
                        .addTag(ServiceScheduler.TAG)
                        .build();

        WorkManager.getInstance().enqueueUniquePeriodicWork(
                ServiceScheduler.TAG,  ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest
        );
    }
}
