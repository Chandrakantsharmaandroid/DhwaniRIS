package com.example.cksharma.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.StateListAdapter;
import appconstent.UtilityClass;
import appglobal.AsyncResponse;
import appglobal.MyAsynCall;
import appglobal.ServiceCall;
import appglobal.URIClass;
import beanclass.AllStateBean;

public class AllStateActivity extends AppCompatActivity implements AsyncResponse {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_state);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       if(UtilityClass._f_chk_internet_conn(AllStateActivity.this)){

           SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
           sendServerRequestForGetState(pref.getString("token",""));

       }else {
           Toast.makeText(AllStateActivity.this, "Internet/wi-fi not available",
                   Toast.LENGTH_SHORT).show();
       }
    }

    public void sendServerRequestForGetState(String token) {

        try {
            ServiceCall mServiceCall = new ServiceCall(AllStateActivity.this, "");
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            MyAsynCall asyncTask = new MyAsynCall(AllStateActivity.this, URIClass.GET_STATE, pairs, "get_state",token);
            asyncTask.delegate = AllStateActivity.this;
            asyncTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Fail", "Error send Request To Server " + e.getMessage());
        }

    }

    @Override
    public void processFinish(String output, String flag) {
        ListView listView = (ListView)findViewById(R.id.listview);
        try {
            JSONObject jsonObject=new JSONObject(output);
            if(jsonObject.optBoolean("success")){
                Toast.makeText(AllStateActivity.this, jsonObject.optString("message"),
                        Toast.LENGTH_SHORT).show();
                List<AllStateBean> allStateBeans= UtilityClass.getAllState(jsonObject.optJSONArray("state"));

                StateListAdapter listAdapter=new StateListAdapter(AllStateActivity.this,allStateBeans);
                listView.setAdapter(listAdapter);

            }else {
                Toast.makeText(AllStateActivity.this, jsonObject.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
