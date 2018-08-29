
package com.invgate.discover.androidagent.resources;

import com.invgate.discover.androidagent.models.InventoryModel;
import com.invgate.discover.androidagent.models.InventoryResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface Inventory {
    @POST("/api/inventory-mobile/")
    Observable<InventoryResponse> send(@Body InventoryModel data);
}