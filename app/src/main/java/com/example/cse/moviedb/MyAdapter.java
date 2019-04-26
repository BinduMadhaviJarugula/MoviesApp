package com.example.cse.moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.SubAdapter> {

    Context context;
    ArrayList<MyModel> arrayList;

    public MyAdapter(MainActivity mainActivity, ArrayList<MyModel> arrayList) {
        this.context=mainActivity;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public MyAdapter.SubAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.rowdesign,parent,false);
        return new SubAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.SubAdapter holder, final int position) {
        MyModel model=arrayList.get(position);
        Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+model.posterpath).placeholder(R.drawable.movie).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] str=new String[7];
                str[0]=arrayList.get(position).getTitle();
                str[1]=arrayList.get(position).getPosterpath();
                str[2]=arrayList.get(position).getBackdroppath();
                str[3]=arrayList.get(position).getOverview();
                str[4]=arrayList.get(position).getReleasedate();
                str[5]=arrayList.get(position).getRating();
                str[6]=arrayList.get(position).getId();

                Intent intent=new Intent(context,MovieActivity.class);
                intent.putExtra("movie",str);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return (arrayList==null) ? 0 : arrayList.size();
    }

    public class SubAdapter extends RecyclerView.ViewHolder {
        ImageView imageView;
        public SubAdapter(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image1);
        }
    }
}
