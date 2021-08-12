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

public class formatCartAdapter extends RecyclerView.Adapter<formatCartAdapter.formatCartHolder>{
    private Context context;
    private List<formatCart>formatCartList;
    StorageReference storageRef;;
    onClickInterfaceFormat1 onClickInterfaceFormat1;
    int prog;


    public formatCartAdapter(Context context, List<formatCart> formatCartList, onClickInterfaceFormat1 onClickInterfaceFormat1) {
        this.context = context;
        this.formatCartList = formatCartList;
        this.onClickInterfaceFormat1=onClickInterfaceFormat1;
    }

    @NonNull
    @NotNull
    @Override
    public formatCartHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.formatcart,null);
        return new formatCartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull formatCartHolder holder, int position) {
        formatCart formatCart=formatCartList.get(position);
        holder.tvQns.setText(formatCart.getQnnumber());
        holder.tvTitle.setText(formatCart.getTitle());
        holder.tvPrice.setText(formatCart.getPrice());
        holder.tvInstructor.setText(formatCart.getInstructor());
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
            storageRef = FirebaseStorage.getInstance().getReference().child(formatCart.getImagequiz());
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
        return formatCartList.size();
    }

    class formatCartHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvTitle,tvInstructor,tvQns,tvPrice;
        LinearLayout linearLayout;
        public formatCartHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.fcimage);
            tvTitle=itemView.findViewById(R.id.fctitle);
            tvQns=itemView.findViewById(R.id.fcqns);
            tvInstructor=itemView.findViewById(R.id.fcinstructor);
            tvPrice=itemView.findViewById(R.id.fcprice);
            linearLayout=itemView.findViewById(R.id.linearcart);
        }
    }
}
