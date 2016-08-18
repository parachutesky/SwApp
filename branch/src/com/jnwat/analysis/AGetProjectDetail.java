package com.jnwat.analysis;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.jnwat.bean.AccomdationInfo;
import com.jnwat.bean.BProjectDetail;
import com.jnwat.bean.BTeacherMessgaeBean;
import com.jnwat.bean.TempClassBean;
import com.lidroid.xutils.util.LogUtils;
/**
 * 项目明细 
 * @author wangzh
 *
 */
public class AGetProjectDetail {
    
	public BProjectDetail analysisPersonalDeta(String result ){
		int  iden =  0; // 当  为  1 说明  有数据  当  为 2 说明正常情况下  无数据  当为  0 说明 获取数据异常
		BProjectDetail bProjectDetail = new BProjectDetail();
		List<TempClassBean> list_temp  = new ArrayList<TempClassBean>();
		
		try {
			String remark =""; 
			JSONObject jsonObj = new JSONObject(result);
			String message = jsonObj.getString("Message");
		    int status = jsonObj.getInt("Status");
		    if(status!=0&&status==200){
		    	JSONObject job = jsonObj.getJSONObject("ReplyObject");
		    	 System.out.print("<<<jsonarray>>>"+job.toString());
		    	 
	
		    	if(!job.getString("Dcb").equals("")){
		    		
		    		JSONArray array  = job.getJSONArray("Dcb");
			    	int dbcLen  = array.length();
			    	
			    	for(int i=0;i<dbcLen;i++){
			    		
			    		TempClassBean bean  = new TempClassBean();
			    		String type   =  array.getJSONObject(i).getString("Projecttype");
			    		String code   =  array.getJSONObject(i).getString("Code");
			    		String name   =  array.getJSONObject(i).getString("Projectname");
			    		String start   =  array.getJSONObject(i).getString("Startdate");
			    		String end   =  array.getJSONObject(i).getString("Enddate");
			    		String proNum   =  array.getJSONObject(i).getString("Pronum");
			    		String days   =  array.getJSONObject(i).getString("Days");
			    		String roomNo   =  array.getJSONObject(i).getString("Classroom");
			    		
			    		if( String.class.isInstance(array.getJSONObject(i).getString("Remark"))){
			    			 remark   =  array.getJSONObject(i).getString("Remark");
			    		}else{
			    			 remark   =  "";
			    		}
			    		String client   =  array.getJSONObject(i).getString("Client");
			    		
			    		bean.setProjectname(name);
			    		bean.setProjecttype(type);
			    		bean.setCode(code);
			    		bean.setStartdate(start);
			    		bean.setEnddate(end);
			    		bean.setPronum(proNum);
			    		bean.setDays(days);
			    		bean.setClassroom(roomNo);
			    		bean.setRemark(remark);
			    		bean.setClient(client);
			    		
			    		JSONArray array_room  =  array.getJSONObject(i).getJSONArray("Accommodation");
			    		
			    		List<AccomdationInfo> listInfo  = new ArrayList<AccomdationInfo>();
			    		int roomInfoLen  = array_room.length();
			    		
			    		for(int k=0;k<roomInfoLen;k++){
			    			AccomdationInfo info  = new AccomdationInfo();
			    			info.setBuilding(array_room.getJSONObject(k).getString("Building"));
			    			info.setPersons(array_room.getJSONObject(k).getString("Persons"));
			    			listInfo.add(info);
			    		}
			    		bean.setAccommodation(listInfo);
			    		list_temp.add(bean);
			    	}
			    	 bProjectDetail.setTempClassList(list_temp);
		    	}else{
		    		
		    	}
		    	
		    	String type   = job.getString("Projecttype");
	    		String code   =  job.getString("Code");
	    		String name   =  job.getString("Projectname");
	    		String start   =  job.getString("Startdate");
	    		String end   =  job.getString("Enddate");
	    		String proNum   =  job.getString("Pronum");
	    		String days   =  job.getString("Days");
	    		String roomNo   =  job.getString("Classroom");
	    		if( String.class.isInstance(job.getString("Remark"))){
	    			 remark   =  job.getString("Remark");
	    		}else{
	    			 remark   =  "";
	    		}
	    		//String remark   =  job.getString("Remark");
	    		String client   =  job.getString("Client");
		    	
	    		JSONArray array_room  =  job.getJSONArray("Accommodation");
	    		
	    		List<AccomdationInfo> listInfo  = new ArrayList<AccomdationInfo>();
	    		int roomInfoLen  = array_room.length();
	    		
	    		for(int k=0;k<roomInfoLen;k++){
	    			AccomdationInfo info  = new AccomdationInfo();
	    			info.setBuilding(array_room.getJSONObject(k).getString("Building"));
	    			info.setPersons(array_room.getJSONObject(k).getString("Persons"));
	    			listInfo.add(info);
	    		}
	    		
	    		bProjectDetail.setProjectname(name);
	    		bProjectDetail.setProjecttype(type);
	    		bProjectDetail.setCode(code);
	    		bProjectDetail.setStartdate(start);
	    		bProjectDetail.setEnddate(end);
	    		bProjectDetail.setPronum(proNum);
	    		bProjectDetail.setDays(days);
	    		bProjectDetail.setClassroom(roomNo);
	    		bProjectDetail.setRemark(remark);
	    		bProjectDetail.setClient(client);
	    		bProjectDetail.setAccommodation(listInfo);
		    	
	    		
		     }
		    
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("个人档案解析异常");
			e.printStackTrace();
		}
		return bProjectDetail;
	
	
	}
	
}
