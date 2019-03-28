
package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {

    @SerializedName("deviceId")
    @Expose
    private String deviceId;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("agentId")
    @Expose
    private String agentId;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("agentVersion")
    @Expose
    private String agentVersion;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("carrier")
    @Expose
    private String carrier;
    /**
     *
     * (Required)
     *
     */
    @SerializedName("imei")
    @Expose
    private String imei;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("screenSize")
    @Expose
    private Double screenSize;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("totalMemory")
    @Expose
    private Double totalMemory;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("totalStorage")
    @Expose
    private Double totalStorage;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("freeStorage")
    @Expose
    private Double freeStorage;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("content")
    @Expose
    private Content content;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getAgentId() {
        return agentId;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    /**
     * 
     * (Required)
     * 
     */
    public Double getScreenSize() {
        return screenSize;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setScreenSize(Double screenSize) {
        this.screenSize = screenSize;
    }

    /**
     * 
     * (Required)
     * 
     */
    public Double getTotalMemory() {
        return totalMemory;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setTotalMemory(Double totalMemory) {
        this.totalMemory = totalMemory;
    }

    /**
     * 
     * (Required)
     * 
     */
    public Double getTotalStorage() {
        return totalStorage;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setTotalStorage(Double totalStorage) {
        this.totalStorage = totalStorage;
    }

    /**
     * 
     * (Required)
     * 
     */
    public Double getFreeStorage() {
        return freeStorage;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setFreeStorage(Double freeStorage) {
        this.freeStorage = freeStorage;
    }

    /**
     * 
     * (Required)
     * 
     */
    public Content getContent() {
        return content;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setContent(Content content) {
        this.content = content;
    }

    /**
     *
     * (Required)
     *
     */
    public String getAgentVersion() { return agentVersion; }

    /**
     *
     * (Required)
     *
     */
    public void setAgentVersion(String agentVersion) { this.agentVersion = agentVersion; }

    /**
     *
     * (Required)
     *
     */
    public String getCarrier() {
        return carrier;
    }

    /**
     *
     * (Required)
     *
     */
    public void setCarrier(String carrier) { this.carrier = carrier; }
    /**
     *
     * (Required)
     *
     */
    public String getImei() {
        return imei;
    }

    /**
     *
     * (Required)
     *
     */
    public void setImei(String imei) { this.imei = imei; }
}
