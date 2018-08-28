
package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bios {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("motherBoardSerialNumber")
    @Expose
    private String motherBoardSerialNumber;
    @SerializedName("motherBoardManufacturer")
    @Expose
    private String motherBoardManufacturer;
    @SerializedName("motherBoardModel")
    @Expose
    private String motherBoardModel;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("biosReleaseDate")
    @Expose
    private String biosReleaseDate;

    /**
     * 
     * (Required)
     * 
     */
    public String getMotherBoardSerialNumber() {
        return motherBoardSerialNumber;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setMotherBoardSerialNumber(String motherBoardSerialNumber) {
        this.motherBoardSerialNumber = motherBoardSerialNumber;
    }

    public String getMotherBoardManufacturer() {
        return motherBoardManufacturer;
    }

    public void setMotherBoardManufacturer(String motherBoardManufacturer) {
        this.motherBoardManufacturer = motherBoardManufacturer;
    }

    public String getMotherBoardModel() {
        return motherBoardModel;
    }

    public void setMotherBoardModel(String motherBoardModel) {
        this.motherBoardModel = motherBoardModel;
    }

    /**
     * 
     * (Required)
     * 
     */
    public String getBiosReleaseDate() {
        return biosReleaseDate;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setBiosReleaseDate(String biosReleaseDate) {
        this.biosReleaseDate = biosReleaseDate;
    }

}
