package com.example.ashis.agricultureexpertsservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ashis.agricultureexpertsservice.View.AddProductActivity;
import com.example.ashis.agricultureexpertsservice.View.HelpFragment;
import com.example.ashis.agricultureexpertsservice.View.HistoryFragment;
import com.example.ashis.agricultureexpertsservice.View.HomeFragment;
import com.example.ashis.agricultureexpertsservice.View.LoginInActivity;
import com.example.ashis.agricultureexpertsservice.View.ProfileFragment;
import com.example.ashis.agricultureexpertsservice.View.SettingFragment;
import com.example.ashis.agricultureexpertsservice.utils.SharedPrefUtils;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        TextView name = headerView.findViewById(R.id.full_name);
        TextView email = headerView.findViewById(R.id.email);
        ImageView profileImage = headerView.findViewById(R.id.profile_image);

        name.setText(SharedPrefUtils.getStringPreference(getApplicationContext(), "fullName"));
        email.setText(SharedPrefUtils.getStringPreference(getApplicationContext(), "email"));
        Glide.with(getApplicationContext()).load(SharedPrefUtils.getStringPreference(getApplicationContext(), "imageUrl")).into(profileImage);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.dashboard_container, new HomeFragment(), "home").commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(DashboardActivity.this, AddProductActivity.class);

                startActivity(intent);

            }
        });

    }

    public void showDrawerButton(String title) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        } else if (id == android.R.id.home) {
//
//
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {

            case R.id.nav_home:
                showDrawerButton("Home");
                addFragment(new HomeFragment(), "Home");
                break;
            case R.id.nav_history:
                showBackButton("History");
                addFragment(new HistoryFragment(), "History");
                break;
            case R.id.nav_profile:
                showBackButton("Profile");
                addFragment(new ProfileFragment(), "Profile");
                break;
            case R.id.nav_setting:
                showBackButton("Setting");
                addFragment(new SettingFragment(), "Setting");
                break;
            case R.id.nav_logout:
                SharedPrefUtils.clearPref(getApplicationContext());
                Intent intent = new Intent(DashboardActivity.this, LoginInActivity.class);
                startActivity(intent);
                finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
