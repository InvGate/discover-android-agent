package com.invgate.discover.androidagent.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionHelper {

    private Activity activity;

    public PermissionHelper(Activity activity) {
        this.activity = activity;
    }

    public boolean permissionAlreadyGranted(String permission) {

        int result = ContextCompat.checkSelfPermission(activity, permission);

        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    public void requestPermission(String permission, int requestCode) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

        }
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }


    public void openSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Required Permissions");
        builder.setMessage("This app require permission to use awesome feature. Grant them in app settings.");
        builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
}