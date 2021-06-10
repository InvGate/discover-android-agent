
package com.invgate.discover.androidagent.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hardware {

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
    @SerializedName("lastLoggedUser")
    @Expose
    private String lastLoggedUser;

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
    public String getLastLoggedUser() {
        return lastLoggedUser;
    }

    /**
     * 
     * (Required)
     * 
     */
    public void setLastLoggedUser(String lastLoggedUser) {
        this.lastLoggedUser = lastLoggedUser;
    }

}
