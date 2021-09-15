package com.one.homeserverjava.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;


import com.one.homeserverjava.databinding.DialogboxLayoutBinding;
import com.one.homeserverjava.ui.viewModel.HomeViewModel;


public class DialogBox extends DialogFragment {

    DialogboxLayoutBinding views;

    private boolean type=false;
    private HomeViewModel mainViewModel;

    @Override
    public View onCreateView( LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        views = DialogboxLayoutBinding.inflate(getLayoutInflater(), container, true);

        getDialog().getWindow().setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL);
        setView();
        return views.getRoot();
    }

    @Override
    public void onViewCreated( View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mainViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        initListeners();
    }

    private void initListeners(){
        views.update.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {

        String url=views.url.getText().toString();

        mainViewModel.setBaseURL(url);
    }

    private void setView(){
        if (type){
            views.viewGroup.setVisibility(View.VISIBLE);
            views.progressBar.setVisibility(View.GONE);
        }else{
            views.viewGroup.setVisibility(View.GONE);
            views.progressBar.setVisibility(View.VISIBLE);
        }
    }
}
