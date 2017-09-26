package com.telehuz.doittest;

import android.app.Application;

public class DOITTestApplication extends Application {

    private static DOITTestApplication sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
    }

    public static DOITTestApplication getInstance() {
        return sInstance;
    }
}