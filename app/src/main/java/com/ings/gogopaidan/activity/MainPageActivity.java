package com.ings.gogopaidan.activity;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ings.gogopaidan.R;
import com.ings.gogopaidan.activity.ui.BaseActivity;
import com.ings.gogopaidan.application.UILApplication;
import com.ings.gogopaidan.base.BaseData;
import com.ings.gogopaidan.entity.MyCheckOrderEntity;
import com.ings.gogopaidan.entity.AllResultEntity;
import com.ings.gogopaidan.utils.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class MainPageActivity extends BaseActivity implements OnClickListener {
    private final int GET_DATA_OK = 1;
    private final int DELIVER_OK = 2;
    private final int IS_REFRESH = 3;
    private final int ONE_DATA = 4;
    private final int GET_DATA_ERROR = 5;
    // 以下三个值 主要用来 获取cookie 信息
    private UILApplication myApplication;
    private String ASP_NET_SessionId;
    private String aspnetauth;
    // 订单列表
    private ListView mCheckDataLV;
    // 实体bean
    private MyCheckOrderEntity mCheckEntity;
    private TextView mOrderTitle;
    private TextView mCheckAll;
    private Context context;
    private String orderListBody;
    private AllResultEntity mResultEntity;
    public static int mWidth;
    private Dialog myDialog;
    private Handler handler1;
    private MediaPlayer mPlayer = null;
    private MediaPlayer mPlayer1 = null;
    private AllResultEntity resultEntity;
    private String mRemindWay;
    // 是否派单
    private Button mOk;
    private Button mCancel;
    private TextView mNoMoreOrder;
    private ImageButton mBack;
    private String createdate;
    // private MyCheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_mainpage);
        myApplication = (UILApplication) getApplication();
        ASP_NET_SessionId = myApplication.getASP_NET_SessionId();
        aspnetauth = myApplication.getAspnetauth();
        context = this.getApplicationContext();
        handler1 = new Handler();
        DisplayMetrics dm = new DisplayMetrics();
        // 初始化
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        Bundle bundle = this.getIntent().getExtras();
        mRemindWay = bundle.getString("remindWay");
        LogUtils.e("mainPage传值是否成功", mRemindWay + "");

        initViews();
    }

    private void initViews() {
        // TODO Auto-generated method stub
        View viewpagerBottom = View.inflate(this, R.layout.layout_orderbottom,
                null);
        mNoMoreOrder = (TextView) viewpagerBottom.findViewById(R.id.order_nomoreorder);
        mNoMoreOrder.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                LogUtils.e("", "");
            }
        });
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.layout_mydialog);
        myDialog.setTitle(R.string.order_selectpayway);
        // mOk = (Button) myDialog.findViewById(R.id.mDeliverOk);
        // mCancel = (Button) myDialog.findViewById(R.id.mDeliverCancel);
        mCheckDataLV = (ListView) this.findViewById(R.id.checkorder_waitgolist);
        mCheckDataLV.addFooterView(viewpagerBottom);
        mOrderTitle = (TextView) this.findViewById(R.id.order_all);
        mCheckAll = (TextView) this.findViewById(R.id.order_checkall);
        mCheckAll.setOnClickListener(this);
        mPlayer = MediaPlayer.create(this, R.raw.syin);
        mPlayer.setLooping(true);
        mPlayer1 = MediaPlayer.create(this, R.raw.tishi);
        mPlayer1.setLooping(true);
        mBack = (ImageButton) this.findViewById(R.id.backIB);
        mBack.setOnClickListener(this);
        // playMusic();

    }

    private void playMusic() {
        // TODO Auto-generated method stub
        if (mRemindWay.equals("1")) {
            mPlayer.start();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    SystemClock.sleep(4000);
                    mPlayer.stop();
                }
            }).start();
        } else if (mRemindWay.equals("2")) {
            mPlayer1.start();
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    SystemClock.sleep(4000);
                    mPlayer1.stop();
                }
            }).start();
        } else if (mRemindWay.equals("3")) {
            return;
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.order_checkall:
                Intent intent = new Intent(getApplicationContext(), CheckAllOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.backIB:
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                getOrderData();

            }
        }).start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(1000 * 90);
                    runnable.run();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();

    }

    Runnable runnable = new Runnable() {
        public void run() {
            LogUtils.e("定时器是否执行到", "定时器是否执行到");
            getIsRefreshData();
            handler1.postDelayed(this, 1000 * 90);

        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_DATA_OK:
                    if (mCheckEntity.getData().size() == 0) {
                        showToastLong("暂无定单");
                        mOrderTitle.setText("正在听单");
                    } else {

                        mOrderTitle.setText("正在听单（" + mCheckEntity.getData().size()
                                + "）");
                        // adapter = new MyCheckAdapter();
                        mCheckDataLV.setAdapter(new MyCheckAdapter(context));
                        new MyCheckAdapter(context).notifyDataSetChanged();
                        mCheckDataLV
                                .setOnItemClickListener(new OnItemClickListener() {

                                    @Override
                                    public void onItemClick(AdapterView<?> parent,
                                                            View view, int position, long id) {
                                        // TODO Auto-generated method stub
                                        Intent intent = new Intent(
                                                getApplicationContext(),
                                                CheckOrderDetailActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("OrderNo", mCheckEntity
                                                .getData().get(position)
                                                .getOrderno());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });

                    }
                    break;
                case DELIVER_OK:
                    showToastLong(mResultEntity.getMsg());
                    Log.d("lijiang", mResultEntity.getMsg());
                    Toast toast=Toast.makeText(MainPageActivity.this, mResultEntity.getMsg(), Toast.LENGTH_SHORT);
                    toast.show();

                    if (mResultEntity.getSuccess().equals(true)) {

                        showToastLong(mResultEntity.getMsg());
                        Log.d("lijiang", mResultEntity.getMsg());
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                getOrderData();
                            }
                        }).start();

                    }

                    break;
                case IS_REFRESH:

                    if (resultEntity.getSuccess().equals(true)) {
                        playMusic();
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                getOrderData();
                            }
                        }).start();
                    } else {
                        LogUtils.e("暂时不需要更新", "暂时不需要更新");
                    }

                    break;
                case ONE_DATA:
                    showToastLong(mResultEntity.getMsg());
                    Log.d("lijiang", mResultEntity.getMsg());
                    finish();
                    break;
                case GET_DATA_ERROR:
                    showToastLong("暂无数据");
                    break;

                default:
                    break;
            }
        }

        ;
    };

    protected void getOrderData() {
        // TODO Auto-generated method stub
        OkHttpClient mOkHttpClient = new OkHttpClient();
        // 创建一个Request
        final Request request = new Request.Builder()
                .addHeader("Cookie", aspnetauth + ";" + ASP_NET_SessionId)
                .url(BaseData.GET_DIFFERENT_STATE + "1").build();
        // new call
        Call call = mOkHttpClient.newCall(request);
        // 请求加入调度
        call.enqueue(new Callback() {

            @Override
            public void onResponse(final Response response) throws IOException {
                orderListBody = response.body().string();
                LogUtils.e("代配送订单列表body--->>", orderListBody);
                if (orderListBody.contains("<!DOCTYPE html>") == true) {
                    Message msg = handler.obtainMessage(GET_DATA_ERROR);
                    msg.sendToTarget();
                } else {
                    Gson gson = new Gson();
                    mCheckEntity = gson.fromJson(orderListBody, MyCheckOrderEntity.class);
                    Message msg = handler.obtainMessage(GET_DATA_OK);
                    msg.sendToTarget();
                }

            }

            @Override
            public void onFailure(Request request, IOException e) {
            }

        });
    }


    protected void getIsRefreshData() {
        // TODO Auto-generated method stub
        OkHttpClient mOkHttpClient = new OkHttpClient();
        // 创建一个Request
        final Request request = new Request.Builder()
                .addHeader("Cookie", aspnetauth + ";" + ASP_NET_SessionId)
                .url(BaseData.GET_REFRESH_STATE + mCheckEntity.getData().size())
                .build();
        // new call
        Call call = mOkHttpClient.newCall(request);
        // 请求加入调度
        call.enqueue(new Callback() {

            @Override
            public void onResponse(final Response response) throws IOException {
                String isRefreshBody = response.body().string();
                LogUtils.e("是否刷新订单列表body--->>", isRefreshBody);
                Gson gson = new Gson();
                resultEntity = gson.fromJson(isRefreshBody,
                        AllResultEntity.class);
                Message msg = handler.obtainMessage(IS_REFRESH);
                msg.sendToTarget();
            }

            @Override
            public void onFailure(Request request, IOException e) {
            }

        });

    }

    private class MyCheckAdapter extends BaseAdapter {
        private Context cotext1;

        public MyCheckAdapter(Context cotext) {
            super();
            this.cotext1 = cotext;
        }

        @Override
        public int getCount() {
            LogUtils.e("待配送 集合大小---》》", mCheckEntity.getData().size() + "");
            return mCheckEntity.getData().size();
        }

        @Override
        public Object getItem(int position) {
            return mCheckEntity.getData().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.layout_allorderitem, null);

                ViewHolder viewHolder = new ViewHolder(convertView);

                convertView.setTag(viewHolder);
            }

            initializeViews(mCheckEntity.getData().get(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        @SuppressLint("ResourceAsColor")
        private void initializeViews(final MyCheckOrderEntity.OrderDatas entity, ViewHolder holder) {
            holder.mOrderID.setText("订单号：" + entity.getOrderno());

            createdate = entity.getCreatedate();


            if (entity.getPayway().equals("在线支付")) {
                holder.mOrderMoney.setText("￥" + entity.getTotal() + "（已付款）");
                // holder.mOrderMoney.setTextColor(R.color.green);
                // float size = (float) 14.0;
                // holder.mOrderMoney.setTextSize(size);
            } else if (entity.getPayway().equals("货到付款")) {
                holder.mOrderMoney.setText("￥" + entity.getTotal() + "（未付款）");
            }


            /*点击派单*/
            holder.mOrderGoDeliver.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    LogUtils.e("派送的订单号", entity.getOrderno() + "nnnn");

                    myDialog.show();
                    innitDialog(entity.getOrderno());

                }

                private void innitDialog(final String orderno) {
                    // TODO Auto-generated method stub
                    mOk = (Button) myDialog.findViewById(R.id.mDeliverOk);
                    mCancel = (Button) myDialog.findViewById(R.id.mDeliverCancel);
                    mOk.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            myDialog.dismiss();
                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    deliverOrder(orderno);
                                }
                            }).start();

                        }
                    });

                    mCancel.setOnClickListener(new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            myDialog.dismiss();

                        }
                    });
                }

                private void deliverOrder(String orderno) {
                    // TODO Auto-generated method stub
                    OkHttpClient okHttpClient = new OkHttpClient();
                    RequestBody requestBody = new FormEncodingBuilder().add("orderno", orderno).add("type", "3").build();
                    LogUtils.e("派送的订单号", orderno);
                    Request request = new Request.Builder().addHeader("Cookie", aspnetauth + ";" + ASP_NET_SessionId)
                            .url(BaseData.CANCEL_ORDER).post(requestBody)
                            .build();
                    Response response = null;

                    try {
                        response = okHttpClient.newCall(request).execute();
                        if (response.isSuccessful()) {
                            String deleteOrderBody = response.body().string();
                            LogUtils.e("派单Body---？？？", deleteOrderBody);
                            Gson gson = new Gson();
                            mResultEntity = gson.fromJson(deleteOrderBody, AllResultEntity.class);
                            if (mCheckEntity.getData().size() == 1) {
                                Message msg = handler.obtainMessage(ONE_DATA);
                                msg.sendToTarget();

                            } else {
                                /*showToastLong(mResultEntity.getMsg());
                                Log.d("lijiang", mResultEntity.getMsg());*/
                                Message msg = handler.obtainMessage(DELIVER_OK);
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

            });

            android.view.ViewGroup.LayoutParams params = holder.mOrderListImage.getLayoutParams();
            params.height = (int) (mWidth / 4);
            params.width = (int) (mWidth / 4);
            holder.mOrderListImage.setLayoutParams(params);
            ImageLoader.getInstance().displayImage(entity.getOrderPro().getImgurl(), holder.mOrderListImage, BaseActivity.options);
            holder.mOrderListName.setText(entity.getOrderPro().getProname());
            holder.mOrderListNum.setText("共" + entity.getOrderPro().getPronum()
                    + "个商品");


            holder.nameitextView.setText(entity.getMyconsigee().getConsignee_add());
            holder.namehaoma.setText("拨号");
            holder.nameleirrong.setText(entity.getMyconsigee().getConsignee_no());
            holder.namegneetimegff.setText(entity.getConsigneetime());
            //holder.namegencrsadata.setText(entity.getCreatedate());
            holder.namehom.setText(entity.getMyconsigee().getConsignee_name());

            holder.namehaoma.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //"tel:10010" tel 电话小标识
                    Log.d("name", "点击事件成功");
                    if (entity.getMyconsigee().getConsignee_phone() != null) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + entity.getMyconsigee().getConsignee_phone()));
                        startActivity(intent);
                    }


                }
            });


        }

        public class ViewHolder {
            private final TextView mOrderID;
            private final TextView mOrderMoney;
            private final ImageView mOrderListImage;
            private final TextView mOrderListName;
            private final TextView mOrderListNum;
            private final Button mOrderGoDeliver;
            private final Button mOrderGoCancel;


            private final TextView nameitextView;//收取地址
            private final Button namehaoma;//拨号
            private final TextView nameleirrong;//consigeon
            private TextView namegneetimegff;//收货时间
            //private TextView namegencrsadata;//收货日期
            private TextView namehom;//姓名

            public final View root;

            // http://192.168.1.106:8082/app/StationInfo?stcd=20161022

            public ViewHolder(View root) {


                nameitextView = (TextView) root.findViewById(R.id.name_homing_id);
                namehaoma = (Button) root.findViewById(R.id.name_baohao_id);
                nameleirrong = (TextView) root.findViewById(R.id.name_consingent_id);
                namegneetimegff = (TextView) root.findViewById(R.id.consigeetime_id);
                //namegencrsadata= (TextView)root. findViewById(R.id.createdate_id);
                namehom = (TextView) root.findViewById(R.id.name_hom_id);


                mOrderListImage = (ImageView) root.findViewById(R.id.orderlist_goodsamage);
                mOrderListNum = (TextView) root.findViewById(R.id.orderlist_orderNum);

                mOrderListName = (TextView) root.findViewById(R.id.orderlist_ordermsg);
                mOrderID = (TextView) root.findViewById(R.id.orderlist_orderID);
                mOrderMoney = (TextView) root.findViewById(R.id.orderlist_orderMoney);
                mOrderGoDeliver = (Button) root.findViewById(R.id.orderlist_goDeliver);
                mOrderGoCancel = (Button) root.findViewById(R.id.orderlist_goCancel);

                this.root = root;
            }
        }

    }

}
