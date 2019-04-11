package com.invgate.discover.androidagent;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;
import com.invgate.discover.androidagent.models.MainActivityModel;
import com.invgate.discover.androidagent.services.Api;
import com.invgate.discover.androidagent.services.Preferences;
import com.invgate.discover.androidagent.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        String token = rawResult.getText();
        Log.d(Constants.LOG_TAG, "Qr scan result: " + token); // Prints scan results


        try {
            JSONObject json = new JSONObject(token);
            String url = json.getString("url");
            String invToken = json.getString("token");
            SharedPreferences.Editor editor = Preferences.Instance().edit();
            editor.putString("apiurl", url);
            editor.putString("invToken", invToken);

            if (json.has("cloud_url")) {
                editor.putString("cloud_url", json.getString("cloud_url"));
            }

            editor.commit();
            Api.configure(url);
            MainActivityModel.Instance().setInstanceUrl(url);

        } catch (JSONException ex) {
            String message = getString(R.string.error_json_qr);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            Log.e(Constants.LOG_TAG, message, ex);

        } finally {

            onBackPressed();
        }
    }
}