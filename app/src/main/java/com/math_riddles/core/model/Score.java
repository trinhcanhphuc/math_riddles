package com.math_riddles.core.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Score extends RealmObject {
    @PrimaryKey
    private int id;
    private int challenge_id;

    public Score() {}

    public Score(int id, int challenge_id) {
        this.id = id;
        this.challenge_id = challenge_id;
    }

    public int getId() {

        return id;
    }

    public int getChallengeId() {

        return challenge_id;
    }

    public void setChallengeId(int challenge_id) {

        this.challenge_id = challenge_id;
    }
}
