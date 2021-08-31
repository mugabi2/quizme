package com.quizinfinity.quizme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class fragmentCart extends Fragment {
    RecyclerView recyclerView;
    formatCartAdapter formatCartAdapter;
    List<formatCart> formatCartList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String fsTitle,fsPrice,fsInstructor,fsPhoto,fsCategory,fsDescription,fshasPhoto,fsInstructorEmail,fsInstructorName,
            fsInstructorPhotoUrl,fsLevel,fsNoOfquestions,fsQuizCode,fsRating,fsStudents;
    private onClickInterfaceFormat1 onClickInterfaceFormat1;
    SharedPreferences prefs;
    private String prefName = "userDetails";
    String myemail;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);
        myemail = prefs.getString("email", "");
        String myquizReference = myemail + "CART";

        db.collection(myquizReference)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(requireActivity(),document.getId() + " => " + document.getData(),Toast.LENGTH_LONG).show();
//                                requestArray.add(document.getData().toString());
                                fsDescription=document.getString("description");
                                fsInstructor=document.getString("instructor");
                                fsInstructorPhotoUrl=document.getString("instructorPhotoUrl");
                                fsLevel=document.getString("level");
                                fsNoOfquestions=document.getString("numberOfQuestions");
                                fsPhoto=document.getString("photoUrl");
                                fsQuizCode=document.getString("quizCode");
                                fsRating=document.getString("rating");
                                fsTitle=document.getString("title");
                                fsPrice=document.getString("price");
                                Log.d("*****", fsPhoto+fsInstructorPhotoUrl+fsTitle+fsNoOfquestions+fsInstructor+fsPrice+fsLevel+fsDescription+fsRating+fsQuizCode);

                                formatCartList.add(
                                        new formatCart(fsPhoto,fsInstructorPhotoUrl,fsTitle,fsNoOfquestions,fsInstructor,fsPrice,fsLevel,fsDescription,fsRating,fsQuizCode)
                                );
                            }
//                            firestoreCallback.onCallback(requestArray);
                        } else {
                            Log.d("milan", "Error getting documents: ", task.getException());
                        }
                        recycle();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cart, container, false);
        formatCartList=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclecart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));

        onClickInterfaceFormat1 = new onClickInterfaceFormat1() {
            @Override
            public void setClick(int abcdr) {

            }
        };
        return view;
    }
    public void recycle(){
        try {
            formatCartAdapter=new formatCartAdapter(requireActivity(),formatCartList,onClickInterfaceFormat1);
            recyclerView.setAdapter(formatCartAdapter);
            ((discover)getActivity()).hideProgress();
        }catch(Exception e){
            Log.d("returnerror", e.toString());
            try{
                ((discover)getActivity()).hideProgress();
            }catch(Exception ex){

            }
        }
    }
}