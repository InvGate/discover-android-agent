
package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InventoryModel {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("request")
    @Expose
    private Request request;

    /**
     * 
     * (Required)
     * 
     */
    public Request getRequest() {
        return request;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setRequest(Request request) {
        this.request = request;
    }

}
