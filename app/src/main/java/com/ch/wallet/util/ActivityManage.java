package com.ch.wallet.util;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class ActivityManage extends Application {

	private List<Activity> mList = new LinkedList<Activity>();
	private static ActivityManage instance;

	private ActivityManage() {
	}

	public synchronized static ActivityManage getInstance() {
		if (null == instance) {
			instance = new ActivityManage();
		}
		return instance;
	}

	// 添加要退出的Activity到list集合当中
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivityclass(Class<?> cls) {
		if (mList != null) {
			for (Activity activity : mList) {
				if (activity.getClass().equals(cls)) {
					this.mList.remove(activity);
					finishActivity(activity);
					break;
				}
			}
		}

	}

	/**
	 * 结束指定的Activity
	 * 
	 * @param activity
	 */

	public void finishActivity(Activity activity) {

		if (activity != null) {
			this.mList.remove(activity);
			activity.finish();
		}
	}

	// 退出Activity
	public void exit() {
		for (Activity activity : mList) {
			if (activity != null)
				activity.finish();
		}
	}

	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
}
