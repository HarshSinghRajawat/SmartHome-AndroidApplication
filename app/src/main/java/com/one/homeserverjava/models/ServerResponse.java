package com.one.homeserverjava.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ServerResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("output")
    String temperature;

    List<RelayData> relayDataList;

    public List<RelayData> getRelayList() {
        return relayDataList;
    }

    public void setRelayList(List<RelayData> relayDataList) {
        this.relayDataList = relayDataList;
    }
}
