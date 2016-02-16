package id.tech.rcshospital;

import id.tech.rcshospital.R;
import id.tech.util.PublicFunction;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class BannerDialog extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_banner);

		final int index = getIntent().getExtras().getInt("id_banner");

		ImageView imgIklan = (ImageView) findViewById(R.id.img_banner);

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

		Button btn = (Button) findViewById(R.id.btn_readmore_iklan);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PublicFunction.BukaDetailBanner(BannerDialog.this, index);
				finish();
			}
		});
	}

}
