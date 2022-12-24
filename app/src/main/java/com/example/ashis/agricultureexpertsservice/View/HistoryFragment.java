package com.example.ashis.agricultureexpertsservice.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ashis.agricultureexpertsservice.Model.History;
import com.example.ashis.agricultureexpertsservice.Model.Order;
import com.example.ashis.agricultureexpertsservice.Model.Shop;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewHolder.HistoryAdapter;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.ashis.agricultureexpertsservice.utils.Config.GET_ORDER_HISTROY;
import static com.example.ashis.agricultureexpertsservice.utils.Config.GET_UPLOADED_INFO;

public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;
    private List<Order> orderList = new ArrayList<>();

    ProgressDialog progressDialog;

    HistoryAdapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOrderHistory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.history_recycler_view);

        adapter = new HistoryAdapter(getContext(), orderList);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void getOrderHistory() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading....");
        progressDialog.show();


        String url = GET_ORDER_HISTROY + "/.json";
        final RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                Log.i("json Request", response.toString());

                Iterator<String> keys = response.keys();

                try {

                    while (keys.hasNext()) {

                        String key = keys.next();

                        try {

                            if (response.get(key) instanceof JSONObject) {

                                JSONObject object = response.getJSONObject(key);

                                String orderDate = object.getString("orderDate");
                                String totalQuantity = object.getString("quantity");
                                String paymentType = object.getString("payment_type");
                                String contactName = object.getString("contact_name");
                                String contactNumber = object.getString("contact_number");
                                String deliveryAddress = object.getString("delivery_address");
                                String productImageUrl = object.getString("product_image_url");
                                String productName = object.getString("product_name");
                                String totalAmount = object.getString("total_amount");

                                String productOf = object.getString("product_of");
                                String orderBy = object.getString("order_by");
                                String orderByName = object.getString("order_by_name");

                                String currentUserId = SharedPrefUtils.getStringPreference(requireContext(), "localId");

                                if (currentUserId.equals(productOf) || currentUserId.equals(orderBy)) {

                                    String name = orderByName;
                                    if(currentUserId.equals(orderBy)){
                                        name = "Order by you";
                                    }else{
                                        name = "Order by "+orderByName;
                                    }

                                    Order order = new Order(key, productName, productImageUrl, orderDate, totalAmount, totalQuantity, name, orderBy, contactName, contactNumber);
                                    orderList.add(order);
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception ex) {

                } finally {
                    progressDialog.dismiss();
                    adapter.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(requireContext(), "Error. Try again!", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(objectRequest);
    }
}
