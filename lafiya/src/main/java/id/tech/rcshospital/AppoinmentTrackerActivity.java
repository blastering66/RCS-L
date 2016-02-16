package id.tech.rcshospital;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AppoinmentTrackerActivity extends Activity {
	ArrayList<String> dataArray;
	TextView txt_judul;
	ListView ls;
	Context myContext;

	ImageView imgIklan;
	int flag;
	Runnable r;

	public Integer[] mImageIklan = { R.drawable.iklan1, R.drawable.iklan2,
			R.drawable.iklan3, R.drawable.iklan4, R.drawable.iklan5 };

	int indexIklan;
	SharedPreferences sp;
	GetAppoinmentAsync myTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tracker);

		myContext = this;
		sp = getSharedPreferences(ParameterCollection.SHARED_PREF_NAME,
				Context.MODE_PRIVATE);

		if (PublicFunction.isInternetConnection(myContext)) {
			myTask = new GetAppoinmentAsync();
			myTask.execute();
		} else {
			Dialog dialog = new Dialog(myContext);
			dialog.setTitle(R.string.toast_noconn);
			dialog.show();
		}

		ls = (ListView) findViewById(R.id.listview_tracker);
		txt_judul = (TextView)findViewById(R.id.judul_app);
		imgIklan = (ImageView) findViewById(R.id.img_iklan);
		
		txt_judul.setTypeface(Font.arialFont(myContext));
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
				PublicFunction.BukaBanner(AppoinmentTrackerActivity.this,
						indexIklan);
			}
		});

//		new GetAppoinmentAsync().execute();
	}

	private class GetAppoinmentAsync extends AsyncTask<Void, Void, Void> {
		private String idAppointment, tglAppointment, jamAppointment,
				keperluan, keterangan, createdDate, createdBy, updatedDate,
				updatedBy, idPasien, namaPasien;
		private String idDokter, namaDokter, aaa, pesan;
		private int berhasil;
		Dialog dialog;
		
		private CustomAdapterTracker adapterTracker;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dataArray = new ArrayList<String>();
			dialog = new Dialog(myContext);
			dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					myTask.cancel(true);
				}
			});
			dialog.show();
			dialog.setCancelable(true);
			dialog.setTitle(R.string.toast_proses);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			List<NameValuePair> p = new ArrayList<NameValuePair>();
//			p.add(new BasicNameValuePair("idP", sp.getString("idPasien",
//					sp.getString("idPasien", "0"))));
			p.add(new BasicNameValuePair("idP", sp.getString("idPasien", "0")));

			String response = sh.getAppoinment(p);
			aaa = response;
			if (response != null) {
				try {
					JSONObject jObj = new JSONObject(response);

					JSONArray jArray = jObj
							.getJSONArray(ParameterCollection.TAG_APPOINMENT);

					berhasil = jObj.getInt(ParameterCollection.TAG_SOF);

					if (berhasil == 1) {
						for (int i = 0; i < jArray.length(); i++) {
							JSONObject c = jArray.getJSONObject(i);

							idAppointment = c
									.getString(ParameterCollection.TAG_APPOINMENT_ID);
							tglAppointment = c
									.getString(ParameterCollection.TAG_APPOINMENT_TGL);
							jamAppointment = c
									.getString(ParameterCollection.TAG_APPOINMENT_JAM);
							keperluan = c
									.getString(ParameterCollection.TAG_APPOINMENT_KEPERLUAN);
							keterangan = c
									.getString(ParameterCollection.TAG_APPOINMENT_KETERANGAN);

							createdDate = c
									.getString(ParameterCollection.TAG_CREATEDDATE);
							createdBy = c
									.getString(ParameterCollection.TAG_CREATEDBY);
							updatedDate = c
									.getString(ParameterCollection.TAG_UPDATEDDATE);
							updatedBy = c
									.getString(ParameterCollection.TAG_UPDATEDBY);

							JSONObject d = c
									.getJSONObject(ParameterCollection.TAG_APPOINMENT_DOCTOR);
							idDokter = d
									.getString(ParameterCollection.TAG_APPOINMENT_DOCTOR_IDDOC);
							namaDokter = d
									.getString(ParameterCollection.TAG_APPOINMENT_DOCTOR_NAMADOC);

							JSONObject pasien = c
									.getJSONObject(ParameterCollection.TAG_APPOINMENT_PASIEN);
							idPasien = pasien
									.getString(ParameterCollection.TAG_APPOINMENT_PASIEN_IDPASIEN);
							namaPasien = pasien
									.getString(ParameterCollection.TAG_APPOINMENT_PASIEN_NAMAPASIEN);

							if (idPasien.equals(sp.getString("idPasien", "0"))) {
								dataArray.add(keperluan);
							}

						}
					} else {
						pesan = "No Data";
					}

				} catch (Exception e) {

				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			Log.e("aaa", aaa);
			if (berhasil == 1) {
//				ArrayAdapter<String> data = new ArrayAdapter<String>(myContext,
//						android.R.layout.simple_list_item_1, dataArray);
				
				adapterTracker = new CustomAdapterTracker(myContext, 0, 0, dataArray);
				adapterTracker.notifyDataSetChanged();
//				data.notifyDataSetChanged();
				ls.setAdapter(adapterTracker);
			} else {
				Toast.makeText(myContext, pesan, Toast.LENGTH_LONG).show();
			}

		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.anim_diam, R.anim.slide_out_to_bottom);
	}
}
