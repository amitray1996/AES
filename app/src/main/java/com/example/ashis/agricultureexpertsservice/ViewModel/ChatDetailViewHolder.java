package com.example.ashis.agricultureexpertsservice.ViewModel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ashis.agricultureexpertsservice.R;
import com.mikhaellopez.circularimageview.CircularImageView;

public class ChatDetailViewHolder extends RecyclerView.ViewHolder{

    public CircularImageView profile;
    public TextView sender;
    public TextView receiver;
    public LinearLayout senderContainer, receiverContainer;
    public ImageView personalProfile;
    public TextView sender_time;
    public TextView receiver_time;
    public ImageView receiveImage;
    public ImageView sendImage;

    public ChatDetailViewHolder(@NonNull View itemView) {
        super(itemView);

        profile = itemView.findViewById(R.id.personal_profile);
        sender = itemView.findViewById(R.id.sender);
        receiver = itemView.findViewById(R.id.receiver);
        senderContainer = itemView.findViewById(R.id.sender_message);
        receiverContainer = itemView.findViewById(R.id.receiver_chat);
        sender_time = itemView.findViewById(R.id.sender_time);
        receiver_time = itemView.findViewById(R.id.receiver_time);
        receiveImage = itemView.findViewById(R.id.receive_image);
        sendImage = itemView.findViewById(R.id.send_image);

    }
}
