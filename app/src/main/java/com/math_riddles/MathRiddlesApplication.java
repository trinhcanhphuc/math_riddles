package com.math_riddles;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.math_riddles.common.Constant;
import com.math_riddles.core.model.Question;
import com.math_riddles.core.repository.QuestionRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

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
        sInstance = this;

        this.initRealm();
        this.initDB();
    }

    private void initRealm() {
        Realm.init(this);
        realmConfig = new RealmConfiguration.Builder()
                .name(Constant.REALM_DATABASE)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    public void initDB() {
        if (!isDBHasData()) {
            ArrayList<Question> questions = getAllQuestions();
            QuestionRepository.insertAll(questions);
        }
    }

    public boolean isDBHasData() {
        return QuestionRepository.getFirstQuestion() != null;
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

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> formList = new ArrayList<Question>();
        try {
            JSONArray m_jArry = new JSONArray(loadJSONFromAsset("questions.json"));
            HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Long id = jo_inside.getLong("id");
                String question= jo_inside.getString("question");
                String answer = jo_inside.getString("answer");
                String solution = jo_inside.getString("solution");

                //Add your values in your `ArrayList` as below:
                Question item  = new Question(id, question, answer, solution);

                formList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return formList;
    }

    public String loadJSONFromAsset(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
