package com.quizinfinity.quizme;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class formatWishAdapter extends RecyclerView.Adapter<formatWishAdapter.formatWishviewHolder>{
    private Context context;
    private List<formatWish>formatWishList;
    StorageReference storageRef;;
    onClickInterfaceFormat1 onClickInterfaceFormat1;

    public formatWishAdapter(Context context, List<formatWish> formatWishList, onClickInterfaceFormat1 onClickInterfaceFormat1) {
        this.context = context;
        this.formatWishList = formatWishList;
        this.onClickInterfaceFormat1=onClickInterfaceFormat1;
    }

    @NonNull
    @NotNull
    @Override
    public formatWishviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.formatwish,null);
        return new formatWishviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull formatWishviewHolder holder, int position) {
        formatWish formatWish=formatWishList.get(position);
        holder.tvTitle.setText(formatWish.getTitle());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInterfaceFormat1.setClick(position);
                Toast.makeText(context.getApplicationContext(), String.valueOf(position),Toast.LENGTH_LONG).show();
                Log.i( "onClick: ", String.valueOf(position));


            }
        });
///////////////////////////////////////////
        try {
            storageRef = FirebaseStorage.getInstance().getReference().child(formatWish.getImagequiz());
            final File localfile=File.createTempFile("five","jpg");
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
        return formatWishList.size();
    }

    class formatWishviewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvTitle,tvPrice,tvInstructor;
        RelativeLayout linearLayout;
        public formatWishviewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.fwimage);
            tvTitle=itemView.findViewById(R.id.fwtitle);
            linearLayout=itemView.findViewById(R.id.linearwish);

        }
    }
}
