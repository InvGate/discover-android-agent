package com.invgate.discover.androidagent;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.invgate.discover.androidagent.utils.PermissionHelper;
import com.invgate.discover.androidagent.services.Agent;
import com.invgate.discover.androidagent.services.Api;
import com.invgate.discover.androidagent.services.Preferences;
import com.invgate.discover.androidagent.services.ServiceScheduler;

public class MainActivity extends AppCompatActivity {

    private Long seconds;
    private PermissionHelper permissionHelper;
    private int CAMERA_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("App", "Main Activity onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Preferences.configure(this);

        seconds = Preferences.Instance().getLong(
                "inventory_interval",
                Long.parseLong(getString(R.string.inventory_interval))
        );
        configureScreenSize();
        this.permissionHelper = new PermissionHelper(this);
        // this.startApp();
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        this.startApp();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan_qr:
                /* DO EDIT */
                goToQrScanner();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Starts the app process
     */
    protected void startApp() {

        // Check if is configured
        FrameLayout noConfigured = findViewById(R.id.no_configured);

        if (isConfigured()) {
            noConfigured.setVisibility(View.INVISIBLE);
            Log.d("App", "is configured");
            if (!hasInventoryId()) {
                Log.d("App", "has not the inventory id");
                createInventoryIdAndSchedule();
            } else {
                Log.d("App", "has the inventory id");
                ServiceScheduler.schedule(seconds, this);
            }
        } else {

            noConfigured.setVisibility(View.VISIBLE);
            noConfigured.setOnClickListener((View v) -> goToQrScanner());

        }

    }

    protected void configureScreenSize() {

        double screenInches = Util.getScreenDiagonal(getWindowManager());

        SharedPreferences.Editor editor = Preferences.Instance().edit();
        editor.putString("screen_size", String.format("%.1f", screenInches));
        editor.commit();
    }

    /**
     * Check if was configured with QR
     * @return
     */
    protected boolean isConfigured() {
        // TODO: This comment will be used when QR is implemented
        String apiurl = Preferences.Instance().getString("apiurl", "");
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

    protected void goToQrScanner() {

        if (!permissionHelper.permissionAlreadyGranted(Manifest.permission.CAMERA)) {
            permissionHelper.requestPermission(Manifest.permission.CAMERA, CAMERA_CODE);
        } else {
            Intent intent = new Intent(this, QrScannerActivity.class);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            goToQrScanner();
        } else {
            boolean showRationale = shouldShowRequestPermissionRationale( Manifest.permission.CAMERA );
            if (! showRationale) {
                permissionHelper.openSettingsDialog();
            }
        }

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
