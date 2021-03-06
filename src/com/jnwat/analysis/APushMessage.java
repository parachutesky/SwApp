package com.jnwat.analysis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.jnwat.bean.BPushMessageInfo;
import com.jnwat.bean.BUserlogin;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 推送消息解析类
 */
public class APushMessage {

	private List<BPushMessageInfo> list_PushMessage;
	private BPushMessageInfo mBPushMessageInfo;
	private int listSub;// 获取的消息总数
	int size;
	private Context context;

	public APushMessage() {

	}

	public APushMessage(Context mContext) {
		this.context = mContext;

	}

	public int getListSub() {
		return listSub;
	}

	public void setListSub(int listSub) { 
		this.listSub = listSub;
	}

	/**
	 * @param result
	 * @return 消息解析接口
	 */
	public List<BPushMessageInfo> APushMessage1(String result, DbUtils db)
			throws Exception {

		if (null == list_PushMessage) {
			list_PushMessage = new ArrayList<BPushMessageInfo>();
		}

		JSONObject jsonobject;
		// try {
		jsonobject = new JSONObject(result);

		String Message = jsonobject.get("Message").toString().trim();// 错误提示信息

		int Status = jsonobject.getInt("Status");

		if (0 != Status && Status == 200) {// 如果拿到的值是正确的继续解析

			JSONArray mJsonArray = jsonobject.getJSONArray("ReplyObject");
			size = mJsonArray.length();
			System.out.println("size:" + size);

			for (int j = 0; j < size; j++) {
				JSONObject mmJsonObject = mJsonArray.getJSONObject(j);
				mBPushMessageInfo = new BPushMessageInfo();
				mBPushMessageInfo.setIDid((mmJsonObject.getString("ID")));
				mBPushMessageInfo.setMsgContent(mmJsonObject
						.getString("MsgContent"));
				mBPushMessageInfo.setFromUser(mmJsonObject
						.getString("FromUser"));
				mBPushMessageInfo.setMsgSendTime(mmJsonObject
						.getString("MsgSendTime"));
				mBPushMessageInfo.setTitle(mmJsonObject.getString("Title"));
				mBPushMessageInfo.setMsgType(mmJsonObject.getInt("MsgType"));
				mBPushMessageInfo.setIsRead(0);// 默认消息未读
				mBPushMessageInfo.setLoginName(BUserlogin.NO);
				if (mmJsonObject.getInt("MsgType") == 1) {// 流程
					mBPushMessageInfo.setWorkID(mmJsonObject
							.getString("WorkID"));
					mBPushMessageInfo.setFlowID(mmJsonObject
							.getString("FlowID"));
					mBPushMessageInfo.setFid(mmJsonObject
							.getString("fid"));//2016-04-11 14:59:26
					mBPushMessageInfo.setIsDaiban(mmJsonObject.getBoolean("isdaiban")+"");
					
					
				}

				if (mmJsonObject.getInt("MsgType") == 2) {// 新闻
					mBPushMessageInfo.setNewsUrl(mmJsonObject
							.getString("NewsUrl"));
				}
				// 创建表
				// db.createTableIfNotExist(BPushMessageInfo.class);
				// //创建一个表BPushMessageInfo
				// try {
				// 消息存储
				list_PushMessage.add(mBPushMessageInfo);
				BPushMessageInfo id_size = db.findFirst(Selector.from(
						BPushMessageInfo.class).where("IDid", "=",
						mmJsonObject.getString("ID")));
				if (null == id_size) {
					System.out.println("消息存储:" + mmJsonObject.getString("ID"));
					db.save(mBPushMessageInfo);
				} else {
				}
			}
			setListSub(size);
			/*
			 * JSONArray mmJSONArray; for (int i = 0; i < size; i++) {
			 * mmJSONArray = mJsonArray.getJSONObject(i).getJSONObject("msg")
			 * .getJSONArray("List"); leng = mmJSONArray.length(); for (int j =
			 * 0; j < leng; j++) { JSONObject mmJsonObject =
			 * mmJSONArray.getJSONObject(j); mBPushMessageInfo = new
			 * BPushMessageInfo();
			 * mBPushMessageInfo.setIDid((mmJsonObject.getString("ID")));
			 * mBPushMessageInfo.setMsgContent(mmJsonObject
			 * .getString("MsgContent"));
			 * mBPushMessageInfo.setFromUser(mmJsonObject
			 * .getString("FromUser"));
			 * mBPushMessageInfo.setMsgSendTime(mmJsonObject
			 * .getString("MsgSendTime"));
			 * mBPushMessageInfo.setTitle(mmJsonObject.getString("Title"));
			 * mBPushMessageInfo .setMsgType(mmJsonObject.getInt("MsgType"));
			 * mBPushMessageInfo.setIsRead(0);// 默认消息未读
			 * mBPushMessageInfo.setLoginName(BUserlogin.NO); if
			 * (mmJsonObject.getInt("MsgType") == 1) {// 流程
			 * mBPushMessageInfo.setWorkID(mmJsonObject .getString("WorkId"));
			 * mBPushMessageInfo.setFlowID(mmJsonObject .getString("FlowNo")); }
			 * 
			 * if (mmJsonObject.getInt("MsgType") == 2) {// 新闻
			 * mBPushMessageInfo.setNewsUrl(mmJsonObject .getString("NewsUrl"));
			 * }
			 * 
			 * // 创建表 // db.createTableIfNotExist(BPushMessageInfo.class); //
			 * //创建一个表BPushMessageInfo // try { // 消息存储
			 * list_PushMessage.add(mBPushMessageInfo); BPushMessageInfo id_size
			 * = db.findFirst(Selector.from(
			 * BPushMessageInfo.class).where("IDid", "=",
			 * mmJsonObject.getString("ID"))); if (null == id_size) {
			 * db.save(mBPushMessageInfo); } else { }
			 * 
			 * }
			 * 
			 * }
			 * 
			 * setListSub(size * leng);
			 */
			LogUtils.d("消息的长度" + size);
		} else {// 解析错误编码，提示用户

		}
		// } catch (JSONException e) {
		// TODO Auto-generated catch block

		// LogUtils.d("消息解析异常");
		// } catch (Exception e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return list_PushMessage;
	}

}

/*
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * for (int i = 0; i < size; i++) {
 * 
 * 
 * mBPushMessageInfo = new BPushMessageInfo(); JSONObject mjsonobject =
 * mJsonArray.getJSONObject(i); System.out.println("第" + i + "个" +
 * mjsonobject.getString("MsgContent"));
 * mBPushMessageInfo.setIDid((mjsonobject.getString("ID")));
 * mBPushMessageInfo.setMsgContent(mjsonobject .getString("MsgContent"));
 * mBPushMessageInfo.setFromUser(mjsonobject .getString("FromUser"));
 * mBPushMessageInfo.setMsgSendTime(mjsonobject .getString("MsgSendTime"));
 * mBPushMessageInfo.setTitle(mjsonobject.getString("Title"));
 * mBPushMessageInfo.setMsgType(mjsonobject.getInt("MsgType"));
 * mBPushMessageInfo.setIsRead(0);// 默认消息未读
 * mBPushMessageInfo.setLoginName(BUserlogin.NO); if
 * (mjsonobject.getInt("MsgType") == 1) {// 流程
 * mBPushMessageInfo.setWorkID(mjsonobject .getString("WorkID"));
 * mBPushMessageInfo.setFlowID(mjsonobject .getString("FlowID")); }
 * 
 * if (mjsonobject.getInt("MsgType") == 2) {// 新闻
 * mBPushMessageInfo.setNewsUrl(mjsonobject .getString("NewsUrl")); }
 * 
 * // 创建表 // db.createTableIfNotExist(BPushMessageInfo.class); //
 * //创建一个表BPushMessageInfo // try { // 消息存储 BPushMessageInfo id_size =
 * db.findFirst(Selector.from( BPushMessageInfo.class).where("IDid", "=",
 * mjsonobject.getString("ID"))); if (null == id_size) {
 * db.save(mBPushMessageInfo); } else {
 * 
 * }
 * 
 * // } catch (DbException e) { // TODO Auto-generated catch block //
 * e.printStackTrace(); // db.save(mBPushMessageInfo); // }
 * 
 * list_PushMessage.add(mBPushMessageInfo);
 * 
 * }
 */
