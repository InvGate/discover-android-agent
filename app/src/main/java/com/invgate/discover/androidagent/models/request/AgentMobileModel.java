
package com.invgate.discover.androidagent.models.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AgentMobileModel {

    /**
     *
     * (Required)
     *
     */
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    /**
     *
     * (Required)
     *
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * (Required)
     *
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
