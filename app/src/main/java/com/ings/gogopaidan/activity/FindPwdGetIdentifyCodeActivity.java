package com.ings.gogopaidan.activity;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.google.gson.Gson;
import com.ings.gogopaidan.R;
import com.ings.gogopaidan.activity.ui.BaseActivity;
import com.ings.gogopaidan.base.BaseData;
import com.ings.gogopaidan.entity.RegistEntity;
import com.ings.gogopaidan.utils.CountDownTimerUtils;
import com.ings.gogopaidan.utils.JudgeTelNum;
import com.ings.gogopaidan.utils.LogUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class FindPwdGetIdentifyCodeActivity extends BaseActivity implements
        OnClickListener {
    private static final int GET_CODE_OK = 1;
    private static final int GET_CODE_ERROR = 2;
    // 返回父界面
    private ImageView mFindCodeBackToParent;
    // 获取验证码
    private Button mFindCodeGetIdenftCodeBt;
    // 倒计时的utils
    private CountDownTimerUtils mCountDownTimerUtils;
    // 填写获取到的验证码
    private EditText mFindCodeIdentifyCodeEdt;
    // 输入要找回的账号
    private EditText mFindCodeInputTelNum;
    // 下一步 需要校验验证码
    private Button mFindCodeNextStepBt;
    private RegistEntity mRegistEntity;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GET_CODE_OK:
                    if (mRegistEntity.getSuccess().equals(true)) {
                        Intent intent2NewPwd = new Intent(getApplicationContext(),
                                FindPwdInputNewPwdActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("telNum", mFindCodeInputTelNum.getText()
                                .toString().trim());
                        bundle.putString("IdentifyCode", mFindCodeIdentifyCodeEdt
                                .getText().toString().trim());
                        intent2NewPwd.putExtras(bundle);
                        startActivity(intent2NewPwd);
                    } else {
                        showToastLong(mRegistEntity.getMsg());
                    }

                    break;
                case GET_CODE_ERROR:
                    if (mRegistEntity.getSuccess().equals(false)) {
                        mCountDownTimerUtils.stop();
                        showToastLong(mRegistEntity.getMsg());
                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_findpwdgetcode);
        initView();

    }

    private void initView() {
        // TODO Auto-generated method stub
        mFindCodeGetIdenftCodeBt = (Button) this
                .findViewById(R.id.findcode_getIdenfityCodeBt);
        mFindCodeGetIdenftCodeBt.setOnClickListener(this);
        mCountDownTimerUtils = new CountDownTimerUtils(
                mFindCodeGetIdenftCodeBt, 60000, 1000);
        mFindCodeIdentifyCodeEdt = (EditText) this
                .findViewById(R.id.findcode_inputIdentifyCodeEdt);
        mFindCodeInputTelNum = (EditText) this
                .findViewById(R.id.findcode_inputTelNumCodeEdt);
        mFindCodeNextStepBt = (Button) this
                .findViewById(R.id.findcode_nextStepBt);
        mFindCodeNextStepBt.setOnClickListener(this);
        mFindCodeBackToParent = (ImageView) this
                .findViewById(R.id.findcode_topbackToParentBt);
        mFindCodeBackToParent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.findcode_nextStepBt:
                if (!telNumIsOk()) {

                    return;
                }
                if (!IdentifyCodeIsOk()) {

                    return;
                }
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        checkIndentifyCode();
                    }
                }).start();
                break;
            case R.id.findcode_getIdenfityCodeBt:
                if (!telNumIsOk()) {

                    return;
                }
                if (JudgeTelNum.isTelNum(mFindCodeInputTelNum.getText().toString()
                        .trim()) == true) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mCountDownTimerUtils.start();
                            getIndentifyCode();
                        }
                    }).start();
                } else {
                    showToastLong(R.string.telnumerrorremian);
                }

                break;
            case R.id.findcode_topbackToParentBt:
                this.finish();
                break;

            default:
                break;
        }
    }

    protected void checkIndentifyCode() {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
        OkHttpClient mOkHttpClient = new OkHttpClient();
        // 创建一个Request
        final Request request = new Request.Builder().url(
                BaseData.CHECK_SMS_MSG
                        + mFindCodeInputTelNum.getText().toString().trim()
                        + "&code="
                        + mFindCodeIdentifyCodeEdt.getText().toString().trim())
                .build();
        // new call
        Call call = mOkHttpClient.newCall(request);
        // 请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                String CheckCodeBody = response.body().string();
                LogUtils.e("CheckCodeBody--->>", CheckCodeBody);
                Gson gson = new Gson();
                mRegistEntity = gson.fromJson(CheckCodeBody, RegistEntity.class);
                Message msg = handler.obtainMessage(GET_CODE_OK);
                msg.sendToTarget();
            }

        });
    }

    protected void getIndentifyCode() {
        // TODO Auto-generated method stub
        OkHttpClient mOkHttpClient = new OkHttpClient();
        // 创建一个Request
        final Request request = new Request.Builder().url(
                BaseData.GET_SMS_MSG
                        + mFindCodeInputTelNum.getText().toString().trim()
                        + "&verclassify=2").build();
        // new call
        Call call = mOkHttpClient.newCall(request);
        // 请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                String getCodeBody = response.body().string();
                LogUtils.e("找回密码获取验证码--->>", getCodeBody);
                Gson gson = new Gson();
                mRegistEntity = gson.fromJson(getCodeBody, RegistEntity.class);
                Message msg = handler.obtainMessage(GET_CODE_ERROR);
                msg.sendToTarget();
            }

        });
    }

    private boolean telNumIsOk() {

        if (TextUtils.isEmpty(mFindCodeInputTelNum.getText())) {
            showToastShort(R.string.telnumnotnull);
            return false;
        }

        return true;
        //injg.cn
    }

    private boolean IdentifyCodeIsOk() {

        if (TextUtils.isEmpty(mFindCodeIdentifyCodeEdt.getText())) {
            showToastShort(R.string.identifynotnull);
            return false;
        }

        return true;
    }

}
