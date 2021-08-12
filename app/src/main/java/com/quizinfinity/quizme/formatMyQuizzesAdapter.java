package com.quizinfinity.quizme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class formatMyQuizzesAdapter extends RecyclerView.Adapter<formatMyQuizzesAdapter.formatMyQuizzesviewHolder>{
    private Context context;
    private List<formatMyQuizzes>formatMyQuizzesList;
    StorageReference storageRef;;
    onClickInterfaceFormat1 onClickInterfaceFormat1;
    int prog;
    private String prefName = "userDetails";
    SharedPreferences prefs;


    public formatMyQuizzesAdapter(Context context, List<formatMyQuizzes> formatMyQuizzesList, onClickInterfaceFormat1 onClickInterfaceFormat1) {
        this.context = context;
        this.formatMyQuizzesList = formatMyQuizzesList;
        this.onClickInterfaceFormat1=onClickInterfaceFormat1;
    }

    @NonNull
    @NotNull
    @Override
    public formatMyQuizzesviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.formatmyquizzes,null);
        return new formatMyQuizzesviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull formatMyQuizzesviewHolder holder, int position) {
        formatMyQuizzes formatMyQuizzes=formatMyQuizzesList.get(position);
        prog=Integer.parseInt(formatMyQuizzes.getProgress());
        holder.tvQns.setText(formatMyQuizzes.getQnnumber());
        holder.tvTitle.setText(formatMyQuizzes.getTitle());
        holder.tvProgress.setProgress(prog);
        holder.tvInstructor.setText(formatMyQuizzes.getInstructor());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInterfaceFormat1.setClick(position);
                Toast.makeText(context.getApplicationContext(), formatMyQuizzes.getQuizCode(),Toast.LENGTH_LONG).show();
                Log.i( "onClick: ", String.valueOf(position));

                prefs = context.getApplicationContext().getSharedPreferences(prefName, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("answering quiz code",formatMyQuizzes.getQuizCode());
                editor.putString("answering quiz question number",formatMyQuizzes.getQnnumber());
                editor.putString("answering quiz rating",formatMyQuizzes.getRating());
                editor.apply();

                Intent intent=new Intent(context.getApplicationContext(),answeringQuiz.class);
                context.startActivity(intent);
            }
        });
///////////////////////////////////////////
        try {
            storageRef = FirebaseStorage.getInstance().getReference().child(formatMyQuizzes.getImagequiz());
            final File localfile=File.createTempFile("four","jpg");
            storageRef.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            holder.imageView.setImageBitmap(bitmap);
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
    }

    @Override
    public int getItemCount() {
        return formatMyQuizzesList.size();
    }

    class formatMyQuizzesviewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvTitle,tvInstructor,tvQns;
        ProgressBar tvProgress;
        LinearLayout linearLayout;
        public formatMyQuizzesviewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.fqimage);
            tvTitle=itemView.findViewById(R.id.fqtitle);
            tvQns=itemView.findViewById(R.id.fqqns);
            tvProgress=itemView.findViewById(R.id.fqprogress);
            tvInstructor=itemView.findViewById(R.id.fqinstruct);
            linearLayout=itemView.findViewById(R.id.linearmyquizzes);
        }
    }
}
