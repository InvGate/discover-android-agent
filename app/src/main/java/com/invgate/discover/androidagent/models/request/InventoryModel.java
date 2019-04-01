
package com.invgate.discover.androidagent.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.invgate.discover.androidagent.models.Request;

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
