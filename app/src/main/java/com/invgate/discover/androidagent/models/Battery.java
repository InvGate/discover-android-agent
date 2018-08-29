
package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Battery {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("chemistry")
    @Expose
    private String chemistry;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("temperature")
    @Expose
    private String temperature;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("voltage")
    @Expose
    private String voltage;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("level")
    @Expose
    private String level;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("health")
    @Expose
    private String health;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * 
     * (Required)
     * 
     */
    public String getChemistry() {
        return chemistry;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setChemistry(String chemistry) {
        this.chemistry = chemistry;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getVoltage() {
        return voltage;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getLevel() {
        return level;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getHealth() {
        return health;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setHealth(String health) {
        this.health = health;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
