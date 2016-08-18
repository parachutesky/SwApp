package com.jnwat.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chang-zhiyuan 传递对象使用
 */
public class BIntentObj {
	/**
	 * 代办流程
	 * false : 没获取过：获取网络数据
	 */
	public static BUserTasks mBUserTasks;
	public static boolean IsGetSendProcess = false;// 是否获取过 发起流程
	
	public static boolean IsGetOverProcess = false;// 是否获取过 已办流程
	public static boolean IsGetProcess = false; // 是否获取过 代办流程
	public static boolean IsDoingProcess = false; // 是否获取过 在途流程
	public static boolean EndProcess = false; // 是否获取过 在途流程
	
	public static Boolean IsGetNews = false; // 是否获取过新闻
	
/*	课程表*/
	public static int isGetClass = 0;//o 表示加载UserID  1 为“”
	
	
	/**
	 * 是否有新消息
	 */
	public static boolean IsGetNewPushMessage = false;


	/**
	 * 消息推送列表
	 */
	public static List<BPushMessageInfo> list_PushMessage;

	/**
	 * 切换用户重新初始化
	 */
	public static void recover() {
		IsDoingProcess = true;
		IsGetOverProcess = true;
		IsGetProcess = true;
		EndProcess = true;
		
	}

}
