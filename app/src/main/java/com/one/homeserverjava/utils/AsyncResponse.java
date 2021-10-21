package com.one.homeserverjava.utils;

import com.one.homeserverjava.models.Relay;
import com.one.homeserverjava.models.ServerResponse;

import java.util.List;

public class AsyncResponse<T,E> {
    public static final int WAITING = 0;
    public static final int STATUS_NOT_STARTED = 1;
    public static final int STATUS_STARTED = 2;
    public static final int STATUS_LOADING = 3;
    public static final int STATUS_SUCCESS = 4;
    public static final int STATUS_ERROR = 5;
    public static final int GOT_DATA=6;

    public final T value;
    public final E error;
    public final int status;
    private boolean isFresh;

    public final String message;

    public static <T,E> AsyncResponse<T,E> success(List<Relay> list){
        return new AsyncResponse(list, null, STATUS_SUCCESS, null);
    }

    public static <T,E> AsyncResponse<T,E> error(Throwable e, String message){
        return new AsyncResponse(e, null, STATUS_ERROR, message);
    }
    public static <T,E> AsyncResponse<T,E> failed(String message){
        return new AsyncResponse(null, null, STATUS_ERROR, message);
    }
    public static <T,E> AsyncResponse<T,E> getData(ServerResponse response){
        return new AsyncResponse(response, null, GOT_DATA, null);
    }
    public static <T,E> AsyncResponse<T,E> piIsOnline(){
        return new AsyncResponse(0, null, STATUS_STARTED, null);
    }
    public static <T,E> AsyncResponse<T,E> piIsOffline(){
        return new AsyncResponse(null, null, STATUS_NOT_STARTED, null);
    }
    public static <T,E> AsyncResponse<T,E> notMadeRequestYet(){
        return new AsyncResponse(null, null, WAITING, null);
    }
    public static <T,E> AsyncResponse<T,E> loading(){
        return new AsyncResponse(null, null, STATUS_LOADING, null);
    }
    public static <T,E> AsyncResponse<T,E> success(){
        return new AsyncResponse(null, null, STATUS_SUCCESS, null);
    }


    public boolean isError(){
        return this.status == STATUS_ERROR;
    }
    public boolean isLoading() {return this.status == STATUS_LOADING;}
    public boolean isSuccess() {return this.status == STATUS_SUCCESS;}
    public boolean isNotStarted() {return this.status == STATUS_NOT_STARTED;}
    public boolean isPiOnline(){
        return this.status==STATUS_STARTED;
    }
    public boolean isPiOffline(){
        return this.status==STATUS_NOT_STARTED;
    }
    public T pop(){
        isFresh = false;
        return value;
    }

    public boolean isFresh(){
        return this.isFresh;
    }

    public AsyncResponse(T value, E error, int status, String message) {
        this.value = value;
        this.error = error;
        this.status = status;
        this.isFresh =true;
        this.message = message;
    }
}
