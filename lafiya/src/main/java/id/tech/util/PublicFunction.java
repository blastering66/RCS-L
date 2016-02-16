package id.tech.util;

import id.tech.rcshospital.BannerDialog;
import id.tech.rcshospital.DetailBannerActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class PublicFunction {
	
	public static boolean isInternetConnection(Context ctx) {

		ConnectivityManager connectivityManager = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			Log.e("KONEKSI", "ON");
			return true;

		} else {
			Log.e("KONEKSI", "OFF");
			return false;

		}
	}
	
	public static void BukaDetailBanner(Context ctx, int id){
		Intent load = new Intent(ctx, DetailBannerActivity.class);
		load.putExtra("id_banner", id);
		ctx.startActivity(load);		
	}
	
	public static void BukaBanner(Context ctx, int id){
		Intent load = new Intent(ctx, BannerDialog.class);
		load.putExtra("id_banner", id);
		ctx.startActivity(load);
	}

}
