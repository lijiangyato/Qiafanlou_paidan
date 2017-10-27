package com.ings.gogopaidan.activity;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;
import com.ings.gogopaidan.R;
import com.ings.gogopaidan.activity.ui.BaseActivity;
import com.ings.gogopaidan.application.UILApplication;
import com.ings.gogopaidan.base.BaseData;
import com.ings.gogopaidan.db.MyShopSqlHelper;
import com.ings.gogopaidan.entity.RegistEntity;
import com.ings.gogopaidan.entity.User;
import com.ings.gogopaidan.save.SaveLong;
import com.ings.gogopaidan.utils.JudgeTelNum;
import com.ings.gogopaidan.utils.LogUtils;
import com.ings.gogopaidan.utils.StringMD5Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener {
    private static final int GET_DATA_OK = 1;
    private static final int LOGIN_ERROR = 2;
    private static final int GET_DATA_OK1 = 3;
    // 返回父界面
    private ImageView mLoginBackToParent;
    // 跳转到regist
    private TextView mLoginIntent2Regist;
    // 输入电话号码
    private EditText mLoginTelNumEdt;
    // 密码
    private EditText mLoginPwdEdt;
    // 登录按钮
    private Button mLoginLoginBt;
    // 找回密码
    private Button mLoginFindPwdBt;
    // 短信登录
    private RegistEntity mLoginEntity;
    private String tem1;
    private String tem2;
    private String jiequ;
    private String jiequ2;
    public String aspnetauth;
    public String ASP_NET_SessionId;
    private String username;
    private String password;
    private UILApplication myApplication;
    private MyShopSqlHelper mySqlHelper;
    private SQLiteDatabase db;
    private List<User> users = new ArrayList<User>();
    private String biao = "biaoshi";
    private String bcan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.layout_loginbynamepwd);
        initViews();
        inidata();


   Log.e("lijiangjiang",sHA1(this));

        myApplication = (UILApplication) getApplication();
        mySqlHelper = new MyShopSqlHelper(getApplicationContext(), "GOGO",
                null, 1);
        db = mySqlHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from user", null);
        while (cursor.moveToNext()) {
            users.add(new User(cursor.getString(1), cursor.getString(2)));
        }

        if (users.size() != 0) {
            for (int i = 0; i < users.size(); i++) {
                username = users.get(i).getName();
                password = users.get(i).getPwd();

            }
            LogUtils.e("数据库的大小", users.size() + "  " + username + "  "
                    + password);
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    login(username, password);
                }
            }).start();

        } else {
            return;
        }
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]).toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



    public void inidata() {

        Map<String, String> map = SaveLong.getMap(this);
        if (map != null) {
            String username = map.get("naem");
            String password = map.get("Prasspe");
            String tiaobiaoshi = map.get("bei");
            mLoginTelNumEdt.setText(username);
            mLoginPwdEdt.setText(password);

            if (tiaobiaoshi.equals("beizhu")) {

            }

        }
    }

    protected void login(String username2, String password2) {
        // TODO Auto-generated method stub
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("phone", username2).add("loginkey", password2).build();
        Request request = new Request.Builder().url(BaseData.LOGIN_URL)
                .post(requestBody).build();
        Response response = null;

        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String loginBody = response.body().string();
                LogUtils.e("手动登录结果--》》", loginBody);
                Gson gson = new Gson();
                mLoginEntity = gson.fromJson(loginBody, RegistEntity.class);
                if (mLoginEntity.getSuccess().equals(true)) {
                    tem1 = response.headers().value(4).toString();// asp.net
                    tem2 = response.headers().value(7).toString();// aspnetauth
                    LogUtils.e("Login--->>tem2", tem2);
                    jiequ = tem1.substring(tem1.indexOf("ASP.NET_SessionId="),
                            tem1.indexOf("; path=/;"));
                    ASP_NET_SessionId = jiequ;
                    jiequ2 = tem2.substring(tem2.indexOf("aspnetauth="),
                            tem2.indexOf("; path=/;"));
                    aspnetauth = jiequ2;
                    LogUtils.e("ASP_NET_SessionId--》》", ASP_NET_SessionId);
                    LogUtils.e("aspnetauth", aspnetauth);
                    Message msg = handler.obtainMessage(GET_DATA_OK1);
                    msg.sendToTarget();
                } else {
                    Message msg = handler.obtainMessage(LOGIN_ERROR);
                    msg.sendToTarget();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    private void initViews() {
        // TODO Auto-generated method stub
        mLoginBackToParent = (ImageView) this
                .findViewById(R.id.login_topbackToParentBt);
        mLoginBackToParent.setOnClickListener(this);
        mLoginIntent2Regist = (TextView) this.findViewById(R.id.login_registTv);
        mLoginIntent2Regist.setOnClickListener(this);
        mLoginTelNumEdt = (EditText) this.findViewById(R.id.login_inputNameEdt);
        mLoginPwdEdt = (EditText) this.findViewById(R.id.login_inputPwdEdt);
        mLoginLoginBt = (Button) this.findViewById(R.id.login_loginBt);
        mLoginLoginBt.setOnClickListener(this);
        mLoginFindPwdBt = (Button) this.findViewById(R.id.login_findPwdBt);
        mLoginFindPwdBt.setOnClickListener(this);

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login_topbackToParentBt:
                this.finish();
                break;
            case R.id.login_loginBt:
                if (!telNumIsOk()) {

                    return;
                }
                if (!pwdIsOk()) {

                    return;
                }
                if (JudgeTelNum.isTelNum(mLoginTelNumEdt.getText().toString().trim()) == true) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub

                            login();



                        }
                    }).start();
                } else {
                    showToastLong(R.string.telnumerrorremian);
                }
                break;
            case R.id.login_findPwdBt:
                Intent intent2FindPwd = new Intent(getApplicationContext(),
                        FindPwdGetIdentifyCodeActivity.class);
                startActivity(intent2FindPwd);

                break;

            default:
                break;
        }

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case GET_DATA_OK:
                    myApplication.setASP_NET_SessionId(ASP_NET_SessionId);
                    myApplication.setAspnetauth(aspnetauth);
                    ContentValues values = new ContentValues();
                    values.put("name", mLoginTelNumEdt.getText().toString());
                    values.put("pwd", StringMD5Utils.MD5(mLoginPwdEdt.getText().toString()));
                    db.insert("user", null, values);
                    Intent intent2Person = new Intent(getApplicationContext(),
                            FirstPageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("phoneNum", mLoginTelNumEdt.getText().toString());
                    intent2Person.putExtras(bundle);
                    startActivity(intent2Person);

                    break;
                case GET_DATA_OK1:
                    myApplication.setASP_NET_SessionId(ASP_NET_SessionId);
                    myApplication.setAspnetauth(aspnetauth);

                    Intent intent2Person1 = new Intent(getApplicationContext(),
                            FirstPageActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("phoneNum", username);
                    intent2Person1.putExtras(bundle1);
                    startActivity(intent2Person1);

                    break;
                case LOGIN_ERROR:
                    showToastLong(mLoginEntity.getMsg());
                    break;

                default:
                    break;
            }

        }

        ;
    };

    protected void login() {
        // TODO Auto-generated method stub


        SaveLong.laibao(mLoginTelNumEdt.getText().toString(), mLoginPwdEdt.getText().toString(), "beizhu");
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormEncodingBuilder()
                .add("phone", mLoginTelNumEdt.getText().toString().trim())
                .add("loginkey", StringMD5Utils.MD5(mLoginPwdEdt.getText().toString().trim())).build();

        Request request = new Request.Builder().url(BaseData.LOGIN_URL).post(requestBody).build();
        Response response = null;

        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String loginBody = response.body().string();
                String loginhead = response.headers().toString();
                LogUtils.e("手动登录结果--》》", loginBody);
                Gson gson = new Gson();
                mLoginEntity = gson.fromJson(loginBody, RegistEntity.class);
                if (mLoginEntity.getSuccess().equals(true)) {
                    tem1 = response.headers().value(3).toString();// asp.net
                    tem2 = response.headers().value(6).toString();// aspnetauth
                    LogUtils.e("Login--->>tem2", tem2);
                    jiequ = tem1.substring(tem1.indexOf("ASP.NET_SessionId="),
                            tem1.indexOf("; path=/;"));
                    ASP_NET_SessionId = jiequ;
                    jiequ2 = tem2.substring(tem2.indexOf("aspnetauth="),
                            tem2.indexOf("; path=/;"));
                    aspnetauth = jiequ2;
                    LogUtils.e("ASP_NET_SessionId--》》", ASP_NET_SessionId);
                    LogUtils.e("aspnetauth", aspnetauth);
                    Message msg = handler.obtainMessage(GET_DATA_OK);
                    msg.sendToTarget();
                } else {
                    Message msg = handler.obtainMessage(LOGIN_ERROR);
                    msg.sendToTarget();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

    private boolean telNumIsOk() {

        if (TextUtils.isEmpty(mLoginTelNumEdt.getText())) {
            showToastShort(R.string.telnumnotnull);
            return false;
        }

        return true;
    }

    private boolean pwdIsOk() {
        String pwd = mLoginPwdEdt.getText().toString().trim();
        if (TextUtils.isEmpty(mLoginPwdEdt.getText())) {
            showToastShort(R.string.pwdnotnull);
            return false;
        } else if (pwd.length() < 6) {
            showToastShort(R.string.pwdisshort);
            return false;
        }
        return true;
    }

    // 双击退出
    // // 双击退出 第一次点击的时间设置为0
    private long firstTime = 0;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
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
