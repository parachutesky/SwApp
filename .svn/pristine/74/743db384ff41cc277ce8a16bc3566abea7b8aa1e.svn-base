package com.jnwat.analysis;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 项目列表
 */
public class ATrainingCourse {

	// 0代表成功 1 代表失败 2有新数据刷新 3 不刷新

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int analyTrainingCourse(String result,
			List<HashMap<String, String>> list, boolean isClear) {
		int type = 1;
		try {
			JSONObject jsonObj = new JSONObject(result);
			String message = jsonObj.getString("Message");
			int status = jsonObj.getInt("Status");
			if (status != 0 && status == 200) {
				if (!jsonObj.getString("ReplyObject").equals("")) {
					JSONArray jsonArray = jsonObj.getJSONArray("ReplyObject");
					int length = jsonArray.length();
					if (length > 0) {
						if (isClear) {
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
						HashMap hash = new HashMap<String, String>();
						String Code = obj.getString("Code");//
						String Projectname = obj.getString("Projectname");//
						String Traintype = obj.getString("Traintype");//
						String Startdate = obj.getString("Startdate");//
						String Enddate = obj.getString("Enddate");//
						String Pronum = obj.getString("Pronum");//
						String Projectid = obj.getString("Projectid");//
						String dbc = obj.getString("Dcbflag");

						hash.put("Code", Code);// 类型 1显示时间
						hash.put("Projectname", Projectname);
						hash.put("Traintype", Traintype);
						hash.put("Startdate", Startdate);
						hash.put("Enddate", Enddate);
						hash.put("Pronum", Pronum);
						hash.put("Projectid", Projectid);
						hash.put("hasDBC", dbc);
						list.add(hash);
					}

				}

			}
			type = 3;
			return type;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			LogUtils.d("课表查询异常");
			type = 1;
			e.printStackTrace();

			return type;

		}

	}

}
