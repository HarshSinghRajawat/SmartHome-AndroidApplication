package com.one.homeserverjava.ui.Callbacks;

import com.one.homeserverjava.models.RelayData;

import java.util.UUID;

public interface WorkManagerCallbacks {
    void setObserver(UUID id, RelayData relayData);
}
