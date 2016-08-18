package com.jnwat.bean;

/**
 * @author Administrator
 * 
 */
public class IntentObject {

	/**
	 * 任务列表使用
	 */
	private static BUserTasks mBUserTasks;

	//app下载
	public static String aPPDownLoad;

	public static BUserTasks getmBUserTasks() {
		return mBUserTasks;
	}

	public static void setmBUserTasks(BUserTasks mBUserTasks) {
		IntentObject.mBUserTasks = mBUserTasks;
	}

}
