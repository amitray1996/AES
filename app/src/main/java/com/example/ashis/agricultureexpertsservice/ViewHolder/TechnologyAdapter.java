package com.example.ashis.agricultureexpertsservice.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.Model.Technologies;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.View.TechnologyDetailActivity;
import com.example.ashis.agricultureexpertsservice.ViewModel.TechnologiesViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TechnologyAdapter extends RecyclerView.Adapter<TechnologiesViewHolder> {

    Context context;
    List<Technologies> technologiesList = new ArrayList<>();
    Activity activity;

    public TechnologyAdapter(Context context, List<Technologies> technologiesList, Activity activity) {
        this.context = context;
        this.technologiesList = technologiesList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TechnologiesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.raw_technology_layout,null,false);
        return new TechnologiesViewHolder(view,activity);

    }

    @Override
    public void onBindViewHolder(@NonNull TechnologiesViewHolder technologiesViewHolder, int i) {

        final Technologies technologies = technologiesList.get(i);

        technologiesViewHolder.title.setText(technologies.getTitle());
        technologiesViewHolder.description.setText(technologies.getDescription());
        Glide.with(context).load(technologies.getImageUrl()).into(technologiesViewHolder.technologyImage);


        technologiesViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(new Intent(activity, TechnologyDetailActivity.class).putExtra("data",technologies));
            }
        });
    }

    @Override
    public int getItemCount() {
        return technologiesList.size();
    }
}
