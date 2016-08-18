package com.jnwat.analysis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import com.google.gson.Gson;
import com.jnwat.bean.BNoticeMessage;

/**
 * @author chang-zhiyuan 解析消息通知
 */
public class ANoticeMessage {

	public ANoticeMessage() {

	}

	/**
	 * @return 解析
	 */
	/**
	 * @param res
	 * @param gson
	 * @param type
	 *            成功的话 大于0 失败小于0
	 * @return
	 */
	public List<BNoticeMessage> AnaNotice(String res, Gson gson, Handler handler) {
		List<BNoticeMessage> list_temp = new ArrayList<BNoticeMessage>();
		int type = 0;
		try {
			JSONObject jsonObj = new JSONObject(res);
			String message = jsonObj.getString("Message");
			int status;
			status = jsonObj.getInt("Status");
			if (status == 200) {
				JSONArray job_arr = jsonObj.getJSONArray("ReplyObject");
				int iii = job_arr.length();
				if (iii > 0) {
					type = 1;
				} else {
					type = 10;
				}
				for (int i = 0; i < iii; i++) {
					BNoticeMessage mBNoticeMessage = gson.fromJson(job_arr
							.getJSONObject(i).toString(), BNoticeMessage.class);
					list_temp.add(mBNoticeMessage);
				}

			} else {
				type = -1;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			handler.sendEmptyMessage(type);
			return list_temp;
		}
		handler.sendEmptyMessage(type);
		return list_temp;

	}

}
