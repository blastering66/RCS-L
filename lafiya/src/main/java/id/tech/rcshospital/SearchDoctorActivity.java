package id.tech.rcshospital;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import id.tech.util.ParameterCollection;
import id.tech.util.ServiceHandlerJSON;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchDoctorActivity extends Activity{
	Spinner spinner;
	EditText edKataKunci;
	String cKataKunci, cParameterName;
	Button btn_search;
	Context mContext;
	List<RowDataDoctor> data_row;
	List<RowDataDoctorJadwal> data_row_Jadwal;
	SearchDoctorAsync myTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_searchdoctor);
		mContext = this;		
		
		spinner = (Spinner)findViewById(R.id.spinner_searchby);
		edKataKunci = (EditText)findViewById(R.id.ed_string_search);
		btn_search = (Button)findViewById(R.id.btn_search_doctor);
		btn_search.setEnabled(true);
		btn_search.setSelected(true);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				if(position == 0){
					cParameterName = "dName";
				}else if(position ==1){
					cParameterName = "dSpe";
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				cParameterName = "dName";
			}
		});
		
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cKataKunci = edKataKunci.getText().toString();
				myTask = new SearchDoctorAsync();
				myTask.execute();
			}
		});
	}
	
	private class SearchDoctorAsync extends AsyncTask<Void, Void, Void>{
		private ProgressDialog dialog;
		
		private String response, bbb, idDokter, namDokter, spealisasi, urlGambar;
		private int sof, berhasil;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(mContext);
			dialog.setMessage("Searching...");
			dialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					myTask.cancel(true);
				}
			});
			dialog.setCancelable(true);
			dialog.show();
			data_row = new ArrayList<RowDataDoctor>();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> p = new ArrayList<NameValuePair>();
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			
			p.add(new BasicNameValuePair(cParameterName, cKataKunci));			
			
			response = sh.getFindDoctor(p);
			if(response != null){
				try {
					JSONObject jObj = new JSONObject(response);

					sof = jObj.getInt(ParameterCollection.TAG_SOF);

					if (sof == 1) {
						JSONArray jArray = jObj
								.getJSONArray(ParameterCollection.TAG_DOCTOR);
						
						for (int i = 0; i < jArray.length(); i++) {

							JSONObject c = jArray.getJSONObject(i);

							idDokter = c
									.getString(ParameterCollection.TAG_DOCTOR_IDDOC);
							namDokter = c
									.getString(ParameterCollection.TAG_MEDHISTORY_NAMADOC);
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
			Intent returnIntent = new Intent();
			returnIntent.putExtra("result", response);
			setResult(RESULT_OK, returnIntent);
			ParameterCollection.data = data_row;
			finish();
		}
		
	}
	
	
}
