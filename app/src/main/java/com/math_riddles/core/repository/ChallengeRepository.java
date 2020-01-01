package com.math_riddles.core.repository;

import com.math_riddles.MathRiddlesApplication;
import com.math_riddles.core.model.Challenge;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ChallengeRepository {

    public Realm getRealm() {
        RealmConfiguration realmConfiguration = MathRiddlesApplication.getRealmConfig();
        return Realm.getInstance(realmConfiguration);
    }

    public Challenge getById(int challengeId) {
        Realm realm = getRealm();
        RealmQuery<Challenge> query = realm.where(Challenge.class);
        query.equalTo("id", challengeId);
        return query.findFirst();
    }

    public Challenge getFirst() {
        Realm realm = getRealm();
        return realm.where(Challenge.class).findFirst();
    }

    public RealmResults<Challenge> getAll() {
        Realm realm = getRealm();
        return realm.where(Challenge.class).findAll();
    }

    public int size() {
        Realm realm = getRealm();
        return (int) realm.where(Challenge.class).count();
    }

    public void insertAll(ArrayList<Challenge> list) {
        Realm realm = getRealm();
        realm.beginTransaction();
        for (Challenge item : list) {
            Challenge realmObject = realm.createObject(Challenge.class, item.getId());
            realmObject.setQuestion(item.getQuestion());
            realmObject.setAnswer(item.getAnswer());
            realmObject.setSolution(item.getSolution());
        }
        realm.commitTransaction();
    }
}
