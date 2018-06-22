package appconstent;

import android.content.Context;
import android.net.ConnectivityManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import beanclass.AllStateBean;

/**
 * Created by CK Sharma on 5/9/2018.
 */

public class UtilityClass {

    public static boolean _f_chk_internet_conn(Context c) {
        final ConnectivityManager conMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected())
            return true;
        else
            //System.out.println("Internet Connection Not Present");
            return false;
    }

    public static List<AllStateBean> getAllState(JSONArray jsonArray){
        List<AllStateBean> allStateBeans=null;

        try {
            if (jsonArray != null && jsonArray.length() > 0) {
                allStateBeans = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    AllStateBean bean = new AllStateBean();
                    bean.setId(jsonArray.optJSONObject(i).optString("id"));
                    bean.setName(jsonArray.optJSONObject(i).optString("state_name"));

                    allStateBeans.add(bean);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return allStateBeans;
    }

}
