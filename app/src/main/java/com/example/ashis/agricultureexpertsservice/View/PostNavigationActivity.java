package com.example.ashis.agricultureexpertsservice.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.Model.Technologies;
import com.example.ashis.agricultureexpertsservice.R;
import com.example.ashis.agricultureexpertsservice.ViewHolder.TechnologyAdapter;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.example.ashis.agricultureexpertsservice.utils.Config.GET_INSERTED_INFO;

public class PostNavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    List<Technologies> technologiesList = new ArrayList<>();
    Context context;

    TechnologyAdapter technologyAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_navigation);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = getApplicationContext();

        getSupportActionBar().setTitle("Dashboard");

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        TextView name = headerView.findViewById(R.id.full_name);
        TextView email = headerView.findViewById(R.id.email);
        ImageView profileImage = headerView.findViewById(R.id.profile_image);

        name.setText(SharedPrefUtils.getStringPreference(getApplicationContext(), "fullName"));
        email.setText(SharedPrefUtils.getStringPreference(getApplicationContext(), "email"));

        Log.i("imageUrl", SharedPrefUtils.getStringPreference(context, "imageUrl"));

        Glide.with(getApplicationContext()).load(SharedPrefUtils.getStringPreference(getApplicationContext(), "imageUrl")).into(profileImage);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {

                    getSupportFragmentManager().popBackStack();

                    showDrawerButton("Dashboard");

                } else {

                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        recyclerView = findViewById(R.id.post_recycler_view);
        technologyAdapter = new TechnologyAdapter(getApplicationContext(), technologiesList, PostNavigationActivity.this);
        recyclerView.setAdapter(technologyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(PostNavigationActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


        getInsertedInfo();

    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {

            getSupportFragmentManager().popBackStack();

            showDrawerButton("Dashboard");

        } else {

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.post_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_logout) {

            Intent intent = new Intent(PostNavigationActivity.this, LoginInActivity.class);
            SharedPrefUtils.clearPref(context);
            startActivity(intent);

        } else if (id == R.id.nav_chat) {

            showBackButton("Chat");
            addFragment(new ChatFragment(), "Chat");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getInsertedInfo() {


        technologiesList.clear();

        String Url = GET_INSERTED_INFO + "/.json";

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i("Json Request", response.toString());

                Iterator<String> keys = response.keys();

                while (keys.hasNext()) {

                    String key = keys.next();

                    Log.i("single key", key);

                    try {

                        if (response.get(key) instanceof JSONObject) {

                            JSONObject resultObj = response.getJSONObject(key);

                            String desc = resultObj.getString("description");
                            String imageUrl = resultObj.getString("imageUrl");
                            String title = resultObj.getString("title");

                            Technologies technologies = new Technologies();
                            technologies.setTitle(title);
                            technologies.setId(key);
                            technologies.setDescription(desc);
                            technologies.setImageUrl(imageUrl);

                            technologiesList.add(technologies);
                            technologyAdapter.notifyDataSetChanged();


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(objectRequest);

    }

    public void showDrawerButton(String title) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_menu_arrow);

    }

    public void showBackButton(String title) {


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        getSupportActionBar().setTitle(title);

    }

    public void addFragment(Fragment fragment, String title) {

        getSupportActionBar().setTitle(title);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dashboard_container, fragment, title).addToBackStack(title).commit();
    }

}
