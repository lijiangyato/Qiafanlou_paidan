package com.ings.gogopaidan.activity;

import java.io.IOException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ings.gogopaidan.R;
import com.ings.gogopaidan.activity.ui.BaseActivity;
import com.ings.gogopaidan.adapter.DetailOrderGoodsAdapter;
import com.ings.gogopaidan.application.UILApplication;
import com.ings.gogopaidan.base.BaseData;
import com.ings.gogopaidan.entity.DetailOrderEntity;
import com.ings.gogopaidan.utils.LogUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class CheckOrderDetailActivity extends BaseActivity implements
		OnClickListener {
	private final int GET_DATA_OK = 1;
	// 以下三个值 主要用来 获取cookie 信息
	private UILApplication myApplication;
	private String ASP_NET_SessionId;
	private String aspnetauth;
	// 订单列表
	private ListView mOrderDetalLV;
	// 第三列表适配器
	private DetailOrderGoodsAdapter mGoodsAdapter;
	// 实体bean
	private DetailOrderEntity mDetailEntity;
	// 返回父界面
	private TextView mOrderDetailBack;
	// 下单时间
	private TextView mOrderTime;
	// 订单id
	private TextView mOrderOrderID;
	// 用户信息
	private TextView mOrderUserName;
	private TextView mOrderUserAddress;
	// 支付信息
	private TextView mOrderPayWay;
	private TextView mOrderTotalMoney;
	private TextView mOrderPostMoney;
	// 订单信息
	private TextView mOrderState;

	private String OrderID;

	private TextView mTextViewbeizhu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_checkorderdetailmsg);
		myApplication = (UILApplication) getApplication();
		ASP_NET_SessionId = myApplication.getASP_NET_SessionId();
		aspnetauth = myApplication.getAspnetauth();
		Bundle bundle = this.getIntent().getExtras();
		OrderID = bundle.getString("OrderNo");
		LogUtils.e("订单详情界面的orderID", OrderID);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		mOrderDetalLV = (ListView) this
				.findViewById(R.id.detailOrder_goodsMsgLV);
		mOrderDetailBack = (TextView) this
				.findViewById(R.id.checkdetail_tittle);
		mOrderDetailBack.setOnClickListener(this);
		mOrderDetalLV = (ListView) this
				.findViewById(R.id.detailOrder_goodsMsgLV);
		mOrderTime = (TextView) this
				.findViewById(R.id.detailOrder_detailOrderTime);
		mOrderOrderID = (TextView) this
				.findViewById(R.id.detailOrder_detailOrderNo);
		mOrderUserName = (TextView) this
				.findViewById(R.id.detailOrder_detailUserMsg);
		mOrderUserAddress = (TextView) this
				.findViewById(R.id.detailOrder_detailAddressMsg);
		mOrderPayWay = (TextView) this
				.findViewById(R.id.detailOrder_detailPayWay);
		mOrderPostMoney = (TextView) this
				.findViewById(R.id.detailOrder_detailPayPostMoney);
		mOrderTotalMoney = (TextView) this
				.findViewById(R.id.detailOrder_detailPayTotalMoney);
		mOrderState = (TextView) this.findViewById(R.id.orderlist_orderstate);
		mTextViewbeizhu= (TextView) findViewById(R.id.name_beizhu_id);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.checkdetail_tittle:
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
				getDetailData();
			}
		}).start();
	}

	protected void getDetailData() {
		// TODO Auto-generated method stub
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 创建一个Request
		final Request request = new Request.Builder()
				.addHeader("Cookie", aspnetauth + ";" + ASP_NET_SessionId)
				.url(BaseData.GET_DETAIL_ORDER + OrderID).build();
		// new call
		Call call = mOkHttpClient.newCall(request);
		// 请求加入调度
		call.enqueue(new Callback() {

			@Override
			public void onResponse(final Response response) throws IOException {
				String orderDetailBody = response.body().string();
				LogUtils.e("订单详情---》》》", orderDetailBody);
				Gson gson = new Gson();
				mDetailEntity = gson.fromJson(orderDetailBody, DetailOrderEntity.class);

				Message msg = handler.obtainMessage(GET_DATA_OK);
				msg.sendToTarget();

			}

			@Override
			public void onFailure(Request request, IOException e) {
			}

		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_DATA_OK:
				mOrderTime.setText("下单时间：" + mDetailEntity.getData().getCreatedate());
				mOrderOrderID.setText("订单编号：" + mDetailEntity.getData().getOrderno());
				mOrderUserName.setText(mDetailEntity.getData().getConsignee().getConsignee_name() + "      " + mDetailEntity.getData().getConsignee().getConsignee_phone());
				mOrderUserAddress.setText(mDetailEntity.getData().getConsignee().getConsignee_add());
				mOrderPayWay.setText("支付方式：" + mDetailEntity.getData().getPayway());
				mOrderTotalMoney.setText("商品合计：" + "¥" + mDetailEntity.getData().getTotal());
				mOrderPostMoney.setText("运        费：" + "¥" + mDetailEntity.getData().getPostage());
				mTextViewbeizhu.setText("备注:"+ mDetailEntity.getData().getMark());
				mOrderState.setText(mDetailEntity.getData().getState());
				mGoodsAdapter = new DetailOrderGoodsAdapter(getApplicationContext(), mDetailEntity.getData().getOrderPros());
				mOrderDetalLV.setAdapter(mGoodsAdapter);
				setListViewHeightBasedOnChildren(mOrderDetalLV);
				break;

			default:
				break;
			}
		};
	};

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(10, 10);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

}
