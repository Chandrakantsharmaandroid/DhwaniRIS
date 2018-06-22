package com.example.cksharma.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import appconstent.ConstantClass;
import appconstent.UtilityClass;
import appglobal.AsyncResponse;
import appglobal.MyAsynCall;
import appglobal.ServiceCall;
import appglobal.URIClass;

public class LoginActivity extends Activity implements AsyncResponse{
    private static final String TAG = null;
    private EditText emailField, passwordField;
    Button loginButton;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailField.getText().toString();
                password = passwordField.getText().toString();

                if (validateForm(email, password)) {

                    if (UtilityClass._f_chk_internet_conn(LoginActivity.this)) {
                        sendServerRequestForLogin(email, password);
                    } else {
                        Toast.makeText(LoginActivity.this, "Internet/wi-fi not available",
                                Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }


    public void sendServerRequestForLogin(String user, String password) {

        try {
            ServiceCall mServiceCall = new ServiceCall(LoginActivity.this, "");
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair(ConstantClass.UN, user));
            pairs.add(new BasicNameValuePair(ConstantClass.PWD, password));

            MyAsynCall asyncTask = new MyAsynCall(LoginActivity.this, URIClass.USER_LOGIN, pairs, "login");
            asyncTask.delegate = LoginActivity.this;
            asyncTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Fail", "Error send Request To Server " + e.getMessage());
        }

    }

    @Override
    public void processFinish(String output, String flag) {

        try {
            JSONObject object=new JSONObject(output);
            if(object.optBoolean("success")){
                String token=object.optString("token");
                String login_id=object.optString("login_id");
                boolean response= object.optBoolean("success");

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("token", token);
                editor.putString("login_id", login_id);
                editor.putBoolean("success", response);
                editor.commit();

                Toast.makeText(LoginActivity.this, object.optString("message"),
                        Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                this.finish();
            }else{
                Toast.makeText(LoginActivity.this, object.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            emailField.setError("Please enter the User Name");
            valid = false;
        } else {
            emailField.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Please enter the password");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }

}


