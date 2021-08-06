package com.example.quizme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class discover extends AppCompatActivity {
    RecyclerView recyclerView;
    format1adapter format1adapter;
    List<format1quiz> format1quizList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String fsTitle,fsPrice,fsInstructor,fsPhoto;
    private onClickInterfaceFormat1 onClickInterfaceFormat1;
    Dialog dialogWish;
    private String prefName = "userDetails";
    FloatingActionButton fabwish;
    ExtendedFloatingActionButton fabbuy;
    boolean fabwishshow=true;
    String buybtnText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        SharedPreferences prfs = this.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        String astatus = prfs.getString("email", "");
        Log.i("222", astatus);
        Toast.makeText(discover.this, "2222  "+astatus,Toast.LENGTH_LONG).show();
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

    public void activityFirebase(){

        format1quizList=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.recycle1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        onClickInterfaceFormat1 = new onClickInterfaceFormat1() {
            @Override
            public void setClick(int abcdr) {

            }
        };
    }
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
        Toast.makeText(discover.this,  buybtnText, Toast.LENGTH_SHORT).show();
        if (buybtnText.equals("Buy Now")){
            Toast.makeText(discover.this,  " $$$$$$$$$$", Toast.LENGTH_SHORT).show();
        }else {
//            Toast.makeText(discover.this,  " enroll enroll", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(discover.this, answeringQuiz.class);
//            startActivity(intent);
//            finish();
//            Toast.makeText(discover.this,  " FREE FREE", Toast.LENGTH_SHORT).show();
        }
    }

    public void fabwish(View view){
        dialogWish.show();
    }

    public void lawOfExchange(String price) {
        if (fabwishshow) {
            fabwish.hide();
            if (price.equals("FREE")){
                fabbuy.setText("ENROLL");
            }else {

            }
            fabbuy.show();
            fabwishshow = false;
        } else {
            fabbuy.hide();
            fabwish.show();
            fabwishshow = true;
        }
    }
}