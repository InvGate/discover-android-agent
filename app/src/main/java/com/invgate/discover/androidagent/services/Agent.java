package com.invgate.discover.androidagent.services;

import android.content.Context;
import com.invgate.discover.androidagent.models.AgentModel;
import com.invgate.discover.androidagent.resources.AgentResource;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Agent {

    public AgentResource agentResource;
    private Context context;

    public Agent(Context context) {
        this.context = context;
        agentResource = Api.Instance().create(AgentResource.class);
    }

    /**
     * Calls the agent api resource
     * @return Observable with the inventory id String
     */
    public Observable<String> create() {

        // Create a call instance for looking up Retrofit contributors.
        Observable<AgentModel> agentObs = agentResource.create();
        return agentObs.subscribeOn(Schedulers.newThread())
                       .observeOn(AndroidSchedulers.mainThread())
                       .map(agent -> agent.getUuid());
    }
}
