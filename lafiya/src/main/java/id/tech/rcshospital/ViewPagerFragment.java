package id.tech.rcshospital;

import id.tech.rcshospital.R;
import id.tech.util.Font;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPagerFragment extends Fragment {
	public static final String ARG_PAGE = "pages";
	private static int mPageNumber;

	LayoutInflater myInflater;
	Context myContext;

	public Integer[] mImageMenu = { R.drawable.img_vp_reminder,
			R.drawable.img_vp_appointment,
			R.drawable.img_vp_appointment_tracker, R.drawable.img_vp_news,
			R.drawable.img_vp_direction, R.drawable.img_vp_call, R.drawable.img_vp_finddoctor };

	public String[] mJudul = { "Reminder", "Medical History", "Appoinment Tracker",
			"Article", "Directions", "Call Us", "Find Doctor"};
	
	private String[] gambarUrl = {"http://rcs-indonesia.com/droidmagzid_img/MadeInIndonesia.jpg",
			"http://rcs-indonesia.com/droidmagzid_img/Apps.jpg", 
			"http://rcs-indonesia.com/droidmagzid_img/Berita.jpg", 
			"http://rcs-indonesia.com/droidmagzid_img/Droid101.jpg", 
			"http://rcs-indonesia.com/droidmagzid_img/editor.jpg", "http://rcs-indonesia.com/droidmagzid_img/Features.jpg"};

	public Class<?>[] mIntent = { ReminderActivity.class,
			DirectionActivity.class, DirectionActivity.class,
			ArticleActivity.class, DirectionActivity.class, CallActivity.class, FindDoctorActivity.class };

	public ViewPagerFragment() {

	}

	public static ViewPagerFragment create(int pageNumber) {
		ViewPagerFragment fragment = new ViewPagerFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		myContext = getActivity().getApplicationContext();
		mPageNumber = getArguments().getInt(ARG_PAGE);

		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.layout_frag_vp, null);

		ImageView img = (ImageView) rootView.findViewById(R.id.img_viewpager);
		
//		ImageSlideRidho.slideImageView(myContext, gambarUrl, img, ImageSlideRidho.DURATION_NORMAL);
		
//		Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), mImageMenu[mPageNumber]);		
//		img.setImageBitmap(BlurBuilder.blur(myContext, imageBitmap, BlurBuilder.BITMAP_SCALE_LOW, BlurBuilder.BLUR_RADIUS_LOW));
		img.setImageResource(mImageMenu[mPageNumber]);
		TextView judul = (TextView) rootView.findViewById(R.id.txt_judul);
		judul.setText(mJudul[mPageNumber]);
		judul.setTypeface(Font.pasificoFont(myContext));
		switch (mPageNumber) {
		case 0:
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent loadIsi = new Intent(myContext, ReminderActivity.class);
					startActivity(loadIsi);
					getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.anim_diam);
				}
			});

			break;

		case 1:
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent loadIsi = new Intent(myContext, MedicalHistoryActivity.class);
					startActivity(loadIsi);
					getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.anim_diam);
				}
			});

			break;

		case 2:
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent loadIsi = new Intent(myContext, AppoinmentTrackerActivity.class);
					startActivity(loadIsi);
					getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.anim_diam);
				}
			});

			break;

		case 3:
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent loadIsi = new Intent(myContext, ArticleActivity.class);
					startActivity(loadIsi);
					getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.anim_diam);
				}
			});

			break;

		case 4:
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent loadIsi = new Intent(myContext, DirectionActivity.class);
					startActivity(loadIsi);
					getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.anim_diam);
				}
			});

			break;

		case 5:
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent loadIsi = new Intent(myContext, CallActivity.class);
					startActivity(loadIsi);
					getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.anim_diam);
				}
			});

			break;
			
		case 6:
			img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent loadIsi = new Intent(myContext, FindDoctorActivity.class);
					startActivity(loadIsi);
					getActivity().overridePendingTransition(R.anim.slide_in_from_bottom, R.anim.anim_diam);
				}
			});

			break;

		default:
			break;
		}

		

		return rootView;
	}

}
