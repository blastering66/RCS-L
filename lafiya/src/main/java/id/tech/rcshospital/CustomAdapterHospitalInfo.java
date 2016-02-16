package id.tech.rcshospital;

import java.util.List;

import id.tech.util.Font;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CustomAdapterHospitalInfo extends ArrayAdapter<RowDataHospitalInfo>{
	Context mContext;
	List<RowDataHospitalInfo> data_row;
	LayoutInflater mInflater;
	int idPage;
	int mLastPosition = -1;

	public CustomAdapterHospitalInfo(Context context, int resource,
			int textViewResourceId, List<RowDataHospitalInfo> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		mContext = context;
		data_row = objects;
		idPage = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView tv_namaHospital = null;
		Button btn = null;
		final RowDataHospitalInfo row = data_row.get(position);
		
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rootView = mInflater.inflate(R.layout.list_item_info, null);
		tv_namaHospital = (TextView)rootView.findViewById(R.id.txt_nama_rs);
		tv_namaHospital.setText(row.mNamaCabang);
		tv_namaHospital.setTypeface(Font.arialFont(mContext));
		
		btn = (Button)rootView.findViewById(R.id.btn_map);
		if(idPage ==1){
			btn.setBackgroundResource(R.drawable.img_call_us);
		}
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(idPage==1){
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + row.mTlpCS));
				mContext.startActivity(intent);
					
				}else{
					final String latitude = row.mLat;
					final String longitude = row.mLong;

					String url = "http://maps.google.com/maps/api/staticmap?center="
							+ latitude
							+ ","
							+ longitude
							+ "&zoom=15&size=300x200&markers=color:blue|size:mid|"
							+ latitude + "," + longitude + "&sensor=false";

					Intent intent = new Intent(Intent.ACTION_VIEW,
							Uri.parse("geo:0,0?q=" + latitude + "," + longitude
									+ " (" + "NFL" + ")"));
					mContext.startActivity(intent);
				}
				
			}
		});
		
//		TranslateAnimation ani = null;
//		if(position != mLastPosition){
//			ani = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
//					0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
//					Animation.RELATIVE_TO_SELF, 1.0f,
//					Animation.RELATIVE_TO_SELF, 0.0f);
//			
//			ani.setDuration(600);
//			mLastPosition = position;
//		}
//		
//		try{
//			rootView.startAnimation(ani);
//		}catch(NullPointerException e){
////			Log.e("Error animasi null = ", e.getMessage().toString());
//		}catch (Exception e) {
//			// TODO: handle exception
////			Log.e("Error unknown = ", e.getMessage().toString());
//		}
		return rootView;
		
	}
	

}
