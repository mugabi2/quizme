package com.quizinfinity.quizme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.flutterwave.raveandroid.rave_presentation.RavePayManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class discover extends AppCompatActivity {
    RecyclerView recyclerView;
    formatWishAdapter formatWishAdapter;
    List<formatWish> formatWishList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String fsTitle,fsProgress,fsInstructor,fsPhoto,fsCategory,fsDescription,fsPrice,fsInstructorEmail,fsInstructorName,
            fsInstructorPhotoUrl,fsLevel,fsNoOfquestions,fsQuizCode,fsRating,fsStudents;
    Dialog dialogWish;
    private String prefName = "userDetails";
    FloatingActionButton fabwish;
    ExtendedFloatingActionButton fabbuy;
    boolean fabwishshow=true;
    String buybtnText;
    String myemail;
    SharedPreferences prefs,prefquizquestions,prefquizprogress;
    private onClickInterfaceFormat1 onClickInterfaceFormat1;
    int countforme=0;

    String prefNameQuizQuestions,prefNameQuizProgress,currentQC;
    String myname,myphoto,fsa,fsb,fsc,fsd,fscorrect,fsquestion,quizcode,fsnumber;
    ArrayList<Map> questionsArray=new ArrayList<Map>();
    SQLiteDatabase mydatabase;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        prefs = this.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        myemail = prefs.getString("email", "");
        progressBar=findViewById(R.id.progbarDisc);
//        mydatabase=this.openOrCreateDatabase(myemail,MODE_PRIVATE,null);
//        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ currentQC+ "(number VARCHAR,a VARCHAR,b VARCHAR,c VARCHAR,d VARCHAR,correct VARCHAR,question VARCHAR)");
//        mydatabase.execSQL("DROP TABLE IF EXISTS "+currentQC);
        //        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS currentQC (number VARCHAR, a VARCHAR)");
//
//        mydatabase.execSQL("INSERT INTO currentQC (number, a) VALUES ('one','aaa')");
//        mydatabase.execSQL("INSERT INTO currentQC (number, a) VALUES ('two','bbb')");
//        mydatabase.execSQL("INSERT INTO currentQC (number, a) VALUES ('tree','ccc')");
//        Cursor c=mydatabase.rawQuery("SELECT * FROM currentQC",null);
//        int nameindex=c.getColumnIndex("number");
//        int ageindex=c.getColumnIndex("a");
//        c.moveToFirst();
//
//        while (!c.isAfterLast()){
//            Log.i("dbase:",c.getString(nameindex));
////            Log.i("dbase:",c.getInt(ageindex));
//            c.moveToNext();
//        }

//        for (int i = 0; i <3;i++){
//            String f=String.valueOf(i);
//            Log.i("inner ",f);
//        mydatabase.execSQL("INSERT INTO " + currentQC + " (number,a,b,c,d,correct,question) VALUES (" + f + ",'" + f + "','" + f + "','" + f + "','" + f + "','" + f + "','" + f + "')");
//        }
//        mydatabase.execSQL("INSERT INTO " + currentQC + " (number,a,b,c,d,correct,question) VALUES (" + "f" + ",'" + "f" + "','" + "f" + "','" + "f" + "','" + "f" + "','" + "f" + "','" + "f" + "')");
//        mydatabase.execSQL("INSERT INTO " + currentQC + " (number,a,b,c,d,correct,question) VALUES (" + "s" + ",'" + "s" + "','" + "s" + "','" + "s" + "','" + "s" + "','" + "s" + "','" + "s" + "')");
//        mydatabase.execSQL("INSERT INTO " + currentQC + " (number,a,b,c,d,correct,question) VALUES (" + f + ",'" + f + "','" + f + "','" + f + "','" + f + "','" + f + "','" + f + "')");
//        String f="first",s="second";
//        mydatabase.execSQL("INSERT INTO " + currentQC + " (number,a,b,c,d,correct,question) VALUES (" + f + ",'" + f + "','" + f + "','" + f + "','" + f + "','" + f + "','" + f + "')");
//        mydatabase.execSQL("INSERT INTO " + currentQC + " (number,a,b,c,d,correct,question) VALUES (" + s + ",'" + s + "','" + s + "','" + s + "','" + s + "','" + s + "','" + s + "')");

        //TOOLBAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialogWish = new Dialog(this);
        dialogWish.setContentView(R.layout.popwishlist);
        fabwish = findViewById(R.id.fabwish);
        fabbuy = findViewById(R.id.fabbuy);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new fragmentDiscover()).commit();

        BottomNavigationView bottomNav=findViewById(R.id.bottomnavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        formatWishList=new ArrayList<>();

        prefs = getSharedPreferences(prefName, Context.MODE_PRIVATE);
        myemail = prefs.getString("email", "");
        String myquizReference = myemail + "WISHLIST";
        Log.i( "1111111111",myquizReference);

        db.collection(myquizReference)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(requireActivity(),document.getId() + " => " + document.getData(),Toast.LENGTH_LONG).show();
//                                requestArray.add(document.getData().toString());
                                fsDescription="document.getString()";
                                fsInstructor="document.getString()";
                                fsInstructorPhotoUrl="document.getString()";
                                fsLevel="document.getString()";
                                fsNoOfquestions="document.getString()";
                                fsPhoto="document.getString()";
                                fsQuizCode="document.getString()";
                                fsRating="document.getString()";
                                fsTitle="document.getString()";
                                fsPrice="document.getString()";


                                formatWishList.add(
                                        new formatWish(fsPhoto,fsInstructorPhotoUrl,fsTitle,fsNoOfquestions,fsInstructor,fsProgress,
                                                fsLevel,fsDescription,fsRating,fsQuizCode)
                                );

                            }
//                            firestoreCallback.onCallback(requestArray);
                        } else {
                            Log.d("milan", "Error getting documents: ", task.getException());
                        }
//                        Log.i( "1111111111",formatWishList.toString());

                    }

                });
        recycle();

//        Button pay=findViewById(R.id.pay);
//        pay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////            makePayment();
//            }
//        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                    Fragment selectedFragment=null;
                    showProgress();
                    switch (item.getItemId()){
                        case R.id.botdisc:
                            selectedFragment=new fragmentDiscover();
                            break;
                        case R.id.botquiz:
                            selectedFragment=new fragmentMyquizzes();
                            break;
                        case R.id.botcart:
                            selectedFragment=new fragmentCart();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
                    return true;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuprof:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new fragmentProfile())
                        .addToBackStack(null).commit();
//                Toast.makeText(discover.this,"profile profile",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fabbuy(View view){
        showProgress();
        buybtnText=fabbuy.getText().toString();
        currentQC= prefs.getString("current quiz code", "");

        prefNameQuizQuestions = currentQC;
        prefquizquestions = this.getSharedPreferences(prefNameQuizQuestions, Context.MODE_PRIVATE);
        prefNameQuizProgress = currentQC+"PROGRESS";
        String investig=currentQC+"PROGRESS";
        prefquizprogress = this.getSharedPreferences(investig, Context.MODE_PRIVATE);
        Toast.makeText(discover.this,investig, Toast.LENGTH_SHORT).show();

//                SharedPreferences pref = getSharedPreferences("current quiz code", Context.MODE_PRIVATE);
        String currentimage= prefs.getString("current quiz image", "");
        String currenttitle= prefs.getString("current quiz title", "");
        String currentqnnumb= prefs.getString("current quiz question number", "");
        String currentinstructor= prefs.getString("current quiz instructor", "");
        String currentprice= prefs.getString("current quiz price", "");
        String currentlevel= prefs.getString("current quiz level", "");
        String currentdesc= prefs.getString("current quiz description", "");
        String currentrating= prefs.getString("current quiz rating", "");
        String currentstudents= prefs.getString("current quiz students", "");

//        if (buybtnText.equals(getResources().getString(R.string.buynow))){
            if (buybtnText.equals("BUY NOW")){
            Toast.makeText(discover.this,  " $$$$$$$$$$", Toast.LENGTH_SHORT).show();
        }else {
//                Toast.makeText(discover.this,  currentQC+" AND "+currenttitle, Toast.LENGTH_SHORT).show();

//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                Map<String, Object> quizdetails = new HashMap<>();
                quizdetails.put("quizCode", currentQC);
                quizdetails.put("progress", "0");
                quizdetails.put("photoUrl", currentimage);
                quizdetails.put("title", currenttitle);
                quizdetails.put("numberOfQuestions", currentqnnumb);
                quizdetails.put("instructor", currentinstructor);
                quizdetails.put("price", currentprice);
                quizdetails.put("level", currentlevel);
                quizdetails.put("description", currentdesc);
                quizdetails.put("rating", currentrating);
                quizdetails.put("right", "0");
                quizdetails.put("wrong", "0");
                quizdetails.put("score", "0");
                quizdetails.put("archive", "0");
                quizdetails.put("revealCoins", "0");
                quizdetails.put("revealWatch", "0");
                String myquizReference = myemail + "MYQUIZZES";

                SharedPreferences.Editor editor = prefquizprogress.edit();
                editor.putString("progress", "0");
                editor.putString("right", "0");
                editor.putString("wrong", "0");
                editor.putString("score", "0");
                editor.putString("revealCoins", "0");
                editor.putString("revealWatch", "0");
                editor.apply();
//UPDATE MY QUIZZES
                db.collection(myquizReference).document(currentQC)
                        .set(quizdetails)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                int studentUpdate=Integer.parseInt(currentstudents)+1;
                                Map<String, Object> updetails = new HashMap<>();
                                updetails.put("students", String.valueOf(studentUpdate));
//                        QUIZZES......students enrolled
                                db.collection("QUIZZES").document(currentQC)
                                        .update(updetails)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
//                                                Toast.makeText(discover.this, " added to my quizzes", Toast.LENGTH_SHORT).show();

//       GETS THE QUIZ QUESTIONS
                            db.collection(currentQC)
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
                                                    fsnumber=document.getId();

                                                    SharedPreferences.Editor editor = prefquizquestions.edit();
                                                    editor.putString(fsnumber+"a",fsa);
                                                    editor.putString(fsnumber+"b",fsb);
                                                    editor.putString(fsnumber+"c",fsc);
                                                    editor.putString(fsnumber+"d",fsd);
                                                    editor.putString(fsnumber+"correct",fscorrect);
                                                    editor.putString(fsnumber+"question",fsquestion);
                                                    editor.apply();
//                mydatabase.execSQL("INSERT INTO "+currentQC+" (number,a,b,c,d,correct,question) VALUES ("+fsnumber+",'"+fsa+"','"+fsb+"','"+fsc+"','"+fsd+"','"+fscorrect+"','"+fsquestion+"')");
                                                   countforme++;
                                                }
                                                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new fragmentMyquizzes()).commit();

                                            } else {
                                                Log.d("milan", "Error getting documents: ", task.getException());
                                            }

                                        }

                                    });
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("MY QUIZZES", "Error writing document", e);
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("MY QUIZZES", "Error ", e);
                            }
                        });

        }
    }

//    private void makePayment() {
//
//        new RaveUiManager(discover.this)
//                .setAmount(Double.parseDouble("1"))
//                .setEmail("samuelmugabi2@gmail.com")
//                .setCountry("UG")
//                .setCurrency("USD")
//                .setfName("Mugabi")
//                .setlName("sam")
//                .setNarration("Purchase Goods")
//                .setPublicKey("FLWPUBK-a06274ca7488cff341ae54f21883600f-X")
//                .setEncryptionKey("300c56c4cc3cf1290af704c9")
//                .setTxRef(System.currentTimeMillis() + "Ref")
//                .acceptMpesaPayments(true)
//                .acceptAccountPayments(true)
//                .acceptCardPayments(true)
//                .allowSaveCardFeature( true)
////                .acceptGHMobileMoneyPayments(true)
////                .acceptUgMobileMoneyPayments(true)
////                .acceptZmMobileMoneyPayments(true)
////                .acceptRwfMobileMoneyPayments(true)
////                .acceptUkPayments(true)
////                .acceptSaBankPayments(true)
//                .acceptBankTransferPayments(true)
//                .acceptUssdPayments(true)
////                .onStagingEnv(true)
//                .shouldDisplayFee(true)
////                .showStagingLabel(true)
//                .initialize();
//
//    }

    public void makePayment(int amount){
        String txRef = myemail +" "+  UUID.randomUUID().toString();

        final String publicKey = "FLWPUBK-a06274ca7488cff341ae54f21883600f-X"; //Get your public key from your account
        final String encryptionKey = "300c56c4cc3cf1290af704c9"; //Get your encryption key from your account

        /*
        Create instance of RavePayManager
         */
//        new RavePayManager(discover.this)
//                .setAmount(amount)
//                .setCountry("UG")
//                .setCurrency("USD")
//                .setEmail(myemail)
//                .setfName("fName")
//                .setlName("lName")
//                .setNarration("narration")
//                .setPublicKey(publicKey)
//                .setEncryptionKey(encryptionKey)
//                .setTxRef(txRef)
//                .acceptAccountPayments(true)
//                .acceptCardPayments(
//                        true)
//                .acceptMpesaPayments(false)
//                .acceptGHMobileMoneyPayments(false)
//                .onStagingEnv(false).
//                allowSaveCardFeature(true)
////                .withTheme(R.style.DefaultPayTheme)
//                .initialize();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_LONG).show();
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_LONG).show();
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void fabwish(View view){
        dialogWish.show();
    }

    public void showBuy(String price) {
        fabwish.hide();
        fabbuy.show();
        if (price.equals("FREE")){
//            Toast.makeText(discover.this,  "free %%% "+price, Toast.LENGTH_SHORT).show();

            fabbuy.setText("ENROLL");
        }else {
//            Toast.makeText(discover.this,  "buy %%% "+price, Toast.LENGTH_SHORT).show();

            fabbuy.setText("BUY NOW");
        }
    }

    public void showWish(){
            fabbuy.hide();
            fabwish.show();
    }

    public void showProgress(){
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress(){
        progressBar.setVisibility(View.GONE);
    }

    public void recycle(){
        try{
        Log.w("recyclervvvv", "recyclerecycle");
        recyclerView=(RecyclerView)dialogWish.findViewById(R.id.recyclewish);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(discover.this, LinearLayoutManager.VERTICAL, false));

        formatWishAdapter=new formatWishAdapter(discover.this,formatWishList,onClickInterfaceFormat1);
        recyclerView.setAdapter(formatWishAdapter);


        onClickInterfaceFormat1 = new onClickInterfaceFormat1() {
            @Override
            public void setClick(int abcdr) {

            }
        };
        }catch(Exception e){
            Log.d("returnerror", e.toString());
        }
    }

    public boolean sqlInsert(String fsnumber,String fsa,String fsb,String fsc,String fsd,String fscorrect,String fsquestion){
    mydatabase.execSQL("INSERT INTO "+currentQC+" (number,a,b,c,d,correct,question) VALUES ("+fsnumber+",'"+fsa+"','"+fsb+"','"+fsc+"','"+fsd+"','"+fscorrect+"','"+fsquestion+"')");
        Log.i( "inner ",fsquestion);
        return true;
    }

}