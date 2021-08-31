package com.quizinfinity.quizme;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class fragmentMyquizzes extends Fragment {
    String quizImageUrl,title,qnnumber,instructor,progress;
    String myemail;

    RecyclerView recyclerView;
    format1adapter format1adapter;
    List<formatMyQuizzes> formatMyQuizzesList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String fsTitle,fsProgress,fsInstructor,fsPhoto,fsCategory,fsDescription,fshasPhoto,fsInstructorEmail,fsInstructorName,
            fsInstructorPhotoUrl,fsLevel,fsNoOfquestions,fsQuizCode,fsRating,fsStudents;
    private onClickInterfaceFormat1 onClickInterfaceFormat1;
    formatMyQuizzesAdapter formatMyQuizzesAdapter;
    SharedPreferences prefs,prefquiz;
    private String prefName = "userDetails";
    String prefNameQuiz,currentQC;
    SQLiteDatabase mydatabase;
    ArrayList<Map> questionsArray=new ArrayList<Map>();
    Map<String, Object> outerMap=new HashMap<>();
    Map<String, Object> innerMap=new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            prefs = getActivity().getSharedPreferences(prefName, MODE_PRIVATE);
            myemail = prefs.getString("email", "");
        String myquizReference = myemail + "MYQUIZZES";
        currentQC= prefs.getString("current quiz code", "");

        SharedPreferences prefquizprogress = getActivity().getSharedPreferences("qc0"+"PROGRESS", MODE_PRIVATE);
        String look = prefquizprogress.getString("progress", "");
//        Toast.makeText(getActivity(), "qc3..."+look, Toast.LENGTH_SHORT).show();
        Log.i("progra ",look);

        //        mydatabase=getActivity().openOrCreateDatabase(myemail,MODE_PRIVATE,null);
//        Cursor c=mydatabase.rawQuery("SELECT * FROM "+currentQC,null);
//        int numberindex=c.getColumnIndex("number");
//        int aindex=c.getColumnIndex("a");
//        int bindex=c.getColumnIndex("b");
//        int cindex=c.getColumnIndex("c");
//        int dindex=c.getColumnIndex("d");
//        int correctindex=c.getColumnIndex("correct");
//        int questionindex=c.getColumnIndex("question");
//        c.moveToFirst();
//
//        while (!c.isAfterLast()){
////            questionsArray=c.getString(aindex);
//            innerMap.put("a", c.getString(aindex));
//            innerMap.put("b", c.getString(bindex));
//            innerMap.put("c", c.getString(cindex));
//            innerMap.put("d", c.getString(dindex));
//            innerMap.put("correct", c.getString(correctindex));
//            innerMap.put("question", c.getString(questionindex));
//            questionsArray.add(innerMap);
//            c.moveToNext();
//        }
//        Log.i("inner?:",questionsArray.toString());

        prefNameQuiz = currentQC;
        prefquiz = getActivity().getSharedPreferences(prefNameQuiz, MODE_PRIVATE);
        String theArray= prefquiz.getString(currentQC, "");
//        Toast.makeText(getActivity(),theArray,Toast.LENGTH_LONG).show();
        db.collection(myquizReference)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                fsDescription=document.getString("description");
                                fsInstructor=document.getString("instructor");
                                fsLevel=document.getString("level");
                                fsNoOfquestions=document.getString("numberOfQuestions");
                                fsPhoto=document.getString("photoUrl");
                                fsQuizCode=document.getString("quizCode");
                                fsRating=document.getString("rating");
                                fsTitle=document.getString("title");
                                fsProgress=document.getString("progress");

                                formatMyQuizzesList.add(
                new formatMyQuizzes(fsPhoto,fsTitle,fsNoOfquestions,fsInstructor,fsProgress,
                        fsLevel,fsDescription,fsRating,fsQuizCode)
                                );

                            }
//                            firestoreCallback.onCallback(requestArray);
                        } else {
                            Log.d("milan", "Error getting documents: ", task.getException());
                        }
                        Log.i( "1111111111",formatMyQuizzesList.toString());
                        recycle();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_myquizzes, container, false);
        formatMyQuizzesList=new ArrayList<>();
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclemyquizzes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));

        onClickInterfaceFormat1= new onClickInterfaceFormat1() {
            @Override
            public void setClick(int abcdr) {

            }
        };
        return view;
    }

    public void recycle(){
        try{
        formatMyQuizzesAdapter=new formatMyQuizzesAdapter(requireActivity(),formatMyQuizzesList,onClickInterfaceFormat1);
        recyclerView.setAdapter(formatMyQuizzesAdapter);
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