package com.one.homeserverjava.utils;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.one.homeserverjava.models.RelayData;

public class Scheduler extends Worker {

    Scheduler(Context context, WorkerParameters workerParameters){
        super(context, workerParameters);
    }

    @Override
    public Result doWork() {
        Data taskData = getInputData();
        RelayData relayData = new RelayData(
                taskData.getString("status"),
                taskData.getInt("relay",0),
                taskData.getString("relayName"),
                taskData.getString("time"),
                taskData.getString("date"),
                taskData.getString("method"),
                taskData.getString("output")
        );
        Log.d("myTest", "doWork: " + relayData.toString());
        return Result.success();
    }
}
