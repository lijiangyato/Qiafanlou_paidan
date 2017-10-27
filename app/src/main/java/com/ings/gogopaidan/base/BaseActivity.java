package com.ings.gogopaidan.base;

import com.ings.gogopaidan.R;
import com.ings.gogopaidan.utils.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * 所有 的activity 通用的事情 title back
 * 
 * height width
 * 
 * desity
 * 
 * activity 管理
 */
public class BaseActivity extends FragmentActivity {
	public static DisplayImageOptions options;
	static {
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.product_loading)
				.showImageOnFail(R.drawable.product_loading)
				.resetViewBeforeLoading(true).cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565).considerExifParams(true)
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}

	private String tag;

	// activity
	ActivityStack activityStack = ActivityStack.getInstance();

	// log toast Context startacity startservice eg.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activityStack.push(this);
		tag = this.getClass().getSimpleName();
	}

	@Override
	protected void onDestroy() {
		activityStack.pop(this);
		super.onDestroy();
	}

	public void showToastLong(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	public void showToastLong(int msgRes) {
		showToastLong(getResources().getString(msgRes));
	}

	public void showToastShort(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void showToastShort(int msgRes) {
		showToastShort(getResources().getString(msgRes));
	}

	public void loge(String msg) {
		LogUtils.e(tag, msg);
	}

	public void logd(String msg) {
		LogUtils.d(tag, msg);
	}

	public void logw(String msg) {
		LogUtils.w(tag, msg);
	}

	public void logv(String msg) {
		LogUtils.v(tag, msg);
	}

	public void logi(String msg) {
		LogUtils.i(tag, msg);
	}
}
