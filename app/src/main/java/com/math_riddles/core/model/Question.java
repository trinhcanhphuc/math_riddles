package com.math_riddles.core.model;

import android.app.Activity;
import android.content.Context;

import com.math_riddles.MathRiddlesApplication;
import com.math_riddles.common.Constant;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.PrimaryKey;

public class Question extends RealmObject {
    @PrimaryKey
    private long id;
    private String question;
    private String answer;
    private String solution;

    public Question() {}

    public Question(long id, String question, String answer, String solution) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.solution = solution;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() { return question; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

}
