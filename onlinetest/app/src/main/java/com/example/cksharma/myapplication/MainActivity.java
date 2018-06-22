package com.example.cksharma.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

import adapter.CustomHomeScreenListAdapterView;
import adapter.StateListAdapter;
import appconstent.ConstantClass;
import appglobal.AsyncResponse;
import appglobal.MyAsynCall;
import appglobal.ServiceCall;
import appglobal.URIClass;
import beanclass.HomeItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridView gridViewemp;
    ArrayList<HomeItem> gridArray = new ArrayList<HomeItem>();
    CustomHomeScreenListAdapterView homeAdapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gridViewemp = (GridView) findViewById(R.id.gridView);

        gridArray=ConstantClass.getDashboardData(MainActivity.this);

        homeAdapterList = new CustomHomeScreenListAdapterView(MainActivity.this, R.layout.mycustomhomelistviewlight, gridArray);
        gridViewemp.setAdapter(homeAdapterList);


        gridViewemp.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                HomeItem item;
                item = gridArray.get(arg2);

                homeItemSelected(item.getItemId(), item.getTitle());
            }

        });

    }

    public void homeItemSelected(String val, String title) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        System.out.println(pref.getString("token",null));
        switch (ConstantClass.Homeitems.valueOf(val)) {
            case get_state: {
                Intent intent = new Intent(MainActivity.this, AllStateActivity.class);
                startActivity(intent);
                //MainActivity.this.finish();
                break;
            }
            case get_district: {
                Intent intent = new Intent(MainActivity.this, CreateDistrictActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            MainActivity.this.finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    }
