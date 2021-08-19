package com.quizinfinity.quizme;

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

public class formatSearchAdapter extends RecyclerView.Adapter<formatSearchAdapter.formatSearchHolder>{
    private Context context;
    private List<formatSearch>formatSearchList;
    StorageReference storageRef;;
    onClickInterfaceFormat1 onClickInterfaceFormat1;
    int prog;


    public formatSearchAdapter(Context context, List<formatSearch> formatSearchList, onClickInterfaceFormat1 onClickInterfaceFormat1) {
        this.context = context;
        this.formatSearchList = formatSearchList;
        this.onClickInterfaceFormat1=onClickInterfaceFormat1;
    }

    @NonNull
    @NotNull
    @Override
    public formatSearchHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.formatsearch,null);
        return new formatSearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull formatSearchHolder holder, int position) {
        formatSearch formatSearch=formatSearchList.get(position);
        holder.tvQns.setText(formatSearch.getQnnumber());
        holder.tvTitle.setText(formatSearch.getTitle());
        holder.tvPrice.setText(formatSearch.getPrice());
        holder.tvInstructor.setText(formatSearch.getInstructor());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInterfaceFormat1.setClick(position);
//                Toast.makeText(context.getApplicationContext(), String.valueOf(position),Toast.LENGTH_LONG).show();
//                Log.i( "onClick: ", String.valueOf(position));

                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new
                        fragmentDetails(formatSearch.getImagequiz(),formatSearch.getImageinstructor(),formatSearch.getTitle(),formatSearch.getQnnumber(),formatSearch.getInstructor(),formatSearch.getPrice(),formatSearch.getLevel(),formatSearch.getDescription(),formatSearch.getRating(),formatSearch.getQuizCode(),formatSearch.getStudents()))
                        .addToBackStack(null).commit();

            }
        });
///////////////////////////////////////////
        try {
            storageRef = FirebaseStorage.getInstance().getReference().child(formatSearch.getImagequiz());
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
        return formatSearchList.size();
    }

    class formatSearchHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvTitle,tvInstructor,tvQns,tvPrice;
        LinearLayout linearLayout;
        public formatSearchHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.fsimage);
            tvTitle=itemView.findViewById(R.id.fstitle);
            tvQns=itemView.findViewById(R.id.fsqns);
            tvInstructor=itemView.findViewById(R.id.fsinstruct);
            tvPrice=itemView.findViewById(R.id.fsprice);
            linearLayout=itemView.findViewById(R.id.linearsearch);
        }
    }
}
