
package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatingSystem {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("Name")
    @Expose
    private String name;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("Version")
    @Expose
    private String version;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("fullName")
    @Expose
    private String fullName;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("architecture")
    @Expose
    private String architecture;

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
    public String getVersion() {
        return version;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getArchitecture() {
        return architecture;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

}
