package com.example.ashis.agricultureexpertsservice.View;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.Model.Reviews;
import com.example.ashis.agricultureexpertsservice.Model.Technologies;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewHolder.TechnologiesDetailAdapter;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TechnologyDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Reviews> reviewsArrayList = new ArrayList<>();

    TextView reviewCount;
    ImageView send;
    EditText comments;
    ImageView backgroundImage;
    TextView title;
    TextView description;
    Technologies technologies;
    ImageView likes;

    public static String GET_UPDATE_INFO = "https://collegeproject-a59bc-default-rtdb.firebaseio.com/post/";

    TechnologiesDetailAdapter technologiesDetailAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technology_detail);

        reviewCount = findViewById(R.id.review_count);
        send = findViewById(R.id.imageView2);
        comments = findViewById(R.id.comment);
        backgroundImage = findViewById(R.id.webb_farm);
        title = findViewById(R.id.title);
        description = findViewById(R.id.descriptions);
        likes = findViewById(R.id.likes);

        technologies = (Technologies) getIntent().getSerializableExtra("data");

        title.setText(technologies.getTitle());
        description.setText(technologies.getDescription());
        reviewCount.setText(String.valueOf(technologies.getLikes()));
        Glide.with(getApplicationContext()).load(technologies.getImageUrl()).into(backgroundImage);

        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLiked();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveReview();
            }
        });

        recyclerView = findViewById(R.id.technologies_detail_recycler_view);


        getData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                break;

        }

        return false;
    }

    public void getData(){

        final ProgressDialog progressDialog = new ProgressDialog(TechnologyDetailActivity.this);
        progressDialog.setMessage("Loading Review");
        progressDialog.show();

        String Url = GET_UPDATE_INFO+technologies.getId()+"/reviews/.json";

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        reviewsArrayList.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                Iterator<String> keys = response.keys();

                try{

                    while (keys.hasNext()){

                        String key = keys.next();
                        try {

                            if(response.get(key) instanceof JSONObject){

                                JSONObject resultObj = response.getJSONObject(key);

                                String comment  = resultObj.getString("comment");
                                String name  = resultObj.getString("uploadBy");
                                String imageUrl  = resultObj.getString("uploadByImageUrl");

                                Reviews review = new Reviews(key, comment, name, imageUrl);

                                Log.i("reviews",review.toString());

                                reviewsArrayList.add(review);


                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                            Toast.makeText(TechnologyDetailActivity.this, "Error in loading data", Toast.LENGTH_SHORT).show();
                        }

                    }


                }catch (Exception ex){

                }finally {

                    technologiesDetailAdapter = new TechnologiesDetailAdapter(getApplicationContext(),reviewsArrayList);

                    recyclerView.setAdapter(technologiesDetailAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(TechnologyDetailActivity.this, "Error in loading data", Toast.LENGTH_SHORT).show();



            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private  void setLiked(){

        String Url = GET_UPDATE_INFO+technologies.getId()+".json";

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JSONObject params = new JSONObject();

        try{

            params.put("likes",technologies.getLikes()+1);

        }catch (Exception ex){

        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PATCH, Url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("Json Request Response", response.toString());

                try {

                    reviewCount.setText(String.valueOf(technologies.getLikes()+1));


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(objectRequest);
    }

    private  void saveReview(){

        final ProgressDialog progressDialog = new ProgressDialog(TechnologyDetailActivity.this);
        progressDialog.setMessage("Posting review");
        progressDialog.dismiss();

        String Url = GET_UPDATE_INFO+technologies.getId()+"/reviews/.json";

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        JSONObject params = new JSONObject();

        try{

            params.put("comment",comments.getText().toString().trim());
            params.put("uploadBy", SharedPrefUtils.getStringPreference(getApplicationContext(),"fullName"));
            params.put("uploadByImageUrl", SharedPrefUtils.getStringPreference(getApplicationContext(),"imageUrl"));

        }catch (Exception ex){

        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, Url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                progressDialog.dismiss();
                Toast.makeText(TechnologyDetailActivity.this, "Successfully post review", Toast.LENGTH_SHORT).show();

                try {


                    getData();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(TechnologyDetailActivity.this, "Error in posting review", Toast.LENGTH_SHORT).show();



            }
        });

        requestQueue.add(objectRequest);
    }


}
