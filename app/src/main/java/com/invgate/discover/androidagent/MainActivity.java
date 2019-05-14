package com.invgate.discover.androidagent;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.invgate.discover.androidagent.databinding.ActivityMainBinding;
import com.invgate.discover.androidagent.models.MainActivityModel;
import com.invgate.discover.androidagent.services.CronService;
import com.invgate.discover.androidagent.services.LogExport;
import com.invgate.discover.androidagent.utils.Constants;
import com.invgate.discover.androidagent.utils.PermissionHelper;
import com.invgate.discover.androidagent.services.Agent;
import com.invgate.discover.androidagent.services.Api;
import com.invgate.discover.androidagent.services.Preferences;
import com.invgate.discover.androidagent.services.ServiceScheduler;

import io.fabric.sdk.android.Fabric;
import io.reactivex.Observable;
import retrofit2.HttpException;

public class MainActivity extends AppCompatActivity {

    private Long seconds = 31L;
    private PermissionHelper permissionHelper;
    private static final int CAMERA_CODE = 1;
    private static final int REQUEST_READ_PHONE_STATE_CODE = 2;
    private boolean appConfigured = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        Preferences.configure(this);
        configureScreenSize();
        this.permissionHelper = new PermissionHelper(this);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        this.startApp();
    }


    @Override
    public void onBackPressed() {
        this.moveTaskToBack(false);
    }

    /**
     * Starts the app process
     */
    protected void startApp() {

        // Check if is configured

        if (isConfigured()) {

            if (!appConfigured) {
                setBindingModel();

                Log.i(Constants.LOG_TAG, "The app is configured");
                if (!hasInventoryId()) {
                    Log.i(Constants.LOG_TAG, "The app has not the inventory id");
                    createInventoryId().subscribe(
                        n -> this.startFirstSchedule(),
                        e -> this.setConfigActivity()
                    );
                } else {
                    Log.i(Constants.LOG_TAG, "The app has the inventory id");
                    this.startFirstSchedule();
                }
            }


        } else {
            this.setConfigActivity();
        }

    }

    protected void startFirstSchedule() {
        setButtonHandlers();
        appConfigured = true;
        ServiceScheduler.schedule();

        if (!permissionHelper.permissionAlreadyGranted(Manifest.permission.READ_PHONE_STATE)) {
            Log.i(Constants.LOG_TAG, "Requesting Read Phone State Permission");
            permissionHelper.requestPermission(Manifest.permission.READ_PHONE_STATE, REQUEST_READ_PHONE_STATE_CODE);
        }
    }

    protected void setConfigActivity() {
        Log.i(Constants.LOG_TAG, "The app is not configured yet");
        setContentView(R.layout.activity_config);
        findViewById(R.id.qrScanBtn).setOnClickListener((View v) -> goToQrScanner());
    }

    protected void setBindingModel() {
        ActivityMainBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MainActivityModel model = MainActivityModel.Instance();
        model.setLastReport(Preferences.Instance().getString("last_reported", null));
        model.setInstanceUrl(Preferences.Instance().getString("apiurl", null));
        activityBinding.setModel(model);

        try {
            TextView tv = findViewById(R.id.versionValueText);
            tv.setText(this.getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException ex) {
            Log.e(Constants.LOG_TAG,"App version not found", ex);
        }
    }

    protected void setButtonHandlers() {

        // Re scan Qr
        Button rescanBtn = findViewById(R.id.rescanBtn);
        rescanBtn.setOnClickListener((View v) -> goToQrScanner());

        Button sendInventoryBtn = findViewById(R.id.sendInventoryBtn);
        sendInventoryBtn.setOnClickListener((View v) -> {
            Log.i(Constants.LOG_TAG, "Running force send inventory");
            CronService cronService = new CronService();
            // disable send button
            sendInventoryBtn.setEnabled(false);
            cronService.sendInventory(this).subscribe(result -> {
                // enable button
                sendInventoryBtn.setEnabled(true);
            }, error -> {
                sendInventoryBtn.setEnabled(true);
                Log.e(Constants.LOG_TAG, "Error forcing send inventory");
                String message = getString(R.string.error_sending_inventory);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            });
        });

        // Share error log
        Button errorLogBtn = findViewById(R.id.errorLogBtn);
        errorLogBtn.setOnClickListener((View v) -> {
            String logs = LogExport.getLogs();
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, logs);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });
    }

    protected void configureScreenSize() {

        double screenInches = Util.getScreenDiagonal(getWindowManager());
        String screenInchesText = String.format("%.1f", screenInches);

        Log.d(Constants.LOG_TAG, "Getting screen size: " + screenInchesText);

        SharedPreferences.Editor editor = Preferences.Instance().edit();
        editor.putString("screen_size", screenInchesText);
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
            Log.i(Constants.LOG_TAG, "Requesting Camera Permission");
            permissionHelper.requestPermission(Manifest.permission.CAMERA, CAMERA_CODE);
        } else {
            Intent intent = new Intent(this, QrScannerActivity.class);
            startActivity(intent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(Constants.LOG_TAG, "Camera Permission Granted");
                    goToQrScanner();
                } else {
                    boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
                    Log.d(Constants.LOG_TAG, "Should open settings dialog: " + !showRationale);
                    if (!showRationale) {
                        permissionHelper.openSettingsDialog();
                    }
                }
                break;

            case REQUEST_READ_PHONE_STATE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(Constants.LOG_TAG, "PHONE STATE Permission Granted");
                } else {
                    boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE);
                    Log.d(Constants.LOG_TAG, "Should open settings dialog: " + !showRationale);
                    if (!showRationale) {
                        permissionHelper.openSettingsDialog();
                    }
                }
                break;

        }

    }

    /**
     * Creates the inventory ID, saving and scheduling the service
     */
    protected Observable createInventoryId() {
        Log.i(Constants.LOG_TAG, "Creating the inventory id");
        Agent agentService = new Agent(this);

        Observable observable = Observable.create(emitter -> {
            agentService.create().subscribe(
                uuid -> {
                    this.saveInventoryId(uuid);
                    String message = getString(R.string.qr_scanned_ok);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    emitter.onNext("");
                },
                (Throwable e) -> {
                    String message = getString(R.string.error_saving_inventory_id);
                    if (e instanceof HttpException) {
                        int code = ((HttpException) e).code();
                        if ((code == 401) || (code == 403)) {
                            message = getString(R.string.qr_code_expired);
                        }
                    }
                    Log.e(Constants.LOG_TAG, message, e);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    emitter.onError(e);
                }
            );
        });

        return observable;
    }

    /**
     * Saves the inventory id in the storage and execute the scheduler
     * @param uuid The inventory id
     */
    protected void saveInventoryId(String uuid) {
        Log.d(Constants.LOG_TAG, "Saving inventory id: " + uuid);
        // Save the uuid
        SharedPreferences.Editor edit = Preferences.Instance().edit();
        edit.putString("uuid", uuid);
        edit.commit();
    }


}
