package com.example.ashis.agricultureexpertsservice.ViewModel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.Model.Order;
import com.example.ashis.agricultureexpertsservice.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    ImageView productImage;
    TextView productName;
    TextView totalQtyAmount;
    TextView orderDate;
    TextView orderBy;
    TextView contactName;
    TextView contactNumber;
    Context context;

    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        productImage = itemView.findViewById(R.id.product_image);
        productName = itemView.findViewById(R.id.title_history);
        totalQtyAmount = itemView.findViewById(R.id.sub_title);
        orderDate = itemView.findViewById(R.id.time_history);
        orderBy = itemView.findViewById(R.id.order_by);
        contactName = itemView.findViewById(R.id.contact_name);
        contactNumber = itemView.findViewById(R.id.contact_number);
    }

    public void bindData(Order order) {
        Glide.with(context).load(order.getProductImageUrl()).into(productImage);
        productName.setText(order.getProductName());
        totalQtyAmount.setText("Qty : " + order.getTotalQuantity() + "  Total Price : " + order.getTotalAmount());
        orderBy.setText(order.getOrderByName());
        contactName.setText(order.getContactName());
        contactNumber.setText(order.getContactNumber());

        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat toFormat = new SimpleDateFormat("MMM, dd yyyy");
        try {
            Date oDate = fromFormat.parse(order.getOrderDate());
            orderDate.setText(toFormat.format(oDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
