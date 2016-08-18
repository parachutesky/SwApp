/**
 * 
 */
package com.jnwat.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import com.jnwat.bean.BTaskInfo;
import com.jnwat.bean.BTaskInfo_DetailForm;
import com.jnwat.bean.UnEducationBean;
import com.jnwat.config.ShowRemind;
import com.jnwat.tools.JsonTools;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author zhaorl
 * 
 * @category 获取非教学工作信息
 * 
 */
public class AGetUnEducationMessage {

	/**
	 * @return UnEducationBean
	 * 
	 *    解析返回的列表
	 * 
	 * @throws JSONException
	 * 
	 */
	
	public int getUnEducationList(String result,List<UnEducationBean> list,boolean isClear) throws JSONException {
		LogUtils.d(result);
		int mode  = 1;
		JSONObject json = new JSONObject(result);
		JSONArray array = json.getJSONArray("ReplyObject");
		if (array != null && array.length() > 0) {
			
	       int arrLen  = array.length();
	       if (arrLen > 0) {
				if(isClear){
					if (list.size() > 0) {
						list.clear();
					}
				}
				mode = 0;
			} else {
				mode = 3; // 当 isGetDATA为 2时 说明 正常情况下 无数据
			}
	      
	    	   for (int i = 0; i < arrLen; i++) {

					  UnEducationBean bean = new UnEducationBean();
					  String week_start  = array.getJSONObject(i).getString("Weekdaystart");
					  String week_end  = array.getJSONObject(i).getString("Weekdayend");
					  
						String workId = array.getJSONObject(i).getString("Workid");
						String type = array.getJSONObject(i).getString("Type");
						String content = array.getJSONObject(i).getString("Content");
						String start = array.getJSONObject(i).getString("Startdate");
						String end = array.getJSONObject(i).getString("Enddate");
						
						bean.setWeekStart(week_start);
						bean.setWeekEnd(week_end);
						bean.setWorkId(workId);
						bean.setType(type);
						bean.setContent(content);
						bean.setTitle(content);
						bean.setStartTime(start);
						bean.setEndTime(end);
					    list.add(bean);
				
	       }
			mode = 3;
			//return mode;
			
		} else {
			 mode = 3;
		}
		return mode;
	}

	/**
	 * 解析返回的数据
	 * 
	 * @return UnEducationBean
	 * 
	 * 
	 */

	public UnEducationBean analysisUnEducated(String result, Handler mHandler) {
		LogUtils.d(result);
		UnEducationBean bean = new UnEducationBean();
		Map<String, Object> map = null;
		try {
			JSONObject mjsonobject = new JSONObject(result);
			String Message = mjsonobject.get("Message").toString().trim();// 错误提示信息
			ShowRemind.ErrorMessage = Message;
			int Status = mjsonobject.getInt("Status");

			if (0 != Status && Status == 200) { // 如果拿到的值是正确的继续解析
				JSONObject msonobject = mjsonobject
						.getJSONObject("ReplyObject");

				if (!msonobject.getString("Type").equals("")) {
					map = new HashMap<String, Object>();
					map.put("index", "类别");
					map.put("value", msonobject.getString("Type"));
					bean.list.add(map);
				}

				if (!msonobject.getString("Starttime").equals("")) {
					map = new HashMap<String, Object>();
					map.put("index", "开始时间");
					map.put("value", msonobject.getString("Starttime"));
					bean.list.add(map);
				}
				if (!msonobject.getString("Endtime").equals("")) {
					map = new HashMap<String, Object>();
					map.put("index", "结算时间");
					map.put("value", msonobject.getString("Endtime"));
					bean.list.add(map);
				}
				if (!msonobject.getString("Worktime").equals("")) {
					map = new HashMap<String, Object>();
					map.put("index", "工作量");
					map.put("value", msonobject.getString("Worktime"));
					bean.list.add(map);
				}
				if (!msonobject.getString("Content").equals("")) {
					map = new HashMap<String, Object>();
					map.put("index", "具体内容");
					map.put("value", msonobject.getString("Content"));
					bean.list.add(map);
				}

				mHandler.sendEmptyMessage(Status);
				return bean;

			} else {// 解析错误编码，提示用户
				LogUtils.d("解析BUserlogin错误代码:" + Status);
				mHandler.sendEmptyMessage(Status);
				return bean;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("登录解析异常");
			mHandler.sendEmptyMessage(ShowRemind.HANDLER_NET_ERROE);
			e.printStackTrace();
		}
		return bean;

	}

}
