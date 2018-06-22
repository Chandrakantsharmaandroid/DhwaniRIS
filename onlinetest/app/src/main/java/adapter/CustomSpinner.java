package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cksharma.myapplication.R;

import java.util.List;

import beanclass.AllStateBean;
import beanclass.UserType;

public class CustomSpinner extends BaseAdapter {

	LayoutInflater layoutInflater;

	Activity context;
	List<AllStateBean> data;
	public CustomSpinner(Activity context, List<AllStateBean> data) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.data=data;
		layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//		return super.getDropDownView(position, convertView, parent);
		return getView(position, convertView, parent);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View row=layoutInflater.inflate(R.layout.custom_spinner_row, parent, false);
		TextView label=(TextView) row.findViewById(R.id.label);

		String value=data.get(position).getName();
		label.setText(value);
		return row;
		//		return super.getView(position, convertView, parent);
	}






}
