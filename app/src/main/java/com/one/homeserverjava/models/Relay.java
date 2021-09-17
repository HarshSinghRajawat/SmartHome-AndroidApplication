package com.one.homeserverjava.models;

import com.squareup.moshi.Json;

public class Relay {
    @Json(name = "status")
    String status;
    @Json(name = "relay")
    int relay;
    @Json(name = "relay_name")
    String relay_name;
    @Json(name = "time")
    String time;
    @Json(name = "date")
    String date;
    @Json(name = "method")
    String method;
}
