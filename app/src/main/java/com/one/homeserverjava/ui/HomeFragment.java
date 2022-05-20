package com.one.homeserverjava.ui;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.DirectedAcyclicGraph;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.one.homeserverjava.databinding.FragmentHomeBinding;
import com.one.homeserverjava.models.RelayData;
import com.one.homeserverjava.models.ServerResponse;
import com.one.homeserverjava.ui.Callbacks.WorkManagerCallbacks;
import com.one.homeserverjava.ui.viewModel.HomeViewModel;
import com.one.homeserverjava.utils.AsyncResponse;
import com.one.homeserverjava.utils.Utils;

import java.util.UUID;


public class HomeFragment extends Fragment implements WorkManagerCallbacks {
    FragmentHomeBinding views;
    HomeViewModel viewModel;
    DialogBox notificationDialog;
    public static final int VOICE_CONTROL_REQ = 236;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = FragmentHomeBinding.inflate(getLayoutInflater(),container,false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.getRealTimeData(getActivity(),views.list);
        viewModel.setWorkManagerCallbacks(this);

        views.Shutdown.setOnClickListener(this::handleShutdownTrigger);

        views.Reboot.setOnClickListener(this::handleRebootTrigger);
        views.Mic.setOnClickListener(this::handleVoiceControl);
//        initAPIListener();
//        getLocalIP();
        return views.getRoot();
    }

    private void handleVoiceControl(View view) {
        Intent intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking!...");
        startActivityForResult(intent, VOICE_CONTROL_REQ);
    }

    private void handleShutdownTrigger(View view) {
        viewModel.shutdownPi(getContext());
    }

    private void handleRebootTrigger(View view) {
        viewModel.rebootPi(getContext());
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initAPIListener(){
        viewModel.getApiLiveData().observe(getViewLifecycleOwner(),this::handleApiResponse);
    }

    public void getLocalIP(){
        if(!viewModel.checkLocalPiAddress()){
            Utils.notifyDialogBox(getFragmentManager(),null,null,Utils.GET_IP);
        }else {
            viewModel.setHasLocalIP(true);
        }
        checkPiConnectivity();
    }

    public void checkPiConnectivity(){
        if(viewModel.hasLocalIP) {
            viewModel.sendRequest(viewModel.CHECK, null, null);

        }else{
            Utils.notifyDialogBox(getFragmentManager(),"Pi Connectivity Error","Unable to Connect With Pi. Make sure you entered correct Base URL.",Utils.MSG);

            Utils.notifyDialogBox(getFragmentManager(),null,null,Utils.GET_IP);
        }
    }

    private void handleApiResponse(AsyncResponse<ServerResponse, Exception> response) {

        if(notificationDialog!=null){
            notificationDialog.dismiss();
        }

        switch (response.status){
            case AsyncResponse.STATUS_LOADING:
                notificationDialog= Utils.notifyDialogBox(getFragmentManager(),null,null,Utils.LOADING);
                break;
            case AsyncResponse.STATUS_NOT_STARTED:
                PiNotRunning();
                break;
            case AsyncResponse.STATUS_STARTED:
                PiIsRunning(true);
                callGetDataAPI();
                break;
            case AsyncResponse.STATUS_SUCCESS:
                break;
            case AsyncResponse.GOT_DATA:
                views.list.setAdapter(
                        viewModel.populateList(getActivity(),response.value.getRelayList())
                );
                break;
            case AsyncResponse.STATUS_ERROR:
                Utils.notifyDialogBox(getFragmentManager(),"Unable to call API", response.message,Utils.MSG);
                break;
        }
    }

    public void callGetDataAPI(){
        viewModel.sendRequest(viewModel.GET_DATA, null,null);
    }
    public void dismissDialog(){
        if(Utils.dialog!=null){
            Utils.dialog.dismiss();
        }
    }

    public void PiIsRunning(boolean showDialog){
        if(showDialog) Utils.notifyDialogBox(getFragmentManager(),"Connected Successfully","Connected with Pi",Utils.MSG);
    }


    public void PiNotRunning(){
        Utils.notifyDialogBox(getFragmentManager(),"Connectivity Error","Seems like Pi is not Running on your Network.",Utils.MSG);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VOICE_CONTROL_REQ && resultCode == RESULT_OK){
            //handleInput
            viewModel.handleVoiceCmd(data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0));
            viewModel.getRealTimeData(getActivity(),views.list);
        }
    }

    @Override
    public void setObserver(UUID id, RelayData relayData) {
        WorkManager.getInstance(getContext()).getWorkInfoByIdLiveData(id).observe(this.getViewLifecycleOwner(), workInfo -> {
            if (workInfo.getState() != null &&
                    workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                viewModel.setRelays(relayData);
                viewModel.getRealTimeData(getActivity(),views.list);
            }
        });
    }
}