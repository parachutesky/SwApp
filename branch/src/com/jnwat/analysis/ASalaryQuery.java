/**
 * 
 */
package com.jnwat.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.util.LogUtils;

/**
 * @author zhaorl
 *
 *@category 薪资返回解析 
 *
 */
public class ASalaryQuery {

	public int analySalaryList(String result,
			List<Map<String, Object>> list,boolean isClear) {
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
					JSONObject obj =  jsonArray.getJSONObject(i);
					String name = obj.getString("Name");// 人名
					String month = obj.getString("Monthly");// 月度
					String salary = obj.getString("Shfgz");// 工资
					
					Map<String, Object> hash = new HashMap<String, Object>();
					hash.put("name", name);
					hash.put("date", month);
					hash.put("gz", salary);
					
					list.add(hash);
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
