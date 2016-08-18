/**
 * 
 */
package com.jnwat.analysis;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author zhaorl
 * 
 *         解析工资详细
 * 
 */
public class AGetSalaryDetail {

	public boolean getSalaryDetail(String result, Map<String, Object> map) {

		JSONObject jsonObj;
		boolean isOk = false;
		try {
			jsonObj = new JSONObject(result);
			// String message = jsonObj.getString("Message");
			int status = jsonObj.getInt("Status");
			if (status != 0 && status == 200) {

				JSONObject json = jsonObj.getJSONObject("ReplyObject");
				String name = json.getString("Name");
				String Gwgz = json.getString("Gwgz");
				String Xjgz = json.getString("Xjgz");
				// String Jxgz = json.getString("Jxgz");
				String Jljt = json.getString("Jljt");
				String Jcjx = json.getString("Jcjx");
				String Jljx = json.getString("Jljx");
				String Wybt = json.getString("Wybt");
				String Tigao = json.getString("Tigao");
				String Txbt = json.getString("Txbt");
				String Swjt = json.getString("Swjt");
				String Zfbt = json.getString("Zfbt");
				String Qt1 = json.getString("Qt1");
				String Qt2 = json.getString("Qt2");
				String Gzhj = json.getString("Gzhj");
				String Shd = json.getString("Shd");
				String Cf = json.getString("Cf");
				String Sds = json.getString("Sds");
				String Zfgjj = json.getString("Zfgjj");
				String Yb = json.getString("Yb");
				String Ylbx = json.getString("Ylbx");
				String Dk1 = json.getString("Dk1");
				String Dk2 = json.getString("Dk2");
				String Xj = json.getString("Xj");

				String Shfgz = json.getString("Shfgz");

				map.put("Shfgz", Shfgz);
				map.put("Gwgz", Gwgz);
				map.put("Xjgz", Xjgz);
				// map.put("Jxgz", Jxgz);
				map.put("Jljt", Jljt);
				map.put("Jcjx", Jcjx);
				map.put("Jljx", Jljx);
				map.put("Wybt", Wybt);
				map.put("Tigao", Tigao);
				map.put("Txbt", Txbt);
				map.put("Swjt", Swjt);

				map.put("Zfbt", Zfbt);
				map.put("Qt", (Float.valueOf(Qt1) + Float.valueOf(Qt2)));
				// map.put("Qt2", Qt2);
				map.put("Gzhj", Gzhj);
				map.put("Shd", Shd);
				map.put("Cf", Cf);
				map.put("Sds", Sds);
				map.put("Yb", Yb);
				map.put("Ylbx", Ylbx);
				map.put("Zfgjj", Zfgjj);
				map.put("Dk1", Dk1);
				map.put("Dk2", Dk2);
				map.put("Xj", Xj);

			}
			isOk = true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			isOk = false;
		}

		return isOk;
	}

	public boolean getRetirementSalaryDetail(String result,
			Map<String, Object> map) {

		JSONObject jsonObj;
		boolean isOk = false;

		try {
			jsonObj = new JSONObject(result);
			int status = jsonObj.getInt("Status");
			if (status != 0 && status == 200) {

				JSONObject json = jsonObj.getJSONObject("ReplyObject");
				String name = json.getString("Name");

				String Jbtxf = json.getString("Jbtxf");// 基本退休费
				String Zftxf = json.getString("Zftxf");//
				String Jljt = json.getString("Jljt");
				String Zfbt = json.getString("Zfbt");
				String Wybt = json.getString("Wybt");
				String Txbt = json.getString("Txbt");
				String Qt1 = json.getString("Qt1");
				String Qt2 = json.getString("Qt2");
				String Gzhj = json.getString("Gzhj");

				String Sf = json.getString("Sf");
				String Nqf = json.getString("Nqf");
				String Df = json.getString("Df");
				String Fz = json.getString("Fz");
				String Dk1 = json.getString("Dk1");
				String Dk2 = json.getString("Dk2");
				String Xj = json.getString("Xj");
				String Shfgz = json.getString("Shfgz");

				map.put("Shfgz", Shfgz);
				map.put("Jbtxf", Jbtxf);
				map.put("Zftxf", Zftxf);
				map.put("Jljt", Jljt);
				map.put("Zfbt", Zfbt);
				map.put("Wybt", Wybt);
				map.put("Txbt", Txbt);
				map.put("Qt1", Qt1);
				map.put("Qt2", Qt2);
				map.put("Gzhj", Gzhj);

				map.put("Sf", Sf);
				map.put("Nqf", Nqf);
				map.put("Df", Df);
				map.put("Fz", Fz);
				map.put("Dk", (Integer.valueOf(Dk1) + Integer.valueOf(Dk2)));
				map.put("Xj", Xj);
				map.put("Shfgz", Shfgz);
			}
			isOk = true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			isOk = false;
			e.printStackTrace();
		}

		return isOk;

	}

}
