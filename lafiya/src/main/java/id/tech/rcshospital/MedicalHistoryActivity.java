package id.tech.rcshospital;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import id.tech.util.Font;
import id.tech.util.ParameterCollection;
import id.tech.util.PublicFunction;
import id.tech.util.ServiceHandlerJSON;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

public class MedicalHistoryActivity extends Activity {
	LayoutInflater mInflater;
	Context mContext;
	ListView ls;
	TextView tv_judul;
	CustomAdapterMedicalHistory adapter;
	Vector<RowDataMedicalHistory> data_row;
	SharedPreferences sp;
	GetMedicalHistory myTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_medicalhistory);

		sp = getSharedPreferences(ParameterCollection.SHARED_PREF_NAME,
				Context.MODE_PRIVATE);
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = this;
		ls = (ListView) findViewById(R.id.listview_medicalhistory);
		tv_judul = (TextView)findViewById(R.id.txt_judul);
		tv_judul.setTypeface(Font.arialFont(mContext));
		
		if (PublicFunction.isInternetConnection(mContext)) {
			myTask = new GetMedicalHistory();
			myTask.execute();
		} else {
			Dialog dialog = new Dialog(mContext);
			dialog.setTitle(R.string.toast_noconn);
			dialog.show();
		}

	}

	private class GetMedicalHistory extends AsyncTask<Void, Void, Void> {
		private String idMedHis, idPasien, tglKonsultasi, kesimpulan,
				penanganan, createdDate, createdBy, spesialisasi;
		private String idDokter, namaDokter, pesan, tglParsed, aaa;
		private Dialog dialog;
		private int berhasil;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			dialog = new Dialog(MedicalHistoryActivity.this);
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
			
			data_row = new Vector<RowDataMedicalHistory>();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			List<NameValuePair> p = new ArrayList<NameValuePair>();
			p.add(new BasicNameValuePair("idP", sp.getString("idPasien", "0")));

			String response = sh.getMedicalHistory(p);
			aaa = response;

			try {
				JSONObject jObj = new JSONObject(response);

				int sof = jObj.getInt(ParameterCollection.TAG_SOF);

				if (sof == 1) {
					berhasil = 1;
					JSONArray jArray = jObj
							.getJSONArray(ParameterCollection.TAG_MEDHISTORY);

					for (int i = 0; i < jArray.length(); i++) {
						JSONObject c = jArray.getJSONObject(i);

						idMedHis = c
								.getString(ParameterCollection.TAG_MEDHISTORY_ID);
						idPasien = c
								.getString(ParameterCollection.TAG_MEDHISTORY_IDPASIEN);
						tglKonsultasi = c
								.getString(ParameterCollection.TAG_MEDHISTORY_TGLKONSUL);
						kesimpulan = c
								.getString(ParameterCollection.TAG_MEDHISTORY_KESIMPULAN);
						penanganan = c
								.getString(ParameterCollection.TAG_MEDHISTORY_PENANG);
						createdDate = c
								.getString(ParameterCollection.TAG_CREATEDDATE);
						createdBy = c
								.getString(ParameterCollection.TAG_CREATEDBY);

						JSONArray jArrayObat = c
								.getJSONArray(ParameterCollection.TAG_MEDHISTORY_OBAT);
						if (jArrayObat.length() > 0) {
							// Jika ada Record Obatnya
						}

						JSONObject jObjDokter = c
								.getJSONObject(ParameterCollection.TAG_MEDHISTORY_DOCTOR);
						idDokter = jObjDokter
								.getString(ParameterCollection.TAG_MEDHISTORY_IDDOC);
						namaDokter = jObjDokter
								.getString(ParameterCollection.TAG_MEDHISTORY_NAMADOC);
						spesialisasi = jObjDokter
								.getString(ParameterCollection.TAG_MEDHISTORY_SPESIALISASI);
						
						try{
							Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tglKonsultasi);
							tglParsed = new SimpleDateFormat("dd MMM yyyy").format(date);
						}catch(ParseException e){
							
						}
						data_row.add(new RowDataMedicalHistory(idMedHis,
								namaDokter, spesialisasi, tglParsed,
								kesimpulan, penanganan));
					}
				} else {
					pesan = "No Data";
				}
			} catch (JSONException e) {

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("isi",aaa );
			dialog.dismiss();
			if (berhasil == 1) {
				adapter = new CustomAdapterMedicalHistory(mContext, data_row,
						mInflater);
				adapter.notifyDataSetChanged();

				ls.setAdapter(adapter);
			} else {
				Toast.makeText(mContext, pesan, Toast.LENGTH_LONG).show();
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
