package id.tech.util;

import id.tech.rcshospital.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DialogActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.layout_dialog);
		TextView tv_judul = (TextView)findViewById(R.id.dialog_judul);
		TextView tv_message = (TextView)findViewById(R.id.dialog_message);
		Button btn = (Button)findViewById(R.id.btn_ok);
		
		tv_judul.setTypeface(Font.arialFont(getApplicationContext()));
		tv_message.setTypeface(Font.arialFont(getApplicationContext()));
		btn.setTypeface(Font.arialFont(getApplicationContext()));
		
		String cMessage = getIntent().getExtras().getString("dialog_message");
//		String cMessage = "MEssage";
		tv_message.setText(cMessage);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

}
