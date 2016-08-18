/**
 * 
 */
package com.jnwat.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import com.jnwat.bean.UnEducationBean;

/**
 * @author zhaorl
 * 
 * @category 培训项目列表
 */
public class ATrainingProgramList {

	
	public List<Map<String,Object>> getTrainingList(String result
			) throws JSONException{
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		JSONObject json = new JSONObject(result);
		JSONObject obj = json.getJSONObject("ReplyObject");
		int Status = json.getInt("Status");
		
		if (0 != Status && Status == 200) {   // 如果拿到的值是正确的继续解析
			
			String  classNum  = obj.getString("Count");
			String  peopleNum  = obj.getString("Persons");
			Map<String,Object> map  = new HashMap<String,Object>();
			map.put("index", "在办培训班数");
			map.put("value", classNum);
			 list.add(map);
			 Map<String,Object> map1  = new HashMap<String,Object>();
			 map1.put("index", "当前在校人数");
		     map1.put("value", peopleNum);
		     list.add(map1);

			 Map<String,Object> map2  = new HashMap<String,Object>();
			 map2.put("index", "办班情况统计图表");
		     map2.put("value", -100);
		     list.add(map2);
		//handler.sendEmptyMessage(Status);
	}else{
		//handler.sendEmptyMessage(101);
	}
		return list;
	}
}
