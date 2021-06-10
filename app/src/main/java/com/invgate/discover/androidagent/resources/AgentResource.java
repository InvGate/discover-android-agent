package com.invgate.discover.androidagent.resources;

import com.invgate.discover.androidagent.models.AgentModel;
import com.invgate.discover.androidagent.models.request.AgentMobileModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface AgentResource {
    @POST("api/agent-mobile/")
    Observable<AgentModel> create(@Body AgentMobileModel data);
}