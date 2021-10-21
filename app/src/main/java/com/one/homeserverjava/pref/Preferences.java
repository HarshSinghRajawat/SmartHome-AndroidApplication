package com.one.homeserverjava.pref;

public interface Preferences {

    String getLocalBaseUrl();
    void setLocalBaseUrl(String url);
    boolean checkLocalBaseUrl();

    void setUserStatus(boolean status);
    boolean getUserStatus();
}
