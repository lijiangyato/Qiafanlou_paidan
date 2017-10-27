package com.ings.gogopaidan.activity;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.ings.gogopaidan.R;
import com.ings.gogopaidan.activity.ui.BaseActivity;
import com.ings.gogopaidan.base.BaseData;
import com.ings.gogopaidan.entity.RegistEntity;
import com.ings.gogopaidan.utils.LogUtils;
import com.ings.gogopaidan.utils.StringMD5Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class FindPwdInputNewPwdActivity extends BaseActivity implements
		OnClickListener {
	private static final int GET_CODE_OK = 1;
	private ImageView mNewPwdBackToParent;
	private EditText mNewPwdInputEdt;
	private EditText mNewPwdAgainEdt;
	private Button mNewPwdConfirmChangeBt;
	private TextView mNewPwdPhoneNumTv;
	private String mTelNum;
	private String mIdentifyCode;
	private RegistEntity mRegistEntity;
	private CheckBox mPwdShowCB1;
	private CheckBox mPwdShowCB2;
	private StringBuilder sb;
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_CODE_OK:
				if (mRegistEntity.getSuccess().equals(true)) {
					showToastLong(mRegistEntity.getMsg());
					Intent intent2Ok = new Intent(getApplicationContext(),
							FindPwdSuccessActivity.class);
					startActivity(intent2Ok);
				} else {
					showToastLong(mRegistEntity.getMsg());
				}

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_findinputnewpwd);
		Bundle bundle = this.getIntent().getExtras();
		mTelNum = bundle.getString("telNum");
		mIdentifyCode = bundle.getString("IdentifyCode");
		LogUtils.e("电话号码和验证码", mTelNum + "和" + mIdentifyCode);
		if (!TextUtils.isEmpty(mTelNum) && mTelNum.length() > 6) {
			sb = new StringBuilder();
			for (int i = 0; i < mTelNum.length(); i++) {
				char c = mTelNum.charAt(i);
				if (i >= 3 && i <= 7) {
					sb.append('*');
				} else {
					sb.append(c);
				}
			}

		}
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		mNewPwdPhoneNumTv = (TextView) this
				.findViewById(R.id.newpwd_telNumTipsEdt);
		mNewPwdPhoneNumTv.setText("你正在设置账号" + sb.toString() + "的登录密码");
		mNewPwdInputEdt = (EditText) this.findViewById(R.id.newpwd_inputPwdEdt);
		mNewPwdAgainEdt = (EditText) this
				.findViewById(R.id.newpwd_inputPwdAgainEdt);
		mNewPwdConfirmChangeBt = (Button) this
				.findViewById(R.id.newpwd_confirmChangeBt);
		mNewPwdConfirmChangeBt.setOnClickListener(this);
		mNewPwdBackToParent = (ImageView) this
				.findViewById(R.id.newpwd_topbackToParentBt);
		mNewPwdBackToParent.setOnClickListener(this);
		mPwdShowCB1 = (CheckBox) this.findViewById(R.id.newpwd_isShowPwdCb);
		mPwdShowCB2 = (CheckBox) this.findViewById(R.id.newpwd_isShowPwdCb2);
		mPwdShowCB1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					// 如果选中，显示密码
					mNewPwdInputEdt
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
				} else {
					// 否则隐藏密码
					mNewPwdInputEdt
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
				}

			}

		});
		mPwdShowCB2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					// 如果选中，显示密码
					mNewPwdAgainEdt
							.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());
				} else {
					// 否则隐藏密码
					mNewPwdAgainEdt
							.setTransformationMethod(PasswordTransformationMethod
									.getInstance());
				}

			}

		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.newpwd_confirmChangeBt:
			if (!pwd1IsOk()) {

				return;
			}
			if (!pwd2IsOk()) {

				return;
			}
			if (mNewPwdInputEdt
					.getText()
					.toString()
					.trim()
					.equalsIgnoreCase(
							mNewPwdAgainEdt.getText().toString().trim())) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						checkRegistResult();
					}
				}).start();

			} else {
				showToastShort(R.string.pwdnosameto);
			}

			break;
		case R.id.newpwd_topbackToParentBt:
			this.finish();
			break;

		default:
			break;
		}
	}

	private void checkRegistResult() {
		// TODO Auto-generated method stub
		OkHttpClient okHttpClient = new OkHttpClient();
		RequestBody requestBody = new FormEncodingBuilder()
				.add("phone", mTelNum.trim())
				.add("smscode", mIdentifyCode.trim())
				.add("loginkey",
						StringMD5Utils.MD5(mNewPwdInputEdt.getText().toString()
								.trim())).build();

		Request request = new Request.Builder().url(BaseData.RESET_URL)
				.post(requestBody).build();
		Response response = null;

		try {
			response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()) {
				String checkRegistBody = response.body().string();
				LogUtils.e("验证注册结果--》》", checkRegistBody);
				Gson gson = new Gson();
				mRegistEntity = gson.fromJson(checkRegistBody,
						RegistEntity.class);
				Message msg = handler.obtainMessage(GET_CODE_OK);
				msg.sendToTarget();

			}
		} catch (IOException e) {
			e.printStackTrace();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	private boolean pwd1IsOk() {
		String pwd = mNewPwdInputEdt.getText().toString().trim();
		if (TextUtils.isEmpty(mNewPwdInputEdt.getText())) {
			showToastShort(R.string.pwdnotnull);
			return false;
		} else if (pwd.length() > 18 || pwd.length() < 6) {
			showToastShort(R.string.pwdisshort);
			return false;
		}
		return true;
	}

	private boolean pwd2IsOk() {
		String pwd = mNewPwdAgainEdt.getText().toString().trim();
		if (TextUtils.isEmpty(mNewPwdAgainEdt.getText())) {
			showToastShort(R.string.pwdnotnull);
			return false;
		} else if (pwd.length() > 18 || pwd.length() < 6) {
			showToastShort(R.string.pwdisshort);
			return false;
		}
		return true;
	}

}
