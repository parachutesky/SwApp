package com.jnwat.analysis;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.NewsInfo;
import com.jnwat.bean.OverTask;
import com.jnwat.bean.Participatants;
import com.lidroid.xutils.util.LogUtils;

public class AGetParticipatants {
      
public int analysisParticipatants(String result,List<Participatants> list){
		int  isGETDATA = 0; //当 为 0 时说明 是 未获取新数据   当   为  1 时 说明  获取了新的数据
		try {
			JSONObject jsonObj = new JSONObject(result);
			String message = jsonObj.getString("Message");
		    int status = jsonObj.getInt("Status");
		    if(status!=0&&status==200){
		    	 JSONArray jsonArray = jsonObj.getJSONArray("ReplyObject");
		    	 int length = jsonArray.length();
		    	 if(length>0){
		    		 isGETDATA = 1;
		    	 }else{
		    		 isGETDATA = 2; // 当 isGetDATA为 2时 说明  正常情况下 无数据
		    	 }
		    	 String str="";
		    	 for(int i =0;i < length ;i ++){
			    		JSONObject obj = (JSONObject) jsonArray.get(i);
			    		String Name = obj.getString("Name");
			    		String MobilePhone = obj.getString("Mobilephone");
			    		String Organization = obj.getString("Organization");
			    		Participatants participatants = new Participatants();
			    		participatants.setName(Name);
			    		participatants.setMobilePhone(MobilePhone);
			    		participatants.setOrganization(Organization);
			    		list.add(participatants);
		    	 }
		    	
		    	 
		 }
		    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("新闻解析异常");
			e.printStackTrace();
			
		}
		return isGETDATA;
	}
  
}
