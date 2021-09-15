package com.one.homeserverjava.api;

import com.one.homeserverjava.models.RelayRequest;
import com.one.homeserverjava.models.ServerResponse;
import com.one.homeserverjava.models.setNameRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiResource {
    @POST("/relay")
    Call<ServerResponse> setRelay(@Body RelayRequest body);

    @GET("/get&&check=2sa69y23dgf")
    Call<ServerResponse> getData();

    @POST("/setname")
    Call<ServerResponse> setName(@Body setNameRequest body);

    @GET("/temp&&check=2sa69y23dgf")
    Call<ServerResponse> getTemp();

    @GET("/check&&check=2sa69y23dgf")
    Call<ServerResponse> check();
}
