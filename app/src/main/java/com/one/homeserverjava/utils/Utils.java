package com.one.homeserverjava.utils;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.one.homeserverjava.ui.DialogBox;

public class Utils {
    public static final int GET_IP=0;
    public static final int LOADING=1;
    public static final int MSG=3;
    public static DialogBox dialog;

    public static DialogBox notifyDialogBox(FragmentManager manager,String title,String des, int FLAG){

        Bundle bundle=new Bundle();
        DialogBox dialogBox=new DialogBox();

        if(FLAG==MSG){
            bundle.putString("title",title);
            bundle.putString("des",des);
        }else if(FLAG==LOADING){
            dialog=dialogBox;
        }

        bundle.putInt("flag",FLAG);

        dialogBox.setArguments(bundle);
        dialogBox.show(manager,null);

        return dialogBox;
    }

}
