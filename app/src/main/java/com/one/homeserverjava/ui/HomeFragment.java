package com.one.homeserverjava.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = FragmentHomeBinding.inflate(getLayoutInflater(),container,false);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        getLocalIP();
        checkPiConnectivity();
        initListeners();
        return views.getRoot();
    }

    public void initListeners(){
        viewModel.getApiLiveData().observe(getViewLifecycleOwner(),this::handleApiResponse);
    }

    public void getLocalIP(){
        if(viewModel.checkLocalPiAddress()){
            return;
        }else{
            Utils.notifyDialogBox(getFragmentManager(),Utils.GET_IP);

        }
    }

    public void checkPiConnectivity(){
        viewModel.sendRequest(viewModel.CHECK, null,null);
    }

    private void handleApiResponse(AsyncResponse<ServerResponse, Exception> response) {

        switch (response.status){
            case AsyncResponse.STATUS_NOT_STARTED:
                break;
            case AsyncResponse.STATUS_STARTED:
                break;
            case AsyncResponse.STATUS_SUCCESS:
                break;
        }

    }


}