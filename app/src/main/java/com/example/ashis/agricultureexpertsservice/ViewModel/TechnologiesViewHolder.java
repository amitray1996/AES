package com.example.ashis.agricultureexpertsservice.ViewModel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashis.agricultureexpertsservice.R;


public class TechnologiesViewHolder  extends RecyclerView.ViewHolder{

    public ImageView technologyImage;
    public TextView title;
    public TextView description;


    public TechnologiesViewHolder(@NonNull final View itemView, final Activity activity) {
        super(itemView);

        technologyImage = itemView.findViewById(R.id.webb_farm);
        title = itemView.findViewById(R.id.tech_title);
        description = itemView.findViewById(R.id.tech_description);
    }
}
