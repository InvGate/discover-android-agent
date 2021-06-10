

package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentModel {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("uuid")
    @Expose
    private String uuid;

    @SerializedName("last_report")
    @Expose
    private String lastReport;

    @SerializedName("last_successful_report")
    @Expose
    private Object lastSuccessfulReport;

    @SerializedName("version")
    @Expose
    private String version;

    @SerializedName("platform")
    @Expose
    private String platform;

    @SerializedName("report_counter")
    @Expose
    private int reportCounter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLastReport() {
        return lastReport;
    }

    public void setLastReport(String lastReport) {
        this.lastReport = lastReport;
    }

    public Object getLastSuccessfulReport() {
        return lastSuccessfulReport;
    }

    public void setLastSuccessfulReport(Object lastSuccessfulReport) {
        this.lastSuccessfulReport = lastSuccessfulReport;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getReportCounter() {
        return reportCounter;
    }

    public void setReportCounter(int reportCounter) {
        this.reportCounter = reportCounter;
    }

}
