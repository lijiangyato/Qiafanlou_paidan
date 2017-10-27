package com.ings.gogopaidan.utils;

import android.util.Log;

/**
 * Created by Administrator on 2015/10/20.
 */
public class LogUtils {
	/**
	 * 是否 发布 程序 如果发布，改成 true 测试使用 false
	 */
	private static boolean release = false;

	public static void e(String tag, String msg) {
		if (release) {
			return;
		}
		Log.e(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (release) {
			return;
		}
		Log.i(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (release) {
			return;
		}
		Log.v(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (release) {
			return;
		}
		Log.d(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (release) {
			return;
		}
		Log.w(tag, msg);
	}

	public static void logE(String tag, String content) {
		int p = 2048;
		long length = content.length();
		if (length < p || length == p)
			Log.e(tag, content);
		else {
			while (content.length() > p) {
				String logContent = content.substring(0, p);
				content = content.replace(logContent, "");
				Log.e(tag, logContent);
			}
			Log.e(tag, content);
		}
	}
}
