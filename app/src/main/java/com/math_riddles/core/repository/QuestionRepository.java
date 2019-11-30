package com.math_riddles.core.repository;

import com.math_riddles.MathRiddlesApplication;
import com.math_riddles.core.model.Question;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

public class QuestionRepository extends BaseRepository {

    public static Question getQuestionById(int level) {
        RealmConfiguration realmConfiguration = MathRiddlesApplication.getRealmConfig();
        Realm realm = Realm.getInstance(realmConfiguration);
        RealmQuery<Question> query = realm.where(Question.class);
        query.equalTo("id", level);
        return query.findFirst();
    }
}
