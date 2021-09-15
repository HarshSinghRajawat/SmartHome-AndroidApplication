package com.one.homeserverjava.provider;

import android.app.Application;

import com.one.homeserverjava.pref.PrefProvider;
import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ApiProvider {

    public static ApiProvider INSTANCE;
    public final ApiResource resource;
    public final OkHttpClient client;

    public static ApiProvider getInstance(Application app){
        synchronized (ApiProvider.class){
            if (INSTANCE == null) {
                INSTANCE = new ApiProvider(app);
            }
        }
        return INSTANCE;
    }



    private ApiProvider(Application application) {



        client = new OkHttpClient.Builder()
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    final Request.Builder requestBuilder = chain.request().newBuilder();
                    requestBuilder.addHeader("Content-type", "application/json");
                    requestBuilder.addHeader("Accept", "*/*");
                    requestBuilder.addHeader("processData", "true");
                    return chain.proceed(requestBuilder.build());
                })
                .build();


        final Moshi moshi = new Moshi.Builder()
                .build();

        String baseUrl= PrefProvider.INSTANCE.getLocalBaseUrl();

        final Retrofit rf = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .build();

        resource =rf.create(ApiResource.class);

    }

}
