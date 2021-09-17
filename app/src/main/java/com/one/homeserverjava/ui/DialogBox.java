package com.one.homeserverjava.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;


import com.one.homeserverjava.databinding.DialogboxLayoutBinding;
import com.one.homeserverjava.ui.viewModel.HomeViewModel;
import com.one.homeserverjava.utils.Utils;


public class DialogBox extends DialogFragment {

    private DialogboxLayoutBinding views;
    private Bundle arg;
    private int FLAG;
    private HomeViewModel mainViewModel;

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        views = DialogboxLayoutBinding.inflate(getLayoutInflater(), container, false);

        getDialog().getWindow().setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
        return views.getRoot();
    }

    @Override
    public void onViewCreated( View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        arg=getArguments();

        initListeners();
        setView();
    }

    private void initListeners(){
        views.update.setOnClickListener(this::onClick);
        views.dismiss.setOnClickListener(this::dismiss);
    }

    private void dismiss(View view) {
        getDialog().dismiss();
    }

    public void onClick(View view) {

        String url=views.url.getText().toString();

        if (url.contains("def_wifi")){
            url="192.168.29.163:8080";
        }else if (url.contains("def_lan")){
            url="192.168.29.150:8080";
        }else if(url.contains("def_phone")){
            url="192.168.112.204:8080";
        }

        if(views.http.isChecked()){
            url="http://"+url;
        }else if(views.https.isChecked()){
            url="https://"+url;
        }else{
            Toast.makeText(getContext(),"Please Select a valid Protocol",Toast.LENGTH_LONG).show();
        }
        mainViewModel.setBaseURL(url);
        mainViewModel.setHasLocalIP(true);

        dismiss(view);
    }

    private void setView(){
        FLAG=arg.getInt("flag");

        views.getInput.setVisibility(View.GONE);
        views.progressBar.setVisibility(View.GONE);
        views.showMsg.setVisibility(View.GONE);

        switch (FLAG){
            case Utils.GET_IP:
                views.getInput.setVisibility(View.VISIBLE);
                break;
            case Utils.LOADING:
                views.progressBar.setVisibility(View.VISIBLE);
                break;
            case Utils.MSG:
                showMessage();
                views.showMsg.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void showMessage(){
        String title=arg.getString("title");
        String des=arg.getString("des");

        views.msgTitle.setText(title);
        views.msgDescription.setText(des);
    }

}
