package com.example.ashis.agricultureexpertsservice.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.Model.Shop;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.View.ShopDetailActivity;
import com.example.ashis.agricultureexpertsservice.ViewModel.ShopViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {

    Context context;
    List<Shop> vegList = new ArrayList<>();
    Activity activity;

    public ShopAdapter(Context context, List<Shop> vegList, Activity activity) {
        this.context = context;
        this.vegList = vegList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_layout_all, null, false);
        return new ShopViewHolder(view, context);

    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder shopViewHolder, int i) {

        final Shop shop = vegList.get(i);
        shopViewHolder.title.setText(shop.getTitle());

        Log.i("image url", shop.getImageUrl());

        Glide.with(shopViewHolder.itemView.getContext()).load(shop.getImageUrl()).into(shopViewHolder.image);
        shopViewHolder.rate.setText(shop.getPricePerUnit());

        shopViewHolder.bindData(shop);

        shopViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, ShopDetailActivity.class);
                intent.putExtra("shopData", shop);

                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return vegList.size();
    }
}
