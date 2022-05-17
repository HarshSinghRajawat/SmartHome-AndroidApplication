package com.one.homeserverjava.ui.Callbacks;

import android.app.Activity;
import android.content.Context;

import com.one.homeserverjava.models.RelayData;

public interface LocalNetworkCallbacks {
    public void setRelays(RelayData relayData);
    public void schedule(Context context, RelayData relayData);
}
