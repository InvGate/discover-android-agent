
package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("resolution")
    @Expose
    private String resolution;

    /**
     * 
     * (Required)
     * 
     */
    public String getResolution() {
        return resolution;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

}
