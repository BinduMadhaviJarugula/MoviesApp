package com.example.cse.moviedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyAdapter> {
    ArrayList<ModelClass> arrayList;
    Context context;

    public ReviewAdapter(MovieActivity movieActivity, ArrayList<ModelClass> arrayList) {
        this.context=movieActivity;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ReviewAdapter.MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.review,parent,false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyAdapter holder, int position) {
        ModelClass modelClass=arrayList.get(position);
        holder.review.setText(modelClass.getReviews());

    }

    @Override
    public int getItemCount() {
        return (arrayList==null)? 0 :arrayList.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        TextView review;
        public MyAdapter(View itemView) {
            super(itemView);
            review=itemView.findViewById(R.id.reviews);
        }
    }
}
