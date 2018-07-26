package com.invgate.discover.androidagent.services;

import android.content.Context;
import android.util.Log;

import com.invgate.discover.androidagent.Util;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inventory {

    public com.invgate.discover.androidagent.resources.Inventory inventory;
    private Retrofit retrofit;


    public Inventory(Context context) {
        try {
            // Create a very simple REST adapter which points the GitHub API.
            retrofit = new Retrofit.Builder()
                    .baseUrl(Util.getProperty("APIURL", context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            inventory = retrofit.create(com.invgate.discover.androidagent.resources.Inventory.class);
        } catch (IOException ex) {
            Log.d("Configuration", "The key doesn't exists");
        }
    }

    public void send(String data) {


        // Create a call instance for looking up Retrofit contributors.
        Call<ResponseBody> call = inventory.send(data);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    ResponseBody body=response.body();
                    Log.d("Inventory Response", body.string());
                } catch (IOException ex) {
                    Log.d("Inventory Response Err", ex.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Handle failure
                Log.d("Inventory Response Fail", t.getMessage());
            }
        });
    }
}
