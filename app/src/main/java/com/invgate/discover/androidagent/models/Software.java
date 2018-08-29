
package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Software {

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
    @SerializedName("version")
    @Expose
    private String version;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("manufacturer")
    @Expose
    private String manufacturer;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("installDate")
    @Expose
    private String installDate;

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
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getInstallDate() {
        return installDate;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

}
