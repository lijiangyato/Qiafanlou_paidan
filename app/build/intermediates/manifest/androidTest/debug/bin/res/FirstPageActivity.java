package com.ings.gogopaidan.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.ings.gogopaidan.R;
import com.ings.gogopaidan.activity.ui.BaseActivity;
import com.ings.gogopaidan.application.UILApplication;
import com.ings.gogopaidan.db.MyShopSqlHelper;
import com.ings.gogopaidan.entity.User;
import com.ings.gogopaidan.utils.LogUtils;

public class FirstPageActivity extends BaseActivity implements OnClickListener {
	private Button mCheckOrderBt;
	private Button mExitBt;
	private UILApplication myApplication;
	private String ASP_NET_SessionId;
	private String aspnetauth;
	private RadioGroup mMyRG;
	// ������ʾ��ʽ 1 λ��������(Ĭ�Ϸ�ʽ) 2 ���� 3 ����
	private String mRemindWay = "1";
	private MyShopSqlHelper mySqlHelper;
	private SQLiteDatabase db;
	private String telNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_firstactivity);
		myApplication = (UILApplication) getApplication();
		ASP_NET_SessionId = myApplication.getASP_NET_SessionId();
		aspnetauth = myApplication.getAspnetauth();
		LogUtils.e("firstActivity cookie msg", ASP_NET_SessionId + aspnetauth);
		mySqlHelper = new MyShopSqlHelper(getApplicationContext(), "GOGO",
				null, 1);
		db = mySqlHelper.getWritableDatabase();
		Bundle bundle = this.getIntent().getExtras();
		telNum = bundle.getString("phoneNum");
		LogUtils.e("��¼����Ϣ0000", telNum);
		initViews();

	}

	private void initViews() {
		// TODO Auto-generated method stub
		mCheckOrderBt = (Button) this.findViewById(R.id.first_CheckOrderBt);
		mCheckOrderBt.setOnClickListener(this);
		mExitBt = (Button) this.findViewById(R.id.first_ExitBt);
		mExitBt.setText("�˳��˺ţ�" + telNum + "��");
		mExitBt.setOnClickListener(this);
		mMyRG = (RadioGroup) this.findViewById(R.id.first_myRadioGroup);
		mMyRG.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId == R.id.checkBox1) {
					mRemindWay = "1";
				} else if (checkedId == R.id.checkBox2) {
					mRemindWay = "2";
				} else if (checkedId == R.id.checkBox3) {
					mRemindWay = "3";
				}
				RadioButton rb = (RadioButton) findViewById(checkedId);
				// showToastLong(rb.getText().toString() + mRemindWay);
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.first_CheckOrderBt:
			Intent intent = new Intent(getApplicationContext(), MainPageActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("remindWay", mRemindWay);
			intent.putExtras(bundle);
			startActivity(intent);

			break;
		case R.id.first_ExitBt:
			exitDialog();
			break;

		default:
			break;
		}
	}

	private void exitDialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
				.setIcon(R.drawable.tishi)
				.setTitle("��ܰ��ʾ")
				.setMessage("ȷ���˳���")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// Cursor cursor = db.rawQuery("DELETE FROM user",
						// null);
						// while (cursor.moveToNext()) {
						// dialog.dismiss();
						// Intent intent = new Intent(getApplicationContext(),
						// LoginActivity.class);
						// startActivity(intent);
						// }
						db.execSQL("DELETE FROM user");
						dialog.dismiss();
						Intent intent = new Intent(getApplicationContext(),
								LoginActivity.class);
						startActivity(intent);
						// if (LoginActivity.editor != null) {
						// LoginActivity loginActivity = new LoginActivity();
						// loginActivity.clearData();
						// myApplication.setASP_NET_SessionId("");
						// myApplication.setAspnetauth("");
						// dialog.dismiss();
						// Intent intent = new Intent(getApplicationContext(),
						// LoginActivity.class);
						// startActivity(intent);
						// } else {
						// LogUtils.e("", "");
						// }
						//
						// }
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).show();
	}

	// ˫���˳�
	// // ˫���˳� ��һ�ε����ʱ������Ϊ0
	private long firstTime = 0;

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 2000) {
				Toast.makeText(this, "�ٰ�һ���˳�", Toast.LENGTH_SHORT).show();
				firstTime = System.currentTimeMillis();
				return true;
			} else {
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				startActivity(intent);
				System.exit(0);
				finish();
			}
		}
		return super.onKeyUp(keyCode, event);
	}

}
