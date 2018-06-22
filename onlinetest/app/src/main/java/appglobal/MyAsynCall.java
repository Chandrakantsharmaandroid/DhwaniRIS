package appglobal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by ck on 6/3/17.
 */

public class MyAsynCall extends AsyncTask<Void, Void, String> {
    public AsyncResponse delegate = null;
    ProgressDialog progressDialog;
    Activity activity;
    String URI;
    List<NameValuePair> pairs;
    String flag;
    boolean isProgress=true;
    String token;

    public MyAsynCall(Activity activity, String URI, List<NameValuePair> pairs, String flag) {
        this.activity = activity;
        this.URI = URI;
        this.pairs = pairs;
        this.flag = flag;

    }

    public MyAsynCall(Activity activity, String URI, List<NameValuePair> pairs, String flag, boolean isProgress ) {
        this.activity = activity;
        this.URI = URI;
        this.pairs = pairs;
        this.flag = flag;
        this.isProgress=isProgress;

    }

    public MyAsynCall(Activity activity, String URI, List<NameValuePair> pairs, String flag, String token ) {
        this.activity = activity;
        this.URI = URI;
        this.pairs = pairs;
        this.flag = flag;
        this.token=token;

    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        if (isProgress) {
            progressDialog = ProgressDialog.show(activity, "Please wait", "while we are processing...");

        } else {
            System.out.println("No Progress bar Oprn");
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        // TODO Auto-generated method stub
        String responce = getData();
        return responce;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result, flag);
        if (isProgress) {
            progressDialog.dismiss();
        } else{
            System.out.println("No Progress bar Close");
        }

    }

    public String getData() {

        String setServerString="";
        try {

            ServiceCall mServiceCall = new ServiceCall(activity, "");

            setServerString = mServiceCall.getResponseFromServerPost(URI,pairs,token);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Fail", "Error send message " + e.getMessage());
            return "fail";
        }
        return setServerString;
    }
}
