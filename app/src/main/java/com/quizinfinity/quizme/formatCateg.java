package com.quizinfinity.quizme;

public class formatCateg {
    String categorytitle;
    String numberofquizzes;

    public formatCateg(String categorytitle, String numberofquizzes) {
        this.categorytitle = categorytitle;
        this.numberofquizzes = numberofquizzes;
    }

    public String getCategorytitle() {
        return categorytitle;
    }

    public String getNumberofquizzes() {
        return numberofquizzes;
    }
}
