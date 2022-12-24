package com.example.ashis.agricultureexpertsservice.ViewModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ashis.agricultureexpertsservice.Model.Shop;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;

import static android.view.View.GONE;

public class ShopViewHolder extends RecyclerView.ViewHolder{

    public ImageView image;
    public TextView title;
    public TextView rate;
    public Button buy;
    Context mContext;

    public ShopViewHolder(@NonNull View itemView, final Context context) {
        super(itemView);
        mContext = context;
        image = itemView.findViewById(R.id.productImage);
        title = itemView.findViewById(R.id.title);
        rate = itemView.findViewById(R.id.rate);
        buy = itemView.findViewById(R.id.buy);
    }

    public void bindData(Shop shop){
        String userId = SharedPrefUtils.getStringPreference(mContext, "localId");
        if(userId.equals(shop.getUploadBy())){
            buy.setVisibility(GONE);
        }
    }
}
