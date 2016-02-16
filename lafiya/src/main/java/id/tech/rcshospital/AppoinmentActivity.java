package id.tech.rcshospital;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import id.tech.rcshospital.R;
import id.tech.util.CustomDialog;
import id.tech.util.ParameterCollection;
import id.tech.util.PublicFunction;
import id.tech.util.ServiceHandlerJSON;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AppoinmentActivity extends FragmentActivity {
	Spinner spinner_dokter;
	// List<String> arrayDokter;
	TextView txtJadwalDokter;

	ImageView imgIklan;
	int flag;
	Runnable r;

	public Integer[] mImageIklan = { R.drawable.iklan1, R.drawable.iklan2,
			R.drawable.iklan3, R.drawable.iklan4, R.drawable.iklan5 };

	int indexIklan, index_dokter;

	String id_dokter;
	String nama_dokter, tgl, time, username, id_pasien, nama_pasien,
			keperluan_pasien, jadwalSelected;
	TextView tv_NamaDokter;
	DatePicker datePicker;
	TimePicker timePicker;
	Context mContext;
	EditText ed_NamaPasien, ed_namaKeperluan;
	SharedPreferences sh;

	public AppoinmentActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_appoinment);
		mContext = this;

		sh = getSharedPreferences(ParameterCollection.SHARED_PREF_NAME,
				Context.MODE_PRIVATE);
		username = sh.getString("username", "not_found");
		id_pasien = sh.getString("idPasien", "0");
		id_dokter = getIntent().getExtras().getString("id_dokter");
		nama_dokter = getIntent().getExtras().getString("nama_dokter");
		jadwalSelected = getIntent().getExtras().getString("jadwal");

		// spinner_dokter = (Spinner) findViewById(R.id.spinner_dokter);
		tv_NamaDokter = (TextView) findViewById(R.id.txt_nama_dokter);
		txtJadwalDokter = (TextView) findViewById(R.id.jadwal_dokter);
		datePicker = (DatePicker) findViewById(R.id.datepicker);
		timePicker = (TimePicker) findViewById(R.id.timePicker);
		ed_NamaPasien = (EditText) findViewById(R.id.txt_nama_pasien);
		ed_namaKeperluan = (EditText) findViewById(R.id.txt_keperluan);
		imgIklan = (ImageView) findViewById(R.id.img_iklan);

		tv_NamaDokter.setText(nama_dokter);
		txtJadwalDokter.setText(jadwalSelected);
		datePicker.setMinDate(System.currentTimeMillis() - 1000);

		// arrayDokter = new ArrayList<String>();
		// arrayDokter.add("dr. Soekoyo Soedibyo, Sp.THT-KL");
		// arrayDokter.add("dr. Anis Suraningsih, Sp.A");
		// arrayDokter.add("dr. Budi Suradi, Sp.U");
		// arrayDokter.add("dr. Tyas Khotimah, Sp.A");
		// ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(
		// AppoinmentActivity.this, android.R.layout.simple_spinner_item,
		// arrayDokter);
		// adapterSpinner
		// .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// adapterSpinner.notifyDataSetChanged();
		// spinner_dokter.setAdapter(adapterSpinner);

		Button btnSubmit = (Button) findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// AlertDialog.Builder builder = new AlertDialog.Builder(
				// AppoinmentActivity.this);
				// builder.setMessage("Appointment has been created...")
				// .setCancelable(false)
				// .setPositiveButton("OK",
				// new DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog,
				// int which) {
				// // TODO Auto-generated method stub
				// finish();
				//
				// }
				// });
				//
				// AlertDialog alert = builder.create();
				// alert.show();
				int bulannya = datePicker.getMonth()+1;
				tgl = datePicker.getYear() + "/" + bulannya + "/"
						+ datePicker.getDayOfMonth();
				time = String.valueOf(timePicker.getCurrentHour()) + ":"
						+ String.valueOf(timePicker.getCurrentMinute());

				nama_pasien = ed_NamaPasien.getText().toString();
				keperluan_pasien = ed_namaKeperluan.getText().toString();

				if (PublicFunction.isInternetConnection(mContext)) {
					if (ed_NamaPasien.getText().toString().isEmpty()
							|| ed_namaKeperluan.getText().toString().isEmpty()
							|| ed_NamaPasien.getText().toString()
									.equalsIgnoreCase("")
							|| ed_namaKeperluan.getText().toString().equals("")) {

						CustomDialog dialog = CustomDialog
								.newInstance("Tolong isi Nama Pasien dan Keperluan");
						dialog.show(getSupportFragmentManager(), "");
					} else {
						new POSTappointment().execute();
						
					}

				} else {
					Dialog dialog = new Dialog(mContext);
					dialog.setTitle(R.string.toast_noconn);
					dialog.show();
				}

			}
		});

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
				PublicFunction.BukaBanner(AppoinmentActivity.this, indexIklan);
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.anim_diam,
				R.anim.anim_left_to_right_out);

	}

	private class POSTappointment extends AsyncTask<Void, Void, Void> {
		private String aaa, message;
		private int sof;
		private Dialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new Dialog(mContext);
			dialog.show();
			dialog.setCancelable(false);
			dialog.setTitle(R.string.toast_createappointment);
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> p = new ArrayList<NameValuePair>();
			ServiceHandlerJSON sh = new ServiceHandlerJSON();

			p.add(new BasicNameValuePair("useraccount", username));
			p.add(new BasicNameValuePair("patientID", id_pasien));
			p.add(new BasicNameValuePair("doctorID", String.valueOf(id_dokter)));
			p.add(new BasicNameValuePair("date", tgl));
			p.add(new BasicNameValuePair("time", time));
			p.add(new BasicNameValuePair("purpose", keperluan_pasien));

			String response = sh.postAppoinment(p);
			aaa = response;

			try {
				JSONObject obj = new JSONObject(response);

				sof = obj.getInt(ParameterCollection.TAG_SOF);
				message = obj.getString(ParameterCollection.TAG_SOF_MESSAGE);

			} catch (JSONException e) {

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if (sof == 1) {
				Toast.makeText(AppoinmentActivity.this, message,
						Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(AppoinmentActivity.this,
						R.string.toast_failappointment + message,
						Toast.LENGTH_LONG).show();
			}

		}

	}

}
