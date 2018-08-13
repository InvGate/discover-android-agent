package com.invgate.discover.androidagent.resources;

import com.invgate.discover.androidagent.models.AgentModel;

import io.reactivex.Observable;
import retrofit2.http.POST;


public interface AgentResource {
    @POST("api/agent/")
    Observable<AgentModel> create();
}