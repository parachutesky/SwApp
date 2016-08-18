package com.jnwat.analysis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.jnwat.bean.BKaiBanTongZhi;
import com.jnwat.bean.BPushMessageInfo;
import com.jnwat.bean.BUserlogin;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 推送消息解析类
 */
public class AClassNotifiMessage {

	private List<BKaiBanTongZhi> list_PushMessage;
	private BKaiBanTongZhi mBKaiBanTongZhi;
	private int listSub;// 获取的消息总数
	int size;
	private Context context;

	public AClassNotifiMessage() {

	}

	public AClassNotifiMessage(Context mContext) {
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
	public List<BKaiBanTongZhi> APushMessage1(String result)
			throws Exception {

		if (null == list_PushMessage) {
			list_PushMessage = new ArrayList<BKaiBanTongZhi>();
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
				mBKaiBanTongZhi = new BKaiBanTongZhi();
				mBKaiBanTongZhi.setMsgContent(mmJsonObject
						.getString("MsgContent"));
				mBKaiBanTongZhi.setMsgSendTime(mmJsonObject
						.getString("MsgSendTime"));
				mBKaiBanTongZhi.setTitle(mmJsonObject.getString("Title"));
				mBKaiBanTongZhi.setMsgType(mmJsonObject.getInt("MsgType")+"");
				mBKaiBanTongZhi.setIsRead(0+"");
				

				// 创建表
				// db.createTableIfNotExist(BPushMessageInfo.class);
				// //创建一个表BPushMessageInfo
				// try {
				// 消息存储
				list_PushMessage.add(mBKaiBanTongZhi);
			}
			/*
			 * }
			 * 
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
 * mBKaiBanTongZhi = new BPushMessageInfo(); JSONObject mjsonobject =
 * mJsonArray.getJSONObject(i); System.out.println("第" + i + "个" +
 * mjsonobject.getString("MsgContent"));
 * mBKaiBanTongZhi.setIDid((mjsonobject.getString("ID")));
 * mBKaiBanTongZhi.setMsgContent(mjsonobject .getString("MsgContent"));
 * mBKaiBanTongZhi.setFromUser(mjsonobject .getString("FromUser"));
 * mBKaiBanTongZhi.setMsgSendTime(mjsonobject .getString("MsgSendTime"));
 * mBKaiBanTongZhi.setTitle(mjsonobject.getString("Title"));
 * mBKaiBanTongZhi.setMsgType(mjsonobject.getInt("MsgType"));
 * mBKaiBanTongZhi.setIsRead(0);// 默认消息未读
 * mBKaiBanTongZhi.setLoginName(BUserlogin.NO); if
 * (mjsonobject.getInt("MsgType") == 1) {// 流程
 * mBKaiBanTongZhi.setWorkID(mjsonobject .getString("WorkID"));
 * mBKaiBanTongZhi.setFlowID(mjsonobject .getString("FlowID")); }
 * 
 * if (mjsonobject.getInt("MsgType") == 2) {// 新闻
 * mBKaiBanTongZhi.setNewsUrl(mjsonobject .getString("NewsUrl")); }
 * 
 * // 创建表 // db.createTableIfNotExist(BPushMessageInfo.class); //
 * //创建一个表BPushMessageInfo // try { // 消息存储 BPushMessageInfo id_size =
 * db.findFirst(Selector.from( BPushMessageInfo.class).where("IDid", "=",
 * mjsonobject.getString("ID"))); if (null == id_size) {
 * db.save(mBKaiBanTongZhi); } else {
 * 
 * }
 * 
 * // } catch (DbException e) { // TODO Auto-generated catch block //
 * e.printStackTrace(); // db.save(mBKaiBanTongZhi); // }
 * 
 * list_PushMessage.add(mBKaiBanTongZhi);
 * 
 * }
 */
