package id.tech.rcshospital;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import id.tech.rcshospital.R;
import id.tech.util.CustomDialog;
import id.tech.util.DialogActivity;
import id.tech.util.DialogPesan;
import id.tech.util.Font;
import id.tech.util.ParameterCollection;
import id.tech.util.ServiceHandlerJSON;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginSignupActivity extends FragmentActivity{
	EditText ed_Username, ed_Pass;
	Button btn_Login;
	TextView tv_Signup,tv_Signup_Label,tv_Forgot;
	String cUsername, cPassword;
	SharedPreferences sp;
	boolean loging;
	LinearLayout view_form;
	ImageView btn_showPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		
		sp = getSharedPreferences(ParameterCollection.SHARED_PREF_NAME, Context.MODE_PRIVATE);	
		loging = sp.getBoolean("logged", false);
		
		if(loging){
			Intent loadMainMenu = new Intent(LoginSignupActivity.this,
					MenuUtamaActivity.class);
			loadMainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			loadMainMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(loadMainMenu);
			finish();
		}
		
		view_form = (LinearLayout)findViewById(R.id.layout_form);
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_bottom);
		view_form.startAnimation(anim);
		ed_Username = (EditText) findViewById(R.id.edittext_username);
		ed_Pass = (EditText) findViewById(R.id.edittext_pass);
		
		ed_Username.setTypeface(Font.arialFont(getApplicationContext()));
		ed_Pass.setTypeface(Font.arialFont(getApplicationContext()));
		
		btn_Login = (Button) findViewById(R.id.btn_login);
		btn_showPass = (ImageView)findViewById(R.id.btn_showPass);
		tv_Signup = (TextView) findViewById(R.id.txt_register);
		tv_Signup_Label = (TextView)findViewById(R.id.txt_register_label);
		tv_Forgot = (TextView)findViewById(R.id.txt_forgotpassword);
		
		btn_Login.setTypeface(Font.arialFont(getApplicationContext()));
		tv_Signup.setTypeface(Font.arialFont(getApplicationContext()));
		tv_Signup_Label.setTypeface(Font.arialFont(getApplicationContext()));
		tv_Forgot.setTypeface(Font.arialFont(getApplicationContext()));
		
		btn_Login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cUsername = ed_Username.getText().toString();
				cPassword = ed_Pass.getText().toString();

				if (TextUtils.isEmpty(cUsername)
						&& TextUtils.isEmpty(cPassword)) {
					ed_Username.setError(getResources().getString(
							R.string.edittext_error_emptyusername));
					ed_Pass.setError(getResources().getString(
							R.string.edittext_error_emptypassword));
				} else if (TextUtils.isEmpty(cUsername)) {
					ed_Username.setError(getResources().getString(
							R.string.edittext_error_emptyusername));
				} else if (TextUtils.isEmpty(cPassword)) {
					ed_Pass.setError(getResources().getString(
							R.string.edittext_error_emptypassword));
				} else {
					Animation anim = AnimationUtils
							.loadAnimation(getApplicationContext(),
									R.anim.slide_out_to_bottom);
					view_form.startAnimation(anim);

					new PostLogin().execute();
				}
			}
		});

		tv_Signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_to_bottom);
				view_form.startAnimation(anim);
				
				Intent loadMainMenu = new Intent(LoginSignupActivity.this,
						SignupActivity.class);
				loadMainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				loadMainMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(loadMainMenu);
				
				finish();
			}
		});
		
		btn_showPass.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				if(ed_Pass.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
					ed_Pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
				}else{
					ed_Pass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				}
				
			}
		});
		
		

	}

	private class PostLogin extends AsyncTask<Void, Void, Void> {
		private String response, message, idPasien;
		private Dialog dialog;
		private int berhasil;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new Dialog(LoginSignupActivity.this);
			dialog.setTitle(R.string.toast_proses);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			List<NameValuePair> p = new ArrayList<NameValuePair>();
			ServiceHandlerJSON sh = new ServiceHandlerJSON();

			p.add(new BasicNameValuePair("username", cUsername));
			p.add(new BasicNameValuePair("password", cPassword));
			response = sh.postLOGIN(p);

			if (response != null) {
				try {
					JSONObject jObj = new JSONObject(response);

					berhasil = jObj.getInt(ParameterCollection.TAG_SOF);

					if (berhasil > 0) {

						JSONArray jArray = jObj
								.getJSONArray(ParameterCollection.TAG_LOGIN_ITEM);

						for (int i = 0; i < jArray.length(); i++) {
							JSONObject c = jArray.getJSONObject(i);

							idPasien = c
									.getString(ParameterCollection.TAG_LOGIN_IDPASIEN);
						}
						
//						sp = getSharedPreferences(ParameterCollection.SHARED_PREF_NAME, Context.MODE_PRIVATE);				
						sp.edit().putString("no_kartu", cUsername).commit();
						sp.edit().putBoolean("logged", true).commit();
						sp.edit().putString("idPasien", idPasien).commit();
						sp.edit().putString("username", cUsername).commit();

					} 
					message = jObj
							.getString(ParameterCollection.TAG_SOF_MESSAGE);
				} catch (JSONException e) {

				}

			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			if (berhasil == 0) {
//				DialogFragment dialog = CustomDialog.newInstance(message);
//				dialog.show(getSupportFragmentManager(), "");
				Intent loadDialog = new Intent(LoginSignupActivity.this, DialogActivity.class);
				loadDialog.putExtra("dialog_message", message);
				startActivity(loadDialog);
//				Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
				
				cUsername = "";
				cPassword = "";
				
			} else {
				// Toast.makeText(LoginSignupActivity.this, "Login sukses...",
				// Toast.LENGTH_LONG).show();
				Intent loadMainMenu = new Intent(LoginSignupActivity.this,
						MenuUtamaActivity.class);
				loadMainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				loadMainMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(loadMainMenu);
				
				finish();
			}
		}

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		view_form = (LinearLayout)findViewById(R.id.layout_form);
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_from_bottom);
		view_form.startAnimation(anim);
		
//		ed_Pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}
}
