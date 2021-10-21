package com.one.homeserverjava.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.one.homeserverjava.R;
import com.one.homeserverjava.ui.login.LoginFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment()).commit();

    }




}