package com.example.ashis.agricultureexpertsservice.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashis.agricultureexpertsservice.Model.History;
import com.example.ashis.agricultureexpertsservice.Model.Order;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewModel.HistoryViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {

    Context context;
    private List<Order> orderList = new ArrayList<>();

    public HistoryAdapter(Context context, List<Order> historyList) {
        this.context = context;
        this.orderList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_raw_layout, null, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        if (historyViewHolder instanceof HistoryViewHolder) {
            historyViewHolder.bindData(orderList.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
