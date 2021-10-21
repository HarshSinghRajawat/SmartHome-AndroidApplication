package com.one.homeserverjava.models;

public class RelayRequest {
    //'{ "RelayData":"$relay" , "status":"$set" }'
    String relay;
    String status;

    public RelayRequest(String relay, String status) {
        this.relay = relay;
        this.status = status;
    }

    public String getRelay() {
        return relay;
    }

    public void setRelay(String relay) {
        this.relay = relay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
