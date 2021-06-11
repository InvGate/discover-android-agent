package com.invgate.discover.androidagent.services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.invgate.discover.androidagent.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class InheritedMobileDevice {

    protected Context context;

    public InheritedMobileDevice(Context context) {
        this.context = context;
    }

    public void updateJsonData(JSONObject data) {
        try {
            JSONObject content = data.getJSONObject("request").getJSONObject("content");

            setCarrier(content);
            setPhoneLineNumber(content);

        } catch (JSONException ex) {
            Log.e(Constants.LOG_TAG, "Error setting carrier in json", ex);
        }
    }

    private void setCarrier(JSONObject content) throws JSONException {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        content.put("carrier", carrierName);
    }

    private void setPhoneLineNumber(JSONObject content) throws JSONException {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (
            ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.i(Constants.LOG_TAG, "The permission to get Line number has rejected by user");
            return;
        }
        Log.i(Constants.LOG_TAG, "Getting line number");
        String lineNumber = manager.getLine1Number();
        Log.d(Constants.LOG_TAG, "Line number is " + lineNumber);
        content.put("lineNumber", lineNumber);
    }
}
