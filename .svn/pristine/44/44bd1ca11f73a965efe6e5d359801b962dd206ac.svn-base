package com.jnwat.analysis;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

import com.jnwat.bean.BUserTasks;
import com.jnwat.bean.OverTask;
import com.lidroid.xutils.util.LogUtils;

public class AGetOverProcessDetail {
    public int analysisOverdProcess(String result,List<BUserTasks> lisArray){
    	int iden = 0; // 当 为0 说明  获取服务异常    当为   1 说明正常情况下有数据   当为  2 说明 正常情况下无数据
    	lisArray.clear();
    	try {
			JSONObject jsonObj = new JSONObject(result);
			String message = jsonObj.getString("Message");
		    int status = jsonObj.getInt("Status");
		    if(status!=0&&status==200){
		    	 JSONArray jsonArray = jsonObj.getJSONArray("ReplyObject");
		    	 int length = jsonArray.length();
		    	 if(length>0){
		    		 iden = 1;
		    	 }else if(length ==0){
		    		 iden = 2;
		    	 }
		    	 String str="";
		    	 for(int i =0;i < length ;i ++){
			    		JSONObject obj = (JSONObject) jsonArray.get(i);
			    		String WORKID = (int)obj.getDouble("TaskId")+"";// ID
			    		String STARTERNAME = obj.getString("Sender");//发起人
			    		String TITLE= obj.getString("TaskName");
			    		String RDT = obj.getString("StartDate");  
			    		String FLOWNAME = obj.getString("FlowName");
			    		String FK_NODE = obj.getString("CurrNodeID");
			    		String FID = obj.getString("FID");
			    		String CurrNodeName = obj.getString("CurrNodeName");
			    		String photo = obj.getString("Sender_Photo");
			    		
			    		BUserTasks task = new BUserTasks();
			    		task.setTaskId(WORKID);
			    		task.setSender(STARTERNAME);
			    		task.setTitle(TITLE);
			    		task.setRdt(RDT);
			    		task.setFlowName(FLOWNAME);
			    		task.setCurrNodeID(FK_NODE);
			    		task.setFID(FID);
			    		task.setCurrNodeName(CurrNodeName);
			    		task.setSender_Photo(photo);
			    		
			    		lisArray.add(task);
		    	 }
		    
		     }
		    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("已办流程detail解析异常");
			e.printStackTrace();
		}
    	return iden ;
    }
}
