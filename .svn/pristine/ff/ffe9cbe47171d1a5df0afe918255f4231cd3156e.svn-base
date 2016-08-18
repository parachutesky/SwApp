package com.jnwat.analysis;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BTeacherMessgaeBean;
import com.jnwat.bean.OverTask;
import com.lidroid.xutils.util.LogUtils;

public class AGETPERSONALDETAIL {
	public BTeacherMessgaeBean analysisPersonalDetail(String result ){
		int  iden =  0; // 当  为  1 说明  有数据  当  为 2 说明正常情况下  无数据  当为  0 说明 获取数据异常
		BTeacherMessgaeBean teacherMessageBean = new BTeacherMessgaeBean();
		try {
			JSONObject jsonObj = new JSONObject(result);
			String message = jsonObj.getString("Message");
		    int status = jsonObj.getInt("Status");
		    if(status!=0&&status==200){
		    	JSONObject job = jsonObj.getJSONObject("ReplyObject");
		    	 System.out.print("<<<jsonarray>>>"+job.toString());
		    	if(job==null){
		    		return null;
		    	}
		    	 Gson gson = new Gson();
		    	
		    	 teacherMessageBean = gson.fromJson(job.toString(), BTeacherMessgaeBean.class);
		    	
		    	 
		     }
		    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("个人档案解析异常");
			e.printStackTrace();
		}
		return teacherMessageBean;
	
	
	}
}
