package com.example.ashis.agricultureexpertsservice.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ashis.agricultureexpertsservice.Model.Shop;
import com.example.ashis.agricultureexpertsservice.R;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.ashis.agricultureexpertsservice.utils.Config.GET_UPLOADED_INFO;

public class ShopActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    List<Shop> vegetableList = new ArrayList<>();
    List<Shop> fruitsList = new ArrayList<>();
    List<Shop> herbsList = new ArrayList<>();
    ProgressDialog progressDialog;
    TabLayout tabLayout;

    String Url = "https://collegeproject-a59bc-default-rtdb.firebaseio.com/product/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getSupportActionBar().setTitle("Shop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this,AddProductActivity.class);
                startActivity(intent);
            }
        });

        getUploadData();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);


    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (position){


                case 0:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("vegProduct", (Serializable) vegetableList);
                    fragment = new VegetableFragment();
                    fragment.setArguments(bundle);
                    break;

                case 1:
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("fruitsProduct", (Serializable) fruitsList);
                    fragment = new FruitsFragment();
                    fragment.setArguments(bundle1);
                    break;

                case 2:
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("herbsProduct", (Serializable) herbsList);
                    fragment = new HerbsFragment();
                    fragment.setArguments(bundle2);
                    break;
            }

            return  fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            String title = "";

            switch (position){

                case 0:
                    title = "Vegetables";
                    break;

                case 1:
                    title = "Fruits";
                    break;

                case 2:
                    title = "Herbs";
                    break;
            }

            return title;
        }
    }

    private void getUploadData(){

        progressDialog = new ProgressDialog(ShopActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();


        String url = GET_UPLOADED_INFO + "/.json";
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                Log.i("json Request", response.toString());

                Iterator<String> keys = response.keys();

                try{

                    while (keys.hasNext()) {

                        String key = keys.next();
                        Log.i("Single Keys", key);

                        try {

                            if (response.get(key) instanceof JSONObject) {


                                JSONObject object = response.getJSONObject(key);

                                String des = object.getString("description");
                                String pricePerUnit = object.getString("pricePerUnit");
                                String title = object.getString("title");
                                String totalQuantity = object.getString("totalQuantity");
                                String type = object.getString("type");
                                String imageUrl = object.getString("imageUrl");
                                String uploadBy = object.getString("uploadBy");

                                Shop shop = new Shop();
                                shop.setDescription(des);
                                shop.setPricePerUnit(pricePerUnit);
                                shop.setTitle(title);
                                shop.setTotalQuantity(totalQuantity);
                                shop.setType(type);
                                shop.setImageUrl(imageUrl);
                                shop.setUploadBy(uploadBy);

                                if (type.equalsIgnoreCase("vegetables")) {
                                    vegetableList.add(shop);

                                } else if (type.equalsIgnoreCase("fruits")) {

                                    fruitsList.add(shop);

                                } else if (type.equalsIgnoreCase("herbs")) {

                                    herbsList.add(shop);
                                }

                            }

                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    }

                }catch (Exception ex){

                }finally {

                    mViewPager.setAdapter(mSectionsPagerAdapter);
                    tabLayout.setupWithViewPager(mViewPager);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(objectRequest);

    }
}
