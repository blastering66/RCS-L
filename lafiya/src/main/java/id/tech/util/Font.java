package id.tech.util;


import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;

public class Font  {
	
	public static Typeface mFont, mFontBold = null;
	
	public static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();
	private static final String url_font_reklame = "font/reklame_script_regular.otf";
	private static final String url_font_pacifico = "font/pacifico.ttf";
	private static final String url_font_arial = "font/arial.ttf";
	private static final String url_font_arial_bold = "font/arial_bold.ttf";
	private static final String url_font_arial_bold_italic = "font/arial_bold_italic.ttf";
	private static final String url_font_arial_italic = "font/arial_italic.ttf";
	
//	public static TypeFace createFromAsset(AssetManager mnr, String path);
	public static Typeface reklameFont(Context c) {
		synchronized (cache) {
			if (!cache.containsKey(url_font_reklame)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							url_font_reklame);
					cache.put(url_font_reklame, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(url_font_reklame);
		}
	}
	
	public static Typeface pasificoFont(Context c) {
		synchronized (cache) {
			if (!cache.containsKey(url_font_pacifico)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							url_font_pacifico);
					cache.put(url_font_pacifico, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(url_font_pacifico);
		}
	}
	
	public static Typeface arialFont(Context c) {
		synchronized (cache) {
			if (!cache.containsKey(url_font_arial)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							url_font_arial);
					cache.put(url_font_arial, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(url_font_arial);
		}
	}
	
	public static Typeface arialItalicFont(Context c) {
		synchronized (cache) {
			if (!cache.containsKey(url_font_arial_italic)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							url_font_arial_italic);
					cache.put(url_font_arial_italic, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(url_font_arial_italic);
		}
	}

	public static Typeface arialBoldItalicFont(Context c) {
		synchronized (cache) {
			if (!cache.containsKey(url_font_arial_bold_italic)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							url_font_arial_bold_italic);
					cache.put(url_font_arial_bold_italic, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(url_font_arial_bold_italic);
		}
	}
	
	public static Typeface arialBoldFont(Context c) {
		synchronized (cache) {
			if (!cache.containsKey(url_font_arial_bold)) {
				try {
					Typeface t = Typeface.createFromAsset(c.getAssets(),
							url_font_arial_bold);
					cache.put(url_font_arial_bold, t);
				} catch (Exception e) {
					return null;
				}
			}
			return cache.get(url_font_arial_bold);
		}
	}
	
	
}