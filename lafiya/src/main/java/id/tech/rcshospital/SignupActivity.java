package id.tech.rcshospital;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import id.tech.rcshospital.R;
import id.tech.util.Font;
import id.tech.util.ParameterCollection;
import id.tech.util.ServiceHandlerJSON;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SignupActivity extends Activity {
	EditText et_NoKartu, et_Username, et_Pass1, et_Pass2;
	String cNoKartu, cUsername, cPass, cPass2, pesan_validasi = "";
	SharedPreferences sp;
	Button btn_Register;
	String pesan_error;
	Context ctx;
	View view_form;
	ImageView btn_showPass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_signup);
		ctx = this;

		sp = getSharedPreferences(ParameterCollection.SHARED_PREF_NAME,
				Context.MODE_PRIVATE);
		et_NoKartu = (EditText) findViewById(R.id.txt_nokartu);
		et_Username = (EditText) findViewById(R.id.txt_reg_username);
		et_Pass1 = (EditText) findViewById(R.id.txt_reg_pass);
		et_Pass2 = (EditText) findViewById(R.id.txt_reg_pass2);
		btn_Register = (Button) findViewById(R.id.btn_register);
		btn_showPass = (ImageView) findViewById(R.id.btn_showPass);
		

		// setTypeFace
		et_NoKartu.setTypeface(Font.arialFont(ctx));
		et_Username.setTypeface(Font.arialFont(ctx));
		et_Pass1.setTypeface(Font.arialFont(ctx));
		et_Pass2.setTypeface(Font.arialFont(ctx));
		btn_Register.setTypeface(Font.arialFont(ctx));

		view_form = (View) findViewById(R.id.layout_form);
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_in_from_bottom);
		view_form.startAnimation(anim);

		btn_Register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cNoKartu = et_NoKartu.getText().toString();
				cUsername = et_Username.getText().toString();
				cPass = et_Pass1.getText().toString();
				cPass2 = et_Pass2.getText().toString();

				if (!cPass.equals(cPass2)) {
					pesan_validasi = "Password tidak sama";
					;
				} else if (cNoKartu.equals("") || cNoKartu.isEmpty()) {
					pesan_validasi = pesan_validasi
							+ " -- Isi No Kartu Anda -- ";
				} else if (cUsername.equals("") || cUsername.isEmpty()) {
					pesan_validasi = pesan_validasi
							+ "-- Isi Username yang anda inginkan --";
				} else {
					pesan_validasi = cNoKartu + cUsername + cPass;

					new RegisterPasienAsync().execute();

				}

			}
		});

		btn_showPass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				et_Pass1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				et_Pass2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			}
		});

	}

	private class RegisterPasienAsync extends AsyncTask<Void, Void, Void> {
		Dialog dialog;
		String response, message;
		int sof;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new Dialog(ctx);
			dialog.setTitle(R.string.toast_proses);
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ServiceHandlerJSON sh = new ServiceHandlerJSON();
			List<NameValuePair> paramnya = new ArrayList<NameValuePair>();
			paramnya.add(new BasicNameValuePair("cardNumber", cNoKartu));
			paramnya.add(new BasicNameValuePair("username", cUsername));
			paramnya.add(new BasicNameValuePair("password", cPass));

			response = sh.postRegister(paramnya);
			if (response != null) {
				try {
					JSONObject obj = new JSONObject(response);

					int SOF = obj.getInt(ParameterCollection.TAG_SOF);

					sof = SOF;
					if (SOF == 1) {
						message = "Register Sukses";
						sp.edit().putString("no_kartu", cNoKartu).commit();
						sp.edit().putBoolean("logged", true).commit();
						sp.edit().putString("username", cUsername).commit();
						sp.edit()
								.putString(
										"idPasien",
										obj.getString(ParameterCollection.TAG_LOGIN_IDPASIEN));
					} else {
						message = obj
								.getString(ParameterCollection.TAG_SOF_MESSAGE);
					}
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

			if (sof == 1) {
				Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG)
						.show();
				Intent loadMainMenu = new Intent(SignupActivity.this,
						MenuUtamaActivity.class);
				loadMainMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				loadMainMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(loadMainMenu);
				finish();
			} else {
				Toast.makeText(SignupActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}

		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent loadMainMenu = new Intent(SignupActivity.this,
				LoginSignupActivity.class);

		startActivity(loadMainMenu);

		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.slide_in_from_bottom);
		view_form.startAnimation(anim);
	}

}
