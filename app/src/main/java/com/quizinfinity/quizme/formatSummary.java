package com.quizinfinity.quizme;

public class formatSummary {
    String number,answer,question;

    public formatSummary(String number, String question,String answer) {
        this.number = number;
        this.question = question;
        this.answer = answer;
    }

    public String getNumber() {
        return number;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }
}
