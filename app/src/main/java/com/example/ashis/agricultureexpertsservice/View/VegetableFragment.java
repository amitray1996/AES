package com.example.ashis.agricultureexpertsservice.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashis.agricultureexpertsservice.Model.Shop;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewHolder.ShopAdapter;

import java.util.ArrayList;
import java.util.List;


public class VegetableFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Shop> vegList = new ArrayList<>();

    public VegetableFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){

            vegList = (List<Shop>) getArguments().getSerializable("vegProduct");

            Log.i("veg list",vegList.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vegetable, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.vegetable_recycler_view);

        ShopAdapter shopAdapter = new ShopAdapter(getContext(), vegList, getActivity());

        recyclerView.setAdapter(shopAdapter);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));


        return  view;

    }

}
