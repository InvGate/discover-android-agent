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

        String message = "";
        try {
            JSONObject json = new JSONObject(token);
            String url = json.getString("url");
            SharedPreferences.Editor editor = Preferences.Instance().edit();
            editor.putString("apiurl", url);
            editor.commit();
            Api.configure(url);
            MainActivityModel.Instance().setInstanceUrl(url);

            message = getString(R.string.qr_scanned_ok);

        } catch (JSONException ex) {
            message = getString(R.string.error_json_qr);
            Log.e(Constants.LOG_TAG, "Error loading QR code", ex);

        } finally {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }
}