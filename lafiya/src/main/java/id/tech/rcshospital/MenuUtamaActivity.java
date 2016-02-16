package id.tech.rcshospital;

import id.tech.rcshospital.R;
import id.tech.util.ParameterCollection;

import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuUtamaActivity extends FragmentActivity{
	private static final int NUM_PAGES = 7;
	ViewPager vp;
	private PagerAdapter mPagerAdapter;
	CirclePageIndicator mCircleIndicator;
	Context mContext;
	LayoutInflater mInflater;
	ImageView btn_setting;
	private boolean exit = false;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		exit = false;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if(!exit){
			Toast.makeText(mContext, R.string.toast_exit, Toast.LENGTH_LONG).show();
			exit = true;
		}else{
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_menuutama);
		mContext = this;


		//Pakai GCM Nanti
//		Intent startService = new Intent(mContext, ReminderService.class);
//		startService(startService);
		
		mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		vp = (ViewPager)findViewById(R.id.viewPager);
		mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
		vp.setAdapter(mPagerAdapter);

		mCircleIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
		mCircleIndicator.setViewPager(vp);
		
		OnPageChangeListener op_listener = new ViewPager.SimpleOnPageChangeListener();
		mCircleIndicator.setOnPageChangeListener(op_listener);
		
		btn_setting = (ImageView)findViewById(R.id.img_account);
		btn_setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent= new Intent(mContext, ChangePasswordActivity.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.anim_diam);
			}
		});
		
	}

	private class PagerAdapter extends FragmentStatePagerAdapter{

		public PagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return ViewPagerFragment.create(arg0);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return NUM_PAGES;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.splashscreen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_changepassword:
			Intent intent= new Intent(mContext, ChangePasswordActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.anim_diam);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
