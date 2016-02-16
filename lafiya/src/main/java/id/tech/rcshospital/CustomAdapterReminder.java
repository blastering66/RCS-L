package id.tech.rcshospital;

import java.util.List;

import id.tech.util.Font;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterReminder extends ArrayAdapter<RowDataReminder>{
	Context mContext;
	List<RowDataReminder> data_row;
	LayoutInflater mInflater;
	int idPage;
	int mLastPosition = -1;
	
	public CustomAdapterReminder(Context context, int resource,
			int textViewResourceId, List<RowDataReminder> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		mContext = context;
		data_row = objects;
		idPage = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView txt_judul = null;
		ImageView img_reminder=null;
		
		final RowDataReminder row = data_row.get(position);
		
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rootView = mInflater.inflate(R.layout.list_reminder, null);
		
		txt_judul = (TextView)rootView.findViewById(R.id.txt_desc_reminder);
		img_reminder = (ImageView)rootView.findViewById(R.id.img_reminder);
		
		txt_judul.setText(row.mPesan);
		txt_judul.setTypeface(Font.arialFont(mContext));
		if(row.mNamaObat.equals("Obat") || row.mNamaObat.equals("Vaksin")){
			img_reminder.setImageResource(R.drawable.img_reminder_obat);
		}else if(row.mNamaObat.equals("Dokter")){
			img_reminder.setImageResource(R.drawable.img_reminder_appoinment);
		}else if(row.mNamaObat.equals("Checkup") ){
			img_reminder.setImageResource(R.drawable.img_reminder_appoinment);
		}
		return rootView;
	}

}
