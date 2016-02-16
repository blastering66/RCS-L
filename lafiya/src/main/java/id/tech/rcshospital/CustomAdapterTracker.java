package id.tech.rcshospital;

import java.util.ArrayList;
import java.util.List;

import id.tech.util.Font;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapterTracker extends ArrayAdapter<String>{
	Context myContext;
	ArrayList<String> data;
	LayoutInflater mInflater;

	public CustomAdapterTracker(Context context, int resource,
			int textViewResourceId, ArrayList<String> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		myContext = context;
		data = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		mInflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TextView rootView = (TextView) mInflater.inflate(R.layout.list_item_tracker, null);
		
		rootView.setText(data.get(position));
		rootView.setTypeface(Font.arialFont(myContext));
		
		return rootView;
	}

}
