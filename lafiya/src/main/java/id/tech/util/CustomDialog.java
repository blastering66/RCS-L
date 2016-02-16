package id.tech.util;

import id.tech.rcshospital.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class CustomDialog extends DialogFragment{
	private String mPesan;
	
	public static CustomDialog newInstance(String pesan){
		CustomDialog dialog = new CustomDialog();
		Bundle bundle = new Bundle();
		bundle.putString("pesan", pesan);
		dialog.setArguments(bundle);
		return dialog;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mPesan = getArguments().getString("pesan");
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View rootView = inflater.inflate(R.layout.fragmentdialog_message, null);
		
		TextView txtPesan = (TextView)rootView.findViewById(R.id.dialog_txt_pesan);
		txtPesan.setText(mPesan);
		
		builder.setView(rootView)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).setCancelable(false);
		
		return builder.create();
	}

}
