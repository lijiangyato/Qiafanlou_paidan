package com.ings.gogopaidan.activity.ui;

import android.os.Bundle;
import android.util.DisplayMetrics;

public class BaseActivity extends com.ings.gogopaidan.base.BaseActivity {

	private int mWidth;
	private int mHeight;
	private float mDensity;

	// 双击退出 第一次点击的时间设置为0

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ActionBar actionBar = getActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(false); // 显示返回
		// actionBar.setDisplayShowHomeEnabled(false); // 显示logo
		DisplayMetrics dm = new DisplayMetrics();
		// 初始化
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		mWidth = dm.widthPixels;
		mHeight = dm.heightPixels;
		mDensity = dm.density;

	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
	}

}
