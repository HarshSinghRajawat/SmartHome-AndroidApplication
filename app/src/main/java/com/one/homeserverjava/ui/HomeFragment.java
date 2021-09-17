package com.one.homeserverjava.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.one.homeserverjava.utils.AsyncResponse;
import com.one.homeserverjava.databinding.FragmentHomeBinding;
import com.one.homeserverjava.models.ServerResponse;
import com.one.homeserverjava.ui.viewModel.HomeViewModel;
import com.one.homeserverjava.utils.Utils;


public class HomeFragment extends Fragment {
    FragmentHomeBinding views;
    HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = FragmentHomeBinding.inflate(getLayoutInflater(),container,false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        initListener();
        getLocalIP();
        return views.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initListener(){
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
            Log.d("myLogs", "checkPiConnectivity: making Request");
            viewModel.sendRequest(viewModel.CHECK, null, null);

        }else{
            Utils.notifyDialogBox(getFragmentManager(),"Pi Connectivity Error","Unable to Connect With Pi. Make sure you entered correct Base URL.",Utils.MSG);

            Utils.notifyDialogBox(getFragmentManager(),null,null,Utils.GET_IP);
        }
    }

    private void handleApiResponse(AsyncResponse<ServerResponse, Exception> response) {

        switch (response.status){
            case AsyncResponse.STATUS_LOADING:
                Utils.notifyDialogBox(getFragmentManager(),null,null,Utils.LOADING);
                break;
            case AsyncResponse.STATUS_NOT_STARTED:
                dismissDialog();
                PiNotRunning();
                break;
            case AsyncResponse.STATUS_STARTED:
                dismissDialog();
                PiIsRunning(true);
                break;
            case AsyncResponse.STATUS_SUCCESS:
                break;
        }

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

}