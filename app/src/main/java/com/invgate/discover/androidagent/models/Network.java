
package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Network {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("ipAddress")
    @Expose
    private String ipAddress;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("macAddress")
    @Expose
    private String macAddress;

    /**
     * 
     * (Required)
     * 
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

}
