package com.one.homeserverjava.ui.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.one.homeserverjava.utils.Repository;

public class BaseViewModel extends AndroidViewModel {

    Repository repository;

    FirebaseAuth firebaseAuth;
    DatabaseReference relayDatabase;
    DatabaseReference piInfoDatabase;
    DatabaseReference errDatabase;

    public BaseViewModel(Application application) {
        super(application);

        repository=Repository.getInstance(application);

        firebaseAuth=FirebaseAuth.getInstance();

        relayDatabase = FirebaseDatabase.getInstance().getReference("/admin/relays");
        piInfoDatabase = FirebaseDatabase.getInstance().getReference("/admin/pi");
        errDatabase = FirebaseDatabase.getInstance().getReference("/admin/error");
    }

    protected Repository getRepository(){return repository;}



}
