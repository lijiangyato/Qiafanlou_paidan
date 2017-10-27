package com.ings.gogopaidan.utils;


import com.ings.gogopaidan.R;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

public class CountDownTimerUtils extends CountDownTimer {
	private TextView mTextView;

	public CountDownTimerUtils(TextView textView, long millisInFuture,
			long countDownInterval) {
		super(millisInFuture, countDownInterval);
		this.mTextView = textView;
	}

	@Override
	public void onTick(long millisUntilFinished) {
		mTextView.setClickable(false); // ���ò��ɵ��
		mTextView.setText(millisUntilFinished / 1000 + "��s��"); // ���õ���ʱʱ��
		mTextView.setBackgroundResource(R.drawable.deadtimal_click); // ���ð�ťΪ��ɫ����ʱ�ǲ��ܵ����

		/**
		 * ������ URLSpan ���ֱ�����ɫ BackgroundColorSpan ������ɫ ForegroundColorSpan �����С
		 * AbsoluteSizeSpan ���塢б�� StyleSpan ɾ���� StrikethroughSpan �»���
		 * UnderlineSpan ͼƬ ImageSpan
		 * http://blog.csdn.net/ah200614435/article/details/7914459
		 */
		SpannableString spannableString = new SpannableString(mTextView
				.getText().toString()); // ��ȡ��ť�ϵ�����
		ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
		/**
		 * public void setSpan(Object what, int start, int end, int flags) {
		 * ��Ҫ��start��end��start����ʼλ��,������Ӣ�ģ�����һ����
		 * ��0��ʼ������end�ǽ���λ�ã����Դ�������֣�������ʼλ�ã�������������λ�á�
		 */
		spannableString.setSpan(span, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);// ������ʱ��ʱ������Ϊ��ɫ
		mTextView.setText(spannableString);
	}

	@Override
	public void onFinish() {
		mTextView.setText("���»�ȡ��֤��");
		mTextView.setClickable(true);// ���»�õ��
		mTextView.setBackgroundResource(R.drawable.deadtimal_normal); // ��ԭ����ɫ
	}

	public void stop() {
		// TODO Auto-generated method stub
		mTextView.setText("���»�ȡ��֤��");
		mTextView.setClickable(true);// ���»�õ��
		mTextView.setBackgroundResource(R.drawable.deadtimal_normal); // ��ԭ����ɫ
	}
}
