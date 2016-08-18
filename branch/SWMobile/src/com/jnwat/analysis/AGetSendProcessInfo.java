package com.jnwat.analysis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.OAProcess;
import com.lidroid.xutils.util.LogUtils;

public class AGetSendProcessInfo {
   
	public int analysisSendProcess(String result,List<String> group,List<List<OAProcess>> child){
		int iden = 0; //当 为 0 说明  获取服务 异常   当为 1说明   正常获取到数据   当为 2 说明  正常情况下无数据
		try {
			JSONObject jsonObj = new JSONObject(result);
			String message = jsonObj.getString("Message");
		    int status = jsonObj.getInt("Status");
		    if(status!=0&&status==200){
		    	 JSONArray jsonArray = jsonObj.getJSONArray("ReplyObject");
		    	 BIntentObj.IsGetSendProcess = true;
		    	 int length = jsonArray.length();
		    	 if(length>0){
		    		 iden = 1;   
		    	 }else if(length ==0){
		    		 iden = 2;
		    	 }
		    	 group.clear();
	    		 child.clear();
		    	 String str="";
		    	 List<OAProcess> ChildItem = new ArrayList<OAProcess>();
		    	 for(int i =0;i < length ;i ++){
			    		JSONObject obj = (JSONObject) jsonArray.get(i);
			    		String No = obj.getString("No");
			    		String Sort = obj.getString("Sort");
			    		String Name = obj.getString("Name");
			    		OAProcess process = new OAProcess();
			    		process.setNo(No);
			    		process.setName(Name);
			    		process.setSort(Sort);
			    		if(!Sort.equals(str)&&!"".equals(str)){
			    			 group.add(str);
			    			 child.add(ChildItem);
			    			 ChildItem = new ArrayList<OAProcess>();
			    			 ChildItem.add(process);
			    			 str = Sort;
			    		}else{
			    			ChildItem.add(process);
			    			str = Sort;
			    		}
			    		
		    	 }
		    	 child.add(ChildItem);
		    	 
		     }
		    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("发送流程解析异常");
			e.printStackTrace();
		}
		return iden;
	}
}
