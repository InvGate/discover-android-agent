package com.invgate.discover.androidagent.services;

import android.util.Log;

import com.google.gson.Gson;
import com.invgate.discover.androidagent.models.Battery;
import com.invgate.discover.androidagent.models.Bios;
import com.invgate.discover.androidagent.models.Camera;
import com.invgate.discover.androidagent.models.Content;
import com.invgate.discover.androidagent.models.Cpu;
import com.invgate.discover.androidagent.models.Hardware;
import com.invgate.discover.androidagent.models.request.InventoryModel;
import com.invgate.discover.androidagent.models.request.InventoryResponse;
import com.invgate.discover.androidagent.models.Network;
import com.invgate.discover.androidagent.models.OperatingSystem;
import com.invgate.discover.androidagent.models.Request;
import com.invgate.discover.androidagent.models.Software;
import com.invgate.discover.androidagent.models.Video;
import com.invgate.discover.androidagent.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Inventory {

    protected InheritedMobileDevice inheritedMobileDevice;

    public Inventory(InheritedMobileDevice inheritedMobileDevice) {
        this.inheritedMobileDevice = inheritedMobileDevice;
    }

    public Observable send(String data) {

        Log.d(Constants.LOG_TAG, "API Base URL: " + Api.Instance().baseUrl());
        com.invgate.discover.androidagent.resources.Inventory inventory = Api.Instance()
                .create(com.invgate.discover.androidagent.resources.Inventory.class);

        InventoryModel inventoryModel = prepareData(data);
        if (inventoryModel != null) {
            Gson gson = new Gson();
            Log.d(Constants.LOG_TAG, "Inventory model string result: " + gson.toJson(inventoryModel));
            Observable<InventoryResponse> inventoryObs = inventory.send(inventoryModel);
            return inventoryObs.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());

        } else {
            Log.e(Constants.LOG_TAG, "Inventory JSON is bad formatted");
            return Observable.error(new Exception());
        }
    }

    private InventoryModel prepareData(String data) {
        try {
            JSONObject json = new JSONObject(data);

            inheritedMobileDevice.updateJsonData(json);
            InventoryModel inventoryModel = new InventoryModel();
            Request request = createRequest(json.getJSONObject("request"));

            inventoryModel.setRequest(request);
            return inventoryModel;
        } catch (JSONException ex) {
            Log.e(Constants.LOG_TAG, "Error parsing data from flyve", ex);
            return null;
        }
    }

    private Request createRequest(JSONObject json) throws JSONException {
        Request request = new Request();
        request.setAgentId(Preferences.Instance().getString("uuid", ""));

        JSONObject item = json.getJSONObject("content").getJSONArray("bios").optJSONObject(0);


        request.setDeviceId(this.getString(item,"systemSerialNumber"));
        request.setAgentVersion(json.getString("versionClient"));

        long totalExternal = Storage.getTotalExternalMemorySize();
        long totalInternal = Storage.getTotalInternalMemorySize();
        long availableExternalMemorySize = Storage.getAvailableExternalMemorySize();
        long availableInternalMemorySize = Storage.getAvailableInternalMemorySize();

        request.setFreeStorage((double) availableExternalMemorySize + availableInternalMemorySize);
        request.setTotalStorage((double) totalExternal + totalInternal);
        String screenSize = Preferences.Instance().getString("screen_size", "").replaceAll(",", ".");
        request.setScreenSize(Double.parseDouble(screenSize));

        // The IMEI is in request, but needs to be sended in content
        Content content = createContent(json.getJSONObject("content"));
        if (json.has("IMEI")) {
            content.setImei(json.getString("IMEI"));
        }
        request.setContent(content);

        request.setTotalMemory(getTotalRam(json.getJSONObject("content").getJSONArray("memories")));

        return request;
    }

    private double getTotalRam(JSONArray json) {
        Double total = 0.0;
        if(json!=null && json.length()>0){
            for (int i = 0; i < json.length(); i++) {
                JSONObject item = json.optJSONObject(i);
                total += this.getDouble(item, "capacity");
            }
        }
        return total;
    }

    private Content createContent(JSONObject json) throws JSONException {
        Content content = new Content();
        content.setBatteries(createBatteries(json));
        content.setBios(createBios(json.getJSONArray("bios")));
        content.setCameras(createCameras(json));
        content.setCpus(createCpus(json.getJSONArray("cpus")));
        content.setHardware(createHardwares(json.getJSONArray("hardware")));
        content.setNetworks(createNetworks(json.getJSONArray("networks")));
        content.setOperatingSystem(createOperatingSystems(json));
        content.setSoftwares(createSoftwares(json.getJSONArray("softwares")));
        content.setVideos(createVideos(json.getJSONArray("videos")));
        content.setCarrier(json.getString("carrier"));
        if (json.has("lineNumber")) {
            content.setLineNumber(json.getString("lineNumber"));
        }

        return content;
    }

    private List<Battery> createBatteries(JSONObject json) throws JSONException {
        List<Battery> batteries = new ArrayList<>();

        try {
            JSONArray jsonArray = json.getJSONArray("batteries");

            if(jsonArray!=null && jsonArray.length()>0){
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.optJSONObject(i);

                    Battery battery = new Battery();
                    battery.setChemistry(this.getString(item,  "chemistry"));
                    battery.setHealth(this.getString(item,  "health"));
                    battery.setLevel(this.getString(item,  "level"));
                    battery.setStatus(this.getString(item,  "status"));
                    battery.setTemperature(this.getString(item,  "temperature"));
                    battery.setVoltage(this.getString(item,  "voltage"));
                    batteries.add(battery);
                }
            }

        } catch (JSONException ex) {
            Log.e(Constants.LOG_TAG, "Batteries was not found in the JSON");
            Battery battery = new Battery();
            battery.setChemistry("Unknown");
            battery.setHealth("Unknown");
            battery.setLevel("Unknown");
            battery.setStatus("Unknown");
            battery.setTemperature("Unknown");
            battery.setVoltage("Unknown");
            batteries.add(battery);
        }

        return batteries;
    }

    private List<Bios> createBios(JSONArray json) throws JSONException {
        List<Bios> bios = new ArrayList<>();

        if(json!=null && json.length()>0){
            for (int i = 0; i < json.length(); i++) {
                JSONObject item = json.optJSONObject(i);

                Bios aBios = new Bios();
                aBios.setBiosReleaseDate(this.getString(item,  "biosReleaseDate"));
                aBios.setMotherBoardManufacturer(this.getString(item,  "motherBoardManufacturer"));
                aBios.setMotherBoardModel(this.getString(item,  "motherBoardModel"));
                aBios.setMotherBoardSerialNumber(this.getString(item,  "motherBoardSerialNumber"));
                bios.add(aBios);
            }
        }

        return bios;
    }

    private List<Camera> createCameras(JSONObject json) {
        List<Camera> cameras = new ArrayList<>();

        try {
            if (json.has("cameras")) {
                JSONArray jsonArray = json.getJSONArray("cameras");

                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.optJSONObject(i);

                        Camera camera = new Camera();
                        camera.setResolutions(this.getString(item, "resolutions"));
                        cameras.add(camera);
                    }
                }
            } else {
                Camera camera = new Camera();
                camera.setResolutions("1x1");
                cameras.add(camera);
            }
        } catch (JSONException ex) {
            Log.i(Constants.LOG_TAG, "Camera was not found in the JSON");
        }


        return cameras;
    }

    private List<Cpu> createCpus(JSONArray json) {
        List<Cpu> cpus = new ArrayList<>();

        if (json != null && json.length() > 0) {
            for (int i = 0; i < json.length(); i++) {
                JSONObject item = json.optJSONObject(i);
                Cpu cpu = new Cpu();
                cpu.setCpuCores(this.getDouble(item, "core"));
                cpu.setCpuFrequency(this.getDouble(item, "cpuFrequency"));
                cpu.setName(this.getString(item, "name"));
                cpus.add(cpu);
            }
        }


        return cpus;
    }

    private List<Hardware> createHardwares(JSONArray json) throws JSONException {
        List<Hardware> hardwares = new ArrayList<>();

        if(json!=null && json.length()>0){
            for (int i = 0; i < json.length(); i++) {
                JSONObject item = json.optJSONObject(i);
                Hardware hardware= new Hardware();
                hardware.setLastLoggedUser(this.getString(item, "lastLoggedUser"));
                hardware.setName(this.getString(item, "name"));
                hardwares.add(hardware);
            }
        }

        return hardwares;
    }

    private List<Network> createNetworks(JSONArray json) throws JSONException {
        List<Network> networks = new ArrayList<>();

        if(json!=null && json.length()>0){
            for (int i = 0; i < json.length(); i++) {
                JSONObject item = json.optJSONObject(i);
                Network network= new Network();
                network.setIpAddress(this.getString(item, "ipAddress"));
                network.setMacAddress(this.getString(item, "macAddress"));
                networks.add(network);
            }
        }

        return networks;
    }

    private List<OperatingSystem> createOperatingSystems(JSONObject json) throws JSONException {

        List<OperatingSystem> operatingSystems = new ArrayList<>();

        if (json.has("operatingSystem")) {
            JSONArray jsonItems = json.getJSONArray("operatingSystem");

            if(jsonItems!=null && jsonItems.length()>0){
                for (int i = 0; i < jsonItems.length(); i++) {
                    JSONObject item = jsonItems.optJSONObject(i);
                    OperatingSystem operatingSystem = new OperatingSystem();
                    operatingSystem.setArchitecture(this.getString(item, "architecture"));
                    operatingSystem.setFullName(this.getString(item, "fullName"));
                    operatingSystem.setName(this.getString(item, "Name"));
                    operatingSystem.setVersion(this.getString(item, "Version"));
                    operatingSystems.add(operatingSystem);
                }

                return operatingSystems;
            }
        }

        // If doesn't has operativeSystem json attribute
        OperatingSystem operatingSystem = new OperatingSystem();
        operatingSystem.setArchitecture("unknown");
        operatingSystem.setFullName("unknown");
        operatingSystem.setName("Android");
        operatingSystem.setVersion("unknown");
        operatingSystems.add(operatingSystem);

        return operatingSystems;
    }

    private List<Software> createSoftwares(JSONArray json) throws JSONException {
        List<Software> softwares = new ArrayList<>();

        if(json!=null && json.length()>0){
            for (int i = 0; i < json.length(); i++) {
                JSONObject item = json.optJSONObject(i);

                try {
                    Software software = new Software();

                    String dateString = this.getString(item, "installDate");
                    SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
                    String timestamp = Long.toString(((java.util.Date) df.parse(dateString)).getTime());

                    software.setInstallDate(timestamp);
                    software.setManufacturer(this.getString(item, "from")); //TODO buscar otra forma
                    software.setName(this.getString(item, "name"));
                    software.setVersion(this.getString(item, "version"));
                    softwares.add(software);
                } catch (ParseException ex) {
                    Log.e(Constants.LOG_TAG, "Error parsing installation date", ex);
                }
            }
        }
        return softwares;
    }

    private List<Video> createVideos(JSONArray json) throws JSONException {
        List<Video> videos = new ArrayList<>();

        if(json!=null && json.length()>0){
            for (int i = 0; i < json.length(); i++) {
                JSONObject item = json.optJSONObject(i);
                Video video = new Video();
                video.setResolution(this.getString(item, "resolution"));
                videos.add(video);
            }
        }

        return videos;
    }
    
    private String getString(JSONObject item, String key) {
        try {
            return item.getString(key);
        } catch (JSONException ex) {
            return "";
        }
    }

    private Double getDouble(JSONObject item, String key) {
        try {
            return item.getDouble(key);
        } catch (JSONException ex) {
            return 0.00;
        }
    }
}
