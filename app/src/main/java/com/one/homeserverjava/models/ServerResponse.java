package com.one.homeserverjava.models;

import com.squareup.moshi.Json;

public class ServerResponse {
    @Json(name="status")
    boolean status;
}
