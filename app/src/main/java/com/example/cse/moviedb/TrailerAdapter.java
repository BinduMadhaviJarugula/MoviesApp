package com.example.cse.moviedb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.SubClass> {
    Context context;
    ArrayList<TrailerModel> arraytrailer;

    public TrailerAdapter(MovieActivity movieActivity, ArrayList<TrailerModel> arrayTrailer) {
        this.context=movieActivity;
        this.arraytrailer=arrayTrailer;
    }

    @NonNull
    @Override
    public TrailerAdapter.SubClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.trailer,parent,false);
        return new SubClass(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.SubClass holder, int position) {
        final TrailerModel trailerModel=arraytrailer.get(position);
        holder.textView.setText(trailerModel.getMname());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=trailerModel.getMkey();
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                intent.setPackage("com.google.android.youtube");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arraytrailer.size();
    }

    public class SubClass extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public SubClass(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.timage);
            textView=itemView.findViewById(R.id.ttitle);
        }
    }
}
