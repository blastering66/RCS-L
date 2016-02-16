package id.tech.rcshospital;

import java.util.ArrayList;
import java.util.List;

import id.tech.rcshospital.R;
import id.tech.util.Font;
import id.tech.util.JadwalDoctorStatic;
import id.tech.util.ZoomImage;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailDoctorActivity extends Activity {
	Button btnMakeAppointment;
	String id_dokter;
	String nama_dokter, jadwal_selected, img_dokter;
	List<RowDataDoctorJadwal> data_jadwal;
	ArrayList<String> list_itemJadwal;
	ArrayAdapter<String> adapter;
	TextView txt_judul, txt_schedule;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_doctordetail);

		id_dokter = getIntent().getExtras().getString("id_dokter");
		nama_dokter = getIntent().getExtras().getString("nama_dokter");
		img_dokter = getIntent().getExtras().getString("img_dokter");
		data_jadwal = JadwalDoctorStatic.data;
		jadwal_selected = "";

		TextView tv_nama_dokter = (TextView) findViewById(R.id.txt_nama_dokter);
		txt_judul = (TextView)findViewById(R.id.judul_app);
		txt_schedule = (TextView)findViewById(R.id.txt_detaildoctor_schedule);
		
		tv_nama_dokter.setTypeface(Font.arialFont(getApplicationContext()));
		txt_judul.setTypeface(Font.arialFont(getApplicationContext()));
		txt_schedule.setTypeface(Font.arialFont(getApplicationContext()));
		
		ListView listview = (ListView) findViewById(R.id.listview_jadwal);
		final ImageView img_doctor = (ImageView)findViewById(R.id.img_profiledoctor);
		View viewEmptyJadwal = (View)findViewById(R.id.iv_emptydataset);
		
		Picasso.with(DetailDoctorActivity.this).load(img_dokter).into(img_doctor);
		
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		final View rootView = inflater.inflate(R.layout.layout_doctordetail, null);
		final ImageView imgExpanded = (ImageView)findViewById(R.id.expanded_image);
		final View containerId = (View)findViewById(R.id.container);
		
		img_doctor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Drawable d = img_doctor.getDrawable();
				ZoomImage.zoomView(img_doctor, d, imgExpanded, containerId);
			}
		});
		
		
		
		list_itemJadwal = new ArrayList<String>();

		for (int i = 0; i < data_jadwal.size(); i++) {
			final RowDataDoctorJadwal row = data_jadwal.get(i);
			String j = row.mHariJam + "\n" + row.mNamaCabang;
			list_itemJadwal.add(j);
		}

		adapter = new ArrayAdapter<String>(DetailDoctorActivity.this,
				android.R.layout.simple_list_item_1, list_itemJadwal);
		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				for (int i = 0; i < parent.getChildCount(); i++) {
					parent.getChildAt(i).setBackgroundResource(
							R.color.default_color);
				}
				view.setEnabled(true);
				view.setBackgroundColor(DetailDoctorActivity.this
						.getResources().getColor(R.color.pressed_color));
				jadwal_selected = adapter.getItem(position).toString();
			}
		});
		
		if(list_itemJadwal.size() == 0){
			viewEmptyJadwal.setVisibility(View.VISIBLE);
		}

		tv_nama_dokter.setText(nama_dokter);

		btnMakeAppointment = (Button) findViewById(R.id.btn_makeappointment);
		btnMakeAppointment.setTypeface(Font.arialFont(getApplicationContext()));
		btnMakeAppointment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (jadwal_selected == "" || jadwal_selected.equals("")
						|| jadwal_selected == null) {
					Toast.makeText(DetailDoctorActivity.this,
							R.string.toast_pilihjadwal,
							Toast.LENGTH_LONG).show();
				} else {
					Intent loadAppointment = new Intent(
							DetailDoctorActivity.this, AppoinmentActivity.class);
					loadAppointment.putExtra("id_dokter", id_dokter);
					loadAppointment.putExtra("nama_dokter", nama_dokter);
					loadAppointment.putExtra("jadwal", jadwal_selected);
					startActivity(loadAppointment);
					overridePendingTransition(R.anim.anim_right_to_left,
							R.anim.anim_diam);
				}

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

}
