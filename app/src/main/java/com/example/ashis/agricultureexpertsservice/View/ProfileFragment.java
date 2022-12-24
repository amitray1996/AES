package com.example.ashis.agricultureexpertsservice.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    Button edit;
    TextView fullName;
    TextView email;
    TextView number;
    TextView address;
    TextView occupation;

    String fname, eml, num, addres, occu;

    String Url = "https://collegeproject-a59bc-default-rtdb.firebaseio.com/user/";
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    CircularImageView profileImage;

    public ProfileFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        edit = view.findViewById(R.id.edit_button);
        fullName = view.findViewById(R.id.full_name);
        number = view.findViewById(R.id.number);
        occupation = view.findViewById(R.id.occupation);
        address = view.findViewById(R.id.address);
        profileImage = view.findViewById(R.id.profile_image);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCustomDialog();
            }
        });

        initView();

        return view;


    }

    private void initView(){


        fullName.setText(SharedPrefUtils.getStringPreference(getContext(),"fullName"));
        number.setText(SharedPrefUtils.getStringPreference(getContext(),"phoneNumber"));
        address.setText(SharedPrefUtils.getStringPreference(getContext(),"address"));
        occupation.setText(SharedPrefUtils.getStringPreference(getContext(),"occupation"));

        Glide.with(getContext()).load(SharedPrefUtils.getStringPreference(getContext(),"imageUrl")).into(profileImage);
    }


    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_profile_layout, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        final EditText fullName = dialogView.findViewById(R.id.full_name);
        fullName.setText(SharedPrefUtils.getStringPreference(getContext(),"fullName"));


        final EditText number = dialogView.findViewById(R.id.number);
        number.setText(SharedPrefUtils.getStringPreference(getContext(),"phoneNumber"));

        final EditText address = dialogView.findViewById(R.id.address);
        address.setText(SharedPrefUtils.getStringPreference(getContext(),"address"));

        final EditText occupation = dialogView.findViewById(R.id.occupation);
        occupation.setText(SharedPrefUtils.getStringPreference(getContext(),"occupation"));

        Button saveBtn = dialogView.findViewById(R.id.save);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fname = fullName.getText().toString().trim();
                num = number.getText().toString().trim();
                addres = address.getText().toString().trim();
                occu = occupation.getText().toString().trim();
                eml = SharedPrefUtils.getStringPreference(getContext(),"email");

                saveUserInfo();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();


    }

    private void saveUserInfo(){


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("saving");
        progressDialog.show();


        final JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("fullName",fname);
            jsonObject.put("phoneNumber",num);
            jsonObject.put("address",addres);
            jsonObject.put("occupation",occu);
            jsonObject.put("email", eml);

        }catch (Exception e){

        }

        String saveUserInfoUrl = Url + SharedPrefUtils.getStringPreference(getContext(),"localId") + "/.json";

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, saveUserInfoUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                Toast.makeText(getContext(), "User information Saved", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();

                SharedPrefUtils.setStringPreference(getContext(),"email",eml);
                SharedPrefUtils.setStringPreference(getContext(),"address",addres);
                SharedPrefUtils.setStringPreference(getContext(),"fullName",fname);
                SharedPrefUtils.setStringPreference(getContext(),"occupation",occu);
                SharedPrefUtils.setStringPreference(getContext(),"phoneNumber",num);

                initView();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getActivity(), "Error in updating user info", Toast.LENGTH_SHORT).show();
            }
        });

       requestQueue.add(jsonObjectRequest);



    }


}
