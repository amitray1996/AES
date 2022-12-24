package com.example.ashis.agricultureexpertsservice.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.Model.Reviews;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewModel.TechnologiesDetailViewHolder;

import java.util.ArrayList;
import java.util.List;


public class TechnologiesDetailAdapter extends RecyclerView.Adapter<TechnologiesDetailViewHolder> {

    Context context;
    private List<Reviews> reviewsArrayList = new ArrayList<>();

    public TechnologiesDetailAdapter(Context context, List<Reviews> reviewsArrayList) {
        this.context = context;
        this.reviewsArrayList = reviewsArrayList;
    }

    @NonNull
    @Override
    public TechnologiesDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.raw_technologies_detail_layout,null,false);
        return new TechnologiesDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TechnologiesDetailViewHolder holder, int i) {

        Reviews reviews = reviewsArrayList.get(i);

        Log.i("reviews",reviews.toString());

        Glide.with(context).load(reviews.getUploadByImageUrl()).into(holder.profileImage);
        holder.comments.setText(reviews.getComment());
        holder.profileName.setText(reviews.getUploadBy());

    }

    @Override
    public int getItemCount() {
        return reviewsArrayList.size();
    }
}
