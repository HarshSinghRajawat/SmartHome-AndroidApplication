package com.one.homeserverjava.ui.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.one.homeserverjava.utils.Repository;

public class BaseViewModel extends AndroidViewModel {

    Repository repository;

    public BaseViewModel(Application application) {
        super(application);
        repository=Repository.getInstance(application);
    }

    protected Repository getRepository(){return repository;}



}
