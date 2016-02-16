package id.tech.util;

import id.tech.rcshospital.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DialogPesan extends DialogFragment{
	String mPesan;
	
	public static DialogPesan newInstance(String pesan){
		DialogPesan dialog = new DialogPesan();
		Bundle args = new Bundle();
		args.putString("pesan", pesan);
		dialog.setArguments(args);
		return dialog;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mPesan = getArguments().getString("pesan");
		setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog);
		setCancelable(false);
	}	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragmentdialog_message, null);		
		TextView pesan = (TextView)view.findViewById(R.id.dialog_txt_pesan);
		pesan.setText(mPesan);		
//		Button btnOK = (Button)view.findViewById(R.id.dialog_btn_ok);
//		btnOK.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				dismiss();
//			}
//		});
		return view;
	}

}
