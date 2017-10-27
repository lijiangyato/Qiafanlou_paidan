package com.ings.gogopaidan.activity;

import com.ings.gogopaidan.R;
import com.ings.gogopaidan.R.layout;
import com.ings.gogopaidan.activity.ui.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;

public class WelcomActivity extends BaseActivity {
	private ImageView mBgPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_welcome);
		ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		@SuppressWarnings("deprecation")
		boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting();
		@SuppressWarnings("deprecation")
		boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.isConnectedOrConnecting();

		if (wifi | internet) {

			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent);

		} else {
			showToastLong("亲，您的网络连了么？");
		}
	}
}
