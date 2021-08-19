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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class formatCategAdapter extends RecyclerView.Adapter<formatCategAdapter.formatCategviewHolder>{
    private Context context;
    private List<formatCateg>formatCategList;
    StorageReference storageRef;;
    onClickInterfaceFormat1 onClickInterfaceFormat1;

    public formatCategAdapter(Context context, List<formatCateg> formatCategList, onClickInterfaceFormat1 onClickInterfaceFormat1) {
        this.context = context;
        this.formatCategList = formatCategList;
        this.onClickInterfaceFormat1=onClickInterfaceFormat1;
    }

    @NonNull
    @NotNull
    @Override
    public formatCategviewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.formatcateg,null);
        return new formatCategviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull formatCategviewHolder holder, int position) {
        formatCateg formatCateg=formatCategList.get(position);
        holder.tvTitle.setText(formatCateg.getCategorytitle());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInterfaceFormat1.setClick(position);
                Toast.makeText(context.getApplicationContext(), formatCateg.getCategorytitle(),Toast.LENGTH_LONG).show();
                Log.i( "onClick: ", String.valueOf(position));

                AppCompatActivity activity=(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new
                        fragmentSearch(formatCateg.getCategorytitle()))
                        .addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return formatCategList.size();
    }

    class formatCategviewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        MaterialCardView linearLayout;
        public formatCategviewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.categtitle);
            linearLayout=itemView.findViewById(R.id.cardcateg);

        }
    }
}
