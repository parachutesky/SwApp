package com.jnwat.analysis;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import com.jnwat.config.ShowRemind;
import com.lidroid.xutils.util.LogUtils;

public class AGetCurNodeByworkid {
	private ArrayList<HashMap<String, String>> list_getnode;
    private int status;
	
	public ArrayList<HashMap<String,String>> analyGetCurNode(String result,Handler mHandler){
		list_getnode = new ArrayList<HashMap<String,String>>();
	try {
		JSONObject jsonObj = new JSONObject(result);
		String message = jsonObj.getString("message");
		ShowRemind.ErrorMessage = message;
	     status = jsonObj.getInt("status");
	    if(status!=0&&status==200){
	    	
	    	 JSONObject jsonObject  = jsonObj.getJSONObject("replyobject");
	    	JSONArray nextnodes = jsonObject.getJSONArray("nextnodes");
	    	
	    	int i = nextnodes.length();
	    	for (int j = 0; j <i; j++) {
	    		JSONObject obj = (JSONObject) nextnodes.get(j);
	    		HashMap<String,String> hash  = new HashMap<String,String>();
	    		hash.put("nodeid", obj.getInt("nodeid")+"");
	    		hash.put("nodename", obj.getString("nodename"));
	    		hash.put("recname", obj.getString("recname"));
	    		list_getnode.add(hash);
			}
	    	
	    	
	     }
	    mHandler.sendEmptyMessage(201);
	    
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		LogUtils.d("新闻解析异常");
		e.printStackTrace();
	    mHandler.sendEmptyMessage(status);
	}
	return list_getnode;
	
	}
	
	

}
