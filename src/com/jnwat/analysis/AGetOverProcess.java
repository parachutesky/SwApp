package com.jnwat.analysis;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.OverTask;
import com.lidroid.xutils.util.LogUtils;

public class AGetOverProcess {
   
	public int analysisOverdProcess(String result,List<OverTask> list){
		int  iden =  0; // 当  为  1 说明  有数据  当  为 2 说明正常情况下  无数据  当为  0 说明 获取数据异常
		try {
			JSONObject jsonObj = new JSONObject(result);
			String message = jsonObj.getString("Message");
		    int status = jsonObj.getInt("Status");
		    if(status!=0&&status==200){
		    	 JSONArray jsonArray = jsonObj.getJSONArray("ReplyObject");
		    	 int length = jsonArray.length();
		         list.clear();
		         if(length > 0){
		        	 iden = 1 ;
		         }else{
		        	 iden = 2;
		         }
		    	 String str="";
		    	 for(int i =0;i < length ;i ++){
			    		JSONObject obj = (JSONObject) jsonArray.get(i);
			    		String NO = obj.getString("NO");
			    		String FLOWNAME = obj.getString("FLOWNAME");
			    		String NUM = obj.getString("NUM");
			    		OverTask task = new OverTask();
			    		task.setNO(NO);//流程id
			    		task.setFLOWNAME(FLOWNAME);
			    		task.setNUM(NUM);
			    		list.add(task);
		    	 }
		    	
		    	 
		     }
		    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("已办流程解析异常");
			e.printStackTrace();
		}
		return iden;
	}
}
