package com.ings.gogopaidan.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/20.
 */
public class ActivityStack {
	private static ActivityStack ourInstance = new ActivityStack();

	public static ActivityStack getInstance() {
		return ourInstance;
	}

	// activity 管理
	// 添加 ，删除 ，结束 ，启动

	private List<Activity> acs = new ArrayList<Activity>();

	private ActivityStack() {
	}

	/**
	 * activity is create
	 * 
	 * @param a
	 */
	public void push(Activity a) {
		acs.add(a);
	}

	/**
	 * activity is destory
	 * 
	 * @param a
	 */
	public void pop(Activity a) {
		acs.remove(a);
	}

	/**
	 *
	 * @param a
	 *            kill a a is a acitivity
	 */
	public void killActivity(Class a) {

		for (Activity ac : acs) {
			if (ac.getClass().getName().equals(a.getName())) {
				ac.finish();
			}
		}
	}

	/**
	 *
	 * @param a
	 *            kill a a is a acitivity name
	 */
	public void killActivity(String a) {

		for (Activity ac : acs) {
			if (ac.getClass().getName().equals(a)) {
				ac.finish();
			}
		}
	}

	/**
	 * the application is quit with 0
	 */
	public void exit() {
		for (Activity ac : acs) {
			ac.finish();
		}
		// safe exit
		System.exit(0);
	}
}
