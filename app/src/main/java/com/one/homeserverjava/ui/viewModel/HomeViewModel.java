package com.one.homeserverjava.ui.viewModel;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.one.homeserverjava.models.Relay;
import com.one.homeserverjava.ui.Callbacks.LocalNetworkCallbacks;
import com.one.homeserverjava.utils.Adapter;
import com.one.homeserverjava.utils.AsyncResponse;
import com.one.homeserverjava.models.RelayRequest;
import com.one.homeserverjava.models.ServerResponse;
import com.one.homeserverjava.models.SetNameRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeViewModel extends BaseViewModel implements LocalNetworkCallbacks {
    public final int CHECK=0;
    public final int GET_DATA=1;
    public final int GET_TEMP=2;
    public final int SET_STATE=3;
    public final int SET_NAME=4;

    public boolean hasLocalIP=false;


    private MutableLiveData<AsyncResponse<ServerResponse,Exception>> apiLiveData;

    public HomeViewModel( Application application) {
        super(application);
    }

    public void sendRequest(int check,String relay,String data){
        RelayRequest relayRequest=null;
        SetNameRequest setNameRequest=null;

        repository.connectAPIs(getApplication());
        apiLiveData.postValue(AsyncResponse.loading());

        switch(check){
            case CHECK: checkPi();
                break;
            case GET_DATA: getData();
                break;
            case GET_TEMP: getTemp();
                break;
            case SET_STATE:
                relayRequest=new RelayRequest(relay,data);
                setRelayRequest(relayRequest);
                break;
            case SET_NAME:
                setNameRequest=new SetNameRequest(relay,data);
                setRelayNameRequest(setNameRequest);
                break;
        }
    }


    public boolean checkLocalPiAddress(){
        return repository.preferences.getLocalBaseUrl()!="";
    }
    private void setRelayRequest(RelayRequest relayRequest){
        repository.api.resource
                .setRelay(relayRequest).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if(response.isSuccessful()){
                    apiLiveData.setValue(AsyncResponse.success());
                }else{
                    apiLiveData.setValue(AsyncResponse.failed("API Call Fail"));
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                apiLiveData.setValue(AsyncResponse.error(t.fillInStackTrace(),"API Call Fail"));
            }
        });
    }
    private void setRelayNameRequest(SetNameRequest setNameRequest){
        repository.api.resource
                .setName(setNameRequest).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }
    private void getData(){
        repository.api.resource
                .getData().enqueue(new Callback<List<Relay>>() {
            @Override
            public void onResponse(Call<List<Relay>> call, Response<List<Relay>> response) {
                ServerResponse res=new ServerResponse();
                res.setRelayList(response.body());
                apiLiveData.setValue(AsyncResponse.getData(res));
            }

            @Override
            public void onFailure(Call<List<Relay>> call, Throwable t) {
                apiLiveData.setValue(AsyncResponse.error(t,t.getMessage()));
            }
        });
    }

    private void getTemp(){
        repository.api.resource
                .getTemp().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }
    private void checkPi(){
        repository.api.resource
                .check().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                apiLiveData.setValue(AsyncResponse.piIsOnline());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("myLogs", "onFailure: "+t);
                apiLiveData.setValue(AsyncResponse.piIsOffline());
            }
        });
    }
    public void setHasLocalIP(boolean hasLocalIP) {
        this.hasLocalIP = hasLocalIP;
    }

    public MutableLiveData<AsyncResponse<ServerResponse, Exception>> getApiLiveData() {
        if(apiLiveData == null) apiLiveData = new MutableLiveData<>(AsyncResponse.notMadeRequestYet());
        return apiLiveData;
    }
    public void setBaseURL(String url){
        repository.preferences.setLocalBaseUrl(url);
    }

    public Adapter populateList(Activity activity, List<Relay> list){
        ArrayList<Relay> relays=new ArrayList<>();
        relays.addAll(list);
        relays.remove(8);
        Adapter adapter=new Adapter(activity,relays,this);
        return adapter;
    }


    public void getRealTimeData(){
        relayDatabase.orderByKey()
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(new Consumer<DataSnapshot>() {
                    @Override
                    public void accept(DataSnapshot dataSnapshot) {
                        Log.d("myTest", "onDataChange: "+dataSnapshot.getKey());
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void setRelays(Relay relay) {
        Log.d("myTest","Wokring");
    }
}
