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
            JSONObject content = data.getJSONObject("request").getJSONObject("content");

            setCarrier(content);

        } catch (JSONException ex) {
            Log.e(Constants.LOG_TAG, "Error setting carrier in json", ex);
        }
    }

    private void setCarrier(JSONObject content) throws JSONException {
        TelephonyManager manager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String carrierName = manager.getNetworkOperatorName();
        content.put("carrier", carrierName);
    }
}
