package com.example.ashis.agricultureexpertsservice.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ashis.agricultureexpertsservice.Model.Shop;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewHolder.ShopAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends Fragment {

    RecyclerView recyclerView;
    private List<Shop> shopList = new ArrayList<>();



    public AllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.all_recycler_view);

        ShopAdapter shopAdapter = new ShopAdapter(getContext(), shopList,getActivity());

        recyclerView.setAdapter(shopAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return view;
    }

}
