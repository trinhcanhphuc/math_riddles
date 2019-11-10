package com.math_riddles;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * @author phuocns on 25/11/2018.
 */

public class MathRiddlesApplication extends Application {
    private static MathRiddlesApplication sInstance;
    private Scheduler mScheduler;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public Scheduler subscribeScheduler() {
        if (mScheduler == null) {
            mScheduler = Schedulers.io();
        }

        return mScheduler;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        return (networkInfo != null && networkInfo.isConnected());
    }

    public static MathRiddlesApplication getInstance() {
        if(sInstance != null) {
            return sInstance;
        }
        throw new IllegalArgumentException("MathRiddlesApplication -> onCreate(): Must call: setInstance(this)");
    }

}
