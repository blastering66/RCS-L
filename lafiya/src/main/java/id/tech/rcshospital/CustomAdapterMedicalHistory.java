package id.tech.rcshospital;

import java.util.ArrayList;
import java.util.List;

import id.tech.util.Font;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapterMedicalHistory extends ArrayAdapter<RowDataMedicalHistory>{
	public LayoutInflater mInflater;
	public Context mContext;
	public List<RowDataMedicalHistory> data;
	int mLastPosition = -1;

	public CustomAdapterMedicalHistory(Context ctx, List<RowDataMedicalHistory> objects, LayoutInflater inflater){
		super(ctx, 0, objects);
		mContext = ctx;
		mInflater = inflater;
		data = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		TextView t_Dokter=null;
		TextView t_Bagian=null;
		TextView t_Diagnosa=null;
		TextView t_Tgl=null;
		View view_ALL, divider=null;
		
		final RowDataMedicalHistory row = getItem(position);
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.list_item_medical, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder)convertView.getTag();
		
		view_ALL = holder.getAllView();
		t_Dokter = holder.getDokter();
		t_Bagian = holder.getBagian();
		t_Diagnosa = holder.getDiagnosa();
		t_Tgl = holder.getTgl();
		divider = (View)convertView.findViewById(R.id.garis_divider);
		
		t_Dokter.setText(row.mNamaDok);
		t_Bagian.setText(row.mBagian);
		t_Diagnosa.setText(row.mDiagnosa);
		t_Tgl.setText(row.mTgl);
		
		t_Dokter.setTypeface(Font.arialFont(mContext));
		t_Bagian.setTypeface(Font.arialFont(mContext));
		t_Diagnosa.setTypeface(Font.arialFont(mContext));
		t_Tgl.setTypeface(Font.arialFont(mContext));
		
		if(position == 0){
			view_ALL.setBackgroundResource(R.drawable.shape_rounded_top);
		}else if(position == data.size()-1){
			view_ALL.setBackgroundResource(R.drawable.shape_rounded_bot);
			divider.setVisibility(View.GONE);
		}
		
		view_ALL.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent loadDetail = new Intent(mContext, DetailMedicalHistoryActivity.class);
				loadDetail.putExtra("nama_dokter", row.mNamaDok);
				loadDetail.putExtra("diagnosa", row.mDiagnosa);
				loadDetail.putExtra("penanganan", row.mPenanganan);
				loadDetail.putExtra("tgl", row.mTgl);				
				Activity activity = (Activity)mContext;
				activity.startActivity(loadDetail);
				activity.overridePendingTransition(R.anim.anim_right_to_left, R.anim.anim_diam);
			}
		});
		
		
		
		return convertView;
	}
	
	private class ViewHolder {
		private final View mRow;
		private View view_all = null;
		private TextView tv_dokter = null;
		private TextView tv_bagian = null;
		private TextView tv_diagnosa = null;
		private TextView tv_tgl = null;
		
		public ViewHolder(final View row){
			mRow = row;
		}
		
		public View getAllView(){
			if(view_all == null){
				view_all = (View)mRow.findViewById(R.id.view_all);
			}
			
			return view_all;
		}
		
		public TextView getDokter(){
			if(tv_dokter == null){
				tv_dokter = (TextView)mRow.findViewById(R.id.tv_namadokter);
			}
			
			return tv_dokter;
		}
		
		public TextView getBagian(){
			if(tv_bagian == null){
				tv_bagian = (TextView)mRow.findViewById(R.id.tv_bagian);
			}
			return tv_bagian;
		}
		
		public TextView getDiagnosa(){
			if(tv_diagnosa == null){
				tv_diagnosa = (TextView)mRow.findViewById(R.id.tv_descDiagnosa);
			}
			return tv_diagnosa;
		}
		
		public TextView getTgl(){
			if(tv_tgl == null){
				tv_tgl=(TextView)mRow.findViewById(R.id.tv_diagnosa_tgl);
			}
			return tv_tgl;
		}
		
	}
	

}
