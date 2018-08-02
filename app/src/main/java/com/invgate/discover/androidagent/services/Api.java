package com.invgate.discover.androidagent.services;

import android.content.Context;
import android.util.Log;

import com.invgate.discover.androidagent.Util;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static Api instance;
    private Retrofit retrofit;

    private Api (Context context) {
        try {
            // Create a very simple REST adapter
            retrofit = new Retrofit.Builder()
                    .baseUrl(Util.getProperty("APIURL", context))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } catch (IOException ex) {
            Log.e("Configuration", "The key doesn't exists");
        }
    }

    public static Retrofit Instance(Context context) {
        if (instance == null) {
            instance = new Api(context);
        }
        return instance.retrofit;
    }

}
