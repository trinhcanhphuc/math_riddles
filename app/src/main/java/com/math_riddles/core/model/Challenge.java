package com.math_riddles.core.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Challenge extends RealmObject {
    @PrimaryKey
    private int id;
    private String question;
    private String answer;
    private String solution;

    public Challenge() {
        this.id = -1;
        this.question = "?";
        this.answer = "?";
        this.solution = "?";
    }

    public Challenge(int id, String question, String answer, String solution) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.solution = solution;
    }

    public int getId() {
        return id;
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
