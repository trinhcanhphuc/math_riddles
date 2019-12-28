package com.math_riddles.core.repository;

import com.math_riddles.MathRiddlesApplication;
import com.math_riddles.core.model.Score;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ScoreRepository {

    public Realm getRealm() {
        RealmConfiguration realmConfiguration = MathRiddlesApplication.getRealmConfig();
        return Realm.getInstance(realmConfiguration);
    }

    public Score getById(int id) {
        Realm realm = getRealm();
        RealmQuery<Score> query = realm.where(Score.class);
        query.equalTo("id", id);
        return query.findFirst();
    }

    public Score getFirst() {
        Realm realm = getRealm();
        return realm.where(Score.class).findFirst();
    }

    public RealmResults<Score> getAll() {
        Realm realm = getRealm();
        return realm.where(Score.class).findAll();
    }

    public int size() {
        Realm realm = getRealm();
        return (int) realm.where(Score.class).count();
    }
}
