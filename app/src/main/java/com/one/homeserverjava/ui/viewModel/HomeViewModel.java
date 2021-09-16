package com.one.homeserverjava.ui.viewModel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.one.homeserverjava.utils.AsyncResponse;
import com.one.homeserverjava.models.RelayRequest;
import com.one.homeserverjava.models.ServerResponse;
import com.one.homeserverjava.models.SetNameRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeViewModel extends BaseViewModel{
    public final int CHECK=0;
    public final int GET_DATA=1;
    public final int GET_TEMP=2;
    public final int SET_STATE=3;
    public final int SET_NAME=4;


    private MutableLiveData<AsyncResponse<ServerResponse,Exception>> apiLiveData;

    public HomeViewModel( Application application) {
        super(application);
    }

    public void sendRequest(int check,String relay,String data){
        RelayRequest relayRequest=null;
        SetNameRequest setNameRequest=null;

        switch(check){
            case CHECK: checkPi();
                break;
            case GET_DATA:getData();
                break;
            case GET_TEMP:getTemp();
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

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

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
                .getData().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

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
                .check().enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                apiLiveData.setValue(AsyncResponse.piIsOnline());
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                apiLiveData.setValue(AsyncResponse.piIsOffline());
            }
        });
    }

    public MutableLiveData<AsyncResponse<ServerResponse, Exception>> getApiLiveData() {
        return apiLiveData;
    }
    public void setBaseURL(String url){
        repository.preferences.setLocalBaseUrl(url);
    }
}
