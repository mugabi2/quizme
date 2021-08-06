package com.example.quizme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class format1adapter extends RecyclerView.Adapter<format1adapter.format1viewHolder>{
    private Context context;
    private List<format1quiz>format1quizList;
    StorageReference storageRef;;
    onClickInterfaceFormat1 onClickInterfaceFormat1;

    public format1adapter(Context context, List<format1quiz> format1quizList, onClickInterfaceFormat1 onClickInterfaceFormat1) {
        this.context = context;
        this.format1quizList = format1quizList;
        this.onClickInterfaceFormat1=onClickInterfaceFormat1;
    }

    @NonNull
    @NotNull
    @Override
    public format1viewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.format1,null);
        return new format1viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull format1viewHolder holder, int position) {
        format1quiz format1quiz=format1quizList.get(position);
        holder.tvTitle.setText(format1quiz.getTitle());
        holder.tvPrice.setText(format1quiz.getPrice());
        holder.tvInstructor.setText(format1quiz.getInstructor());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInterfaceFormat1.setClick(position);
                Toast.makeText(context.getApplicationContext(), String.valueOf(position),Toast.LENGTH_LONG).show();
                Log.i( "onClick: ", String.valueOf(position));

                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new
        fragmentDetails(format1quiz.getImagequiz(),format1quiz.getImageinstructor(),format1quiz.getTitle(),format1quiz.getQnnumber(),format1quiz.getInstructor(),format1quiz.getPrice(),format1quiz.getLevel(),format1quiz.getDescription(),format1quiz.getRating(),format1quiz.quizCode))
                        .addToBackStack(null).commit();

            }
        });
///////////////////////////////////////////
        storageRef = FirebaseStorage.getInstance().getReference().child(format1quiz.getImagequiz());
        try {
            final File localfile=File.createTempFile("two","jpg");
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return format1quizList.size();
    }

    class format1viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvTitle,tvPrice,tvInstructor;
        LinearLayout linearLayout;
        public format1viewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.f1image);
            tvTitle=itemView.findViewById(R.id.f1title);
            tvPrice=itemView.findViewById(R.id.f1price);
            tvInstructor=itemView.findViewById(R.id.f1instructor);
            linearLayout=itemView.findViewById(R.id.linearformat1);

        }
    }
}
