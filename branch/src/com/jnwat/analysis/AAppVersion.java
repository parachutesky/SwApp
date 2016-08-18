package com.jnwat.analysis;

import org.json.JSONArray;
import org.json.JSONObject;

import com.jnwat.bean.BAppVersion;
import com.jnwat.config.ShowRemind;
import com.lidroid.xutils.util.LogUtils;

/**
 * AppVersion data 解析 Created by WeiBo on 14/12/12.
 */
public class AAppVersion {

	public AAppVersion() {
		
	}

	/**
	 * 登录
	 * @param data
	 * @return 解析网络获取的数据
	 */
	public   BAppVersion Login_SetData(String data) {
		BAppVersion bAppVersion = new BAppVersion();
		try {
			JSONObject mjsonobject = new JSONObject(data);
			String Message = mjsonobject.get("Message").toString().trim();// 错误提示信息
			ShowRemind.ErrorMessage = Message;
			int Status = mjsonobject.getInt("Status");
			if (0 != Status && Status == 200) {// 如果拿到的值是正确的继续解析
				JSONObject msonobject = mjsonobject.getJSONObject("ReplyObject");
				bAppVersion.setDATADATE(msonobject.getString("Publishdate").trim());
				bAppVersion.setVERSION(Float.parseFloat(msonobject.getString("Versionnumber").trim()));
				bAppVersion.setFILENAME(msonobject.getString("Filepath").trim());
				bAppVersion.setINFO(msonobject.getString("Info"));
				System.out.println("路径："+bAppVersion.getFILENAME());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return bAppVersion;
	}


	
	
	/*
	 * 
	public   BAppVersion CheckVersion_SetData(String data) {
		BAppVersion bAppVersion = new BAppVersion();
		try {
			JSONObject jsonObj = new JSONObject(data);//
			if (!bAppVersion.isSuccess()) {// 判断是否获取数据成功
				return null;
			}
			JSONObject jodata = jsonObj.getJSONObject("data");
			bAppVersion.setVercode(jodata.getInt("vercode"));
			bAppVersion.setVername(jodata.getString("vername"));
			bAppVersion.setVerfile(jodata.getString("verfile"));
			bAppVersion.setVerlog(jodata.getString("verlog"));
			// bAppVersion.setErrorMsg(jodata.getString("ErrorMsg"));
			setErrorMsg(jodata.getString("ErrorMsg"));
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.d(e.getCause() + "");
		}
		return bAppVersion;
	}
	 * */
}
