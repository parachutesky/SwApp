package com.jnwat.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author chang-zhiyuan 传递对象使用
 */
public class BIntentObj {
	/**
	 * 代办流程 false : 没获取过：获取网络数据
	 */
	public static BUserTasks mBUserTasks;
	public static boolean IsGetSendProcess = false;// 是否获取过 发起流程

	public static boolean IsHadDidProcess = false;// 是否获取过 已办流程
	public static boolean IsDoProcess = false; // 是否获取过 代办流程
	public static boolean IsEndProcess = false; // 是否获取过 办结流程
	public static boolean IsModifyUi = true; // 是否需要切换视图
	public static boolean END_MEETINGAPPLY = false; // 结束meeting 和审核

	public static Boolean IsGetNews = false; // 是否获取过新闻
	// 是否解析
	public static Boolean IsAna = false;
	// 是不是多选
	public static Boolean ismul = false;
	// 主任驳回，就是那么任性  ture 可以驳回
	public static Boolean isreturn = false;
	// 是否是子线程 issub 跳跃
	public static Boolean issub = false;
	public static String issub_str = "";
	public static String issub_str_ID = "";

	/* 课程表 */
	public static int isGetClass = 0;// o 表示加载UserID 1 为

	/**
	 * 是否有新消息
	 */
	public static boolean IsGetNewPushMessage = false;

	/**
	 * 消息推送列表
	 */
	public static List<BPushMessageInfo> list_PushMessage;

	/**
	 * 审核节点 1
	 */
	public static ArrayList<BMeetingApplyLeade> list_getnode;
	public static ArrayList<BMeetingApplyLeade> list_getreturnnode;

	/**
	 * 审核节点 2 child
	 */
	// public static List<HashMap<BMeetingApplyLeade, List<BMeetingApplyLeade>>>
	// list_getnode_child;
	public static HashMap<String, List<BMeetingApplyLeade>> list_getnode_child;
	public static HashMap<String, List<BMeetingApplyLeade>> list_getreturnnode_child;
	/**
	 * 默认为0 人选信息
	 */
	public static int index = 0;

	/**
	 * 任务介绍
	 */
	public static String task_name;
	/**
	 *选中的人员信息
	 */
	public	 static List<Contacts2> UserInfos = new ArrayList<Contacts2>();// 好友信息

	/**
	 * 切换用户重新初始化
	 */
	public static void recover() {
		IsDoProcess = true;
		IsHadDidProcess = true;
		IsEndProcess = true;

	}

}
