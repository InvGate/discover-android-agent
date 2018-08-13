package com.invgate.discover.androidagent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.invgate.discover.androidagent.services.Agent;
import com.invgate.discover.androidagent.services.Api;
import com.invgate.discover.androidagent.services.Preferences;
import com.invgate.discover.androidagent.services.ServiceScheduler;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Long seconds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Preferences.configure(this);

        seconds = Preferences.Instance().getLong(
                "inventory_interval",
                Long.parseLong(getString(R.string.inventory_interval))
        );
        this.startApp();
    }

    /**
     * Starts the app process
     */
    protected void startApp() {

        // Check if is configured

        if (isConfigured()) {
            Log.d("App", "is configured");
            if (!hasInventoryId()) {
                Log.d("App", "has not the inventory id");
                createInventoryIdAndSchedule();
            } else {
                Log.d("App", "has the inventory id");
                ServiceScheduler.schedule(seconds, this);
            }
        } else {
            // Show the qr button
        }

    }

    /**
     * Check if was configured with QR
     * @return
     */
    protected boolean isConfigured() {
        // TODO: This comment will be used when QR is implemented
        // String apiurl = Preferences.Instance().getString("apiurl", "");
        String apiurl = getString(R.string.apiurl);
        if (apiurl != "") {
            Api.configure(apiurl);
            return true;
        }
        return false;
    }

    /**
     * Checks if the inventory id is stored
     * @return boolean
     */
    protected boolean hasInventoryId() {
        String uuid = Preferences.Instance().getString("uuid", "");
        return uuid != "";
    }

    /**
     * Creates the inventory ID, saving and scheduling the service
     */
    protected void createInventoryIdAndSchedule() {
        Log.d("App", "creating the inventory id");
        Agent agentService = new Agent(this);
        agentService.create().subscribe(
            uuid -> saveInventoryId(uuid),
            e -> Log.e("Subscribe", "Error", e)
        );
    }

    /**
     * Saves the inventory id in the storage and execute the scheduler
     * @param uuid The inventory id
     */
    protected void saveInventoryId(String uuid) {
        Log.d("App", "saving inventory id " + uuid);
        // Save the uuid
        SharedPreferences.Editor edit = Preferences.Instance().edit();
        edit.putString("uuid", uuid);
        edit.commit();

        ServiceScheduler.schedule(seconds, this);
    }

}
