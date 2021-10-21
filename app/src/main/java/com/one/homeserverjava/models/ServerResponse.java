package com.one.homeserverjava.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ServerResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("output")
    String temperature;

    List<Relay> relayList;

    public List<Relay> getRelayList() {
        return relayList;
    }

    public void setRelayList(List<Relay> relayList) {
        this.relayList = relayList;
    }
}
