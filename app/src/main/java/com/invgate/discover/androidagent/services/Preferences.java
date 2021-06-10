package com.invgate.discover.androidagent.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.invgate.discover.androidagent.R;

public class Preferences {

    private SharedPreferences preferences;
    private static Preferences instance;
    private static Context context;

    private Preferences() {
        preferences = context.getSharedPreferences(
                context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
    }

    public static SharedPreferences Instance() {
        if (Preferences.instance == null) {
            Preferences.instance = new Preferences();
        }

        return Preferences.instance.preferences;
    }

    public static void configure(Context context) {
        Preferences.context = context;
    }


}
