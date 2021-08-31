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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class fragmentDiscover extends Fragment {
    RecyclerView recyclerViewcateg;
    formatCategAdapter formatCategAdapter;
    List<formatCateg> formatcategList;
    String fsCategoryTitle,fsNoOfQuizzes,withover;

    RecyclerView recyclerView;
    format1adapter format1adapter;
    format2adapter format2adapter;
    List<format1quiz> format1quizList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String fsTitle,fsPrice,fsInstructor,fsPhoto,fsCategory,fsDescription,fshasPhoto,fsInstructorEmail,fsInstructorName,
            fsInstructorPhotoUrl,fsLevel,fsNoOfquestions,fsQuizCode,fsRating,fsStudents;
    private onClickInterfaceFormat1 onClickInterfaceFormat1;

    RecyclerView recyclerView2;
    List<format1quiz> format1quizList2;
    private FirebaseFirestore db2 = FirebaseFirestore.getInstance();
    String fsTitle2,fsPrice2,fsInstructor2,fsPhoto2,fsCategory2,fsDescription2,fshasPhoto2,fsInstructorEmail2,fsInstructorName2,
            fsInstructorPhotoUrl2,fsLevel2,fsNoOfquestions2,fsQuizCode2,fsRating2,fsStudents2;

    RecyclerView recyclerView3;
    List<format1quiz> format1quizList3;
    private FirebaseFirestore db3 = FirebaseFirestore.getInstance();
    String fsTitle3,fsPrice3,fsInstructor3,fsPhoto3,fsCategory3,fsDescription3,fshasPhoto3,fsInstructorEmail3,fsInstructorName3,
            fsInstructorPhotoUrl3,fsLevel3,fsNoOfquestions3,fsQuizCode3,fsRating3,fsStudents3;

    RecyclerView recyclerView4;
    List<format1quiz> format1quizList4;
    private FirebaseFirestore db4 = FirebaseFirestore.getInstance();
    String fsTitle4,fsPrice4,fsInstructor4,fsPhoto4,fsCategory4,fsDescription4,fshasPhoto4,fsInstructorEmail4,fsInstructorName4,
            fsInstructorPhotoUrl4,fsLevel4,fsNoOfquestions4,fsQuizCode4,fsRating4,fsStudents4;

    private Bundle savedState;
    private boolean saved;
    private static final String _FRAGMENT_STATE = "FRAGMENT_STATE";
    ImageView imageViewtest;
    String arrayQuizInfo="";
    ArrayList <String>recycler1array=new ArrayList<String>();
    Map<String, Object> outerMap=new HashMap<>();
    Map<String, Object> innerMap=new HashMap<>();
    int counter=0;

    private AdView mAdView1,mAdView2,mAdView3,mAdView4;

    private String prefName = "userDetails";
    SharedPreferences prefs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            savedState = savedInstanceState.getBundle(_FRAGMENT_STATE);
        }
        Log.i("222", "fragment create");

        db.collection("CATEGORIES")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                fsCategoryTitle=document.getId();
                                fsNoOfQuizzes= document.getString("numberOfQuizzes");
                                Log.i("awuli",fsCategoryTitle+fsNoOfQuizzes);
//                                recycler1array.add(document.getData().toString());
                                formatcategList.add(
                                        new formatCateg(fsCategoryTitle,fsNoOfQuizzes)
                                );
//                                arrayQuizInfo=arrayQuizInfo+document.getData().toString();
                            }
                        } else {
                            Log.d("milan categories", "Error getting documents: ", task.getException());
                        }
                        recycleCateg();
                    }
                });

        db.collection("QUIZZES")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(requireActivity(),document.getId() + " => " + document.getData(),Toast.LENGTH_LONG).show();
//                                requestArray.add(document.getData().toString());
                                fsCategory=document.getString("category");
                                fsDescription=document.getString("description");
                                fshasPhoto=document.getString("hasPhoto");
                                fsInstructor=document.getString("instructor");
                                fsInstructorEmail=document.getString("instructorEmail");
                                fsInstructorName=document.getString("instructorName");
                                fsInstructorPhotoUrl=document.getString("instructorPhotoUrl");
                                fsLevel=document.getString("level");
                                fsNoOfquestions=document.getString("numberOfQuestions");
                                fsPhoto=document.getString("photoUrl");
                                fsQuizCode=document.getString("quizCode");
                                fsRating=document.getString("rating");
                                fsStudents=document.getString("students");
                                fsTitle=document.getString("title");
                                fsPrice=document.getString("price");

                                recycler1array.add(document.getData().toString());
                                format1quizList.add(
                                        new format1quiz(fsPhoto,fsInstructorPhotoUrl,fsTitle,fsNoOfquestions,fsInstructor,fsPrice,fsLevel,fsDescription,fsRating,fsQuizCode,fsStudents)
                                );
                                arrayQuizInfo=arrayQuizInfo+document.getData().toString();
                            }
//                            firestoreCallback.onCallback(requestArray);
                        } else {
                            Log.d("milan quizzes", "Error getting documents: ", task.getException());
                        }
                        recycle();
                    }
                });
        String ranka="2";
        db.collection("QUIZZES")
                .whereEqualTo("ranking",ranka )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(requireActivity(),document.getId() + " => " + document.getData(),Toast.LENGTH_LONG).show();
//                                requestArray.add(document.getData().toString());
                                fsCategory2=document.getString("category");
                                fsDescription2=document.getString("description");
                                fshasPhoto2=document.getString("hasPhoto");
                                fsInstructor2=document.getString("instructor");
                                fsInstructorEmail2=document.getString("instructorEmail");
                                fsInstructorName2=document.getString("instructorName");
                                fsInstructorPhotoUrl2=document.getString("instructorPhotoUrl");
                                fsLevel2=document.getString("level");
                                fsNoOfquestions2=document.getString("numberOfQuestions");
                                fsPhoto2=document.getString("photoUrl");
                                fsQuizCode2=document.getString("quizCode");
                                fsRating2=document.getString("rating");
                                fsStudents2=document.getString("students");
                                fsTitle2=document.getString("title");
                                fsPrice2=document.getString("price");

//                                recycler1array.add(document.getData().toString());
                                format1quizList2.add(
                                        new format1quiz(fsPhoto2,fsInstructorPhotoUrl2,fsTitle2,fsNoOfquestions2,fsInstructor2,fsPrice2,fsLevel2,fsDescription2,fsRating2,fsQuizCode2,fsStudents2)
                                );
//                                arrayQuizInfo=arrayQuizInfo+document.getData().toString();
                            }
//                            firestoreCallback.onCallback(requestArray);
                        } else {
                            Log.d("milan quizzes", "Error getting documents: ", task.getException());
                        }
                        recycle2();
                    }
                });
        db.collection("QUIZZES")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(requireActivity(),document.getId() + " => " + document.getData(),Toast.LENGTH_LONG).show();
//                                requestArray.add(document.getData().toString());
                                fsCategory3=document.getString("category");
                                fsDescription3=document.getString("description");
                                fshasPhoto3=document.getString("hasPhoto");
                                fsInstructor3=document.getString("instructor");
                                fsInstructorEmail3=document.getString("instructorEmail");
                                fsInstructorName3=document.getString("instructorName");
                                fsInstructorPhotoUrl3=document.getString("instructorPhotoUrl");
                                fsLevel3=document.getString("level");
                                fsNoOfquestions3=document.getString("numberOfQuestions");
                                fsPhoto3=document.getString("photoUrl");
                                fsQuizCode3=document.getString("quizCode");
                                fsRating3=document.getString("rating");
                                fsStudents3=document.getString("students");
                                fsTitle3=document.getString("title");
                                fsPrice3=document.getString("price");

//                                recycler1array.add(document.getData().toString());
                                format1quizList3.add(
                                        new format1quiz(fsPhoto3,fsInstructorPhotoUrl3,fsTitle3,fsNoOfquestions3,fsInstructor3,fsPrice3,fsLevel3,fsDescription3,fsRating3,fsQuizCode3,fsStudents3)
                                );
                            }
//                            firestoreCallback.onCallback(requestArray);
                        } else {
                            Log.d("milan quizzes", "Error getting documents: ", task.getException());
                        }
                        recycle3();
                    }
                });
        db.collection("QUIZZES")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Toast.makeText(requireActivity(),document.getId() + " => " + document.getData(),Toast.LENGTH_LONG).show();
//                                requestArray.add(document.getData().toString());
                                fsCategory4=document.getString("category");
                                fsDescription4=document.getString("description");
                                fshasPhoto4=document.getString("hasPhoto");
                                fsInstructor4=document.getString("instructor");
                                fsInstructorEmail4=document.getString("instructorEmail");
                                fsInstructorName4=document.getString("instructorName");
                                fsInstructorPhotoUrl4=document.getString("instructorPhotoUrl");
                                fsLevel4=document.getString("level");
                                fsNoOfquestions4=document.getString("numberOfQuestions");
                                fsPhoto4=document.getString("photoUrl");
                                fsQuizCode4=document.getString("quizCode");
                                fsRating4=document.getString("rating");
                                fsStudents4=document.getString("students");
                                fsTitle4=document.getString("title");
                                fsPrice4=document.getString("price");

                                format1quizList4.add(
                                        new format1quiz(fsPhoto4,fsInstructorPhotoUrl4,fsTitle4,fsNoOfquestions4,fsInstructor4,fsPrice4,fsLevel4,fsDescription4,fsRating4,fsQuizCode4,fsStudents4)
                                );
                            }
//                            firestoreCallback.onCallback(requestArray);
                        } else {
                            Log.d("milan quizzes", "Error getting documents: ", task.getException());
                        }
                        recycle4();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_discover, container, false);
        Log.i("222", "fragment view view create");
//
//        double deup=8,dedo=9;
//        double desco=Math.round((deup/dedo)*100);
//        int intcore=(int)desco;
//        String ccscore=String.valueOf(intcore);
//        Toast.makeText(getActivity(),"=== "+ccscore+"%",Toast.LENGTH_SHORT).show();

        formatcategList=new ArrayList<>();
        recyclerViewcateg=(RecyclerView)view.findViewById(R.id.recyclecateg);
        recyclerViewcateg.setHasFixedSize(true);
        recyclerViewcateg.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        format1quizList=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.recycle1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        format1quizList2=new ArrayList<>();
        recyclerView2=(RecyclerView)view.findViewById(R.id.recycle2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        format1quizList3=new ArrayList<>();
        recyclerView3=(RecyclerView)view.findViewById(R.id.recycle3);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        format1quizList4=new ArrayList<>();
        recyclerView4=(RecyclerView)view.findViewById(R.id.recycle4);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        prefs = getActivity().getSharedPreferences(prefName, Context.MODE_PRIVATE);
        withover = prefs.getString("withover", "");
        TextView withovertv=view.findViewById(R.id.withover);
        withovertv.setText(".....with over "+withover+" quizzes");

        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView1 = view.findViewById(R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);
        mAdView2 = view.findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);
        mAdView3 = view.findViewById(R.id.adView3);
        AdRequest adRequest3 = new AdRequest.Builder().build();
        mAdView3.loadAd(adRequest3);
        mAdView4 = view.findViewById(R.id.adView4);
        AdRequest adRequest4 = new AdRequest.Builder().build();
        mAdView4.loadAd(adRequest4);

//        recyclerViewcateg=(RecyclerView)view.findViewById(R.id.recyclecateg);
//        recyclerViewcateg.setHasFixedSize(true);
//        recyclerViewcateg.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        onClickInterfaceFormat1 = new onClickInterfaceFormat1() {
            @Override
            public void setClick(int abcdr) {

            }
        };
        return view;
    }
    public void recycle(){
        try {
        format1adapter=new format1adapter(requireActivity(),format1quizList,onClickInterfaceFormat1);
        recyclerView.setAdapter(format1adapter);
        }catch(Exception e){
            Log.d("returnerror", e.toString());
        }
    }
    public void recycle2(){
        try {
        format2adapter=new format2adapter(requireActivity(),format1quizList2,onClickInterfaceFormat1);
        recyclerView2.setAdapter(format2adapter);
            ((discover)getActivity()).hideProgress();
        }catch(Exception e){
            Log.d("returnerror", e.toString());
            try{
            ((discover)getActivity()).hideProgress();
            }catch(Exception ex){

            }
        }
    }
    public void recycle3(){
        try {
        format1adapter=new format1adapter(requireActivity(),format1quizList3,onClickInterfaceFormat1);
        recyclerView3.setAdapter(format1adapter);
        }catch(Exception e){
            Log.d("returnerror", e.toString());
        }
    }
    public void recycle4(){
        try {
        format1adapter=new format1adapter(requireActivity(),format1quizList4,onClickInterfaceFormat1);
        recyclerView4.setAdapter(format1adapter);
        }catch(Exception e){
            Log.d("returnerror", e.toString());
        }
    }
    public void recycleCateg(){
        try {
            formatCategAdapter = new formatCategAdapter(requireActivity(), formatcategList, onClickInterfaceFormat1);
            recyclerViewcateg.setAdapter(formatCategAdapter);
        }catch(Exception e){
            Log.d("returnerror", e.toString());
        }
    }
//    @Override
//    public void onSaveInstanceState(Bundle state) {
//        if (getView() == null) {
//            state.putBundle(_FRAGMENT_STATE, savedState);
//        } else {
//            Bundle bundle = saved ? savedState : getStateToSave();
//            state.putBundle(_FRAGMENT_STATE, bundle);
//        }
//        saved = false;
//        super.onSaveInstanceState(state);
//    }
//
//    @Override
//    public void onDestroyView() {
//        savedState = getStateToSave();
//        saved = true;
//        super.onDestroyView();
//    }
//
//    protected Bundle getSavedState() {
//        return savedState;
//    }
//
//    protected  boolean hasSavedState();
//
//    protected  Bundle getStateToSave();
}