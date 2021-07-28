package com.example.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class answeringQuiz extends AppCompatActivity {

    public void next(View view){
        Intent intent=new Intent(this,discover.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answering_quiz);

        Dialog dialogScore;
        dialogScore = new Dialog(this);
        dialogScore.setContentView(R.layout.popupscore);
        dialogScore.show();
    }
}