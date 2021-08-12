package com.quizinfinity.quizme;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class fragmentDetails extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    StorageReference storageRef;
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String prefName = "userDetails";
    String imagequiz, imageinstructor,title, qnnumber, instructor,price,level,description,rating,quizCode;
    String myemail;
    SharedPreferences prefs;
    Map<String, Object> quizdetails;
    Button wishbtn,cartbtn;

    public fragmentDetails(String imagequiz, String imageinstructor, String title, String qnnumber, String instructor, String price, String level, String description, String rating,String quizCode) {
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
    public fragmentDetails() {
        // Required empty public constructor
    }
    public static fragmentDetails newInstance(String param1, String param2) {
        fragmentDetails fragment = new fragmentDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        prefs = getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);
//        imageinstructor="USERS/"+imageinstructor;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("current quiz code",quizCode);
        editor.putString("current quiz image",imagequiz);
        editor.putString("current quiz title",title);
        editor.putString("current quiz question number",qnnumber);
        editor.putString("current quiz instructor",instructor);
        editor.putString("current quiz price",price);
        editor.putString("current quiz level",level);
        editor.putString("current quiz description",description);
        editor.putString("current quiz rating",rating);
        editor.apply();

        quizdetails = new HashMap<>();
        quizdetails.put("quizCode", quizCode);
        quizdetails.put("photoUrl", imagequiz);
        quizdetails.put("title", title);
        quizdetails.put("numberOfQuestions", qnnumber);
        quizdetails.put("instructor", instructor);
        quizdetails.put("instructorPhotoUrl", imageinstructor);
        quizdetails.put("price", price);
        quizdetails.put("level", level);
        quizdetails.put("description", description);
        quizdetails.put("rating", rating);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ImageView imageholder=view.findViewById(R.id.detimage);
        ImageView imageholderinstruct=view.findViewById(R.id.detinstructphoto);
        TextView priceholder=view.findViewById(R.id.detprice);
        TextView instructholder=view.findViewById(R.id.detinstruct);
        TextView titleholder=view.findViewById(R.id.dettitle);
        TextView numbholder=view.findViewById(R.id.detnumb);
        TextView descholder=view.findViewById(R.id.detdescript);
        RatingBar ratingBar=view.findViewById(R.id.detrating);
        wishbtn=view.findViewById(R.id.addtowishlistbtn);
        cartbtn=view.findViewById(R.id.addtocartbtn);

        if (price.equals("FREE")){
            cartbtn.setEnabled(false);
            Toast.makeText(requireActivity(),"free disable",Toast.LENGTH_LONG).show();
        }
                ((discover)getActivity()).showBuy(price);

        wishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myemail = prefs.getString("email", "");
// Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                String wishlistReference = myemail + "WISHLIST";
                String wishtext=wishbtn.getText().toString();
                wishbtn.setText("REMOVE FROM WISHLIST");
                if (wishtext.equals("ADD TO WISHLIST")){
// Add a new document with a generated ID

                    db.collection(wishlistReference).document(quizCode)
                            .set(quizdetails)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), title + " added to Wishlist", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("wishlist", "Error wishlisting", e);
                                }
                            });
                }else{
                    wishbtn.setText("ADD TO WISHLIST");
                    db.collection(wishlistReference).document(quizCode)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), title + " removed from Wishlist", Toast.LENGTH_SHORT).show();
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
        });

        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prfs = getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);
                myemail = prfs.getString("email", "");
// Initialize Firebase Auth
                mAuth = FirebaseAuth.getInstance();
                String cartReference = myemail + "CART";
                String carttext=cartbtn.getText().toString();
                if (carttext.equals("ADD TO CART")){
                    cartbtn.setText("REMOVE FROM CART");
// Add a new document with a generated ID

                    db.collection(cartReference).document(quizCode)
                            .set(quizdetails)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
               Toast.makeText(getActivity(), title + " added to Cart", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("cart", "Error carting", e);
                                }
                            });
                }else{
                    cartbtn.setText("ADD TO CART");
                    db.collection(cartReference).document(quizCode)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), title + " removed from Cart", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("cart", "Error carting", e);
                                }
                            });
                }
            }
        });

        priceholder.setText(price);
        titleholder.setText(title);
        instructholder.setText(instructor);
        qnnumber=qnnumber+" qns";
        numbholder.setText(qnnumber);
        descholder.setText(description);
        ratingBar.setRating(Float.parseFloat(rating));

        try {
            storageRef = FirebaseStorage.getInstance().getReference().child(imagequiz);
            final File localfile=File.createTempFile("two","jpg");
            storageRef.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            imageholder.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.w("11111111", imageinstructor);
        Log.w("11111111", imagequiz);

        storageRef = FirebaseStorage.getInstance().getReference().child(imageinstructor);
        try {
            final File localfile=File.createTempFile("one","jpg");
            storageRef.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            imageholderinstruct.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }
    public void enroll(){
        Toast.makeText(getActivity(), quizCode + " enroll from details", Toast.LENGTH_SHORT).show();
    }
    public void onBackPressed(){
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new
                fragmentDiscover()).addToBackStack(null).commit();
                ((discover)getActivity()).showBuy(price);
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.w("%%%", "pausepause");
        ((discover)getActivity()).showWish();
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.w("%%%", "stop stop");

    }
    public void wishlist(String quizcode){

    }

}