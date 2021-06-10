package com.invgate.discover.androidagent.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import com.invgate.discover.androidagent.BR;

public class MainActivityModel extends BaseObservable {

    private static MainActivityModel instance;

    public boolean internetConnection = false;
    public boolean showLastReport = false;
    public String lastReport = null;



    public String instanceUrl = "";

    
    public static MainActivityModel Instance() {
        if (instance == null) {
            instance = new MainActivityModel();
        }
        return instance;
    }
    
    private MainActivityModel() {
    }

    @Bindable
    public String getLastReport() {
        return lastReport;
    }

    public void setLastReport(String lastReport) {
        this.lastReport = lastReport;
        this.showLastReport = lastReport != null;
        notifyPropertyChanged(BR.lastReport);
        notifyPropertyChanged(BR.showLastReport);
    }

    @Bindable
    public String getInstanceUrl() {
        return instanceUrl;
    }

    public void setInstanceUrl(String instanceUrl) {
        this.instanceUrl = instanceUrl;
        notifyPropertyChanged(BR.instanceUrl);
    }

    @Bindable
    public boolean isInternetConnection() {
        return internetConnection;
    }

    @Bindable
    public boolean isShowLastReport() {
        return showLastReport;
    }

}
