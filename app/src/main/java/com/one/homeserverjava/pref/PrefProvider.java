package com.one.homeserverjava.pref;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefProvider implements Preferences {

    public static PrefProvider INSTANCE;
    private final SharedPreferences client;

    public static PrefProvider getInstance(Application app){
        synchronized (PrefProvider.class){
            if (INSTANCE == null) {
                INSTANCE = new PrefProvider(app);
            }
        }
        return INSTANCE;
    }



    private PrefProvider(Application application) {
        client = application.getSharedPreferences("DEFAULT", Context.MODE_PRIVATE);
    }


    @Override
    public String getLocalBaseUrl() {
        return client.getString("localBaseUrl","");
    }

    @Override
    public void setLocalBaseUrl(String url) {
        client.edit().putString("localBaseUrl",url).commit();
    }

    @Override
    public boolean checkLocalBaseUrl() {
        return !client.getString("localBaseUrl","").equals("");
    }
}
