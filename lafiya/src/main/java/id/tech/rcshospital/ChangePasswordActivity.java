package id.tech.rcshospital;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import id.tech.util.Font;
import id.tech.util.ParameterCollection;
import id.tech.util.ServiceHandlerJSON;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends Activity {
	EditText ed_Username, ed_OldPass, ed_newPass, ed_newPass2;
	Button btn_save, btn_logout, btn_cancel;
	String cUsername, cOldPass, cNewPass, cNewPass2;
	Context mContext;
	SharedPreferences sh;
	View view_form;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_account);
		mContext = this;

		sh = getSharedPreferences(ParameterCollection.SHARED_PREF_NAME,
				Context.MODE_PRIVATE);
		cUsername = sh.getString("username", "Guest");
		
		view_form = (View)findViewById(R.id.layout_form);
		Animation ani = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
		view_form.startAnimation(ani);
		
		ed_Username = (EditText) findViewById(R.id.txt_edit_username);
		ed_OldPass = (EditText) findViewById(R.id.txt_edit_oldpassword);
		ed_newPass = (EditText) findViewById(R.id.txt_edit_newpassword);
		ed_newPass2 = (EditText) findViewById(R.id.txt_edit_newpassword2);

		btn_save = (Button) findViewById(R.id.btn_save);
		btn_logout = (Button) findViewById(R.id.btn_logout);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		ed_Username.setTypeface(Font.arialFont(mContext));
		ed_OldPass.setTypeface(Font.arialFont(mContext));
		ed_newPass.setTypeface(Font.arialFont(mContext));
		ed_newPass2.setTypeface(Font.arialFont(mContext));
		btn_save.setTypeface(Font.arialFont(mContext));
		btn_logout.setTypeface(Font.arialFont(mContext));
		btn_cancel.setTypeface(Font.arialFont(mContext));

		ed_Username.setText(cUsername);

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent loadUtama = new Intent(mContext, MenuUtamaActivity.class);

				startActivity(loadUtama);
				finish();
				overridePendingTransition(R.anim.slide_in_from_top,
						R.anim.anim_diam);

			}
		});

		btn_save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cUsername = ed_Username.getText().toString();
				cOldPass = ed_OldPass.getText().toString();
				cNewPass = ed_newPass.getText().toString();
				cNewPass2 = ed_newPass2.getText().toString();

				if (cNewPass.equals(cNewPass2)
						&& (!cNewPass.isEmpty() && !cNewPass2.isEmpty())) {
					new PostChangePassword().execute();

				} else {
					Dialog dialog = new Dialog(mContext);
					dialog.setTitle(R.string.toast_password_notmatch);
					dialog.show();
				}
			}
		});

		btn_logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setMessage("Are you sure want to Logout ?");
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								sh.edit().clear().commit();
								Toast.makeText(mContext, "Logged out",
										Toast.LENGTH_SHORT).show();
								Intent loadLogin = new Intent(mContext,
										LoginSignupActivity.class);
								startActivity(loadLogin);
								finish();
								
								
							}

						});

				builder.setNegativeButton("No", null);

				builder.create();
				builder.show();
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent loadUtama = new Intent(mContext, MenuUtamaActivity.class);
		startActivity(loadUtama);
		finish();
		overridePendingTransition(R.anim.slide_in_from_top,
				R.anim.anim_diam);
	}

	private class PostChangePassword extends AsyncTask<Void, Void, Void> {
		private Dialog dialog;
		private String response, pesan;
		private int sof;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new Dialog(mContext);
			dialog.setTitle(R.string.toast_proses);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			List<NameValuePair> p = new ArrayList<NameValuePair>();
			p.add(new BasicNameValuePair("useraccount", cUsername));
			p.add(new BasicNameValuePair("oldPassword", cOldPass));
			p.add(new BasicNameValuePair("newPassword", cNewPass));

			response = sh.postChangeUser(p);

			if (response != null) {
				try {
					JSONObject obj = new JSONObject(response);

					sof = obj.getInt(ParameterCollection.TAG_SOF);
					pesan = obj.getString(ParameterCollection.TAG_SOF_MESSAGE);

				} catch (JSONException e) {

				}
			} else {
				response = "Failed to Get Data!";
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if (sof == 1) {
				Intent loadLogin = new Intent(mContext, MenuUtamaActivity.class);
				startActivity(loadLogin);
				finish();
				Toast.makeText(mContext, "Change Password success",
						Toast.LENGTH_LONG).show();
				overridePendingTransition(R.anim.slide_in_from_top,
						R.anim.anim_diam);
			} else {
				Dialog dialogPesan = new Dialog(mContext);
				dialogPesan.setTitle(pesan);
				dialogPesan.show();
				// Toast.makeText(mContext, pesan, Toast.LENGTH_LONG).show();
			}
		}

	}

}
