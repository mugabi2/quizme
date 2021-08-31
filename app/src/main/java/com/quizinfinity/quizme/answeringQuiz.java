package com.quizinfinity.quizme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class answeringQuiz extends AppCompatActivity {
    RecyclerView recyclerViewsummary;
    formatSummaryAdapter formatSummaryAdapter;
    List<formatSummary> formatsummaryList;
    String fsuquestion,fsunumber,fsuanswer;

    private FirebaseAuth mAuth;
    Dialog dialogScore,dialogreveal;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String prefName = "userDetails";
    String myemail,myname,myphoto,fsa,fsb,fsc,fsd,fscorrect,fsquestion,quizcode;
    String cprogress,cright,cwrong,ctitle,cscore,crevealCoins,crevealwatch,cquestionnumber;
    TextView popscore,popright,popwrong,poppts;
    String currentCorect;
    SharedPreferences prefs,prefquizquestions,prefquizprogress;
//    String questionsArray;
    ArrayList<Map> questionsArray=new ArrayList<Map>();
    Map<String, Object> outerMap=new HashMap<>();
    Map<String, Object> innerMap=new HashMap<>();
    String quesio,numbio,scorio,ai,bi,ci,di,ansio;
    ProgressBar progressBar;
    String myquizReference,stars,feedMsg,currentStars;
    RatingBar ratingBar;
    double doubcurrentstars,doubstars;
    View viewColor;

    CardView carda,cardb,cardc,cardd;
    TextView a,b,c,d,number,question,score,title;
    EditText textInputLayout;
    Button popmyquizzes;
    String currentpts;
    ProgressBar progressBarloading;
    Map<String, Object> quizdetails = new HashMap<>();
    private AdView mAdViewans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answering_quiz);

        prefs = getSharedPreferences(prefName, Context.MODE_PRIVATE);
        myemail = prefs.getString("email", "");
        myname = prefs.getString("name", "");
        currentpts = prefs.getString("pts", "");
        myphoto = prefs.getString("profilePhotoUrl", "");
        quizcode = prefs.getString("answering quiz code", "");
        cquestionnumber = prefs.getString("current quiz question number", "");
        currentStars = prefs.getString("answering quiz rating", "");
        ctitle = prefs.getString("answering quiz title", "");
//ADS ADS ADS
        MobileAds.initialize(answeringQuiz.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdViewans= findViewById(R.id.adViewans);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdViewans.loadAd(adRequest1);

//        SUMMARY
        formatsummaryList=new ArrayList<>();
        prefquizquestions = this.getSharedPreferences(quizcode, MODE_PRIVATE);
        String investig=quizcode+"PROGRESS";
        prefquizprogress = this.getSharedPreferences(investig, MODE_PRIVATE);
        Toast.makeText(answeringQuiz.this,investig, Toast.LENGTH_SHORT).show();

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
        title=findViewById(R.id.anstitle);
        title.setText(ctitle);
        progressBar=findViewById(R.id.ansprogressBar);
        carda=findViewById(R.id.carda);
        cardb=findViewById(R.id.cardb);
        cardc=findViewById(R.id.cardc);
        cardd=findViewById(R.id.cardd);
        progressBarloading=findViewById(R.id.progbarAns);

        popmyquizzes=dialogScore.findViewById(R.id.popmyquizzes);
        popmyquizzes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new fragmentMyquizzes()).commit();
            }
        });

        ratingBar = dialogScore.findViewById(R.id.poprating);
//        feedMsg=dialogScore.findViewById(R.id.popfeedback).toString();
         textInputLayout = dialogScore.findViewById(R.id.popfeedback);

//    });

//      MAINLY FOR PROGRESS
        myquizReference = myemail + "MYQUIZZES";
        int countryer=0;
//       GETS THE QUIZ QUESTIONS

//        mydatabase=this.openOrCreateDatabase(myemail,MODE_PRIVATE,null);
//        Cursor c=mydatabase.rawQuery("SELECT * FROM "+quizcode,null);
//        int numberindex=c.getColumnIndex("number");
//        int aindex=c.getColumnIndex("a");
//        int bindex=c.getColumnIndex("b");
//        int cindex=c.getColumnIndex("c");
//        int dindex=c.getColumnIndex("d");
//        int correctindex=c.getColumnIndex("correct");
//        int questionindex=c.getColumnIndex("question");
//        c.moveToFirst();

//        while (!c.isAfterLast()){
////            questionsArray=c.getString(aindex);
//            innerMap.put("a", c.getString(aindex));
//            innerMap.put("b", c.getString(bindex));
//            innerMap.put("c", c.getString(cindex));
//            innerMap.put("d", c.getString(dindex));
//            innerMap.put("correct", c.getString(correctindex));
//            innerMap.put("question", c.getString(questionindex));
//            questionsArray.add(innerMap);
//            c.moveToNext();
//        }
//        Log.i("inner?:",questionsArray.toString());
//                        begin();

//        db.collection(quizcode)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Map<String, Object> innerMap=new HashMap<>();
//                                fsa=document.getString("a");
//                                fsb=document.getString("b");
//                                fsc=document.getString("c");
//                                fsd=document.getString("d");
//                                fscorrect=document.getString("correct");
//                                fsquestion=document.getString("question");
//
//                                innerMap.put("a", fsa);
//                                innerMap.put("b", fsb);
//                                innerMap.put("c", fsc);
//                                innerMap.put("d", fsd);
//                                innerMap.put("correct", fscorrect);
//                                innerMap.put("question", fsquestion);
//                                questionsArray.add(innerMap);
//                                Log.i( "inner ",innerMap.toString());
//                            }
//                            Log.i( "inner",questionsArray.toString());
//                        } else {
//                            Log.d("milan", "Error getting documents: ", task.getException());
//                        }
////                        Log.i( "1111111111",formatWishList.toString());
                        begin();
//                    }
//
//                });

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
//BEGIN GETS THE PROGRESS
    public void begin(){
        cscore = prefquizprogress.getString("score", "");
        cprogress = prefquizprogress.getString("progress", "");
        cright = prefquizprogress.getString("right", "");
        cwrong = prefquizprogress.getString("wrong", "");
        crevealCoins = prefquizprogress.getString("revealCoins", "");
        crevealwatch = prefquizprogress.getString("revealWatch", "");
        Log.d("progra ",cprogress);

        if (cquestionnumber.equals(cprogress)) {
                    popsetting();
        } else {
            next(cprogress);
        }
//        DocumentReference docRef = db.collection(myquizReference).document(quizcode);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        cscore = document.getString("score");
//                        cprogress = document.getString("progress");
//                        cright = document.getString("right");
//                        cwrong = document.getString("wrong");
//                        crevealCoins = document.getString("revealCoins");
//                        crevealwatch = document.getString("revealWatch");
//                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
//                    } else {
////                        Log.d( "get failed with ", task.getException());
//                    }
//                } else {
////                    Log.d( "get failed with ", task.getException());
//                }
//                if (cquestionnumber.equals(cprogress)) {
////                    popsetting();
//                } else {
//                    next(cprogress);
//                }
//            }
//        });
    }
//    ADDS 1 AND SETS QUESTION
    public void next(String currentprogress){
//        Toast.makeText(answeringQuiz.this, "SCORE "+cscore+" right "+cright+" wr "+cwrong, Toast.LENGTH_LONG).show();
        try{
            hideProgress();
        }catch(Exception ex){

        }
        int mmh=(Integer.parseInt(currentprogress))+1;
        numbio=Integer.toString(mmh);
        cprogress=numbio;
        ai = prefquizquestions.getString(numbio +"a", "");
        bi = prefquizquestions.getString(numbio +"b", "");
        ci = prefquizquestions.getString(numbio +"c", "");
        di = prefquizquestions.getString(numbio +"d", "");
        quesio = prefquizquestions.getString(numbio +"question", "");
        currentCorect = prefquizquestions.getString(numbio +"correct", "");

        a.setText(ai);
        carda.setTag(ai);
        b.setText(bi);
        cardb.setTag(bi);
        c.setText(ci);
        cardc.setTag(ci);
        d.setText(di);
        cardd.setTag(di);
        number.setText(numbio);
        score.setText(cscore+"%");
        question.setText(quesio);

        double doubprog=1.0*Integer.parseInt(currentprogress),
                doubcqnnumb=1.0*Integer.parseInt(cquestionnumber),
                doubupascore;
        doubupascore=Math.round(doubprog*100/doubcqnnumb);
        progressBar.setProgress(Integer.parseInt(cprogress));
//        progressBar.setProgress(Integer.parseInt(cscore));
//        progressBar.setProgress((int)doubupascore);
//        score.setText(cscore);
//        Toast.makeText(answeringQuiz.this,  "score."+doubupascore, Toast.LENGTH_SHORT).show();
        updateCloud();
    }
    public void marking(View view){
        ansio=view.getTag().toString();
        try{
            showProgress();
        }catch(Exception ex){

        }

//        int mmh=(Integer.parseInt(cprogress))+1;
//        String upaprog=Integer.toString(mmh);
        viewColor=view;
        String upaprog=cprogress;
        int upascore;
        double  doubcqnnumb=1.0*Integer.parseInt(cquestionnumber),
                doubupascore;

        formatsummaryList.add(
                new formatSummary(numbio,quesio,currentCorect)
        );

        if (ansio.equals(currentCorect)){
            view.setBackgroundColor(getResources().getColor(R.color.greenday));
            int mmhr=(Integer.parseInt(cright))+1;
            cright=String.valueOf(mmhr);
            double doubright=1.0*Integer.parseInt(cright);
            String uparight=Integer.toString(mmhr);
            doubupascore=Math.round((doubright/doubcqnnumb)*100);
            int intscore=(int)doubupascore;
            cscore=String.valueOf(intscore);
        Toast.makeText(answeringQuiz.this,  doubright+" / "+doubcqnnumb+" score."+cscore+"%", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                    updating(uparight,cwrong,upaprog,cscore);
                }
            }, 1000);
        }else {
            view.setBackgroundColor(getResources().getColor(R.color.redday));
            int mmhr=(Integer.parseInt(cwrong))+1;
            cwrong=String.valueOf(mmhr);

            if(currentCorect.equals(ai)) {
                carda.setBackgroundColor(getResources().getColor(R.color.greenday));
            }else if(currentCorect.equals(bi)){
                cardb.setBackgroundColor(getResources().getColor(R.color.greenday));
            }else if(currentCorect.equals(ci)){
                cardc.setBackgroundColor(getResources().getColor(R.color.greenday));
            }else if(currentCorect.equals(di)){
                cardd.setBackgroundColor(getResources().getColor(R.color.greenday));
            }


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.setBackgroundColor(getResources().getColor(R.color.white));
                    carda.setBackgroundColor(getResources().getColor(R.color.white));
                    cardb.setBackgroundColor(getResources().getColor(R.color.white));
                    cardc.setBackgroundColor(getResources().getColor(R.color.white));
                    cardd.setBackgroundColor(getResources().getColor(R.color.white));
                    updating(cright,cwrong,upaprog,cscore);
                }
            }, 2000);

        }
    }
    public void updating(String upright,String upwrong,String upprogress,String upscore){
//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
        quizdetails.put("right", upright);
        quizdetails.put("wrong", upwrong);
        quizdetails.put("progress", upprogress);
        quizdetails.put("score", upscore);

        SharedPreferences.Editor editor = prefquizprogress.edit();
        editor.putString("right", upright);
        editor.putString("wrong", upwrong);
        editor.putString("progress", upprogress);
        editor.putString("score", upscore);
        editor.apply();

        if (cquestionnumber.equals(upprogress)){
            popsetting();
        }else {
            next(upprogress);
        }

//        db.collection(myquizReference).document(quizcode)
//                .update(quizdetails)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
////                        int mmh=(Integer.parseInt(upprogress))+1;
////                        String numbo=Integer.toString(mmh);
//                        if (cquestionnumber.equals(upprogress)){
//                            popsetting();
//                        }else {
//                            next(upprogress);
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("updating", "Error updating", e);
//                    }
//                });
    }

    public void popsetting(){
        dialogScore.show();
        popscore.setText(cscore+"%");
        popright.setText(cright);
        popwrong.setText(cwrong);
        String pointers=String.valueOf(Integer.parseInt(cright)+Integer.parseInt(currentpts));
        poppts.setText(cright+" pts");

        recyclerViewsummary=dialogScore.findViewById(R.id.recyclesummary);
        recyclerViewsummary.setHasFixedSize(true);
        recyclerViewsummary.setLayoutManager(new LinearLayoutManager(answeringQuiz.this, LinearLayoutManager.VERTICAL, false));

        recycleSummary();

        Map<String, Object> updetails = new HashMap<>();
        updetails.put("pts", pointers);
//                        QUIZZES......rating
        db.collection("USERS").document(myemail)
                .update(updetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("pts",pointers);
                        editor.apply();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("luanda", "Error writing document", e);
                    }
                });
    }

    public void feedbacking(View view){
        feedMsg = textInputLayout.getText().toString();
        stars = String.valueOf(ratingBar.getNumStars());
// Initialize Firebase Auth
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
//                        QUIZZES......rating
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

    public void showProgress(){
        progressBarloading.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        progressBarloading.setVisibility(View.GONE);
    }

    public void updateCloud(){
        try {
            db.collection(myquizReference).document(quizcode)
                    .update(quizdetails)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.i("update", "cloud success");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("updating", "Error updating", e);
                        }
                    });
        }catch (Exception e) {
            Log.i("update", "cloud failure "+e);
        }
    }

    public void recycleSummary(){
        try {
            formatSummaryAdapter = new formatSummaryAdapter(answeringQuiz.this, formatsummaryList);
            recyclerViewsummary.setAdapter(formatSummaryAdapter);
        }catch(Exception e){
            Log.d("returnerror", e.toString());
        }
    }

}