package com.jnwat.swmobilegy.mail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.jnwat.bean.Contacts;
import com.jnwat.config.ShowRemind;
import com.lidroid.xutils.util.LogUtils;

public class AGetContactsInfo {
   public int analysisMaillist(String result,Handler mHandler,Contacts contactsParams){
	   int  iden = 0; // 当  为 0 说明 获取数据异常   为 1 说明  正常情况下有数据  为  2 说明正常情况下无数据
	   List<Contacts> contactArray = new ArrayList<Contacts>();
       try {
	         
		     JSONObject jsonObj = new JSONObject(result);
		     String message = jsonObj.getString("Message");
		     String ReplyObject = jsonObj.getString("ReplyObject");
		     int status = jsonObj.getInt("Status");
		     if(status!=0&&status==200){
		    	  JSONArray jsonArray = jsonObj.getJSONArray("ReplyObject");
		    	  int length = jsonArray.length();
		    	  if(length>0){
		    		  iden = 1;
		    	  }else if(length==0){
		    		  iden = 2;
		    	  }
		    	  Gson gson = new Gson();
		    	  Contacts contacts = new Contacts();
		    	  contacts =gson.fromJson(result, Contacts.class);
		    	  contactsParams.setReplyObject(contacts.getReplyObject());
		    	 /* for(int i = 0; i < length ; i ++){
		    		  JSONObject ob = jsonArray.getJSONObject(i);
		    		  
		    		  
		    		  Contacts contacts = new Contacts();
		    		  contacts.setName(ob.getString("NAME"));
		    		  contacts.setSex(ob.getString("SEX"));
		    		  contacts.setOffPhone(ob.getString("OFFPHONE"));
		    		  contacts.setMobPhone(ob.getString("MOBPHONE"));
		    		  contacts.setDepartment(ob.getString("DEPARTMENT"));
		    		  contacts.setEmail(ob.getString("EMAIL"));
		    		  contacts.setHeadUrl(ob.getString("HEADURL"));
		    		  contacts.setPingyin(ob.getString("PINYIN1"));
		    		  contacts.setShortPingyin(ob.getString("PINYIN2"));
		    		  lisArray.add(contacts);
		    	  }
		    	  */
		       }else{
		    	   mHandler.sendEmptyMessage(status);
		       }
	       } catch (JSONException e) {
		           // TODO Auto-generated catch block
	    	       mHandler.sendEmptyMessage(ShowRemind.HANDLER_ANALYSIS_ERROE);
	    	       LogUtils.d("analysisMaillist 通讯录   解析异常");
		           e.printStackTrace();
	       }
             
             return iden;  
      }
}
