package com.one.homeserverjava.models;

import com.squareup.moshi.Json;

import java.util.List;

public class ServerResponse {
    @Json(name="status")
    boolean status;

    List<Relay> relayList;
}
