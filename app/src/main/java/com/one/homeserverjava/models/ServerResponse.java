package com.one.homeserverjava.models;

import com.google.gson.annotations.SerializedName;


public class ServerResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("output")
    String temperature;

}
