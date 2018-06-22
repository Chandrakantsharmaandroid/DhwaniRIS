package com.example.cksharma.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import adapter.CustomSpinner;
import adapter.StateListAdapter;
import appconstent.ConstantClass;
import appconstent.UtilityClass;
import appglobal.AsyncResponse;
import appglobal.MyAsynCall;
import appglobal.ServiceCall;
import appglobal.URIClass;
import beanclass.AllStateBean;

public class CreateDistrictActivity extends AppCompatActivity implements AsyncResponse {

    String state_id;
    Button save;
    EditText district_name;
    Spinner stateSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_district);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        if(UtilityClass._f_chk_internet_conn(CreateDistrictActivity.this)){
            sendServerRequestForGetState(pref.getString("token",""));

        }else {
            Toast.makeText(CreateDistrictActivity.this, "Internet/wi-fi not available",
                    Toast.LENGTH_SHORT).show();
        }
        save=(Button)findViewById(R.id.btnSend) ;
        district_name=(EditText)findViewById(R.id.districtEditText);
        stateSpinner = (Spinner) findViewById(R.id.spinner);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String name = district_name.getText().toString();

                if(UtilityClass._f_chk_internet_conn(CreateDistrictActivity.this))
                {
                    sendServerRequestForPostDistrict(pref.getString("token",""),state_id, name);
                }

                else{
                    Toast.makeText(CreateDistrictActivity.this, "Internet/wi-fi not available",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void sendServerRequestForGetState(String token) {

        try {
            ServiceCall mServiceCall = new ServiceCall(CreateDistrictActivity.this, "");
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();

            MyAsynCall asyncTask = new MyAsynCall(CreateDistrictActivity.this, URIClass.GET_STATE, pairs, "get_state",token);
            asyncTask.delegate = CreateDistrictActivity.this;
            asyncTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Fail", "Error send Request To Server " + e.getMessage());
        }

    }

    public void sendServerRequestForPostDistrict(String token, String state_id,String name) {

        try {
            ServiceCall mServiceCall = new ServiceCall(CreateDistrictActivity.this, "");
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair(ConstantClass.ID, state_id));
            pairs.add(new BasicNameValuePair(ConstantClass.NAME, name));

            MyAsynCall asyncTask = new MyAsynCall(CreateDistrictActivity.this, URIClass.POST_DISTRICT, pairs, "get_state",token);
            asyncTask.delegate = CreateDistrictActivity.this;
            asyncTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Fail", "Error send Request To Server " + e.getMessage());
        }

    }

    @Override
    public void processFinish(String output, String flag) {
        try {
            if(flag.equalsIgnoreCase("get_state")) {
                JSONObject jsonObject = new JSONObject(output);
                if (jsonObject.optBoolean("success")) {
                    Toast.makeText(CreateDistrictActivity.this, jsonObject.optString("message"),
                            Toast.LENGTH_SHORT).show();
                    final List<AllStateBean> allStateBeans = UtilityClass.getAllState(jsonObject.optJSONArray("state"));

                    CustomSpinner listAdapter = new CustomSpinner(CreateDistrictActivity.this, allStateBeans);
                    stateSpinner.setAdapter(listAdapter);

                    stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(
                                AdapterView<?> adapterView, View view,
                                int i, long l) {
               AllStateBean bean = allStateBeans.get(i);
                state_id = bean.getId();
                System.out.println("student_id======" + state_id);
                        }

                        public void onNothingSelected(
                                AdapterView<?> adapterView) {

                        }
                    });
                } else {
                    Toast.makeText(CreateDistrictActivity.this, jsonObject.optString("message"),
                            Toast.LENGTH_SHORT).show();
                }
            }else{

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    }