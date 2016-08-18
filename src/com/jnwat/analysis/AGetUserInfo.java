package com.jnwat.analysis;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import com.google.gson.Gson;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.ShowRemind;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 解析登录
 */
public class AGetUserInfo {

	/**
	 * 解析登录返回的数据
	 * 
	 * @return
	 */
	public void analysisUserLogin(String result, Handler mHandler) {
		int Status = 0;
		 BUserlogin.loginStatus = 0;
		JSONObject jsonobject;
		try {
			jsonobject = new JSONObject(result);

			String Message = jsonobject.get("Message").toString().trim();// 错误提示信息
			ShowRemind.ErrorMessage = Message;

			Status = jsonobject.getInt("Status");
			// ShowRemind.HANDLER_STATUS = Status;
			if (0 != Status && Status == 200) {// 如果拿到的值是正确的继续解析
				JSONObject msonobject = jsonobject.getJSONObject("ReplyObject");
				BUserlogin.loginStatus = 1;
				// Gson gson = new Gson();
				// gson.fromJson(msonobject.toString(), BUserlogin.class);
				// OpenID 暂时没解析
				BUserlogin.userid = msonobject.getString("Userid");
				BUserlogin.Flag = msonobject.getString("Flag");
				BUserlogin.Departmentid = msonobject.getString("Departmentid");
				BUserlogin.Username = msonobject.getString("Username");
				BUserlogin.Departmentname = msonobject.getString("Departmentname");
				BUserlogin.Photo = msonobject.getString("Photo");
				
			//	mHandler.sendEmptyMessage(10001);
			} else {// 解析错误编码，提示用户
				LogUtils.d("解析BUserlogin错误代码:" + Status);
				BUserlogin.loginStatus = 0;
			//	mHandler.sendEmptyMessage(10000);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("登录解析异常");
			BUserlogin.loginStatus = 0;
			mHandler.sendEmptyMessage(ShowRemind.HANDLER_ANALYSIS_ERROE);
			e.printStackTrace();
		}
		if (Status != 200) {
			BUserlogin.loginStatus = 0;// 登录失败后 显示状态为 未登录
		}
		System.out.println(">>>>>>in analysisUserLogin BUserlogin.loginStatus="
				+ BUserlogin.loginStatus);

	}

}
