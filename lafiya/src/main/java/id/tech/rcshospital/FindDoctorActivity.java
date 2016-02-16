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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FindDoctorActivity extends Activity {

	ImageView imgIklan;
	int flag;
	Runnable r;

	public Integer[] mImageIklan = { R.drawable.iklan1, R.drawable.iklan2,
			R.drawable.iklan3, R.drawable.iklan4, R.drawable.iklan5 };
	int indexIklan;

	Context mContext;
	ListView ls;
	CustomAdapterDoctor adapter;
	LayoutInflater mInflater;
	List<RowDataDoctor> data_row; 
	List<RowDataDoctorJadwal> data_row_Jadwal;
	
	View imgView_emptyDataSet;
	GetFindDoctor myTask;
	TextView txt_judul;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_findoctor);
		
		mContext = this;
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ls = (ListView) findViewById(R.id.ls_dokter);
		imgView_emptyDataSet = (View)findViewById(R.id.iv_emptydataset);		
		ImageView btn_search = (ImageView) findViewById(R.id.img_search);
		txt_judul = (TextView)findViewById(R.id.judul_app);
		txt_judul.setTypeface(Font.arialFont(mContext));
		
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent loadSearch = new Intent(mContext, SearchDoctorActivity.class);
				startActivityForResult(loadSearch, ParameterCollection.RESULT_REQUEST_CODE);
			}
		});

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
				PublicFunction.BukaBanner(FindDoctorActivity.this, indexIklan);
			}
		});

		if (PublicFunction.isInternetConnection(mContext)) {
			myTask = new GetFindDoctor();
			myTask.execute();
		} else {
			Dialog dialog = new Dialog(mContext);
			dialog.setTitle(R.string.toast_noconn);
			dialog.show();
		}
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(resultCode == RESULT_OK){
			if(requestCode == 3003){
				if(ParameterCollection.data.size() > 0){
				Log.e("RESULT", data.getExtras().getString("result"));
				adapter = new CustomAdapterDoctor(mContext, 0, 0, ParameterCollection.data);
				adapter.notifyDataSetChanged();
				ls.setAdapter(adapter);
				imgView_emptyDataSet.setVisibility(View.INVISIBLE);
				}else{
					List<RowDataDoctor> data_kosong = new ArrayList<RowDataDoctor>();
					adapter = new CustomAdapterDoctor(mContext, 0, 0,  data_kosong);
					adapter.notifyDataSetChanged();
					ls.setAdapter(adapter);
					imgView_emptyDataSet.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	private class GetFindDoctor extends AsyncTask<Void, Void, Void> {
		private String aaa, bbb, idDokter, namDokter, spealisasi, urlGambar;
		private int sof, berhasil;
		Dialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new Dialog(mContext);
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
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> parameter = new ArrayList<NameValuePair>();
			
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			
			data_row = new ArrayList<RowDataDoctor>();
			adapter = new CustomAdapterDoctor(mContext, 0, 0, data_row);

			// parameter.add(new BasicNameValuePair("idD", "3"));
			// aaa = sh.getFindDoctor(parameter);
			aaa = sh.getAllDoctor();

			if(aaa != null){
				try {
					JSONObject jObj = new JSONObject(aaa);

					sof = jObj.getInt(ParameterCollection.TAG_SOF);

					if (sof == 1) {
						JSONArray jArray = jObj
								.getJSONArray(ParameterCollection.TAG_DOCTOR);
						
						for (int i = 0; i < jArray.length(); i++) {

							JSONObject c = jArray.getJSONObject(i);

							idDokter = c
									.getString(ParameterCollection.TAG_DOCTOR_IDDOC);
							namDokter = c
									.getString(ParameterCollection.TAG_DOCTOR_NAMADOC);
							spealisasi = c
									.getString(ParameterCollection.TAG_DOCTOR_SPESIALISASI);
							urlGambar = c.getString(ParameterCollection.TAG_DOCTOR_PHOTO);
							
							List<NameValuePair> parameterDetailJadwalDoktor = new ArrayList<NameValuePair>();
							parameterDetailJadwalDoktor.add(new BasicNameValuePair("idD", idDokter));
							
							bbb = sh.getFindDoctor(parameterDetailJadwalDoktor);
							
							if(bbb !=null){
								JSONObject objJadwal = new JSONObject(bbb);
								JSONArray arrayJadwal = objJadwal.getJSONArray(ParameterCollection.TAG_DOCTOR);								
								
								for(int j=0; j < arrayJadwal.length(); j++){
									JSONObject d = arrayJadwal.getJSONObject(j);
									data_row_Jadwal = new ArrayList<RowDataDoctorJadwal>();
									
									JSONArray array = d
											.getJSONArray(ParameterCollection.TAG_DOCTOR_JADWAL);
									
									for(int k=0; k< array.length(); k++){
										
										JSONObject obj_info = array
												.getJSONObject(k);
										
										String idJadwal = obj_info.getString(ParameterCollection.TAG_DOCTOR_JADWAL_ID);
										String haridanjam = obj_info.getString(ParameterCollection.TAG_DOCTOR_JADWAL_WAKTU);

										JSONObject objCabang = obj_info.getJSONObject(ParameterCollection.TAG_DOCTOR_JADWAL_CABANG);
										
										String idCabang = objCabang.getString(ParameterCollection.TAG_DOCTOR_JADWAL_CABANG_ID);
										String namaCabang = objCabang.getString(ParameterCollection.TAG_DOCTOR_JADWAL_CABANG_NAMA);
										
										data_row_Jadwal.add(new RowDataDoctorJadwal(idJadwal, haridanjam, idCabang, namaCabang));
									}
									
								}
							}

							data_row.add(new RowDataDoctor(idDokter, namDokter,
									spealisasi, urlGambar, data_row_Jadwal));
							
							
							Log.e("insert data", namDokter);
						}						

					}

				} catch (JSONException e) {

				}
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			dialog.dismiss();
			if (sof == 1) {
				adapter.notifyDataSetChanged();
				adapter.notifyDataSetChanged();
				ls.setAdapter(adapter);

			}else {
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