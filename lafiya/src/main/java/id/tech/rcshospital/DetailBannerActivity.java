package id.tech.rcshospital;

import id.tech.rcshospital.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailBannerActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_detailbanner);
		
		final int index = getIntent().getExtras().getInt("id_banner");

		ImageView imgIklan = (ImageView) findViewById(R.id.img_banner_detail);

		switch (index) {
		case 0:
			imgIklan.setBackgroundResource(R.drawable.iklan1);
			break;

		case 1:
			imgIklan.setBackgroundResource(R.drawable.iklan2);
			break;

		case 2:
			imgIklan.setBackgroundResource(R.drawable.iklan3);
			break;

		case 3:
			imgIklan.setBackgroundResource(R.drawable.iklan4);
			break;

		case 4:
			imgIklan.setBackgroundResource(R.drawable.iklan5);
			break;

		default:
			break;
		}
	}

}
