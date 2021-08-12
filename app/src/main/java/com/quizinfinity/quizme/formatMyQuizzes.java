package com.quizinfinity.quizme;

public class formatMyQuizzes {
    String imagequiz;
    String title;
    String qnnumber;
    String instructor;
    String progress;
    String level;
    String description;
    String rating;
    String quizCode;

    public formatMyQuizzes(String imagequiz, String title, String qnnumber, String instructor, String progress, String level, String description, String rating, String quizCode) {
        this.imagequiz = imagequiz;
        this.title = title;
        this.qnnumber = qnnumber;
        this.instructor = instructor;
        this.progress = progress;
        this.level = level;
        this.description = description;
        this.rating = rating;
        this.quizCode = quizCode;
    }

    public String getImagequiz() {
        return imagequiz;
    }

    public String getTitle() {
        return title;
    }

    public String getQnnumber() {
        return qnnumber;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getProgress() {
        return progress;
    }

    public String getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public String getQuizCode() {
        return quizCode;
    }
}
