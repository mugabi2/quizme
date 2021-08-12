package com.quizinfinity.quizme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.Toast;

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
    SharedPreferences prefs;
    private onClickInterfaceFormat1 onClickInterfaceFormat1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        prefs = this.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        myemail = prefs.getString("email", "");
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
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                    Fragment selectedFragment=null;
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

    public void next(View view){
        Intent intent=new Intent(this,login.class);
        startActivity(intent);
    }

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
                Toast.makeText(discover.this,"profile profile",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void fabbuy(View view){
        buybtnText=fabbuy.getText().toString();
//        if (buybtnText.equals(getResources().getString(R.string.buynow))){
            if (buybtnText.equals("BUY NOW")){
            Toast.makeText(discover.this,  " $$$$$$$$$$", Toast.LENGTH_SHORT).show();
        }else {
//                SharedPreferences pref = getSharedPreferences("current quiz code", Context.MODE_PRIVATE);
                String currentQC= prefs.getString("current quiz code", "");
                String currentimage= prefs.getString("current quiz image", "");
                String currenttitle= prefs.getString("current quiz title", "");
                String currentqnnumb= prefs.getString("current quiz question number", "");
                String currentinstructor= prefs.getString("current quiz instructor", "");
                String currentprice= prefs.getString("current quiz price", "");
                String currentlevel= prefs.getString("current quiz level", "");
                String currentdesc= prefs.getString("current quiz description", "");
                String currentrating= prefs.getString("current quiz rating", "");

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
                quizdetails.put("revealCoins", "0");
                quizdetails.put("revealWatch", "0");
                String myquizReference = myemail + "MYQUIZZES";

                db.collection(myquizReference).document(currentQC)
                        .set(quizdetails)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(discover.this, " added to my quizzes", Toast.LENGTH_SHORT).show();
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new fragmentMyquizzes()).commit();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("wishlist", "Error wishlisting", e);
                            }
                        });
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
            Toast.makeText(discover.this,  "buy %%% "+price, Toast.LENGTH_SHORT).show();

            fabbuy.setText("BUY NOW");
        }
    }
    public void showWish(){
            fabbuy.hide();
            fabwish.show();
    }
    public void recycle(){
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
    }
}