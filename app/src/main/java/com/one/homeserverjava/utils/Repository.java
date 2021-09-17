package com.one.homeserverjava.utils;

import android.app.Application;

import com.one.homeserverjava.api.ApiProvider;
import com.one.homeserverjava.pref.PrefProvider;

public class Repository {
    public ApiProvider api;
    public final PrefProvider preferences;
    public static Repository INSTANCE;

    public Repository(Application app) {
        preferences =PrefProvider.getInstance(app);
    }
    public void connectAPIs(Application app){
        api=ApiProvider.getInstance(app);
    }

    public static Repository getInstance(Application app){
        synchronized (com.one.homeserverjava.pref.PrefProvider.class){
            if (INSTANCE == null) {
                INSTANCE = new Repository(app);
            }
        }
        return INSTANCE;
    }
}
