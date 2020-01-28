package com.math_riddles.core.model;

import com.math_riddles.common.Utilities;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Challenge extends RealmObject {
    @PrimaryKey
    private int id;
    private int type;
    private String question;
    private String style;
    private String options;
    private String answer;

    public Challenge() {
        this.id = -1;
        this.question = "?";
        this.style = "?";
        this.options = "?";
        this.answer = "?";
    }

    public Challenge(int id, int type, String question, String style, String options, String answer) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.style = style;
        this.options = options;
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public int getType() { return type; }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuestion() { return question; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getOptions() {
        return Utilities.trimWhiteSpace(options);
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
