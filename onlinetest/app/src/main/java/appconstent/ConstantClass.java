package appconstent;

import android.app.Activity;

import com.example.cksharma.myapplication.R;

import java.util.ArrayList;

import beanclass.HomeItem;

/**
 * Created by CK Sharma on 5/9/2018.
 */

public class ConstantClass {

    public static final String COMMAN_URI="https://stgnandghar.dhwaniris.in/index.php/api";

    public static ArrayList<HomeItem> gridArray;

    public static String UN="username";
    public static String PWD="password";
    public static String ID="state_id";
    public static String NAME="district_name";




    public static ArrayList<HomeItem> getDashboardData(Activity activity) {

        gridArray = new ArrayList<HomeItem>();

        gridArray.add(new HomeItem(R.drawable.about_icon, "State", "get_state", 0));
        gridArray.add(new HomeItem(R.drawable.about_icon, "District", "get_district", 0));
        return gridArray;
    }

    public static enum Homeitems {
        get_state, get_district
    }

}
