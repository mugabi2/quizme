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

public class formatSummaryAdapter extends RecyclerView.Adapter<formatSummaryAdapter.formatSummaryHolder>{
    private Context context;
    private List<formatSummary>formatSummaryList;
    int prog;


    public formatSummaryAdapter(Context context, List<formatSummary> formatSummaryList) {
        this.context = context;
        this.formatSummaryList = formatSummaryList;
    }

    @NonNull
    @NotNull
    @Override
    public formatSummaryHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.formatsummary,null);
        return new formatSummaryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull formatSummaryHolder holder, int position) {
        formatSummary formatSummary=formatSummaryList.get(position);
        holder.tvQn.setText(formatSummary.getQuestion());
        holder.tvNmb.setText(formatSummary.getNumber());
        holder.tvAns.setText(formatSummary.getAnswer());

    }

    @Override
    public int getItemCount() {
        return formatSummaryList.size();
    }

    class formatSummaryHolder extends RecyclerView.ViewHolder {
        TextView tvQn,tvNmb,tvAns;
        public formatSummaryHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvQn=itemView.findViewById(R.id.sumquestion);
            tvAns=itemView.findViewById(R.id.sumanswer);
            tvNmb=itemView.findViewById(R.id.sumnumber);
        }
    }
}
