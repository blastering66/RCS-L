package id.tech.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;

public class ServiceHandlerJSON {
	static String respose = null;
	static String url = null;
	public ServiceHandlerJSON() {

	}

	public String getReminder(List<NameValuePair> params) {
		
		try {
			url = ParameterCollection.URL_GETREM; 
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse hResponse = null;

			if (params != null) {
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url = url + paramString;
			}

			HttpGet hGEt = new HttpGet(url);
			hResponse = hClient.execute(hGEt);
			entity = hResponse.getEntity();
			
			respose = EntityUtils.toString(entity);
		}catch(HttpHostConnectException e){
			respose = e.getMessage().toString();
		}
		
		catch (UnsupportedEncodingException e) {
			respose = e.getMessage().toString();

		} catch (ClientProtocolException e) {
			// TODO: handle exception
			respose = e.getMessage().toString();
		} catch (IOException e) {
			// TODO: handle exception
			respose = e.getMessage().toString();
		}

		return respose;
	}
	
	public String getMedicalHistory(List<NameValuePair> params) {
		
		try {
			url = ParameterCollection.URL_GETMEDHISTORY;
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse hResponse = null;

			if (params != null) {
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url = url  + paramString;
			}

			HttpGet hGEt = new HttpGet(url);
			hResponse = hClient.execute(hGEt);
			entity = hResponse.getEntity();
			respose = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {

		} catch (ClientProtocolException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO: handle exception
		}

		return respose;
	}
//	
	public String getAppoinment(List<NameValuePair> params) {
		try {
			url = ParameterCollection.URL_GETAPPOINTMENT;
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse hResponse = null;

			if (params != null) {
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url = url  + paramString;
			}

			HttpGet hGEt = new HttpGet(url);
			hResponse = hClient.execute(hGEt);
			entity = hResponse.getEntity();
			respose = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {
			respose = e.getMessage().toString();
		} catch (ClientProtocolException e) {
			// TODO: handle exception
			respose = e.getMessage().toString();
		} catch (IOException e) {
			// TODO: handle exception
			respose = e.getMessage().toString();
		}

		return respose;
	}
	

	public String postAppoinment(List<NameValuePair> params) {
		try {
			url = ParameterCollection.URL_POST_APPOINMENT;
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse hResponse = null;

			if (params != null) {
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url = url + paramString;
			}

			HttpGet hGEt = new HttpGet(url);
			hResponse = hClient.execute(hGEt);
			entity = hResponse.getEntity();
			respose = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {

		} catch (ClientProtocolException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO: handle exception
		}

		return respose;
	}
	
	public String postChangeUser(List<NameValuePair> params) {
		try {
			url = ParameterCollection.URL_CHANGE_PASS;
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse hResponse = null;

			if (params != null) {
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url = url + paramString;
			}

			HttpGet hGEt = new HttpGet(url);
			hResponse = hClient.execute(hGEt);
			entity = hResponse.getEntity();
			respose = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {

		} catch (ClientProtocolException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO: handle exception
		}

		return respose;
	}
	
	public String postRegister(List<NameValuePair> params) {
		try {
			url = ParameterCollection.URL_REGISTER_PASIEN;
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse hResponse = null;

			if (params != null) {
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url = url + paramString;
			}

			HttpGet hGEt = new HttpGet(url);
			hResponse = hClient.execute(hGEt);
			entity = hResponse.getEntity();
			respose = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {

		} catch (ClientProtocolException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO: handle exception
		}

		return respose;
	}
	
//	public String getPostReminder(String url, List<NameValuePair> params) {
//		try {
//			DefaultHttpClient hClient = new DefaultHttpClient();
//			HttpEntity entity = null;
//			HttpResponse hResponse = null;
//
//			if (params != null) {
//				String paramString = URLEncodedUtils.format(params, "utf-8");
//				url = url + "?idP" + paramString;
//			}
//
//			HttpGet hGEt = new HttpGet(url);
//			hResponse = hClient.execute(hGEt);
//			entity = hResponse.getEntity();
//			respose = EntityUtils.toString(entity);
//		} catch (UnsupportedEncodingException e) {
//
//		} catch (ClientProtocolException e) {
//			// TODO: handle exception
//		} catch (IOException e) {
//			// TODO: handle exception
//		}
//
//		return respose;
//	}
	
	public String getAllDoctor() {
		try {
			url = ParameterCollection.URL_GETALLDOCTOR;
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse hResponse = null;

			HttpGet hGEt = new HttpGet(url);
			hResponse = hClient.execute(hGEt);
			entity = hResponse.getEntity();
			respose = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {

		} catch (ClientProtocolException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO: handle exception
		}

		return respose;
	}
	
	public String getFindDoctor(List<NameValuePair> params) {
		try {
			url = ParameterCollection.URL_GETDOCTORINFO;
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse hResponse = null;

			if (params != null) {
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url = url + paramString;
			}

			HttpGet hGEt = new HttpGet(url);
			hResponse = hClient.execute(hGEt);
			entity = hResponse.getEntity();
			respose = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {

		} catch (ClientProtocolException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO: handle exception
		}

		return respose;
	}
	
	public String postLOGIN(List<NameValuePair> params) {
		try {
			url = ParameterCollection.URL_LOGIN;
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse hResponse = null;

			if (params != null) {
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url = url + paramString;
			}

			HttpGet hGEt = new HttpGet(url);
			hResponse = hClient.execute(hGEt);
			entity = hResponse.getEntity();
			respose = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {

		} catch (ClientProtocolException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO: handle exception
		}

		return respose;
	}
//	
	public String getInfoRS(List<NameValuePair> params) {
		try {
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity entity = null;
			HttpResponse hResponse = null;

			if (params != null) {
				url = ParameterCollection.URL_HOSPITALINFO;			
				String paramString = URLEncodedUtils.format(params, "utf-8");
				url = url + "?"+ paramString;
			}else{
				url =ParameterCollection.URL_HOSPITALINFO;
			}

			HttpGet hGEt = new HttpGet(url);
			hResponse = hClient.execute(hGEt);
			entity = hResponse.getEntity();
			respose = EntityUtils.toString(entity);
		} catch (UnsupportedEncodingException e) {

		} catch (ClientProtocolException e) {
			// TODO: handle exception
		} catch (IOException e) {
			// TODO: handle exception
		}

		return respose;
	}
	

}
