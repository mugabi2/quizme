package com.quizinfinity.quizme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private int regStatus=0;
    private String zero="0";
    String name;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String prefName = "userDetails";
    SharedPreferences sharedPreferences;

    @BindView(R.id.register_view)
    RelativeLayout mRegisterView;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.card)
    CardView mCard;
    @BindView(R.id.btn_close)
    ImageView mBtnClose;
    Animation alphaAppear, alphaDisappear;
    int x, y, width, height, hypotenuse;
    float pixelDensity;
    @BindView(R.id.loginbtn)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = this.getSharedPreferences(prefName, MODE_PRIVATE);

        Button loginbtn=findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText liemail=(EditText)findViewById(R.id.et_emailli);
                EditText lipassword=(EditText)findViewById(R.id.et_passwordli);
                Button loginbtn=findViewById(R.id.loginbtn);
// Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();

                String email = liemail.getText().toString().trim();
                String password = lipassword.getText().toString().trim();
                Toast.makeText(getApplicationContext(), "push push", Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Please enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    saveDetails(email);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(login.this, "Authentication failed.",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });
        EditText suname=(EditText)findViewById(R.id.et_username);
        EditText suemail=(EditText)findViewById(R.id.et_email);
        EditText supassword=(EditText)findViewById(R.id.et_password);
        Button registerbtn=findViewById(R.id.registerbtn);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = suname.getText().toString().trim();
                String email = suemail.getText().toString().trim();
                String password = supassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    regStatus=1;
                                    registerDetails(name,email);
                                } else {
                                    regStatus=0;
                                    // If sign in fails, display a message to the user.
                                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(login.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                return ;
                                }
                            }
                        });

            }
        });

        pixelDensity = getResources().getDisplayMetrics().density;
        alphaAppear = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        alphaDisappear = AnimationUtils.loadAnimation(this, R.anim.alpha_disappear);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegister();
            }
        });
        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToLogin();
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBtnLogin.setBackground(ContextCompat.getDrawable(login.this,R.drawable.bg_press_btn));
            }
        });

    }

    private void goToRegister() {
        width = mCard.getWidth();
        height = mCard.getHeight();

        x = width / 2;
        y = height / 2;
        hypotenuse = (int) Math.hypot(x , y);

        y = (int) (y - ((40 * pixelDensity) + (40 * pixelDensity)));

        FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)
                mRegisterView.getLayoutParams();
        parameters.height = mCard.getHeight();
        mRegisterView.setLayoutParams(parameters);

        mFab.animate()
                .translationX(-x)
                .translationY(-y)
                .setDuration(350)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animator) {

                        Animator anim = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            anim = ViewAnimationUtils.createCircularReveal(mRegisterView, width / 2, height / 2, 40 * pixelDensity, hypotenuse);
                        }
                        anim.setDuration(350);
                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                                System.out.println("start");
                            }
                            @Override
                            public void onAnimationEnd(Animator animator) {
                                System.out.println("startend");
                            }
                            @Override
                            public void onAnimationCancel(Animator animator) {
                            }
                            @Override
                            public void onAnimationRepeat(Animator animator) {
                            }
                        });
                        mFab.setVisibility(View.GONE);
                        mRegisterView.setVisibility(View.VISIBLE);
                        anim.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animator) {
                    }
                });
    }

    private void backToLogin() {

        alphaDisappear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animator anim = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    anim = ViewAnimationUtils.createCircularReveal(mRegisterView, width / 2, height / 2, hypotenuse, 40 * pixelDensity);
                }
                anim.setDuration(350);
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        mRegisterView.setVisibility(View.GONE);
                        mFab.setVisibility(View.VISIBLE);
                        mFab.animate()
                                .translationX(0f)
                                .translationY(0f)
                                .setDuration(200)
                                .setListener(null);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                anim.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mBtnClose.startAnimation(alphaDisappear);
    }

    class registrar extends AsyncTask<String, String, String> {
        protected String doInBackground(String... voids) {
            final String result="0";
//                String namesu=voids[0];
            String emailsu=voids[0];
            String passwordsu=voids[1];

//            Toast.makeText(login.this,"back back back",Toast.LENGTH_SHORT).show();


            return emailsu;
        }

        @Override
        protected void onPostExecute(String emailsu) {
//            Toast.makeText(login.this,"poster poster poster",Toast.LENGTH_LONG).show();

            if (regStatus==1) {
            }else {
//                Toast.makeText(login.this,"post noting "+regStatus,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String sharecode(){
//        name=name.substring(0,4);
        final Random RANDOM = new SecureRandom();
        final String ALPHABET = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
        int length=8;

            StringBuffer buffer = new StringBuffer(length);
            for (int i = 0; i < length; i++) {
                buffer.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
            }
            String code=buffer.toString();
        Toast.makeText(login.this,code,Toast.LENGTH_SHORT).show();
        return code;
    }
    public void registerDetails(String name,String email){
// Initialize Firebase Auth
        String shareProfileCode=sharecode();
        mAuth = FirebaseAuth.getInstance();
        Map<String, Object> userdetails = new HashMap<>();
        userdetails.put("name", name);
        userdetails.put("email", email);
        userdetails.put("quizzes", zero);
        userdetails.put("wishlist",zero);
        userdetails.put("cart",zero);
        userdetails.put("pts", zero);
        userdetails.put("shareProfile", zero);
        userdetails.put("shareScore", zero);
        userdetails.put("shareProfileCode", shareProfileCode);
        userdetails.put("shareProfileReceived", zero);
        userdetails.put("coins", zero);
        userdetails.put("coinsSpent", zero);
        userdetails.put("cashSpent", zero);
        userdetails.put("frees", zero);
        userdetails.put("profilePhoto", zero);
        userdetails.put("profilePhotoUrl", "");
        userdetails.put("instructor", zero);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cashSpent",zero);
        editor.putString("coins",zero);
        editor.putString("coinsSpent",zero);
        editor.putString("email",email);
        editor.putString("freeQuizzes",zero);
        editor.putString("instructor",zero);
        editor.putString("name",name);
        editor.putString("profilePhoto",zero);
        editor.putString("profilePhotoUrl","");
        editor.putString("pts",zero);
        editor.putString("quizzes",zero);
        editor.putString("shareProfile",zero);
        editor.putString("shareProfileCode",shareProfileCode);
        editor.putString("shareProfileReceived",zero);
        editor.putString("shareScore",zero);
        editor.putString("wishlist",zero);
        editor.putString("cart",zero);
        editor.apply();

// Add a new document with a generated ID

        db.collection("USERS").document(email)
                .set(userdetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(login.this, discover.class);
                        finish();
                        startActivity(intent);
                        Log.d("luanda", "successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("luanda", "Error writing document", e);
                    }
                });
    }

    public void next(View view){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    public void login(View view){

        EditText liemail=(EditText)findViewById(R.id.et_emailli);
        EditText lipassword=(EditText)findViewById(R.id.et_passwordli);
        Button loginbtn=findViewById(R.id.loginbtn);
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        String email = liemail.getText().toString().trim();
        String password = lipassword.getText().toString().trim();
        Toast.makeText(getApplicationContext(), "push push", Toast.LENGTH_SHORT).show();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveDetails(email);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void saveDetails(String email){
//        GET USER DATA FROM FIREBASE
        db.collection("USERS")
                .whereEqualTo("email",email )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String cashSpent=document.getString("cashSpent");
                                String coins=document.getString("coins");
                                String coinsSpent=document.getString("coinsSpent");
                                String email=document.getString("email");
                                String freeQuizzes=document.getString("freeQuizzes");
                                String instructor=document.getString("instructor");
                                String name=document.getString("name");
                                String profilePhoto=document.getString("profilePhoto");
                                String profilePhotoUrl=document.getString("profilePhotoUrl");
                                String pts=document.getString("pts");
                                String quizzes=document.getString("quizzes");
                                String shareProfile=document.getString("shareProfile");
                                String shareProfileCode=document.getString("shareProfileCode");
                                String shareProfileReceived=document.getString("shareProfileReceived");
                                String shareScore=document.getString("shareScore");
                                String wishlist=document.getString("wishlist");
                                String cart=document.getString("cart");

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("cashSpent",cashSpent);
                                editor.putString("coins",coins);
                                editor.putString("coinsSpent",coinsSpent);
                                editor.putString("email",email);
                                editor.putString("freeQuizzes",freeQuizzes);
                                editor.putString("instructor",instructor);
                                editor.putString("name",name);
                                editor.putString("profilePhoto",profilePhoto);
                                editor.putString("profilePhotoUrl",profilePhotoUrl);
                                editor.putString("pts",pts);
                                editor.putString("quizzes",quizzes);
                                editor.putString("shareProfile",shareProfile);
                                editor.putString("shareProfileCode",shareProfileCode);
                                editor.putString("shareProfileReceived",shareProfileReceived);
                                editor.putString("shareScore",shareScore);
                                editor.putString("wishlist",wishlist);
                                editor.putString("cart",cart);
                                editor.apply();

                                Intent intent=new Intent(login.this,discover.class);
                                finish();
                                startActivity(intent);
                            }
//                            firestoreCallback.onCallback(requestArray);
                        } else {
                            Log.d("milan", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}