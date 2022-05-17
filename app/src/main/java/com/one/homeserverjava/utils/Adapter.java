package com.one.homeserverjava.utils;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.one.homeserverjava.R;
import com.one.homeserverjava.models.RelayData;
import com.one.homeserverjava.ui.Callbacks.LocalNetworkCallbacks;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter<RelayData> {
    LocalNetworkCallbacks callbacks;

    public Adapter(Activity context, ArrayList<RelayData> data, LocalNetworkCallbacks callback){
        super(context,0,data);
        this.callbacks=callback;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem= convertView;
        if(listItem==null){
            listItem= LayoutInflater.from(getContext()).inflate(R.layout.relay_list,parent,false);
        }

        RelayData relayData =getItem(position);


        TextView textView= listItem.findViewById(R.id.relayName);
        Button sch = listItem.findViewById(R.id.schedule);
        Button btn= listItem.findViewById(R.id.relaySwitch);

        textView.setText(relayData.getRelay_name());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn= view.findViewById(R.id.relaySwitch);

                boolean toggle= relayData.setStatus(
                        //updating and setting status
                        relayData.getStatus().contains("on")?"off":"on"
                );


                view.setBackgroundColor( toggle ? Color.parseColor("#4CAF50") : Color.parseColor("#F44336"));
                btn.setText( toggle ? "ON":"OFF");

                callbacks.setRelays(relayData);
            }
        });

        sch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relayData.setStatus(relayData.getStatus().contains("on")?"off":"on");
                callbacks.schedule(getContext(), relayData);
            }
        });


        if(relayData.getStatus().contains("on")){
            btn.setText("ON");
            btn.setBackgroundColor(Color.parseColor("#4CAF50"));
        }else{
            btn.setText("OFF");
            btn.setBackgroundColor(Color.parseColor("#F44336"));
        }

        return listItem;
    }

}
