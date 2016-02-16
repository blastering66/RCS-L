package id.tech.rcshospital;

import java.util.ArrayList;

import id.tech.rcshospital.R;
import id.tech.util.ParameterCollection;


import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.os.Build;

public class SplashscreenActivity extends Activity {
	SharedPreferences sp;
	boolean sudah_login;
	ImageView img_lafiya, img_rcs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		img_lafiya = (ImageView)findViewById(R.id.img_lafiya);
		img_rcs = (ImageView)findViewById(R.id.img_rcs);
		
		Animation ani = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
		img_lafiya.startAnimation(ani);
		
		img_rcs.startAnimation(ani);
		
		new LoadData().execute();
	}
	
	
	
	private class LoadData extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
			} catch (Exception e) {

			}		
			
			sp = getSharedPreferences(ParameterCollection.SHARED_PREF_NAME, Context.MODE_PRIVATE);
			
			sudah_login = sp.getBoolean("logged", false);
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(sudah_login){
				Intent loadMainMenu = new Intent(SplashscreenActivity.this, MenuUtamaActivity.class);
				startActivity(loadMainMenu);
				finish();
			}else{
				Intent loadMainMenu = new Intent(SplashscreenActivity.this, LoginSignupActivity.class);
				startActivity(loadMainMenu);
				finish();
			}
			
			
		}
		
	}
}
