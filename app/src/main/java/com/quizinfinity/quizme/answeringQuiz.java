package com.quizinfinity.quizme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class answeringQuiz extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Dialog dialogScore,dialogreveal;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String prefName = "userDetails";
    String myemail,myname,myphoto,fsa,fsb,fsc,fsd,fscorrect,fsquestion,quizcode;
    String cprogress,cright,cwrong,ctitle,cscore,crevealCoins,crevealwatch,cquestionnumber;
    TextView popscore,popright,popwrong,poppts;
    String currentCorect;
    SharedPreferences prefs;
    ArrayList<Map> questionsArray=new ArrayList<Map>();
    Map<String, Object> outerMap=new HashMap<>();
    String quesio,numbio,scorio,ai,bi,ci,di,ansio;
    ProgressBar progressBar;
    String myquizReference,stars,feedMsg,currentStars;
    RatingBar ratingBar;
    double doubcurrentstars,doubstars;

    CardView carda,cardb,cardc,cardd;
    TextView a,b,c,d,number,question,score;
    EditText textInputLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answering_quiz);

        dialogScore = new Dialog(this);
        dialogScore.setContentView(R.layout.popupscore);
        dialogreveal = new Dialog(this);
        dialogreveal.setContentView(R.layout.popreveal);
        popscore=dialogScore.findViewById(R.id.popscore);
        popright=dialogScore.findViewById(R.id.popright);
        popwrong=dialogScore.findViewById(R.id.popwrong);
        poppts=dialogScore.findViewById(R.id.poppts);

        a=findViewById(R.id.a);
        b=findViewById(R.id.b);
        c=findViewById(R.id.c);
        d=findViewById(R.id.d);
        question=findViewById(R.id.ansquestion);
        number=findViewById(R.id.ansnumber);
        score=findViewById(R.id.ansscore);
        progressBar=findViewById(R.id.ansprogressBar);
        carda=findViewById(R.id.carda);
        cardb=findViewById(R.id.cardb);
        cardc=findViewById(R.id.cardc);
        cardd=findViewById(R.id.cardd);

        prefs = getSharedPreferences(prefName, Context.MODE_PRIVATE);
        myemail = prefs.getString("email", "");
        myname = prefs.getString("name", "");
        myphoto = prefs.getString("profilePhotoUrl", "");
        quizcode = prefs.getString("answering quiz code", "");
        cquestionnumber = prefs.getString("current quiz question number", "");
        currentStars = prefs.getString("answering quiz rating", "");


        ratingBar = dialogScore.findViewById(R.id.poprating);
//        feedMsg=dialogScore.findViewById(R.id.popfeedback).toString();
         textInputLayout = dialogScore.findViewById(R.id.popfeedback);

//    });

//      MAINLY FOR PROGRESS
        myquizReference = myemail + "MYQUIZZES";
        int countryer=0;
//        db.collection(quizcode)
        db.collection(quizcode)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> innerMap=new HashMap<>();
                                fsa=document.getString("a");
                                fsb=document.getString("b");
                                fsc=document.getString("c");
                                fsd=document.getString("d");
                                fscorrect=document.getString("correct");
                                fsquestion=document.getString("question");

                                innerMap.put("a", fsa);
                                innerMap.put("b", fsb);
                                innerMap.put("c", fsc);
                                innerMap.put("d", fsd);
                                innerMap.put("correct", fscorrect);
                                innerMap.put("question", fsquestion);
                                questionsArray.add(innerMap);
                                Log.i( "inner ",innerMap.toString());
                            }
                            Log.i( "inner",questionsArray.toString());
                        } else {
                            Log.d("milan", "Error getting documents: ", task.getException());
                        }
//                        Log.i( "1111111111",formatWishList.toString());
                        begin();
                    }

                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ansmenu, menu);
        return true;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.botreveal:
                            dialogreveal.show();
                            break;
                    }
                    return true;
                }
            };

    public void begin(){
        DocumentReference docRef = db.collection(myquizReference).document(quizcode);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        cscore = document.getString("score");
                        scorio = document.getString("score");
                        cprogress = document.getString("progress");
                        cright = document.getString("right");
                        cwrong = document.getString("wrong");
                        crevealCoins = document.getString("revealCoins");
                        crevealwatch = document.getString("revealWatch");
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    } else {
//                        Log.d( "get failed with ", task.getException());
                    }
                } else {
//                    Log.d( "get failed with ", task.getException());
                }
                if (cquestionnumber.equals(cprogress)) {
                    popsetting();
                } else {
                    next(cprogress);
                }
            }
        });
    }
    public void next(String currentprogress){
        Log.i("next progress", currentprogress);
        Log.i("next array", questionsArray.toString());
        Log.i("next progress", currentprogress);
        outerMap=questionsArray.get(Integer.parseInt(currentprogress));
        ai=outerMap.get("a").toString();
        bi=outerMap.get("b").toString();
        ci=outerMap.get("c").toString();
        di=outerMap.get("d").toString();
        int mmh=(Integer.parseInt(currentprogress))+1;
        numbio=Integer.toString(mmh);
        quesio=outerMap.get("question").toString();
        currentCorect=outerMap.get("correct").toString();

        a.setText(ai);
        carda.setTag(ai);
        b.setText(bi);
        cardb.setTag(bi);
        c.setText(ci);
        cardc.setTag(ci);
        d.setText(di);
        cardd.setTag(di);
        number.setText(numbio);
        score.setText(scorio);
        question.setText(quesio);

        double doubprog=1.0*Integer.parseInt(currentprogress),doubcqnnumb=1.0*Integer.parseInt(cquestionnumber),doubupascore;
        doubupascore=Math.round(doubprog*100/doubcqnnumb);
        progressBar.setProgress((int)doubupascore);
//        Toast.makeText(answeringQuiz.this,  "score."+doubupascore, Toast.LENGTH_SHORT).show();

    }
    public void marking(View view){
        ansio=view.getTag().toString();
        int mmh=(Integer.parseInt(cprogress))+1;
        String upaprog=Integer.toString(mmh);
        int upascore;
        double doubright=1.0*Integer.parseInt(cright),doubcqnnumb=1.0*Integer.parseInt(cquestionnumber),doubupascore;
        if (ansio.equals(currentCorect)){
            view.setBackgroundColor(getResources().getColor(R.color.greenday));
            int mmhr=(Integer.parseInt(cright))+1;
            String uparight=Integer.toString(mmhr);
            doubupascore=Math.round(doubright*100/doubcqnnumb);
            updating(uparight,cwrong,upaprog,String.valueOf(doubupascore));
//            Toast.makeText(answeringQuiz.this,  " correct correct correct", Toast.LENGTH_SHORT).show();
        }else {
            view.setBackgroundColor(getResources().getColor(R.color.redday));
            updating(cright,cwrong,upaprog,cscore);
//            Toast.makeText(answeringQuiz.this,  "wrong", Toast.LENGTH_SHORT).show();
        }
    }
    public void updating(String upright,String upwrong,String upprogress,String upscore){
//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Map<String, Object> quizdetails = new HashMap<>();
        quizdetails.put("right", upright);
        quizdetails.put("wrong", upwrong);
        quizdetails.put("progress", upprogress);
        quizdetails.put("score", upscore);

        db.collection(myquizReference).document(quizcode)
                .update(quizdetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(answeringQuiz.this, cquestionnumber+" with "+upprogress, Toast.LENGTH_SHORT).show();
//                    next(upprogress);
                        if (cquestionnumber.equals(upprogress)){
                            popsetting();
                        }else {
                            begin();
//                            next(upprogress);
//                            Intent intent = new Intent(answeringQuiz.this, answeringQuiz.class);
//                            finish();
//                            startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("wishlist", "Error wishlisting", e);
                    }
                });
    }
    public void popsetting(){
        popscore.setText(cscore);
        popright.setText(cright);
        popwrong.setText(cwrong);
        poppts.setText(cright+" pts");
        dialogScore.show();
    }

    public void feedbacking(View view){
        feedMsg = textInputLayout.getText().toString();
        stars = String.valueOf(ratingBar.getNumStars());
// Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
        Map<String, Object> userdetails = new HashMap<>();
        userdetails.put("name", myname);
        userdetails.put("email", myemail);
        userdetails.put("review", feedMsg);
        userdetails.put("profilePhotoUrl", myphoto);
        String collec=quizcode+"FEEDBACK";


        Log.i("popopo", stars);
        Log.i("popopoc", currentStars);
        int starInt= (int) ratingBar.getRating();

        doubcurrentstars=1.0*Integer.parseInt(currentStars);
        doubstars=1.0*starInt;
        double doubttlstars=Math.round((doubcurrentstars+doubstars)/2);
        int averagestars=(int)doubttlstars;
        Toast.makeText(answeringQuiz.this,feedMsg+" ====  "+ averagestars,Toast.LENGTH_SHORT).show();

        Map<String, Object> updetails = new HashMap<>();
        updetails.put("rating", String.valueOf(averagestars));
        userdetails.put("rating", String.valueOf(starInt));

//        QUIZ FEEDBACK
        db.collection(collec).document(myemail)
                .set(userdetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        QUIZZES
                        db.collection("QUIZZES").document(quizcode)
                                .update(updetails)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogScore.dismiss();
                        Intent intent = new Intent(answeringQuiz.this, discover.class);
                        finish();
                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("luanda", "Error writing document", e);
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("luanda", "Error writing document", e);
                    }
                });

    }
}