
package com.invgate.discover.androidagent.resources;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.Call;


public interface Inventory {
    @POST("/")
    Call<ResponseBody> send(@Body String data);
}