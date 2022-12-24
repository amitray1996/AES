package com.example.ashis.agricultureexpertsservice.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.Model.ChatDetail;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewModel.ChatDetailViewHolder;

import java.util.List;

public class ChatDetailAdapter extends RecyclerView.Adapter<ChatDetailViewHolder> {

    Context context;
    List<ChatDetail> chatDetailList;

    public ChatDetailAdapter(Context context, List<ChatDetail> chatDetailList) {
        this.context = context;
        this.chatDetailList = chatDetailList;
    }

    @NonNull
    @Override
    public ChatDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.personal_row_chat_layout,null,false);
        return new ChatDetailViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ChatDetailViewHolder chatDetailViewHolder, int i) {

        ChatDetail chatDetail = chatDetailList.get(i);

        Log.i("message",chatDetail.toString());

        if (chatDetail.getMessage_type().contentEquals("sent")){

            chatDetailViewHolder.senderContainer.setVisibility(View.VISIBLE);
            chatDetailViewHolder.receiverContainer.setVisibility(View.GONE);
            chatDetailViewHolder.sender.setText(chatDetail.getMessage());
            chatDetailViewHolder.receiver_time.setText(chatDetail.getChattime());

            if(chatDetail.getMessage().isEmpty()){
                chatDetailViewHolder.sender.setVisibility(View.GONE);
            }

            if(chatDetail.getMessageImageUrl().isEmpty()){

                chatDetailViewHolder.sendImage.setVisibility(View.GONE);

            }else{

                Glide.with(context).load(chatDetail.getMessageImageUrl()).into(chatDetailViewHolder.sendImage);
            }


        }else{

            chatDetailViewHolder.senderContainer.setVisibility(View.GONE);
            chatDetailViewHolder.receiverContainer.setVisibility(View.VISIBLE);
            Glide.with(context).load(chatDetail.getSender_image_URL()).into(chatDetailViewHolder.profile);
            chatDetailViewHolder.sender_time.setText(chatDetail.getChattime());

            if(chatDetail.getMessage().isEmpty()){

                chatDetailViewHolder.receiver.setVisibility(View.GONE);

            }else{

                chatDetailViewHolder.receiver.setVisibility(View.VISIBLE);
                chatDetailViewHolder.receiver.setText(chatDetail.getMessage());

            }

            if(chatDetail.getMessageImageUrl().isEmpty()){

                chatDetailViewHolder.receiveImage.setVisibility(View.GONE);

            }else{

                chatDetailViewHolder.receiveImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(chatDetail.getMessageImageUrl()).into(chatDetailViewHolder.receiveImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return chatDetailList.size();
    }

    @Override
    public void onViewAttachedToWindow(final ChatDetailViewHolder holder) {
        if (holder instanceof ChatDetailViewHolder) {
            holder.setIsRecyclable(false);
        }
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(final ChatDetailViewHolder holder) {
        if (holder instanceof ChatDetailViewHolder){
            holder.setIsRecyclable(false);
        }
        super.onViewDetachedFromWindow(holder);
    }
}
