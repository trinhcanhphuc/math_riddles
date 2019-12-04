package com.math_riddles.core.repository;

import com.math_riddles.MathRiddlesApplication;
import com.math_riddles.core.model.Question;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class QuestionRepository extends BaseRepository {

    public static Realm getRealm() {
        RealmConfiguration realmConfiguration = MathRiddlesApplication.getRealmConfig();
        return Realm.getInstance(realmConfiguration);
    }

    public static void insertAll(ArrayList<Question> questions) {
        Realm realm = getRealm();
        realm.beginTransaction();
        for (Question q : questions) {
            Question insertQ = realm.createObject(Question.class, q.getId());
            insertQ.setQuestion(q.getQuestion());
            insertQ.setAnswer(q.getAnswer());
            insertQ.setSolution(q.getSolution());
        }
        realm.commitTransaction();
    }

    public static Question getQuestionById(int level) {
        Realm realm = getRealm();
        RealmQuery<Question> query = realm.where(Question.class);
        query.equalTo("id", level);
        return query.findFirst();
    }

    public static Question getFirstQuestion() {
        Realm realm = getRealm();
        return realm.where(Question.class).findFirst();
    }

    public static RealmResults<Question> getAllQuestions() {
        Realm realm = getRealm();
        return realm.where(Question.class).findAll();
    }
}
