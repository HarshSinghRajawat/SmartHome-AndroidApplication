package com.one.homeserverjava.utils;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.one.homeserverjava.api.ApiProvider;
import com.one.homeserverjava.pref.PrefProvider;

public class Repository {
    public ApiProvider api;
    public final PrefProvider preferences;

    public FirebaseAuth firebaseAuth;
    public DatabaseReference relayDatabase;
    public DatabaseReference piInfoDatabase;
    public DatabaseReference errDatabase;

    public static Repository INSTANCE;


    public Repository(Application app) {
        preferences =PrefProvider.getInstance(app);

        this.firebaseAuth= FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            initialiseFirebase();
        }
    }

    public void connectAPIs(Application app){
        api=ApiProvider.getInstance(app);
    }

    public void initialiseFirebase(){
        this.firebaseAuth= FirebaseAuth.getInstance();
        this.relayDatabase = FirebaseDatabase.getInstance().getReference("/"+firebaseAuth.getCurrentUser().getUid()+"/admin/relays");
        this.piInfoDatabase = FirebaseDatabase.getInstance().getReference("/admin/pi");
        this.errDatabase = FirebaseDatabase.getInstance().getReference("/admin/error");
    }

    public static Repository getInstance(Application app){
        synchronized (com.one.homeserverjava.pref.PrefProvider.class){
            if (INSTANCE == null) {
                INSTANCE = new Repository(app);
            }
        }
        return INSTANCE;
    }
}
