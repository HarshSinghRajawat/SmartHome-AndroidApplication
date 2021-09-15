package com.one.homeserverjava.models;

public class SetNameRequest {//'{ "Relay":"$relay" , "Name":"$name" }'
    String relay;
    String relayName;
    public SetNameRequest(String relay, String relayName) {
        this.relay = relay;
        this.relayName = relayName;
    }

    public String getRelay() {
        return relay;
    }

    public void setRelay(String relay) {
        this.relay = relay;
    }

    public String getStatus() {
        return relayName;
    }

    public void setStatus(String status) {
        this.relayName = status;
    }
}
