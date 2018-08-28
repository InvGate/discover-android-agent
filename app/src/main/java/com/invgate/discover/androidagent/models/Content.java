
package com.invgate.discover.androidagent.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("batteries")
    @Expose
    private List<Battery> batteries = null;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("networks")
    @Expose
    private List<Network> networks = null;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("videos")
    @Expose
    private List<Video> videos = null;
    @SerializedName("hardware")
    @Expose
    private List<Hardware> hardware = null;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("operatingSystem")
    @Expose
    private List<OperatingSystem> operatingSystem = null;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("cpus")
    @Expose
    private List<Cpu> cpus = null;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("bios")
    @Expose
    private List<Bios> bios = null;
    @SerializedName("cameras")
    @Expose
    private List<Camera> cameras = null;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("softwares")
    @Expose
    private List<Software> softwares = null;

    /**
     * 
     * (Required)
     * 
     */
    public List<Battery> getBatteries() {
        return batteries;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setBatteries(List<Battery> batteries) {
        this.batteries = batteries;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<Network> getNetworks() {
        return networks;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<Video> getVideos() {
        return videos;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Hardware> getHardware() {
        return hardware;
    }

    public void setHardware(List<Hardware> hardware) {
        this.hardware = hardware;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<OperatingSystem> getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setOperatingSystem(List<OperatingSystem> operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<Cpu> getCpus() {
        return cpus;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setCpus(List<Cpu> cpus) {
        this.cpus = cpus;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<Bios> getBios() {
        return bios;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setBios(List<Bios> bios) {
        this.bios = bios;
    }

    public List<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(List<Camera> cameras) {
        this.cameras = cameras;
    }

    /**
     * 
     * (Required)
     * 
     */
    public List<Software> getSoftwares() {
        return softwares;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setSoftwares(List<Software> softwares) {
        this.softwares = softwares;
    }

}
