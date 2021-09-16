package com.one.homeserverjava.ui.viewModel;

import android.app.Application;


import androidx.lifecycle.AndroidViewModel;

import com.one.homeserverjava.utils.Repository;

public class BaseViewModel extends AndroidViewModel {

    Repository repository;
    public BaseViewModel(Application application) {
        super(application);
        repository=Repository.getInstance(application);
    }
}
