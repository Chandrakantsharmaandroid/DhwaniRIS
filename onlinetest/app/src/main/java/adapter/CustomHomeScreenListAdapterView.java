package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cksharma.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import beanclass.HomeItem;

/**
 * @author manish.s
 */
public class CustomHomeScreenListAdapterView extends ArrayAdapter<HomeItem> {
    Activity context;
    int layoutResourceId;
    String colorcodeArr[]= {"#52B4AA","#3294B8","#968FB4","#D29BBA","#D17881","#E49C63","#52B4AA","#3294B8","#968FB4","#D29BBA","#D17881","#E49C63","#52B4AA","#3294B8","#968FB4","#D29BBA","#D17881","#E49C63"};
    ArrayList<HomeItem> data = new ArrayList<HomeItem>();

    Context c;

    public CustomHomeScreenListAdapterView(Activity context, int layoutResourceId,
                                           ArrayList<HomeItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;


        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.text1);
            holder.imageItem = (ImageView) row.findViewById(R.id.img1);
            holder.imgLayout = (RelativeLayout) row.findViewById(R.id.relativeLayout3);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        HomeItem item = data.get(position);

        if (item == null) {
            holder.txtTitle.setText("");
            holder.imageItem.setBackgroundResource(0);

        } else {

                holder.imgLayout.setBackgroundColor(Color.parseColor(colorcodeArr[position]));

            holder.txtTitle.setText(item.getTitle());
            holder.imageItem.setBackgroundResource(item.getImage());

        }
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;
        RelativeLayout imgLayout;

    }

}