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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class fragmentSearch extends Fragment {
    String categorisation;
    RecyclerView recyclerView;
    formatSearchAdapter formatSearchAdapter;
    List<formatSearch> formatSearchList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String fsTitle,fsPrice,fsInstructor,fsPhoto,fsCategory,fsDescription,fshasPhoto,fsInstructorEmail,fsInstructorName,
            fsInstructorPhotoUrl,fsLevel,fsNoOfquestions,fsQuizCode,fsRating,fsStudents;
    private onClickInterfaceFormat1 onClickInterfaceFormat1;

    public fragmentSearch(String categorisation) {
        this.categorisation = categorisation;
    }

    public fragmentSearch() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db.collection("QUIZZES")
                .whereEqualTo("category",categorisation )
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

//                                recycler1array.add(document.getData().toString());
                                formatSearchList.add(
                                        new formatSearch(fsPhoto,fsInstructorPhotoUrl,fsTitle,fsNoOfquestions,fsInstructor,fsPrice,fsLevel,fsDescription,fsRating,fsQuizCode,fsStudents)
                                );
                            }
//                            firestoreCallback.onCallback(requestArray);
                        } else {
                            Log.d("milan", "Error getting documents: ", task.getException());
                        }
                        recycleSearch();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        formatSearchList=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclesearch);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));

        onClickInterfaceFormat1 = new onClickInterfaceFormat1() {
            @Override
            public void setClick(int abcdr) {

            }
        };
        return view;
    }
    public void recycleSearch(){
        formatSearchAdapter=new formatSearchAdapter(requireActivity(),formatSearchList,onClickInterfaceFormat1);
        recyclerView.setAdapter(formatSearchAdapter);
    }

}