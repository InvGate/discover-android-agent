package com.invgate.discover.androidagent.services;

import android.content.SharedPreferences;
import android.util.Log;

import com.invgate.discover.androidagent.utils.Constants;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomResponseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        try {
            Response response = chain.proceed(request);
            return response;
        } catch (Exception ex) {

            Log.i(Constants.LOG_TAG, "Proxy url error. Using cloud url");
            SharedPreferences preferences = Preferences.Instance();
            String cloudUrl = preferences.getString("cloud_url", "");

            if (!cloudUrl.isEmpty()) {
                Response r = retryWithCloudUrl(request, chain, cloudUrl);
                return r;
            }

            throw ex;
        }

    }

    private Response retryWithCloudUrl(Request req, Chain chain, String cloudUrl) throws IOException {

        String fullUrl = req.url().toString();
        String newUrl = replaceHostInUrl(fullUrl, cloudUrl);

        Log.d(Constants.LOG_TAG, "Proxy url: " + fullUrl);
        Log.d(Constants.LOG_TAG, "Proxy cloud url: " + newUrl);

        Request newRequest;
        newRequest = req.newBuilder().url(newUrl).build();
        Response another =  chain.proceed(newRequest);
        return another;
    }

    public  String replaceHostInUrl(String originalURL, String cloudUrl) {

        try {

            URI uri = new URI(originalURL);
            URI uriCloud = new URI(cloudUrl);
            uri = new URI(uriCloud.getScheme(), uriCloud.getAuthority(), uri.getPath(), uri.getQuery(), uri.getFragment());

            return uri.toString();
        } catch (URISyntaxException ex) {
            return originalURL;
        }
    }
}
