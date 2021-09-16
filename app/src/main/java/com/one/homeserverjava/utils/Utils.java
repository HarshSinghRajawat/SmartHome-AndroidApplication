package com.one.homeserverjava.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.one.homeserverjava.ui.DialogBox;

public class Utils {
    public static final int GET_IP=0;
    public static final int LOADING=1;
    public static void notifyDialogBox(FragmentManager manager, int FLAG){

        Bundle bundle=new Bundle();
        DialogBox dialogBox=new DialogBox();

        switch (FLAG){
            case GET_IP:
                bundle.putBoolean("type",true);
                break;
            case LOADING:
                bundle.putBoolean("type",false);
                break;
        }

        dialogBox.setArguments(bundle);
        dialogBox.show(manager,null);

    }

}
