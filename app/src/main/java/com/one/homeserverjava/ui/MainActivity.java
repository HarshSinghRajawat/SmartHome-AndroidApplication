package com.one.homeserverjava.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;


import android.app.Fragment;
import android.os.Bundle;

import com.one.homeserverjava.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new HomeFragment()).commit();

    }


}