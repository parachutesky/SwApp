package com.jnwat.analysis;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jnwat.bean.NewsInfo;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 解析课表查询
 */
public class ASearchSchedule {
	// 0代表成功 1 代表失败 2有新数据刷新 3 不刷新

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int analySearchSchedule(String result,
			List<HashMap<String, String>> list,boolean isClear) {
		int type = 1;
		try {
			JSONObject jsonObj = new JSONObject(result);
			String message = jsonObj.getString("Message");
			int status = jsonObj.getInt("Status");
			if (status != 0 && status == 200) {
				JSONArray jsonArray = jsonObj.getJSONArray("ReplyObject");
				int length = jsonArray.length();
				
			
				
				
				
				
				if (length > 0) {
					if(isClear){
						if (list.size() > 0) {
							list.clear();
						}
					}
					type = 0;
				} else {
					type = 3; // 当 isGetDATA为 2时 说明 正常情况下 无数据
				}
				for (int i = 0; i < length; i++) {
					JSONObject obj = (JSONObject) jsonArray.get(i);
					JSONArray jsonArr = obj.getJSONArray("Schedule");
					String classdate = obj.getString("Classdate");// 天数
					HashMap hash = new HashMap<String, String>();
					hash.put("TYPE", "1");// 类型 1显示时间
					hash.put("classdate", classdate);
					list.add(hash);
					// ---
					int jsonArr_lenth = jsonArr.length();
					for (int j = 0; j < jsonArr_lenth; j++) {
						HashMap hashmap = new HashMap<String, String>();
						String Positon = ((JSONObject) jsonArr.get(j))
								.getString("Positon");
						
						String COURSE = ((JSONObject) jsonArr.get(j))
								.getString("Course");
						
						String Sxw = ((JSONObject) jsonArr.get(j))
								.getString("Sxw");
						
						hashmap.put("Positon","地点: " +Positon);
						hashmap.put("COURSE", COURSE);
						hashmap.put("Sxw","时间: "+ Sxw);
						hashmap.put("TYPE", "2");// 类型
						hashmap.put("Id", ((JSONObject) jsonArr.get(j))
								.getString("Id"));// 类型
						list.add(hashmap);
				//		System.out.println("list" + list.get(j).toString());

					}
				}
				type = 3;
				return type;
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("课表查询异常");
			type = 1;
			e.printStackTrace();

			return type;

		}
		return type;

	}

}
