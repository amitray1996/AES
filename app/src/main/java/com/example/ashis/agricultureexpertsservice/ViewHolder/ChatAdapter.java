package com.example.ashis.agricultureexpertsservice.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.Model.Chat;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.View.ChatDetailActivity;
import com.example.ashis.agricultureexpertsservice.ViewModel.ChatViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter  extends RecyclerView.Adapter<ChatViewHolder>{

    Context context;
    List<Chat> chatList = new ArrayList<>();

    public ChatAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.chat_row_layout,null,false);
        return new ChatViewHolder(view,context);

    }
    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {

        final Chat chat = chatList.get(i);

        Glide.with(context).load(chat.getMessage_by_image()).into(chatViewHolder.profile);
        chatViewHolder.profileName.setText(chat.getMessage_by());
        chatViewHolder.summary.setText(chat.getLast_message());
        chatViewHolder.time.setText(chat.getLast_message_time());

        chatViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, ChatDetailActivity.class).putExtra("userId",chat.getChatId()));
            }
        });

    }

    @Override
    public int getItemCount() {
         return chatList.size();
    }


}
