package com.ings.gogopaidan.activity;

import com.ings.gogopaidan.R;
import com.ings.gogopaidan.activity.ui.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FindPwdSuccessActivity extends BaseActivity implements
		OnClickListener {

	private ImageView mFindBackToLoginIv;
	private TextView mFindBackToLoginTv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_findpwdok);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		mFindBackToLoginTv = (TextView) this
				.findViewById(R.id.findok_backToLoginTv);
		mFindBackToLoginTv.setOnClickListener(this);

		mFindBackToLoginIv = (ImageView) this
				.findViewById(R.id.findok_backToLoginIv);
		mFindBackToLoginIv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.findok_backToLoginTv:
			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent);

			break;

		case R.id.findok_backToLoginIv:
			Intent intent2 = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent2);
			break;

		default:
			break;
		}
	}

}
