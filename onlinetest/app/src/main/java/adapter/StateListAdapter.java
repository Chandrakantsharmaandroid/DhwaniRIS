package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cksharma.myapplication.R;

import java.util.List;

import beanclass.AllStateBean;

public class StateListAdapter extends BaseAdapter {
    Bitmap bm, bm1;
    ImageView imageView1;
    private int selectedPos = -1, cnt = 0;
    Activity useactivity;
    LayoutInflater inlator;
    Context c;
    List<AllStateBean> stateList;

    public StateListAdapter(Activity activity, List<AllStateBean> stateList) {
        this.useactivity = activity;
        this.stateList=stateList;
        inlator = (LayoutInflater) useactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return stateList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setSelectedPos(int pos) {
        selectedPos = pos;
        notifyDataSetChanged();
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public static class ViewHolder {
        Layout layout;
        TextView textviewName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewholder;
        if (convertView == null) {
            viewholder = new ViewHolder();
            convertView = inlator.inflate(R.layout.mycustom_list_state,
                    null);
            // layout=findViewById(R.id.layoutList);
            viewholder.textviewName = (TextView) convertView
                    .findViewById(R.id.name);

            convertView.setTag(viewholder);

        } else
            viewholder = (ViewHolder) convertView.getTag();

        viewholder.textviewName.setText(stateList.get(position).getName());

        return convertView;
    }
}
