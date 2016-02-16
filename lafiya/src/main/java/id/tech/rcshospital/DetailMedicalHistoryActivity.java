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
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailMedicalHistoryActivity extends Activity {
	TextView t_Dokter;
	TextView t_Diagnosa;
	TextView t_Penanganan;
	TextView t_Tgl;

	String s_Dokter;
	String s_Diagnosa;
	String s_Penanganan;
	String s_Tgl;

	List<RowDataMedicalHistory> data_row;

	CustomAdapterPrevKonsultasi adapter;
	ListView ls;
	LayoutInflater inflater;
	Context ctx;

	LinearLayout childLinear;
	TextView tvDetail;
	SharedPreferences sp;
	
	View view_prevKonsultasi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_detail_medical_history);
		ctx = this;
		sp = getSharedPreferences(ParameterCollection.SHARED_PREF_NAME, Context.MODE_PRIVATE);
		inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		s_Dokter = getIntent().getExtras().getString("nama_dokter");
		s_Diagnosa = getIntent().getExtras().getString("diagnosa");
		s_Penanganan = getIntent().getExtras().getString("penanganan");
		s_Tgl = getIntent().getExtras().getString("tgl");

		t_Dokter = (TextView) findViewById(R.id.txt_detail_namaDokter);
		t_Diagnosa = (TextView) findViewById(R.id.txt_detail_diagnosa);
		t_Penanganan = (TextView) findViewById(R.id.txt_detail_penanganan);
		t_Tgl = (TextView) findViewById(R.id.txt_detail_tgl_diagnosa);

		t_Dokter.setText(s_Dokter);
		t_Diagnosa.setText(s_Diagnosa);
		t_Penanganan.setText(s_Penanganan);
		t_Tgl.setText(s_Tgl);
		
		t_Dokter.setTypeface(Font.arialFont(ctx));
		t_Diagnosa.setTypeface(Font.arialFont(ctx));
		t_Penanganan.setTypeface(Font.arialFont(ctx));
		t_Tgl.setTypeface(Font.arialFont(ctx));

//		data_row = new Vector<RowDataPrevKonsultasi>();
//		data_row.add(new RowDataPrevKonsultasi("1",
//				"dr. Anis Suraningsih, Sp.A", "Jantung", "20-06-2014",
//				getResources()
//						.getString(R.string.konsultasi_pertama_kesimpulan),
//				getResources()
//						.getString(R.string.konsultasi_pertama_penanganan)));
//		data_row.add(new RowDataPrevKonsultasi("2",
//				"dr. Anis Suraningsih, Sp.A", "Batuk Kronis", "15-07-2014",
//				getResources().getString(R.string.konsultasi_kedua_kesimpulan),
//				getResources().getString(R.string.konsultasi_kedua_penanganan)));
//		data_row.add(new RowDataPrevKonsultasi("3", "dr. Budi Suradi, Sp.A",
//				"Demam", "01-08-2014", getResources().getString(
//						R.string.konsultasi_kedua_kesimpulan), getResources()
//						.getString(R.string.konsultasi_kedua_penanganan)));
//		data_row.add(new RowDataPrevKonsultasi(
//				"4",
//				"dr. Anis Suraningsih, Sp.A",
//				"Sesak dan Muntah",
//				"20-07-2014",
//				getResources().getString(R.string.konsultasi_keemat_kesimpulan),
//				getResources()
//						.getString(R.string.konsultasi_keempat_penanganan)));
		
		view_prevKonsultasi = (View)findViewById(R.id.view_prev_konsultasi);
		view_prevKonsultasi.setVisibility(View.INVISIBLE);

		if(PublicFunction.isInternetConnection(ctx)){
			new GetMedicalHistory().execute();
		}else{
			Dialog dialog = new Dialog(ctx);
			dialog.setTitle(R.string.toast_noconn);
			dialog.show();
		}
		

		
	}
	
	private class GetMedicalHistory extends AsyncTask<Void, Void, Void>{
		private String idMedHis, idPasien, tglKonsultasi, kesimpulan, penanganan, createdDate, createdBy,spesialisasi;
		private String idDokter, namaDokter, tglParsed;
		private Dialog dialog;
		private int berhasil;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new Dialog(DetailMedicalHistoryActivity.this);
			dialog.setTitle(R.string.toast_proses);
			dialog.show();
			data_row = new Vector<RowDataMedicalHistory>();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			List<NameValuePair> p = new ArrayList<NameValuePair>();
			p.add(new BasicNameValuePair("idP", sp.getString("idPasien", "0")));
			
			String response = sh.getMedicalHistory(p);
			
			try{
				JSONObject jObj = new JSONObject(response);
				
				int sof = jObj.getInt(ParameterCollection.TAG_SOF);

				if (sof == 1) {
					berhasil = sof;
					JSONArray jArray = jObj.getJSONArray(ParameterCollection.TAG_MEDHISTORY);
					
					for(int i=0; i<jArray.length(); i++){
						JSONObject c = jArray.getJSONObject(i);
						
						idMedHis = c.getString(ParameterCollection.TAG_MEDHISTORY_ID);
						idPasien = c.getString(ParameterCollection.TAG_MEDHISTORY_IDPASIEN);
						tglKonsultasi = c.getString(ParameterCollection.TAG_MEDHISTORY_TGLKONSUL);
						kesimpulan = c.getString(ParameterCollection.TAG_MEDHISTORY_KESIMPULAN);
						penanganan = c.getString(ParameterCollection.TAG_MEDHISTORY_PENANG);
						createdDate = c.getString(ParameterCollection.TAG_CREATEDDATE);
						createdBy = c.getString(ParameterCollection.TAG_CREATEDBY);
						
						JSONArray jArrayObat = c.getJSONArray(ParameterCollection.TAG_MEDHISTORY_OBAT);						
						if(jArrayObat.length() > 0){
							//Jika ada Record Obatnya
						}
						
						JSONObject jObjDokter = c.getJSONObject(ParameterCollection.TAG_MEDHISTORY_DOCTOR);
						idDokter = jObjDokter.getString(ParameterCollection.TAG_MEDHISTORY_IDDOC);
						namaDokter = jObjDokter.getString(ParameterCollection.TAG_MEDHISTORY_NAMADOC);
						spesialisasi = jObjDokter.getString(ParameterCollection.TAG_MEDHISTORY_SPESIALISASI);
						
						try{
							Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tglKonsultasi);
							tglParsed = new SimpleDateFormat("dd MMM yyyy").format(date);
						}catch(ParseException e){
							
						}
						
						data_row.add(new RowDataMedicalHistory(idMedHis, namaDokter, spesialisasi, tglParsed, kesimpulan, penanganan));
					}
				}
			}catch (JSONException e){
				
			}
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if(berhasil ==1){
				view_prevKonsultasi.setVisibility(View.VISIBLE);
				
				LinearLayout parentLinear = (LinearLayout) findViewById(R.id.linear_prev_konsultasi);

				final TextView[] detailBtn = new TextView[data_row.size()];

				for (int i = 0; i < data_row.size(); i++) {
					final int indexChild = i;

					childLinear = new LinearLayout(ctx);
					childLinear.setLayoutParams(new LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					childLinear.setOrientation(LinearLayout.HORIZONTAL);
					childLinear.setPadding(20, 20, 20, 20);

					ImageView img = new ImageView(ctx);
					img.setLayoutParams(new LayoutParams(100, 100));
					img.setImageResource(R.drawable.img_stethoscope);

					childLinear.addView(img);

					LinearLayout linearVerticalChild = new LinearLayout(ctx);
					linearVerticalChild.setLayoutParams(new LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					linearVerticalChild.setOrientation(LinearLayout.VERTICAL);
					linearVerticalChild.setPadding(20, 0, 0, 0);

					TextView tvTgl = new TextView(ctx);
					tvTgl.setLayoutParams(new LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					TextView tvDokter = new TextView(ctx);
					tvDokter.setLayoutParams(new LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					TextView tvKeluhan = new TextView(ctx);
					tvKeluhan.setLayoutParams(new LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));

					detailBtn[indexChild] = new TextView(ctx);
					detailBtn[indexChild].setLayoutParams(new LayoutParams(
							LinearLayout.LayoutParams.MATCH_PARENT,
							LinearLayout.LayoutParams.WRAP_CONTENT));
					detailBtn[indexChild].setText("See Detail...");
					detailBtn[indexChild].setId(indexChild);
					detailBtn[indexChild].setTextColor(Color.GRAY);

					detailBtn[i].setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (detailBtn[indexChild].getId() == ((TextView) v).getId()) {

								Intent loadDetailLagi = new Intent(ctx,
										DetailMedicalHistoryActivity.class);
								loadDetailLagi.putExtra("nama_dokter",
										data_row.get(indexChild).mNamaDok);
								
								loadDetailLagi.putExtra("diagnosa",
										data_row.get(indexChild).mDiagnosa);
								loadDetailLagi.putExtra("penanganan",
										data_row.get(indexChild).mPenanganan);
								loadDetailLagi.putExtra("tgl",
										data_row.get(indexChild).mTgl);
								startActivity(loadDetailLagi);
								finish();
								overridePendingTransition(R.anim.anim_right_to_left, R.anim.anim_diam);
							}
						}
					});

					tvTgl.setText(data_row.get(indexChild).mTgl);
					tvDokter.setText(data_row.get(indexChild).mNamaDok);
					tvKeluhan.setText(data_row.get(indexChild).mBagian);

					linearVerticalChild.addView(tvTgl);
					linearVerticalChild.addView(tvDokter);
					linearVerticalChild.addView(tvKeluhan);
					linearVerticalChild.addView(detailBtn[indexChild]);

					childLinear.addView(linearVerticalChild);

					parentLinear.addView(childLinear);
					Animation ani = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
					view_prevKonsultasi.startAnimation(ani);
			}
			}
		}
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.anim_diam, R.anim.anim_left_to_right_out );
	}
}
