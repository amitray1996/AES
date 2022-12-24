package com.example.ashis.agricultureexpertsservice.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ashis.agricultureexpertsservice.DashboardActivity;
import com.example.ashis.agricultureexpertsservice.R;

public class HomeFragment extends Fragment {

    LinearLayout weather;
    LinearLayout technologies;
    LinearLayout chat;
    LinearLayout shopping;


    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        weather = (LinearLayout) view.findViewById(R.id.weather_layout);
        technologies = (LinearLayout) view.findViewById(R.id.technology_layout);
        chat = (LinearLayout) view.findViewById(R.id.chat_layout);
        shopping = (LinearLayout) view.findViewById(R.id.shopping_layout);

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addFragment(new WeatherFragment(),"Weather");
            }
        });

        technologies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addFragment(new TechnologiesFragment(),"Technology");
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              addFragment(new ChatFragment(),"Chat With Experts");

            }
        });

        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // addFragment(new BuyFragment(),"Shopping");

                startActivity(new Intent(getActivity(), ShopActivity.class));
            }
        });

        return view;

    }

    public void addFragment(Fragment fragment, String title ){

        if(getActivity() instanceof DashboardActivity){


            ((DashboardActivity) getActivity()).getSupportActionBar().setTitle(title);

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.dashboard_container, fragment, title).commit();
        }
    }
}
