package com.jnwat.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;

import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BMeetingApplyLeade;
import com.jnwat.bean.BTaskInfo;
import com.jnwat.bean.BTaskInfo_DetailForm;
import com.jnwat.config.ShowRemind;
import com.jnwat.tools.JsonTools;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 解析任务信息
 */
public class ATaskInfo {
	HashMap<String, List<BMeetingApplyLeade>> mmBMeetingApplyLeade_list;
	HashMap<String, List<BMeetingApplyLeade>> mmmBMeetingApplyLeade_list;

	/**
	 * 解析登录返回的数据
	 * 
	 * @return
	 */
	public BTaskInfo analysisTaskInfo(String result, Handler mHandler,
			boolean isend) {
		LogUtils.d(result);
		BTaskInfo mBTaskInfo = new BTaskInfo();
		mBTaskInfo.detail = new ArrayList<BTaskInfo_DetailForm>();

		try {
			JSONObject mjsonobject = new JSONObject(result);
			;
			String Message = mjsonobject.get("Message").toString().trim();// 错误提示信息
			ShowRemind.ErrorMessage = Message;
			int Status = mjsonobject.getInt("Status");

			if (0 != Status && Status == 200) {// 如果拿到的值是正确的继续解析
				JSONObject msonobject = mjsonobject
						.getJSONObject("ReplyObject");
				// 拿到ID
				mBTaskInfo.WorkId = msonobject.getString("WorkId");
				mBTaskInfo.TaskId = msonobject.getString("TaskId");
				BIntentObj.mBUserTasks.setSender_Photo(msonobject.getString("Sender_Photo"))     ;
				BIntentObj.mBUserTasks.setFlowName(msonobject.getString("FlowName"))     ;
				BIntentObj.mBUserTasks.setSender(msonobject.getString("Sender"))     ;
				BIntentObj.mBUserTasks.setTaskName(msonobject.getString("TaskName"))     ;
				BIntentObj.mBUserTasks.setWork_State(msonobject.getString("Wfstate"))     ;
				int mlenth = msonobject.length();
				LogUtils.e("对象的长度:" + mlenth);

				// 取的content 内容
				mBTaskInfo.content = JsonTools.getArrayList(msonobject
						.getJSONArray("TaskInfoContent"));

				// Attachment 内容
				mBTaskInfo.attachment = JsonTools.getArrayList(msonobject
						.getJSONArray("Attachments"));

				// detail 内容
				// mBTaskInfo.detail
				JSONArray mJsonarrayJson_detail = msonobject
						.getJSONArray("TaskInfoDetails");// 获取detail

				for (int i = 0; i < mJsonarrayJson_detail.length(); i++) {// 遍历表
					BTaskInfo_DetailForm mBTaskInfo_DetailForm = new BTaskInfo_DetailForm();
					JSONObject mJsonObject_detail = mJsonarrayJson_detail
							.getJSONObject(i);
					// 表的JAVAbean
					// DetailForm mDetailForm = new DetailForm();
					JSONArray mJSONArray_title = mJsonObject_detail
							.getJSONArray("Titles");
					for (int j = 0; j < mJSONArray_title.length(); j++) {
						// ..
						JSONObject mJsonObject_title = mJSONArray_title
								.getJSONObject(j);
						mBTaskInfo_DetailForm.title = mJsonObject_title
								.getString("Name").split(",");
						// mJsonObject_title.getString("Titles");//拿到题目
					}
					// 拿到表信息
					JSONArray mJSONArray_columns = mJsonObject_detail
							.getJSONArray("Columns");
					ArrayList<String[]> mcolumns_row = new ArrayList<String[]>();
					for (int k = 0; k < mJSONArray_columns.length(); k++) {
						JSONObject mJsonObject_row = mJSONArray_columns
								.getJSONObject(k);
						mcolumns_row.add(mJsonObject_row.getString("Row")
								.split(","));
					}
					mBTaskInfo_DetailForm.columns = mcolumns_row;// 赋值
					// 添加表到数组
					mBTaskInfo.detail.add(mBTaskInfo_DetailForm);
				}

				mBTaskInfo.TaskSzs = JsonTools.getArrayList(msonobject
						.getJSONArray("TaskSzs"));
				// 下一个节点
				// NextNodeInfo
				System.out.println("isend:" + isend);
				if (isend) {

					ArrayList<BMeetingApplyLeade> mlist_getnode = new ArrayList<BMeetingApplyLeade>();
					ArrayList<BMeetingApplyLeade> mlist_getreturnnode = new ArrayList<BMeetingApplyLeade>();
					if (msonobject.getString("NextNodeInfo").equals("")) {

					} else {
						JSONObject mNextoBbj = msonobject
								.getJSONObject("NextNodeInfo");
						// System.out.println(mNextoBbj.toString());
						mBTaskInfo.isend = mNextoBbj.getBoolean("isend");
						mBTaskInfo.isline = mNextoBbj.getBoolean("isline");
						mBTaskInfo.isselect = mNextoBbj.getBoolean("isselect");
						mBTaskInfo.ismul = mNextoBbj.getBoolean("ismul");
						BIntentObj.ismul = mBTaskInfo.ismul;
						BIntentObj.isreturn = mNextoBbj.getBoolean("isreturn");
						BIntentObj.issub = mNextoBbj.getBoolean("issub");

						if (mBTaskInfo.isend) {

						} else {

							if (null != BIntentObj.list_getnode_child
									&& BIntentObj.list_getnode_child.size() > 0) {

								BIntentObj.list_getnode_child.clear();
							}
							if (null != BIntentObj.list_getreturnnode_child
									&& BIntentObj.list_getreturnnode_child
											.size() > 0) {

								BIntentObj.list_getreturnnode_child.clear();
							}

							JSONArray nextnodes = mNextoBbj
									.getJSONArray("nextnodes");
			
									
							if (nextnodes != null && !nextnodes.equals("")) {
								int i = nextnodes.length();
								mmBMeetingApplyLeade_list = new HashMap<String, List<BMeetingApplyLeade>>();
								for (int j = 0; j < i; j++) {
									JSONObject obj = (JSONObject) nextnodes
											.get(j);
									if(j == 0){
										BIntentObj.issub_str   = 		obj.getString("rec").toString();
										BIntentObj.issub_str_ID = obj.getString("nodeid").toString();
										System.out.println("issub_str.."+BIntentObj.issub_str +"..."+		BIntentObj.issub_str_ID );
									}
							
											
									BMeetingApplyLeade mBMeetingApplyLeade = new BMeetingApplyLeade();
									String recname = obj.getString("recname");
									mBMeetingApplyLeade.setRecname(recname);
									mBMeetingApplyLeade.setNodeid(obj
											.getString("nodeid"));
									mBMeetingApplyLeade.setIdididi("");
									mBMeetingApplyLeade.setNodename(obj
											.getString("nodename"));
									mBMeetingApplyLeade.setReceNo(obj
											.getString("rec"));
									mlist_getnode.add(mBMeetingApplyLeade);

									int lenth = recname.split(",").length;
									List<BMeetingApplyLeade> list_child = new ArrayList<BMeetingApplyLeade>();
									for (int k = 0; k < lenth; k++) {
										BMeetingApplyLeade mmBMeetingApplyLeade = new BMeetingApplyLeade();

										mmBMeetingApplyLeade.setNodeid(obj
												.getString("nodeid"));
										mmBMeetingApplyLeade.setIdididi("");
										mmBMeetingApplyLeade.setNodename(obj
												.getString("nodename"));
										mmBMeetingApplyLeade.setRecname(recname
												.split(",")[k]);
										mmBMeetingApplyLeade
												.setReceNo(obj.getString("rec")
														.split(",")[k]);

										list_child.add(mmBMeetingApplyLeade);

									}
									mmBMeetingApplyLeade_list.put(
											mBMeetingApplyLeade.getNodeid(),
											list_child);

								}
								BIntentObj.list_getnode_child = mmBMeetingApplyLeade_list;
								BIntentObj.list_getnode = mlist_getnode;
							}

							JSONArray returnnode = mNextoBbj
									.getJSONArray("returnnode");
							if (returnnode != null && !returnnode.equals("")) {
								int i = returnnode.length();
								mmmBMeetingApplyLeade_list = new HashMap<String, List<BMeetingApplyLeade>>();
								for (int j = 0; j < i; j++) {
									JSONObject obj = (JSONObject) returnnode
											.get(j);
									BMeetingApplyLeade mBMeetingApplyLeade = new BMeetingApplyLeade();
									String recname = obj.getString("recname");
									mBMeetingApplyLeade.setRecname(recname);
									mBMeetingApplyLeade.setNodeid(obj
											.getString("nodeid"));
									mBMeetingApplyLeade.setIdididi("");
									mBMeetingApplyLeade.setNodename(obj
											.getString("nodename"));
									mBMeetingApplyLeade.setReceNo(obj
											.getString("rec"));
									mlist_getreturnnode
											.add(mBMeetingApplyLeade);

									int lenth = recname.split(",").length;
									List<BMeetingApplyLeade> list_child = new ArrayList<BMeetingApplyLeade>();
									for (int k = 0; k < lenth; k++) {
										BMeetingApplyLeade mmBMeetingApplyLeade = new BMeetingApplyLeade();

										mmBMeetingApplyLeade.setNodeid(obj
												.getString("nodeid"));
										mmBMeetingApplyLeade.setIdididi("");
										mmBMeetingApplyLeade.setNodename(obj
												.getString("nodename"));
										mmBMeetingApplyLeade.setRecname(recname
												.split(",")[k]);
										mmBMeetingApplyLeade
												.setReceNo(obj.getString("rec")
														.split(",")[k]);

										list_child.add(mmBMeetingApplyLeade);

									}
									mmmBMeetingApplyLeade_list.put(
											mBMeetingApplyLeade.getNodeid(),
											list_child);
								}
								BIntentObj.list_getreturnnode_child = mmmBMeetingApplyLeade_list;
								BIntentObj.list_getreturnnode = mlist_getreturnnode;
							}

						}

						// mBTaskInfo.list_getreturnnode = mlist_getreturnnode;
					}
				}
				// JsonTools.parseJson(msonobject.getJSONArray("TaskInfoDetails").toString());

				/*
				 * // examination JSONObject examination =
				 * msonobject.getJSONObject("examination"); // Track 内容
				 * mBTaskInfo.Track = JsonTools
				 * .getArrayList(examination.getJSONArray("Track")); //
				 * WF_CCList 内容 JSONArray WF_CCList =
				 * examination.getJSONArray("WF_CCList"); if(null !=WF_CCList){
				 * System.out.println("WF_CCList:"+WF_CCList.toString()); }else{
				 * System.out.println("WF_CCList:"+WF_CCList); }
				 * 
				 * mBTaskInfo.WF_CCList = JsonTools .getArrayList(WF_CCList);
				 * System.out.println(mBTaskInfo.WF_CCList);
				 */

				/*
				 * // WF_SelectAccper 内容 mBTaskInfo.WF_SelectAccper = JsonTools
				 * .getArrayList(examination.getJSONArray("WF_SelectAccper"));
				 * // WF_Node 内容 mBTaskInfo.WF_Node = JsonTools
				 * .getArrayList(examination.getJSONArray("WF_Node")); //
				 * WF_Direction 内容 mBTaskInfo.WF_Direction = JsonTools
				 * .getArrayList(examination.getJSONArray("WF_Direction"));
				 */
				// dynamic

				/*
				 * // JSON转成HashMap HashMap<String, Object> content = JsonTools
				 * .parseJson(msonobject.toString()); Iterator<Entry<String,
				 * Object>> iter = content.entrySet() .iterator();
				 * 
				 * while (iter.hasNext()) { Map.Entry entry = (Map.Entry)
				 * iter.next(); Object key = entry.getKey(); Object val =
				 * entry.getValue(); ArrayList<HashMap<String, Object>>
				 * nub_arrayl = (ArrayList<HashMap<String, Object>>) val; for
				 * (HashMap<String, Object> m : nub_arrayl) { for (String k :
				 * m.keySet()) { System.out.println(k + " : " + m.get(k)); } }
				 * System.out.println("key:" + key + "\n" + "val:" + val +
				 * "\n");
				 * 
				 * return null; } /* else { mList = null; LogUtils.d("没有新数据");
				 * 
				 * // 没有新数据 }
				 */
				mHandler.sendEmptyMessage(Status);
				return mBTaskInfo;

			} else {// 解析错误编码，提示用户
				LogUtils.d("解析BUserlogin错误代码:" + Status);
				mHandler.sendEmptyMessage(Status);
				return mBTaskInfo;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LogUtils.d("登录解析异常");
			mHandler.sendEmptyMessage(ShowRemind.HANDLER_NET_ERROE);
			e.printStackTrace();
		}
		return mBTaskInfo;

	}
}
