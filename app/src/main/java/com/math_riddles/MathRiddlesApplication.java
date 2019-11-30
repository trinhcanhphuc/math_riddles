package com.math_riddles;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.math_riddles.common.Constant;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author phuocns on 25/11/2018.
 */

public class MathRiddlesApplication extends Application {
    private static MathRiddlesApplication sInstance;
    private Scheduler mScheduler;
    private static RealmConfiguration realmConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        realmConfig = new RealmConfiguration.Builder()
                .name(Constant.REALM_DATABASE)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();



//        initRealm();

        sInstance = this;
    }

    private void initRealm() {
        Realm.init(this);
        realmConfig = new RealmConfiguration.Builder()
                .name(Constant.REALM_DATABASE)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    public static RealmConfiguration getRealmConfig(){
        return realmConfig;
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
