
package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cpu {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("name")
    @Expose
    private String name;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("cpuCores")
    @Expose
    private Double cpuCores;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("cpuFrequency")
    @Expose
    private Double cpuFrequency;

    /**
     * 
     * (Required)
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * (Required)
     * 
     */
    public Double getCpuCores() {
        return cpuCores;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setCpuCores(Double cpuCores) {
        this.cpuCores = cpuCores;
    }

    /**
     * 
     * (Required)
     * 
     */
    public Double getCpuFrequency() {
        return cpuFrequency;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setCpuFrequency(Double cpuFrequency) {
        this.cpuFrequency = cpuFrequency;
    }

}
