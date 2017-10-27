package com.ings.gogopaidan.adapter;

import java.util.List;

import com.ings.gogopaidan.R;
import com.ings.gogopaidan.activity.MainPageActivity;
import com.ings.gogopaidan.activity.ui.BaseActivity;
import com.ings.gogopaidan.entity.DetailOrderEntity;
import com.ings.gogopaidan.entity.DetailOrderEntity.DetailOrderDatas.OrderProducts;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class DetailOrderGoodsAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater layoutInflater;
	private List<DetailOrderEntity.DetailOrderDatas.OrderProducts> entitys;

	public DetailOrderGoodsAdapter(Context context, List<OrderProducts> entitys) {
		super();
		this.context = context;
		this.entitys = entitys;
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {

		return entitys.size();
	}

	@Override
	public Object getItem(int position) {
		return entitys.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(
					R.layout.layout_detailordergoodsitem, null);
			ViewHolder viewHolder = new ViewHolder(convertView);

			convertView.setTag(viewHolder);
		}

		initializeViews(entitys.get(position),
				(ViewHolder) convertView.getTag());
		return convertView;
	}

	private void initializeViews(
			DetailOrderEntity.DetailOrderDatas.OrderProducts entity,
			ViewHolder holder) {
		android.view.ViewGroup.LayoutParams params = holder.mDetalGoodsImage
				.getLayoutParams();

		params.height = (int) (MainPageActivity.mWidth / 4);
		params.width = (int) (MainPageActivity.mWidth / 4);
		holder.mDetalGoodsImage.setLayoutParams(params);
		ImageLoader.getInstance().displayImage(entity.getImgurl(),
				holder.mDetalGoodsImage, BaseActivity.options);
		holder.mDetalGoodsNameTv.setText(entity.getProname());
		holder.mDetalGoodsPriceTv.setText("¥ " + entity.getPrice());
		holder.mDetalGoodsNumTV.setText("x" + entity.getAmount());
		if (entity.getMark().length() == 0) {
			holder.mDetalGoodsMarkTV.setVisibility(View.GONE);
		} else {
			holder.mDetalGoodsMarkTV.setText(entity.getMark());
		}

	}

	public class ViewHolder {
		private final ImageView mDetalGoodsImage;
		private final TextView mDetalGoodsNameTv;
		private final TextView mDetalGoodsPriceTv;
		private final TextView mDetalGoodsNumTV;
		private final TextView mDetalGoodsMarkTV;
		public final View root;

		// http://192.168.1.106:8082/app/StationInfo?stcd=20161022

		public ViewHolder(View root) {
			mDetalGoodsImage = (ImageView) root
					.findViewById(R.id.detailorder_goodsImage);
			mDetalGoodsNameTv = (TextView) root
					.findViewById(R.id.detailorder_goodsname);
			mDetalGoodsPriceTv = (TextView) root
					.findViewById(R.id.detailorder_goodsPrice);
			mDetalGoodsNumTV = (TextView) root
					.findViewById(R.id.detailorder_goodsNum);
			mDetalGoodsMarkTV = (TextView) root
					.findViewById(R.id.detailorder_goodsMark);
			this.root = root;
		}
	}
}
