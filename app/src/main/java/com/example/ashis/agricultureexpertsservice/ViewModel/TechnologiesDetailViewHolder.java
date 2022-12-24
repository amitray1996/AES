package com.example.ashis.agricultureexpertsservice.ViewModel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ashis.agricultureexpertsservice.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class TechnologiesDetailViewHolder extends RecyclerView.ViewHolder {

    public CircularImageView profileImage;
    public TextView profileName;
    public TextView comments;

    public TechnologiesDetailViewHolder(@NonNull View itemView) {
        super(itemView);

        profileImage = itemView.findViewById(R.id.profile);
        profileName = itemView.findViewById(R.id.profile_name);
        comments = itemView.findViewById(R.id.comments);

    }
}
