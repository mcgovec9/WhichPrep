package com.example.conor.whichprep;


import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    int count = 0;

    public MyApplication(){}

    public MyApplication(int count) {
        this.count = count;
    }

    public int getCount() {
        if(count == 10) {
            count = 0;
        }
        count++;
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}

