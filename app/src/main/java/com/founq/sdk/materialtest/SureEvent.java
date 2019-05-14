package com.founq.sdk.materialtest;

/**
 * Created by ring on 2019/5/13.
 */
public class SureEvent {
    public SureEvent(boolean isSure) {
        this.isSure = isSure;
    }

    private boolean isSure;

    public void setSure(boolean sure) {
        isSure = sure;
    }

    public boolean isSure() {
        return isSure;
    }
}
