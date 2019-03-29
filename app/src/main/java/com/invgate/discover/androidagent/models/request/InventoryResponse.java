package com.invgate.discover.androidagent.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InventoryResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("query_interval")
    @Expose
    private Long queryInterval;

    @SerializedName("inventory_interval")
    @Expose
    private Long inventoryInterval;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getQueryInterval() {
        return queryInterval;
    }

    public void setQueryInterval(Long queryInterval) {
        this.queryInterval = queryInterval;
    }

    public Long getInventoryInterval() {
        return inventoryInterval;
    }

    public void setInventoryInterval(Long inventoryInterval) {
        this.inventoryInterval = inventoryInterval;
    }

}