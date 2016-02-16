package id.tech.rcshospital;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import id.tech.rcshospital.R;
import id.tech.util.Font;
import id.tech.util.ParameterCollection;
import id.tech.util.PublicFunction;
import id.tech.util.ServiceHandlerJSON;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DirectionActivity extends Activity {
	ImageView imgIklan;
	int flag;
	Runnable r;

	public Integer[] mImageIklan = { R.drawable.iklan1, R.drawable.iklan2,
			R.drawable.iklan3, R.drawable.iklan4, R.drawable.iklan5 };

	int indexIklan;
	List<RowDataHospitalInfo> data_row;
	CustomAdapterHospitalInfo adapter;
	ListView ls;
	Context mContext;
	GetHospitalInfo myTask;
	TextView txt_judul;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_direction);
		mContext = this;		
		ls = (ListView)findViewById(R.id.ls_direction);
		txt_judul = (TextView)findViewById(R.id.judul_app);
		txt_judul.setTypeface(Font.arialFont(mContext));
		
		imgIklan = (ImageView) findViewById(R.id.img_iklan);
		flag = 0;
		r = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				imgIklan.setBackgroundResource(mImageIklan[flag]);
				indexIklan = flag;
				flag++;
				if (flag >= mImageIklan.length) {
					flag = 0;
				}
				imgIklan.postDelayed(r, 3000);
			}

		};
		imgIklan.postDelayed(r, 100);
		imgIklan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PublicFunction.BukaBanner(DirectionActivity.this, indexIklan);
			}
		});

		if (PublicFunction.isInternetConnection(mContext)) {
			myTask = new GetHospitalInfo();
			myTask.execute();
		} else {
			Dialog dialog = new Dialog(mContext);
			dialog.setTitle(R.string.toast_noconn);
			dialog.show();
		}
		
		
	}
	
	private class GetHospitalInfo extends AsyncTask<Void, Void, Void>{
		private String aaa, namaCabang, latitude, longitude, noTlpCS, noTlpUGD;;
		private int berhasil;
		Dialog dialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new Dialog(DirectionActivity.this);
			dialog.setTitle(R.string.toast_proses);
			dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					myTask.cancel(true);
				}
			});
			dialog.show();
			dialog.setCancelable(true);
			data_row = new ArrayList<RowDataHospitalInfo>();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> p = new ArrayList<NameValuePair>();
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			
//			p.add(new BasicNameValuePair("idC", "1"));			
			aaa = sh.getInfoRS(p);
			
			try{
				JSONObject jObj = new JSONObject(aaa);
				
				int sof = jObj.getInt(ParameterCollection.TAG_SOF);
				if(sof ==1){
					berhasil = sof;
					JSONArray jArray = jObj.getJSONArray(ParameterCollection.TAG_CABANG);
					
					for(int i=0;i < jArray.length(); i++){
						JSONObject c = jArray.getJSONObject(i);
						
						namaCabang = c.getString(ParameterCollection.TAG_CABANG_NAMA);
						latitude = c.getString(ParameterCollection.TAG_CABANG_LAT);
						longitude = c.getString(ParameterCollection.TAG_CABANG_LONG);
						noTlpCS = c.getString(ParameterCollection.TAG_CABANG_TLPCS);
						noTlpUGD = c.getString(ParameterCollection.TAG_CABANG_TLPUGD);
						
						data_row.add(new RowDataHospitalInfo("", namaCabang, "", noTlpCS, noTlpUGD, latitude, longitude));
					}
				}
				
				
			}catch(JSONException e){
				
			}
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if(berhasil == 1){
				adapter = new CustomAdapterHospitalInfo(mContext, 0, 0, data_row);			
				adapter.notifyDataSetChanged();			
				ls.setAdapter(adapter);
			}else{
				Toast.makeText(mContext, R.string.toast_error, Toast.LENGTH_LONG).show();
			}
			
			
		}
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.anim_diam, R.anim.slide_out_to_bottom );
	}

}
