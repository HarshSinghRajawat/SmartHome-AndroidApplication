package com.one.homeserverjava.api;

import com.one.homeserverjava.models.RelayData;
import com.one.homeserverjava.models.RelayRequest;
import com.one.homeserverjava.models.ServerResponse;
import com.one.homeserverjava.models.SetNameRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiResource {
    @POST("/relay")
    Call<ServerResponse> setRelay(@Body RelayRequest body);

    @GET("/get&&check=2sa69y23dgf")
    Call<List<RelayData>> getData();

    @POST("/setname")
    Call<ServerResponse> setName(@Body SetNameRequest body);

    @GET("/temp&&check=2sa69y23dgf")
    Call<ServerResponse> getTemp();

    @GET("/check&&check=2sa69y23dgf")
    Call<Void> check();
}
