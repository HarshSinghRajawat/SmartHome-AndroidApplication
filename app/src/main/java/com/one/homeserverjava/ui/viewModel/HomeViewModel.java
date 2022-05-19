package com.one.homeserverjava.ui.viewModel;

import android.app.Activity;
import android.app.Application;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.one.homeserverjava.models.RelayData;
import com.one.homeserverjava.models.RelayRequest;
import com.one.homeserverjava.models.ServerResponse;
import com.one.homeserverjava.models.SetNameRequest;
import com.one.homeserverjava.ui.Callbacks.LocalNetworkCallbacks;
import com.one.homeserverjava.ui.Callbacks.WorkManagerCallbacks;
import com.one.homeserverjava.utils.Adapter;
import com.one.homeserverjava.utils.AsyncResponse;
import com.one.homeserverjava.utils.Scheduler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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
    ArrayList<RelayData> relayData;


    private MutableLiveData<AsyncResponse<ServerResponse,Exception>> apiLiveData;
    private WorkManagerCallbacks workManagerCallbacks;

    public HomeViewModel(Application application) {
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
    public void setWorkManagerCallbacks(WorkManagerCallbacks workManagerCallbacks) {
        this.workManagerCallbacks = workManagerCallbacks;
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
                .getData().enqueue(new Callback<List<RelayData>>() {
            @Override
            public void onResponse(Call<List<RelayData>> call, Response<List<RelayData>> response) {
                ServerResponse res=new ServerResponse();
                res.setRelayList(response.body());
                apiLiveData.setValue(AsyncResponse.getData(res));
            }

            @Override
            public void onFailure(Call<List<RelayData>> call, Throwable t) {
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

    public Adapter populateList(Activity activity, List<RelayData> list){
        relayData =new ArrayList<>();
        relayData.addAll(list);
        Adapter adapter=new Adapter(activity, relayData,this);
        return adapter;
    }
    public void rebootPi(Context context){
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("Reboot",false);

            repository.piInfoDatabase.updateChildren(map);

            Thread.sleep(2000);

            map = new HashMap<>();
            map.put("Reboot",true);

            repository.piInfoDatabase.updateChildren(map);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutdownPi(Context context){
        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("PowerOff",false);

            repository.piInfoDatabase.updateChildren(map);

            Thread.sleep(2000);

            map = new HashMap<>();
            map.put("PowerOff",true);

            repository.piInfoDatabase.updateChildren(map);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void getRealTimeData(Activity activity, ListView view){
        ArrayList<RelayData> list = new ArrayList<>();
        Adapter adapter=new Adapter(activity,list,this);
        view.setAdapter(adapter);

        repository.relayDatabase.orderByKey()
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getChildren().forEach(new Consumer<DataSnapshot>() {
                    @Override
                    public void accept(DataSnapshot dataSnapshot) {
                        HashMap<String,Object> relayData = (HashMap<String, Object>) dataSnapshot.getValue();
                        long relay = (long) relayData.get("relay");

                        String value = (String) relayData.get("status");
                        String relayName = (String) relayData.get("relay_name");

                        relayName = relayName==null || relayName.isEmpty() ? "notDefined" : relayName;

                        list.add(new RelayData(value,(int)relay,relayName,null,null,null,null));
                        adapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void setRelays(RelayData relayData) {
        Map<String, Object> map = new HashMap<>();

        map.put("relay"+relayData.getRelay(),relayData);

        repository.relayDatabase.updateChildren(map);
    }

    @Override
    public void schedule(Context context, RelayData relayData){
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog =  new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Calendar calendar = Calendar.getInstance();
                    int totalMinutes = ((i - calendar.get(Calendar.HOUR_OF_DAY))*60)+(i1-calendar.get(Calendar.MINUTE));
                    long diff = Duration.ofMinutes(totalMinutes).toMillis() - Duration.ofSeconds(calendar.get(Calendar.SECOND)).toMillis();

                    scheduleWork(diff, relayData);
                } else{
                    Toast.makeText(getApplication().getApplicationContext(), "Unable to schedule due to API version" , Toast.LENGTH_SHORT).show();
                }
            }
        }, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false);
        timePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void scheduleWork(long diff, RelayData relayData){
        WorkManager manager = WorkManager.getInstance();
        String reqTag="Relay"+relayData.getRelay();
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        manager.cancelAllWorkByTag(reqTag);
        Data inputData = new Data.Builder()
                .putInt("relay",relayData.getRelay())
                .putString("relayName",relayData.getRelay_name())
                .putString("status",relayData.getStatus())
                .putString("date",relayData.getDate())
                .putString("method",relayData.getMethod())
                .putString("output",relayData.getOutput())
                .putString("time",relayData.getTime())
                .build();
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(Scheduler.class)
                .setConstraints(constraints)
                .setInputData(inputData)
                .setInitialDelay(diff, TimeUnit.MILLISECONDS)
                .addTag(reqTag)
                .build();
        manager.enqueue(request);

        workManagerCallbacks.setObserver(request.getId(), relayData);

    }

    public void handleVoiceCmd(String input){
        String status = input.contains("on")?"on":"off";
        int relay;
        if(input.contains("one") || input.contains("1")){
            relay = 1;
        }else if(input.contains("two") || input.contains("2")){
            relay = 2;
        }else if(input.contains("three") || input.contains("3")){
            relay = 3;
        }else if(input.contains("four") || input.contains("4")){
            relay = 4;
        }else if(input.contains("five") || input.contains("5")){
            relay = 5;
        }else if(input.contains("six") || input.contains("6")){
            relay = 6;
        }else if(input.contains("seven") || input.contains("7")){
            relay = 7;
        }else if(input.contains("eight") || input.contains("8")){
            relay = 8;
        } else {
            return;
        }

        Log.d("myTest", "handleVoiceCmd: Relay "+relay+" status "+status);
    }

}
