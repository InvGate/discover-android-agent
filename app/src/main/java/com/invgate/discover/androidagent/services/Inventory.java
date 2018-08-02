package com.invgate.discover.androidagent.services;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inventory {

    public com.invgate.discover.androidagent.resources.Inventory inventory;


    public Inventory(Context context) {
        inventory = Api.Instance(context)
                       .create(com.invgate.discover.androidagent.resources.Inventory.class);
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
                    Log.e("Inventory Response Err", ex.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //Handle failure
                Log.e("Inventory Response Fail", t.getMessage());
            }
        });
    }
}
