package com.quizinfinity.quizme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class fragmentProfile extends Fragment {
    private String prefName = "userDetails";
    String name,email,pts,coins,quizzes,sharecode,profilepic;
    ImageView imageView;
    StorageReference storageRef;

    public fragmentProfile() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prfs = getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);
        name = prfs.getString("name", "");
        email = prfs.getString("email", "");
        pts = prfs.getString("pts", "");
        coins = prfs.getString("coins", "");
        quizzes = prfs.getString("quizzes", "");
        sharecode = prfs.getString("sharecode", "");
        profilepic = prfs.getString("profilePhotoUrl", "");
//        Log.i("222", astatus);
//        Toast.makeText(getActivity(), "2222  "+astatus,Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        TextView nameholder=view.findViewById(R.id.profilename);
        TextView emailholder=view.findViewById(R.id.profileemail);
        TextView ptsholder=view.findViewById(R.id.profilepoints);
        TextView coinsholder=view.findViewById(R.id.profilecoins);
        TextView quizzesholder=view.findViewById(R.id.profilemyquizzes);
        RelativeLayout sharebutton=view.findViewById(R.id.shareRelative);
        imageView=view.findViewById(R.id.profilepic);

        ExtendedFloatingActionButton fabsignout=view.findViewById(R.id.signout);
        fabsignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),login.class);
                startActivity(intent);
            }
        });
        nameholder.setText(name);
        emailholder.setText(email);
        ptsholder.setText(pts+" pts");
        coinsholder.setText(coins+" coins");
        quizzesholder.setText(quizzes);
//        TextView shareholder=view.findViewById(R.id.detdescript);

        try {
            storageRef = FirebaseStorage.getInstance().getReference().child(profilepic);
            final File localfile=File.createTempFile("two","jpg");
            storageRef.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                }
            });
        } catch (Exception e) {
            Log.i("erere", "photo error");
            e.printStackTrace();
        }
        return view;
    }
    public void share(View view) {
        Intent int1 = new Intent(Intent.ACTION_SEND);
        int1.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.stardigitalbikes.digitalbikes" +
                " Follow the link above to download Quiz Infinity and input code: " + sharecode + " to earn 20 coins";
        int1.putExtra(Intent.EXTRA_SUBJECT, shareBody);
        int1.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(int1, "Share using"));
    }
}