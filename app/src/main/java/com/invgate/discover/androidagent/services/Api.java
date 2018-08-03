package com.invgate.discover.androidagent.services;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static Api instance;
    private static String baseURL;
    private Retrofit retrofit;

    private Api () {

        // Create a very simple REST adapter
        retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static Retrofit Instance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance.retrofit;
    }

    public static void configure(String baseURL) {
        Api.baseURL = baseURL;
    }

}
