package com.quizinfinity.quizme;

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
    RecyclerView recyclerView;
    format1adapter format1adapter;
    List<format1quiz> format1quizList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String fsTitle,fsPrice,fsInstructor,fsPhoto,fsCategory,fsDescription,fshasPhoto,fsInstructorEmail,fsInstructorName,
            fsInstructorPhotoUrl,fsLevel,fsNoOfquestions,fsQuizCode,fsRating,fsStudents;
    private onClickInterfaceFormat1 onClickInterfaceFormat1;

    private Bundle savedState;
    private boolean saved;
    private static final String _FRAGMENT_STATE = "FRAGMENT_STATE";
    ImageView imageViewtest;
    String arrayQuizInfo="";
    ArrayList <String>recycler1array=new ArrayList<String>();
    Map<String, Object> outerMap=new HashMap<>();
    Map<String, Object> innerMap=new HashMap<>();
    int counter=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            savedState = savedInstanceState.getBundle(_FRAGMENT_STATE);
        }
        Log.i("222", "fragment create");

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
                                        new format1quiz(fsPhoto,fsInstructorPhotoUrl,fsTitle,fsNoOfquestions,fsInstructor,fsPrice,fsLevel,fsDescription,fsRating,fsQuizCode)
                                );
                                arrayQuizInfo=arrayQuizInfo+document.getData().toString();
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
        View view= inflater.inflate(R.layout.fragment_discover, container, false);
        Log.i("222", "fragment view view create");

        format1quizList=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.recycle1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));

        onClickInterfaceFormat1 = new onClickInterfaceFormat1() {
            @Override
            public void setClick(int abcdr) {

            }
        };
        return view;
    }
    public void recycle(){
        format1adapter=new format1adapter(requireActivity(),format1quizList,onClickInterfaceFormat1);
        recyclerView.setAdapter(format1adapter);
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