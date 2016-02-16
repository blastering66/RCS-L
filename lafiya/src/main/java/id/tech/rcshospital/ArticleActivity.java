package id.tech.rcshospital;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import id.tech.rcshospital.R;
import id.tech.util.Font;
import id.tech.util.PublicFunction;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.FlagToString;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleActivity extends Activity {
	CustomAdapter adapter;
	Context mContext;
	LayoutInflater minflater;
	Vector<RowDataBerita> data_row;
	TextView txt_judul;
	ProgressBar pg;
	ListView listview;
	ImageView imgIklan;
	int flag;
	Runnable r;

	public Integer[] mImageIklan = { R.drawable.iklan1, R.drawable.iklan2,
			R.drawable.iklan3, R.drawable.iklan4, R.drawable.iklan5 };

	int indexIklan;
	loadArtikel myTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_artikel);

		pg = (ProgressBar) findViewById(R.id.pg);
		listview = (ListView) findViewById(R.id.listview_artikel);
		imgIklan = (ImageView) findViewById(R.id.img_iklan);
		txt_judul = (TextView)findViewById(R.id.judul_app);		

		mContext = this;
		
		txt_judul.setTypeface(Font.arialFont(mContext));
		
		minflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (PublicFunction.isInternetConnection(mContext)) {
			myTask = new loadArtikel();
			myTask.execute();
		} else {
			Dialog dialog = new Dialog(mContext);
			dialog.setTitle("No Internet Connection");
			dialog.show();
		}
		

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
				PublicFunction.BukaBanner(ArticleActivity.this, indexIklan);
			}
		});

	}

	private void showIklan() {
		Timer time = new Timer();
		time.schedule(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if (flag > mImageIklan.length) {
							flag = 0;
						} else {
							imgIklan.setBackgroundResource(mImageIklan[flag]);
						}

						flag = flag + 1;

					}
				});
			}
		}, System.currentTimeMillis(), 3000);

	}

	private class loadArtikel extends AsyncTask<Void, Void, Void> {
		String judul, urlRss, isiBerita;
		private Dialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			data_row = new Vector<RowDataBerita>();
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
			XMLRssParser parser = new XMLRssParser();
			String xml = parser.getXmlFromUrl("http://doktersehat.com/feed/");
			Document doc = parser.getDomElement(xml);
			NodeList nl = doc.getElementsByTagName(Parameter.KEY_ITEM);

			for (int i = 0; i < nl.getLength(); i++) {
				Element e = (Element) nl.item(i);

				judul = parser.ambilNilai(e, Parameter.KEY_TITLE);
				urlRss = parser.ambilNilai(e, Parameter.KEY_LINK);
				isiBerita = parser.getValueCDATA(e, Parameter.KEY_ENCODED);

				data_row.add(new RowDataBerita(String.valueOf(i), judul, urlRss));
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if(data_row.size() > 1){
				pg.setVisibility(View.GONE);
				adapter = new CustomAdapter(mContext, data_row, minflater);
				adapter.notifyDataSetChanged();
				listview.setAdapter(adapter);
			}else{
				
				Toast.makeText(mContext, R.string.toast_noarticle, Toast.LENGTH_LONG).show();
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
