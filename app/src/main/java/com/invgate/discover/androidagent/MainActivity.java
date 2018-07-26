package com.invgate.discover.androidagent;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.flyve.inventory.InventoryTask;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.invgate.discover.androidagent.services.Inventory;

public class MainActivity extends AppCompatActivity {

    private InventoryTask inventoryTask;
    private Inventory inventoryService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inventoryService = new Inventory(this);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.INTERNET
                },
                1);

        this.addInventoryButtonClickHandler();

    }

    public void addInventoryButtonClickHandler() {
        Button btnRun = findViewById(R.id.btnRun);
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventoryTask = new InventoryTask(MainActivity.this, "example-app-java", true);
                inventoryTask.getJSON(new InventoryTask.OnTaskCompleted() {
                    @Override
                    public void onTaskSuccess(String data) {
                        Log.d("JSON", data);
                        inventoryService.send(data);
                    }

                    @Override
                    public void onTaskError(Throwable error) {
                        Log.e("JSON error", error.getMessage());
                    }
                });
            }
        });
    }
}
