package com.ings.gogopaidan.activity;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

public class CheckAllOrderActivity extends BaseActivity implements
		OnClickListener {
	private final int GET_DATA_OK = 1;
	private final int GET_DATA_ERROR = 2;
	// 以下三个值 主要用来 获取cookie 信息
	private UILApplication myApplication;
	private String ASP_NET_SessionId;
	private String aspnetauth;
	// 订单列表
	private ListView mCheckDataLV;
	// 实体bean
	private MyCheckOrderEntity mCheckEntity;
	private Context context;
	private String orderListBody;
	private AllResultEntity mResultEntity;
	private TextView mBackTv;
	private TextView mNoMoreOrder;
	// 点击详情跳出来的
	private Dialog myDialog;
	private TextView mShowMoreMsg;
	private Button mCancelDialog;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_mycheckalllist);
		myApplication = (UILApplication) getApplication();
		ASP_NET_SessionId = myApplication.getASP_NET_SessionId();
		aspnetauth = myApplication.getAspnetauth();
		context = this.getApplicationContext();
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		myDialog = new Dialog(this);
		myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		myDialog.setContentView(R.layout.layout_showmoremsg);
		mShowMoreMsg = (TextView) myDialog.findViewById(R.id.MoreMsg_moreMsg);
		mCancelDialog = (Button) myDialog.findViewById(R.id.MoreMsg_Cancel);
		View LVBottom = View.inflate(getApplicationContext(), R.layout.layout_orderbottom, null);
		mNoMoreOrder = (TextView) LVBottom.findViewById(R.id.order_nomoreorder);
		mNoMoreOrder.setOnClickListener(this);
		mCheckDataLV = (ListView) this.findViewById(R.id.checkorder_alllist);
		mCheckDataLV.addFooterView(LVBottom);
		mBackTv = (TextView) this.findViewById(R.id.checkorder_allback);
		mBackTv.setOnClickListener(this);


	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.checkorder_allback:
			finish();
			break;
		case R.id.order_nomoreorder:
			LogUtils.e("", "");
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

				getOrderData();
			}
		}).start();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GET_DATA_OK:
				mCheckDataLV.setAdapter(new MyCheckAdapter());
				mCheckDataLV.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(),
								CheckOrderDetailActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("OrderNo",
								mCheckEntity.getData().get(position)
										.getOrderno());
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
				break;
			case GET_DATA_ERROR:
				showToastLong("暂无数据");
				break;

			default:
				break;
			}
		};
	};

	/*主动请求回来的参数*/
	protected void getOrderData() {
		// TODO Auto-generated method stub
		OkHttpClient mOkHttpClient = new OkHttpClient();
		// 创建一个Request
		final Request request = new Request.Builder().addHeader("Cookie", aspnetauth + ";" + ASP_NET_SessionId)
				.url(BaseData.GET_DIFFERENT_STATE + "2").build();

		//.url(BaseData.GET_ORDER_LIST).build();
		// new call
		Call call = mOkHttpClient.newCall(request);
		// 请求加入调度
		call.enqueue(new Callback() {

			@Override
			public void onResponse(final Response response) throws IOException {
				orderListBody = response.body().string();
				LogUtils.e("订单列表body--->>", orderListBody);
				if(orderListBody.contains("<!DOCTYPE html>")==true){
					Message msg = handler.obtainMessage(GET_DATA_ERROR);
					msg.sendToTarget();
				}else{
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

	private class MyCheckAdapter extends BaseAdapter {

		@Override
		public int getCount() {

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
				convertView = LayoutInflater.from(context).inflate(
						R.layout.layout_allorderitem, null);

				ViewHolder viewHolder = new ViewHolder(convertView);

				convertView.setTag(viewHolder);
			}

			initializeViews(mCheckEntity.getData().get(position), (ViewHolder) convertView.getTag());
			return convertView;
		}

		@SuppressLint("ResourceAsColor")
		private void initializeViews(final MyCheckOrderEntity.OrderDatas entity, ViewHolder holder) {


			holder.mOrderID.setText("订单号：" + entity.getOrderno());
			holder.mOrderMoney.setText("￥" + entity.getTotal());
			holder.mOrderGoDeliver.setVisibility(View.GONE);
			holder.mOrderGoCancel.setVisibility(View.GONE);
			android.view.ViewGroup.LayoutParams params = holder.mOrderListImage.getLayoutParams();
			params.height = (int) (MainPageActivity.mWidth / 4);
			params.width = (int) (MainPageActivity.mWidth / 4);
			holder.mOrderListImage.setLayoutParams(params);
			ImageLoader.getInstance().displayImage(entity.getOrderPro().getImgurl(), holder.mOrderListImage, BaseActivity.options);
			holder.mOrderListName.setText(entity.getOrderPro().getProname());
			holder.mOrderListNum.setText("共" + entity.getOrderPro().getPronum() + "个商品");
			//holder.mOrderMoreMsg.setText("备注123" + entity.getMark());
			/*holder.mOrderMoreMsg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					LogUtils.e("备注点击到了", "备注点击到了");
					myDialog.show();
					mShowMoreMsg.setText("备注：" + entity.getMark());
				}
			});*/
			holder.mTextView.setText(entity.getSenduser()+"  接单");
			holder.nameitextView.setText(entity.getMyconsigee().getConsignee_add());
			holder.namehaoma.setVisibility(View.VISIBLE);
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




			mCancelDialog.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					myDialog.dismiss();
				}
			});



			holder.mOrderState.setVisibility(View.VISIBLE);
			if (entity.getState().equals("已作废")) {
				holder.mOrderState.setText("超时作废");
			} else {
				holder.mOrderState.setText(entity.getState());
			}

		}

		public class ViewHolder {
			private final TextView mOrderID;
			private final TextView mOrderMoney;
			private final ImageView mOrderListImage;
			private final TextView mOrderListName;
			private final TextView mOrderListNum;
			private final Button mOrderGoDeliver;
			private final Button mOrderGoCancel;
			//private final TextView mOrderMoreMsg;
			private final TextView mOrderState;
			private final TextView mTextView;//电话号码；


			//private final TextView nameitextView;//姓名
			//private final TextView namehaoma;//号码
			//private final TextView nameleirrong;//内容
			private final TextView nameitextView;//收取地址
			private final Button namehaoma;//拨号
			private final TextView nameleirrong;//consigeon
			private TextView namegneetimegff;//收货时间
			//private TextView namegencrsadata;//收货日期
			private TextView namehom;//姓名



			public final View root;

			// http://192.168.1.106:8082/app/StationInfo?stcd=20161022

			public ViewHolder(View root) {
                /*获取详情页面的id*/
				//nameitextView= (TextView) root.findViewById(R.id.name_homing_id);
				//namehaoma= (TextView) root.findViewById(R.id.name_leirong_id);
				//nameleirrong= (TextView) root.findViewById(R.id.name_xiang_qing);


				nameitextView = (TextView) root.findViewById(R.id.name_homing_id);
				namehaoma = (Button) root.findViewById(R.id.name_baohao_id);
				nameleirrong = (TextView) root.findViewById(R.id.name_consingent_id);
				namegneetimegff = (TextView) root.findViewById(R.id.consigeetime_id);
				//namegencrsadata= (TextView)root. findViewById(R.id.createdate_id);
				namehom = (TextView) root.findViewById(R.id.name_hom_id);
				mTextView= (TextView) root.findViewById(R.id.name_haoma_id);


				mOrderListImage = (ImageView) root.findViewById(R.id.orderlist_goodsamage);
				mOrderListNum = (TextView) root.findViewById(R.id.orderlist_orderNum);

				mOrderListName = (TextView) root.findViewById(R.id.orderlist_ordermsg);
				mOrderID = (TextView) root.findViewById(R.id.orderlist_orderID);
				mOrderMoney = (TextView) root.findViewById(R.id.orderlist_orderMoney);
				mOrderGoDeliver = (Button) root.findViewById(R.id.orderlist_goDeliver);
				mOrderGoCancel = (Button) root.findViewById(R.id.orderlist_goCancel);
				//mOrderMoreMsg = (TextView) root.findViewById(R.id.orderlist_moreMsg);
				mOrderState = (TextView) root.findViewById(R.id.orderlist_mystate);
				this.root = root;
			}
		}

	}

}
