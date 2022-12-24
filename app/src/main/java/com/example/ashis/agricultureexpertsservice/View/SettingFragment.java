package com.example.ashis.agricultureexpertsservice.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ashis.agricultureexpertsservice.DashboardActivity;
import com.example.ashis.agricultureexpertsservice.R;


public class SettingFragment extends Fragment {

    LinearLayout profile;
    LinearLayout help;
    LinearLayout notifications;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        profile = view.findViewById(R.id.profile);
        help = view.findViewById(R.id.help);
        notifications = view.findViewById(R.id.notification);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getActivity() instanceof  DashboardActivity){
                    ((DashboardActivity) getActivity()).addFragment(new ProfileFragment(),"Profile");
                }

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent intent = new Intent(getActivity(), HelpFragment.class);
                //startActivity(intent);

                if(getActivity() instanceof DashboardActivity){

                    ((DashboardActivity) getActivity()).addFragment(new HelpFragment(), "Help");

                }
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });



        return view;
    }


}
