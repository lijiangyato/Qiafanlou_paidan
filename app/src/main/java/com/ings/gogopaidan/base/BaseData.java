package com.ings.gogopaidan.base;

public class BaseData {
	// qiafanlou
	public static String BASE_URL = "http://api.qiafanlou.com/app/";
	// ��ȡ��֤��
	public static final String GET_SMS_MSG = BASE_URL + "sendsms?phone=";
	// У����֤��
	public static final String CHECK_SMS_MSG = BASE_URL + "checksmscode?phone=";
	// ��¼
	public static final String LOGIN_URL = BASE_URL + "login";
	// ��������
	public static final String RESET_URL = BASE_URL + "resetpwd";


	// ��ȡ�����б�
	public static final String GET_ORDER_LIST = BASE_URL + "getorderspai";
	// ��ȡ��������
	public static final String GET_DETAIL_ORDER = BASE_URL + "getorderinfo?orderno=";



	// ȡ�� ɾ�� ����
	public static final String CANCEL_ORDER = BASE_URL + "cancelorder";
	// ��ȡ����ת̨��ֵ
	public static final String GET_ORDER_STATE = BASE_URL + "getstatecount";
	// http://api.jiangxin168.com/app/getorders
	public static final String GET_DIFFERENT_STATE = BASE_URL + "getorderspai?state=";
	public static final String GET_REFRESH_STATE = BASE_URL + "getorderpaicount?count=";



}
