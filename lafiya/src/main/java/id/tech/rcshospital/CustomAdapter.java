package id.tech.rcshospital;

import java.util.List;

import id.tech.rcshospital.R;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<RowDataBerita> {
	public LayoutInflater mInflater;
	public Context mContext;
	int mLastPosition = -1;

	public CustomAdapter(Context ctx, List<RowDataBerita> objects,
			LayoutInflater inflater) {
		super(ctx, 0, objects);
		mInflater = inflater;
		mContext = ctx;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;

		TextView txt_judul = null;

		Button btn = null;
		TextView txt_link = null;

		final RowDataBerita row = getItem(position);

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item_artikel, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		txt_judul = holder.getJudul();
		txt_judul.setText(row.mJudul);
		txt_judul.setText(row.mJudul);
		txt_judul.setTypeface(Font.arialFont(mContext));
		
		txt_link = holder.getUrlBerita();
		txt_link.setText(row.mUrlBerita);
		txt_link.setTypeface(Font.arialFont(mContext));

		btn = holder.getButtonShare();
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(row.mUrlBerita));
				getContext().startActivity(i);
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

		private TextView tv_judul = null;

		private Button btn_share = null;
		private TextView tv_url = null;

		public ViewHolder(final View row) {
			mRow = row;
		}

		public TextView getJudul() {
			if (tv_judul == null) {
				tv_judul = (TextView) mRow.findViewById(R.id.item_judul);
			}

			return tv_judul;
		}

		public Button getButtonShare() {
			if (btn_share == null) {
				btn_share = (Button) mRow.findViewById(R.id.btn_share);
			}

			return btn_share;
		}

		public TextView getUrlBerita() {
			if (tv_url == null) {
				tv_url = (TextView) mRow.findViewById(R.id.tv_linkBerita);
			}

			return tv_url;
		}
	}

}
