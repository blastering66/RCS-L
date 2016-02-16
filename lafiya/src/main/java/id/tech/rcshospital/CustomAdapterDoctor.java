package id.tech.rcshospital;

import java.util.List;

import id.tech.util.Font;
import id.tech.util.JadwalDoctorStatic;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterDoctor extends ArrayAdapter<RowDataDoctor> {
	Context mContext;
	LayoutInflater mInflater;
	List<RowDataDoctor> data_row;
	List<RowDataDoctorJadwal> data_row_Jadwal;
	int mLastPosition = -1;
	
	public CustomAdapterDoctor(Context context, int resource,
			int textViewResourceId, List<RowDataDoctor> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		mContext = context;
		data_row = objects;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rootView = mInflater.inflate(R.layout.list_item_dokter, null);
		
		final RowDataDoctor row = data_row.get(position);
//		final RowDataDoctor row_jadwal = data_row_Jadwal.get(position);
		final List<RowDataDoctorJadwal> obj_Jadwal = data_row.get(position).mDataJadwal;
		
		TextView tv_namaDokter = (TextView)rootView.findViewById(R.id.txt_nama_dokter);
		TextView tv_spesialisasiDokter = (TextView)rootView.findViewById(R.id.txt_spesialisasi_dokter);
		ImageView img_doctor = (ImageView)rootView.findViewById(R.id.img_doktor);
		
		tv_namaDokter.setText(row.mNamaDoc);
		tv_spesialisasiDokter.setText(row.mSpesialisasi);
		
		tv_namaDokter.setTypeface(Font.arialFont(mContext));
		tv_spesialisasiDokter.setTypeface(Font.arialFont(mContext));
		
		if(row.mUrlGambar.contains("png") || row.mUrlGambar.contains("jpg") ){
			Picasso.with(mContext).load(row.mUrlGambar).into(img_doctor);
		}else{
			img_doctor.setImageResource(R.drawable.img_dokter);
			
		}
		
		
		rootView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent loadAppointment = new Intent(mContext,
						DetailDoctorActivity.class);
				loadAppointment.putExtra("id_dokter", row.mIdDoc);
				loadAppointment.putExtra("nama_dokter", row.mNamaDoc);
				loadAppointment.putExtra("img_dokter", row.mUrlGambar);
				JadwalDoctorStatic.data = obj_Jadwal;				
				
//				mContext.startActivity(loadAppointment);
				Activity activity = (Activity)mContext;
				activity.startActivity(loadAppointment);
				activity.overridePendingTransition(R.anim.anim_right_to_left, R.anim.anim_diam);
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
//			Log.e("Error animasi null = ", e.getMessage().toString());
//		}catch (Exception e) {
//			// TODO: handle exception
//			Log.e("Error unknown = ", e.getMessage().toString());
//		}
		
		return rootView;
	}
	

}
