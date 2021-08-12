package com.quizinfinity.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class splash extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    public void next(View view){
        Intent intent=new Intent(this,answeringQuiz.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer=new Thread()
        {
            public void run() {
                try {
//                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                    if (user != null) {
//                                Toast.makeText(splash.this,"not not not",Toast.LENGTH_SHORT).show();
//                    }else{
//                                Toast.makeText(splash.this,"user user user",Toast.LENGTH_SHORT).show();
//                    }
//                    firebaseAuth = FirebaseAuth.getInstance();
//
//                    mAuthListener = new FirebaseAuth.AuthStateListener(){
//                        @Override
//                        public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                            Toast.makeText(splash.this,"check check",Toast.LENGTH_SHORT).show();
//                            if(user!=null){
//                                Toast.makeText(splash.this,"not not not",Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(splash.this, login.class);
//                                startActivity(intent);
//                                finish();
//                            }else {
//                                Toast.makeText(splash.this,"user user user",Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(splash.this, discover.class);
//                                startActivity(intent);
//                                finish();
//
//                            }
//                        }
//
//
//                    };
                    sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finally
                {
                                Intent intent = new Intent(splash.this, login.class);
                                startActivity(intent);
                                finish();

                }
            }
        };
        timer.start();
    }
}