package com.example.ashis.agricultureexpertsservice.ViewModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashis.agricultureexpertsservice.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ChatViewHolder extends RecyclerView.ViewHolder{

    TextView request;
    public CircularImageView profile;
    public TextView profileName;
    public TextView summary;
    public TextView time;
    CircularImageView expertProfile;
    TextView sender;
    TextView receiver;
    ImageView collections;
    ImageView attachFile;
    EditText responses;
    ImageView sends;
    Context context;

    public ChatViewHolder(@NonNull View view, final Context context) {
        super(view);

        request = (TextView) view.findViewById(R.id.request);
        profile = (CircularImageView) view.findViewById(R.id.profile);
        profileName = (TextView) view.findViewById(R.id.profile_name);
        summary = (TextView) view.findViewById(R.id.message_summary);
        time = (TextView) view.findViewById(R.id.time);
        expertProfile = (CircularImageView) view.findViewById(R.id.personal_profile);
        sender = (TextView) view.findViewById(R.id.sender);
        receiver = (TextView) view.findViewById(R.id.receiver);
        collections = (ImageView) view.findViewById(R.id.collections);
        attachFile = (ImageView) view.findViewById(R.id.attach_files);

    }
}
