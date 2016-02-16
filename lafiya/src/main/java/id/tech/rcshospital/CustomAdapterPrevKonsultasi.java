package id.tech.rcshospital;

import java.util.List;

import id.tech.util.Font;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapterPrevKonsultasi extends ArrayAdapter<RowDataMedicalHistory>{
	public LayoutInflater mInflater;
	public Context mContext;
	int mLastPosition = -1;

	public CustomAdapterPrevKonsultasi(Context ctx, List<RowDataMedicalHistory> objects, LayoutInflater inflater){
		super(ctx, 0, objects);
		mContext = ctx;
		mInflater = inflater;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		TextView t_Dokter=null;
		TextView t_Keluhan=null;
		TextView t_Diagnosa=null;
		TextView t_Tgl=null;
		View view_ALL=null;
		
		final RowDataMedicalHistory row = getItem(position);
		
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.list_prev_konsultasi, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		
		holder = (ViewHolder)convertView.getTag();
		
		view_ALL = holder.getAllView();
		t_Dokter = holder.getDokter();
		t_Keluhan = holder.getKeluhan();		
		t_Tgl = holder.getTgl();
		
		t_Dokter.setText(row.mNamaDok);
		t_Keluhan.setText(row.mBagian);		
		t_Tgl.setText(row.mTgl);
		
		t_Dokter.setTypeface(Font.arialFont(mContext));
		t_Keluhan.setTypeface(Font.arialFont(mContext));		
		t_Tgl.setTypeface(Font.arialFont(mContext));
		
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
//			convertView.startAnimation(ani);
//		}catch(NullPointerException e){
////			Log.e("Error animasi null = ", e.getMessage().toString());
//		}catch (Exception e) {
//			// TODO: handle exception
////			Log.e("Error unknown = ", e.getMessage().toString());
//		}
		
		
		return convertView;
	}
	
	private class ViewHolder {
		private final View mRow;
		private View view_all = null;
		private TextView tv_dokter = null;
		private TextView tv_keluhan = null;
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
				tv_dokter = (TextView)mRow.findViewById(R.id.tv_list_namadokter);
			}
			
			return tv_dokter;
		}
		
		public TextView getKeluhan(){
			if(tv_keluhan == null){
				tv_keluhan = (TextView)mRow.findViewById(R.id.tv_list_keluhan);
			}
			return tv_keluhan;
		}
		
		
		public TextView getTgl(){
			if(tv_tgl == null){
				tv_tgl=(TextView)mRow.findViewById(R.id.tv_list_tgldokter);
			}
			return tv_tgl;
		}
		
	}
}
