package com.one.homeserverjava.utils;

public class AsyncResponse<T,E> {
    public static final int WAITING = 0;
    public static final int STATUS_NOT_STARTED = 1;
    public static final int STATUS_STARTED = 2;
    public static final int STATUS_LOADING = 3;
    public static final int STATUS_SUCCESS = 4;
    public static final int STATUS_ERROR = 5;

    public final T value;
    public final E error;
    public final int status;
    private boolean isFresh;

    public final String message;

    public static <T,E> AsyncResponse<T,E> success(T t){
        return new AsyncResponse(null, t, STATUS_SUCCESS, null);
    }

    public static <T,E> AsyncResponse<T,E> error(E e, String message){
        return new AsyncResponse(e, null, STATUS_ERROR, message);
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
