package com.quizinfinity.quizme;

public class format1quiz {
    String imagequiz;
    String imageinstructor;
    String title;
    String qnnumber;
    String instructor;
    String price;
    String level;
    String description;
    String rating;
    String quizCode;

    public format1quiz(String imagequiz, String imageinstructor, String title, String qnnumber, String instructor, String price, String level, String description, String rating, String quizCode) {
        this.imagequiz = imagequiz;
        this.imageinstructor = imageinstructor;
        this.title = title;
        this.qnnumber = qnnumber;
        this.instructor = instructor;
        this.price = price;
        this.level = level;
        this.description = description;
        this.rating = rating;
        this.quizCode = quizCode;
    }

    public String getImagequiz() {
        return imagequiz;
    }

    public String getImageinstructor() {
        return imageinstructor;
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

    public String getPrice() {
        return price;
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
