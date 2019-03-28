package com.invgate.discover.androidagent.services;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

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
            JSONObject request = data.getJSONObject("request");

            setCarrier(request);

        } catch (JSONException ex) {
            Log.e(Constants.LOG_TAG, "Error setting carrier in json", ex);
        }
    }

    private void setCarrier(JSONObject request) throws JSONException {
        TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        request.put("carrier", carrierName);
        Log.d(Constants.LOG_TAG, "Carrier Name " + carrierName);
    }
}
