package com.example.homework;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class App extends Application {
    private static Context mAppContext;
    private static App mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mInstance = this;
        this.setAppContext(getApplicationContext());
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static App getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context context) {
        this.mAppContext = context;
    }
}
