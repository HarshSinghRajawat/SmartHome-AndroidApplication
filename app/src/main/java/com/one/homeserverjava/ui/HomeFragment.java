package com.one.homeserverjava.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.one.homeserverjava.R;
import com.one.homeserverjava.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    FragmentHomeBinding views;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        views = FragmentHomeBinding.inflate(getLayoutInflater(),container,false);
        return views.getRoot();
    }
}