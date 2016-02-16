package id.tech.rcshospital;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
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
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReminderActivity extends Activity {
	// Vector<RowDataReminder> data_row;
	ArrayList<String> dataArray;

	ListView ls;
	Context myContext;

	int bedaHari, bedaJam, bedaMenit, bedaDetik;
	String ago;

	CountDownTimer mCountDownTimer;
	long mInitialTime;
	String tgl_event;
	Date time_event = null;
	Calendar cal_event = null;

	Calendar cal_now;
	Date event = null;
	Date now;
	long diff;
	long diff_menit;
	float diff_jam;

	String hari, jam, menit, detik;
	boolean event_closed;
	String cEvent_closed;

	ImageView imgIklan;
	int flag;
	Runnable r;

	public Integer[] mImageIklan = { R.drawable.iklan1, R.drawable.iklan2,
			R.drawable.iklan3, R.drawable.iklan4, R.drawable.iklan5 };

	int indexIklan;
	List<RowDataReminder> data_rowReminder;
	TextView txt1;
	TextView txt_countdown;
	TextView txt_judul;
	Button btn_info;
	SharedPreferences sp;

	TaskGetReminder myTask;
	CustomAdapterReminder adapterCustom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reminder);

		myContext = this;
		sp = getSharedPreferences(ParameterCollection.SHARED_PREF_NAME,
				Context.MODE_PRIVATE);
		if (PublicFunction.isInternetConnection(myContext)) {
			myTask = new TaskGetReminder();
			myTask.execute();
		} else {
			Dialog dialog = new Dialog(myContext);
			dialog.setTitle(R.string.toast_noconn);
			dialog.show();
		}

		txt1 = (TextView) findViewById(R.id.txt_nextevent);
		txt_countdown = (TextView) findViewById(R.id.txt_home_countdown);
		txt_judul = (TextView) findViewById(R.id.txt_home_judulacara);
		btn_info = (Button) findViewById(R.id.btn_home_info);
		
		txt1.setTypeface(Font.arialFont(myContext));
		txt_countdown.setTypeface(Font.arialFont(myContext));
		txt_judul.setTypeface(Font.arialFont(myContext));

		ls = (ListView) findViewById(R.id.listview_acara);
		// ls.setDrawingCacheEnabled(false);
		// ls.setDivider(null);
		// ls.setDividerHeight(1);
		// ls.setCacheColorHint(Color.TRANSPARENT);
		ls.setVerticalScrollBarEnabled(true);

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
				PublicFunction.BukaBanner(ReminderActivity.this, indexIklan);
			}
		});

	}

	private class TaskGetReminder extends AsyncTask<Void, Void, Void> {
		private String aaa, bbb, ccc, ddd, eee, isi, keterangan_obat, tglbeli,
				idobat, namaObat = "";

		private String idvaksin, namavaksin, keterangan_vaksin, tglvaksin = "";
		private String jenisRem = "";
		Dialog dialog;

		private String id, judul, pesan, nama, tgl;
		int sof, arraySize;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new Dialog(myContext);
			dialog.setTitle(R.string.toast_proses);

			dialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// TODO Auto-generated method stub
					cancel(true);
					myTask.cancel(true);
				}
			});

			dialog.show();
			// dialog.setCancelable(false);

			data_rowReminder = new ArrayList<RowDataReminder>();
			dataArray = new ArrayList<String>();

		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			Toast.makeText(myContext, "CANCELED", Toast.LENGTH_LONG).show();
			super.onCancelled();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if (isCancelled()) {
				return null;
			} else {

				ServiceHandlerJSON sh = new ServiceHandlerJSON();
				List<NameValuePair> parameter = new ArrayList<NameValuePair>();
				parameter.add(new BasicNameValuePair("idP", sp.getString(
						"idPasien", "0")));
				String response = sh.getReminder(parameter);
				isi = response;
				if (response != null) {
					try {
						JSONObject jObj = new JSONObject(response);

						sof = jObj.getInt(ParameterCollection.TAG_SOF);

						if (sof == 1) {
							JSONArray jArray = jObj
									.getJSONArray(ParameterCollection.TAG_REMINDER);
							arraySize = jArray.length();

							for (int i = 0; i < jArray.length(); i++) {
								JSONObject c = jArray.getJSONObject(i);
								id = c.getString(ParameterCollection.TAG_IDREM);
								jenisRem = c
										.getString(ParameterCollection.TAG_JENISREM);
								pesan = c
										.getString(ParameterCollection.TAG_PESAN);
								tgl = c.getString(ParameterCollection.TAG_TGLREM);
								eee = c.getString(ParameterCollection.TAG_CREATEDDATE);

								if (jenisRem.equals("Obat")) {
									JSONArray array = c
											.getJSONArray(ParameterCollection.TAG_OBAT);

									for (int j = 0; j < array.length(); j++) {
										JSONObject obj_info = array
												.getJSONObject(j);
										keterangan_obat = obj_info
												.getString(ParameterCollection.TAG_OBAT_KET);
										tglbeli = obj_info
												.getString(ParameterCollection.TAG_OBAT_TGLBELI);

										JSONObject info = array
												.getJSONObject(j)
												.getJSONObject(
														ParameterCollection.TAG_OBAT_INFOOBAT);
										idobat = info
												.getString(ParameterCollection.TAG_OBAT_INFOOBAT_IDOBAT);
										namaObat = info
												.getString(ParameterCollection.TAG_OBAT_INFOOBAT_NAMAOBAT);
										nama = namaObat;
									}

								} else if (jenisRem.equals("Vaksin")) {
									JSONArray array = c
											.getJSONArray(ParameterCollection.TAG_VAKSIN);

									for (int j = 0; j < array.length(); j++) {
										JSONObject obj_info = array
												.getJSONObject(j);

										tglvaksin = obj_info
												.getString(ParameterCollection.TAG_VAKSIN_TGLVAKSIN);

										JSONObject info = array
												.getJSONObject(j)
												.getJSONObject(
														ParameterCollection.TAG_VAKSIN_INFOVAKSIN);
										idvaksin = info
												.getString(ParameterCollection.TAG_VAKSIN_INFOVAKSIN_IDVAKSIN);
										namavaksin = info
												.getString(ParameterCollection.TAG_VAKSIN_INFOVAKSIN_NAMAVAKSIN);
										nama = namavaksin;
										keterangan_vaksin = info
												.getString(ParameterCollection.TAG_VAKSIN_INFOVAKSIN_KETERANGAN);
									}

								} else if (jenisRem.contains("Dokter")) {
									nama = "Appointment";
								} else if (jenisRem.contains("Checkup")) {
									nama = "Checkup";
								}

								dataArray.add(nama);
								data_rowReminder.add(new RowDataReminder(id,
										"", pesan, nama, tgl));

								Log.e("Penambahan Data", "");

							}
						} else {
							return null;
						}

					} catch (JSONException e) {

					} catch (Exception e) {
						// TODO: handle exception
					}
				}

			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.e("isi", isi);
			dialog.dismiss();
			// Log.e("namavaksin", namavaksin);
			// Log.e("keterangan_vaksin", keterangan_vaksin);
			// Log.e("namaObat", namaObat);
			// Log.e("eee", eee);
			Log.e("Penambahan Data SELESAI", "");
			if (arraySize > 0) {
				ArrayAdapter<String> data = new ArrayAdapter<String>(myContext,
						android.R.layout.simple_list_item_1, dataArray);
				data.notifyDataSetChanged();
				
				adapterCustom = new CustomAdapterReminder(myContext, 0, 0, data_rowReminder);
//				ls.setAdapter(data);
				ls.setAdapter(adapterCustom);

				txt_judul.setText(data_rowReminder.get(0).mPesan);
				String date_nextevent = data_rowReminder.get(0).mTgl;
				tgl_event = date_nextevent;
				try {
					time_event = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
							Locale.US).parse(tgl_event);

					Calendar c = Calendar.getInstance();
					now = c.getTime();

					DateTime dt_event = new DateTime(time_event);
					DateTime dt_now = new DateTime(now);

					bedaHari = Days.daysBetween(dt_now, dt_event).getDays();
					bedaJam = Hours.hoursBetween(dt_now, dt_event).getHours() % 24;
					bedaMenit = Minutes.minutesBetween(dt_now, dt_event)
							.getMinutes() % 60;
					bedaDetik = Seconds.secondsBetween(dt_now, dt_event)
							.getSeconds() % 60;

					mInitialTime = DateUtils.DAY_IN_MILLIS * bedaHari
							+ DateUtils.HOUR_IN_MILLIS * bedaJam
							+ DateUtils.MINUTE_IN_MILLIS * bedaMenit
							+ DateUtils.SECOND_IN_MILLIS * bedaDetik;
				} catch (ParseException e) {
					e.printStackTrace();
				}

				mCountDownTimer = new CountDownTimer(mInitialTime, 1000) {
					StringBuilder time = new StringBuilder();

					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						time.setLength(0);
						if (millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
							long count = millisUntilFinished
									/ DateUtils.DAY_IN_MILLIS;
							if (count > 1)
								time.append(count).append(" days ");
							else
								time.append(count).append(" day ");

							millisUntilFinished %= DateUtils.DAY_IN_MILLIS;
						}

						time.append(DateUtils.formatElapsedTime(Math
								.round(millisUntilFinished / 1000d)));
						txt_countdown.setText(time.toString());
						event_closed = false;
					}

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						// txt_countdown.setText(DateUtils.formatElapsedTime(0));
						txt_countdown.setText("This event closed");
						event_closed = true;
						cEvent_closed = "1";
					}
				}.start();

				ls.setOnItemClickListener(new ItemReminderListener());
			} else {
				Toast.makeText(myContext, R.string.toast_noreminder,
						Toast.LENGTH_LONG).show();
				ls.setVisibility(View.GONE);
			}
		}
	}

	public class ItemReminderListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			String judul = "";
			String date = "";

			judul = data_rowReminder.get(position).mPesan;
			date = data_rowReminder.get(position).mTgl;

			txt_judul.setText(judul);

			String date_nextevent = date;

			tgl_event = date_nextevent;

			try {
				time_event = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
						Locale.US).parse(tgl_event);

				Calendar c = Calendar.getInstance();
				now = c.getTime();

				DateTime dt_event = new DateTime(time_event);
				DateTime dt_now = new DateTime(now);

				bedaHari = Days.daysBetween(dt_now, dt_event).getDays();
				bedaJam = Hours.hoursBetween(dt_now, dt_event).getHours() % 24;
				bedaMenit = Minutes.minutesBetween(dt_now, dt_event)
						.getMinutes() % 60;
				bedaDetik = Seconds.secondsBetween(dt_now, dt_event)
						.getSeconds() % 60;

				mInitialTime = DateUtils.DAY_IN_MILLIS * bedaHari
						+ DateUtils.HOUR_IN_MILLIS * bedaJam
						+ DateUtils.MINUTE_IN_MILLIS * bedaMenit
						+ DateUtils.SECOND_IN_MILLIS * bedaDetik;

			} catch (ParseException e) {
				e.printStackTrace();
			}

			mCountDownTimer.cancel();

			mCountDownTimer = new CountDownTimer(mInitialTime, 1000) {
				StringBuilder time = new StringBuilder();

				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					time.setLength(0);
					if (millisUntilFinished > DateUtils.DAY_IN_MILLIS) {
						long count = millisUntilFinished
								/ DateUtils.DAY_IN_MILLIS;
						if (count > 1)
							time.append(count).append(" days ");
						else
							time.append(count).append(" day ");

						millisUntilFinished %= DateUtils.DAY_IN_MILLIS;
					}

					time.append(DateUtils.formatElapsedTime(Math
							.round(millisUntilFinished / 1000d)));
					txt_countdown.setText(time.toString());
					event_closed = false;
				}

				@Override
				public void onFinish() {
					// TODO Auto-generated method stub
					// txt_countdown.setText(DateUtils.formatElapsedTime(0));
					txt_countdown.setText("This event closed");
					event_closed = true;
					cEvent_closed = "1";
				}
			}.start();
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.anim_diam, R.anim.slide_out_to_bottom);
	}
}
