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
import com.example.ashis.agricultureexpertsservice.Model.Technologies;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewHolder.TechnologyAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.ashis.agricultureexpertsservice.utils.Config.GET_INSERTED_INFO;


public class TechnologiesFragment extends Fragment {


    RecyclerView recyclerView;
    List<Technologies> technologiesList = new ArrayList<>();
    TechnologyAdapter technologyAdapter;

    ProgressDialog progressDialog;

    public TechnologiesFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_technologies, container, false);

        recyclerView = view.findViewById(R.id.technology_recycler_view);

        technologyAdapter = new TechnologyAdapter(getActivity(),technologiesList,getActivity());

        recyclerView.setAdapter(technologyAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;

    }

    private void getInsertedInfo(){

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading post");
        progressDialog.show();


        technologiesList.clear();

        String Url = GET_INSERTED_INFO + "/.json";

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("Json Request",response.toString());

                progressDialog.dismiss();

                Iterator<String> keys = response.keys();

                while (keys.hasNext()){

                    String key = keys.next();

                    Log.i("single key",key);

                    try {

                        if(response.get(key) instanceof JSONObject){

                            JSONObject resultObj = response.getJSONObject(key);

                            String desc  = resultObj.getString("description");
                            String imageUrl  = resultObj.getString("imageUrl");
                            String title  = resultObj.getString("title");

                            int likes = 0;

                            if(resultObj.has("likes")){

                                likes = resultObj.getInt("likes");
                            }



                            Log.i("img url",imageUrl);

                            Technologies technologies = new Technologies();
                            technologies.setId(key);
                            technologies.setTitle(title);
                            technologies.setDescription(desc);
                            technologies.setImageUrl(imageUrl);
                            technologies.setLikes(likes);

                            Log.i("img url in ab",technologies.getImageUrl());

                            technologiesList.add(technologies);
                            technologyAdapter.notifyDataSetChanged();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error in loading data", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Error in loading data", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(objectRequest);

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();

        getInsertedInfo();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
