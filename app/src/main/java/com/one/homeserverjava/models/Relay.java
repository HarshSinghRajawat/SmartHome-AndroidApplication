package com.one.homeserverjava.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Relay {
    @SerializedName("status")
    String status;
    @SerializedName("relay")
    int relay;
    @SerializedName("relay_name")
    String relay_name;
    @SerializedName("time")
    String time;
    @SerializedName("date")
    String date;
    @SerializedName("method")
    String method;
    @SerializedName("output")
    String output;

    public Relay(String status, int relay, String relay_name, String time, String date, String method, String output) {
        this.status = status;
        this.relay = relay;
        this.relay_name = relay_name;
        this.time = time;
        this.date = date;
        this.method = method;
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "\nRelay name:- "+relay_name+"\nStatus:- "+status+"\nOutput:- "+output;
    }

    public String getStatus() {
        return status;
    }

    public boolean setStatus(String status) {
        this.status = status;
        return this.status!="off";
    }

    public int getRelay() {
        return relay;
    }

    public void setRelay(int relay) {
        this.relay = relay;
    }

    public String getRelay_name() {
        return relay_name;
    }

    public void setRelay_name(String relay_name) {
        this.relay_name = relay_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
