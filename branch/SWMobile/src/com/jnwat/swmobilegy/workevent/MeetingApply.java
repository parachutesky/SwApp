package com.jnwat.swmobilegy.workevent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jnwat.analysis.ATaskInfo;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BTaskInfo;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.dialog.PopClickListening;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.dialog.PopWindowShow;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterPopSelectLeader;
import com.jnwat.swmobilegy.view.CircleImageView;
import com.jnwat.tools.DownLoadFileTools;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.SDCardTools;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 会议审核申请
 */
public class MeetingApply extends BaseActivity {
	// 返回的数据解析
	private Button btn_meetingapply_check;
	private LinearLayout lin_meetingcheck_detail_, lin_meetingcheck_task,
			lin_meeting_nameinfo, lin_meetingcheck_attachment,
			lin_meeting_main;
	private ScrollView sc_view;
	private CircleImageView iv_meetingcheck_title_user;
	private BTaskInfo mBTaskInfo;
	private LayoutInflater mInflater;
	private BitmapUtils bitmapUtils;
	private boolean detile_title;
	private PopWindowShow popWindowShow;
	BitmapDisplayConfig config;
	private MyTask mTask;
	private String filePath;
	private int file_posi;
	private TextView tv_meetingcheck_title_name, tv_flow_name,
			tv_state_overshenpi;// tv_state_overshenpi 头部 待审批的 显示控件
	private final String SHENPI = "已审批";
	private ArrayList wide_List;
	private String pop_leaderid = ""; // 下一节点
	private final String EXAMINE = "1"; // 标记 审批类型
	private final String PIGEONHOLE = "0"; // 标记 归档 类型
	private String recepterName; // 接收人的 姓名
	AdapterPopSelectLeader mAdapterPopSelectLeader;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		lin_meeting_main = (LinearLayout) findViewById(R.id.lin_meeting_main);
		sc_view = (ScrollView) findViewById(R.id.sc_view);
		btn_meetingapply_check = (Button) findViewById(R.id.btn_meetingapply_check);
		iv_meetingcheck_title_user = (CircleImageView) findViewById(R.id.iv_meetingcheck_title_user);
		lin_meetingcheck_detail_ = (LinearLayout) findViewById(R.id.lin_meetingcheck_detail_);// 设置整个布局是否显示
		lin_meetingcheck_task = (LinearLayout) findViewById(R.id.lin_meetingcheck_task);// 设置流程
		lin_meeting_nameinfo = (LinearLayout) findViewById(R.id.lin_meeting_nameinfo);// 人员详情
		lin_meetingcheck_attachment = (LinearLayout) findViewById(R.id.lin_meetingcheck_attachment);// 人员详情
		tv_meetingcheck_title_name = (TextView) findViewById(R.id.tv_meetingcheck_title_name);// 姓名
		tv_flow_name = (TextView) findViewById(R.id.tv_flow_name);// 姓名
		tv_state_overshenpi = (TextView) findViewById(R.id.tv_state_overshenpi);
		config = new BitmapDisplayConfig();
		config.setLoadingDrawable(getResources().getDrawable(
				R.drawable.detail_icon_user_default));
		config.setLoadFailedDrawable(getResources().getDrawable(
				R.drawable.detail_icon_user_default));

		if (null != BIntentObj.mBUserTasks.getCurrNodeName()) {

			setBackListener(BIntentObj.mBUserTasks.getFlowName(), true);

			tv_meetingcheck_title_name.setText(BIntentObj.mBUserTasks
					.getSender());
			if (!BIntentObj.mBUserTasks.getSender_Photo().equals("")) {
				bitmapUtils.display(iv_meetingcheck_title_user,
						BIntentObj.mBUserTasks.getSender_Photo());
			}

			tv_flow_name.setText(BIntentObj.mBUserTasks.getFlowName());
			detile_title = true;
		} else {
			setBackListener("审核", true);
		}
		setInVistibility();
		// initlinDetailView();
		// initLinAtttachment();
	}

	/**
	 * 不可见视图
	 */
	private void setInVistibility() {
		lin_meetingcheck_detail_.setVisibility(View.GONE);
		btn_meetingapply_check.setVisibility(View.GONE);
		lin_meetingcheck_task.setVisibility(View.GONE);
		lin_meeting_nameinfo.setVisibility(View.INVISIBLE);
		lin_meetingcheck_attachment.setVisibility(View.GONE);
	}

	//

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	/**
	 * 可见试图
	 */
	private void setVistibility() {// 判断是否有数据
		if (BIntentObj.mBUserTasks.getWork_State() != null
				&& !BIntentObj.mBUserTasks.getWork_State().equals("")) {
			tv_state_overshenpi.setText(BIntentObj.mBUserTasks.getWork_State());

		}

		if (!isFrom()) {
			btn_meetingapply_check.setVisibility(View.GONE);// 当 从 已办 查看时 隐藏掉
															// 审批button

		} else {
			btn_meetingapply_check.setVisibility(View.VISIBLE); // 当 从 待办 查看时 显示
																// 审批button
			if (mBTaskInfo.isend) {
				btn_meetingapply_check.setText("归档");
			} else {
				btn_meetingapply_check.setText("审核");
			}
		}

		// if (null != mBTaskInfo.detail && !mBTaskInfo.detail.equals("")) {
		// lin_meetingcheck_detail_.setVisibility(View.VISIBLE);
		// }

		if (null != mBTaskInfo.TaskSzs && !mBTaskInfo.TaskSzs.equals("")) {
			lin_meetingcheck_task.setVisibility(View.VISIBLE);
		}

		lin_meeting_nameinfo.setVisibility(View.VISIBLE);

		if (null != mBTaskInfo.attachment && !mBTaskInfo.attachment.isEmpty()) {
			lin_meetingcheck_attachment.setVisibility(View.VISIBLE);
		}
		sc_view.smoothScrollTo(0, 0);

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

		btn_meetingapply_check.setOnClickListener(new OnClickListener() {// 提交按钮的监听

					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						// btn_meetingapply_check.setClickable(false);
						if (!mBTaskInfo.isend) { // 审批
							mmHandler.sendEmptyMessage(201);
						} else { // 审核
							ToastViewTools.initToast(MeetingApply.this, "归档");
							flowEnd();
						}

					}
				});
	}

	// 归档 方法调用
	public void flowEnd() {
		RequestParams params = new RequestParams(); // 参数

		params.addBodyParameter("loginName", BUserlogin.NO);
		params.addBodyParameter("workid", BIntentObj.mBUserTasks.getTaskId());
		params.addBodyParameter("flowno", BIntentObj.mBUserTasks.getFlowID());
		String url = AppConfig.getIp(MeetingApply.this)+AppConfig.METHOAD_FLOWEND_OA;
		LogUtils.e("url:" + url);
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						ToastViewTools.initToast(MeetingApply.this, "出错了....");
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						try {
							JSONObject mJsonObject = new JSONObject(
									responseInfo.result);
							String status = mJsonObject.getString("status");
							if (null != status && status.equals("200")) {
								BIntentObj.IsGetProcess = true; // 归档成功之后 将
																// 获取流程设置为false
																// 为了刷新代办页面
								BIntentObj.EndProcess = true;

								ToastViewTools.initToast(MeetingApply.this,
										"归档成功");
							} else {
								ToastViewTools.initToast(MeetingApply.this,
										"归档失败");
							}
							finish();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});

		// wangzhen
	}

	/**
	 * 增加POP的监听事件 1 同意 2退回
	 */
	final PopClickListening popClickListenershow = new PopClickListening() {
		@Override
		public void PopPositiveLister() {// 同意

			popWindowShow.popWindowdismiss();

		}

		@Override
		public void PopNegativeLister() {

			popWindowShow.popWindowdismiss();

		}

		// 审核的同意
		@Override
		public void EditPopPositiveLister(EditText et) {
			// TODO Auto-generated method stub
			BIntentObj.IsGetProcess = true; // 为了处理完成后刷新待办流程列表
			BIntentObj.IsGetOverProcess = true;

			popWindowShow.popWindowdismiss();
			System.out.println("===" + et.getText().toString().trim());
			// loginName, workID, FK_Flow, string FID, string fkNode, string
			// content, AuditType intAuditType, int nextnode, string toemps
			if (null != pop_leaderid && !pop_leaderid.equals("")) {

				if (!mBTaskInfo.isend) {
					getTaskAuditing(BUserlogin.NO,
							BIntentObj.mBUserTasks.getTaskId(),
							BIntentObj.mBUserTasks.getFlowID(),
							BIntentObj.mBUserTasks.getFID(),
							BIntentObj.mBUserTasks.getCurrNodeID(), et
									.getText().toString().trim(), "1",
							pop_leaderid, recepterName);
				}
			} else {

				ToastViewTools.initToast(MeetingApply.this, "请选择下一节点");
			}

		}

		// 审核的驳回
		@Override
		public void EditPopNegativeLister(EditText et) {
			// TODO Auto-generated method stub
			BIntentObj.IsGetProcess = false; // 为了处理完成后刷新待办流程列表
			BIntentObj.IsGetOverProcess = false;
			;

			popWindowShow.popWindowdismiss();
			if (null != pop_leaderid && !pop_leaderid.equals("")) {
				if (!mBTaskInfo.isend) {
					getTaskAuditing(BUserlogin.NO,
							BIntentObj.mBUserTasks.getTaskId(),
							BIntentObj.mBUserTasks.getFlowID(),
							BIntentObj.mBUserTasks.getFID(),
							BIntentObj.mBUserTasks.getCurrNodeID(), et
									.getText().toString().trim(), "2",
							pop_leaderid, recepterName);
				}
			} else {

				ToastViewTools.initToast(MeetingApply.this, "请选择下一节点");
			}
		}

		@Override
		public void setListViewForName(ListView lv, LinearLayout lin) {
			// TODO Auto-generated method stub
			try {
				if (mBTaskInfo.isline) {
					lin.setVisibility(View.GONE);
				} else {
					lin.setVisibility(View.INVISIBLE);
				}

				int k = mBTaskInfo.list_getnode.size();
				if (k > 1) {
					lin.setVisibility(View.VISIBLE);
					if (null == mAdapterPopSelectLeader) {
						mAdapterPopSelectLeader = new AdapterPopSelectLeader(
								mBTaskInfo.list_getnode, MeetingApply.this);
					}
					// wangzhen 2
					lv.setAdapter(mAdapterPopSelectLeader);
					lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							uPDate(arg2);
							pop_leaderid = mBTaskInfo.list_getnode.get(arg2)
									.getNodeid(); // 获取下一节点的 id
							recepterName = mBTaskInfo.list_getnode.get(arg2)
									.getReceNo(); // 接收人的账号

							mAdapterPopSelectLeader.notifyDataSetChanged();
						}

					});

				} else {
					if (k == 1) {
						uPDate(0);
						pop_leaderid = mBTaskInfo.list_getnode.get(0)
								.getNodeid(); // 获取下一节点的
												// id
						recepterName = mBTaskInfo.list_getnode.get(0)
								.getReceNo(); // 获取接收人的账号

					}
					lin.setVisibility(View.GONE);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	};

	/**
	 * 更新数据
	 */
	public void uPDate(int arg2) {
		int kj = mBTaskInfo.list_getnode.size();
		for (int i = 0; i < kj; i++) {
			if (i == arg2) {
				mBTaskInfo.list_getnode.get(i).setIdididi(arg2 + "");
			} else {
				mBTaskInfo.list_getnode.get(i).setIdididi("");
			}
		}
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		if (null == BIntentObj.mBUserTasks.getFlowID()
				|| null == BIntentObj.mBUserTasks.getTaskId()) {
			getWorkInfo_All("wangzy", "209", "067");
		} else {
			getWorkInfo_All(BUserlogin.NO, BIntentObj.mBUserTasks.getTaskId(),
					BIntentObj.mBUserTasks.getFlowID());
		}
		mPopLoginDialog = new PopLoginDialog();
		mmHandler.sendEmptyMessageDelayed(111, 150);
		if (popWindowShow == null) {
			popWindowShow = new PopWindowShow();
		}
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_meetingapply);
		ModifySysTitle.ModifySysTitleStyle(R.color.title_blue,
				MeetingApply.this);
		http = new HttpUtils();
		http.configTimeout(8000);// 最多等待6秒
		http.configCurrentHttpCacheExpiry(1500);
		mInflater = LayoutInflater.from(this);
		bitmapUtils = new BitmapUtils(getApplicationContext());
		bitmapUtils
				.configDefaultLoadFailedImage(R.drawable.detail_icon_user_default);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}

	// 判定是否是 从已办流程 跳转进来的
	public boolean isFrom() {
		Intent intent = getIntent();
		String str = intent.getStringExtra("From");
		if (null != str && !"".equals(str)) {
			tv_state_overshenpi.setText(SHENPI); // 显示 审批
			return false; // 返回false 说明是从 已办跳转的
		}
		return true; // 说明是从 代办跳转来的
	}

	/**
	 * 得到任务详情
	 */
	private void getWorkInfo_All(String loginName, String workID, String flowID) {
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("loginName", loginName);
		params.addBodyParameter("workID", workID);
		params.addBodyParameter("flowID", flowID);
		System.out.println("loginName:" + loginName);
		System.out.println("workID:" + workID);
		System.out.println("flowID:" + flowID);
		System.out.println(">>>getWorkInfo_All 参数<<<" + "loginName" + loginName
				+ "workID" + workID + "flowID" + flowID);
		String url = AppConfig.getIp(this)+AppConfig.METHOAD_GETTASKINFO_OA;
		LogUtils.e("得到任务详情url:" + url);
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						/*
						 * ToastViewTools.initToast(MeetingApply.this,
						 * responseInfo.result);
						 */
						ATaskInfo mATaskInfo = new ATaskInfo();

						// GetRawStringFile.rawRead(MeetingApply.this, R.raw.a2)
						mBTaskInfo = mATaskInfo.analysisTaskInfo(
								responseInfo.result, mmHandler);

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						System.out.println("error:" + error + msg);
						/*
						 * ATaskInfo mATaskInfo = new ATaskInfo(); //
						 * GetRawStringFile.rawRead(MeetingApply.this, R.raw.a2)
						 * mBTaskInfo = mATaskInfo.analysisTaskInfo(
						 * GetRawStringFile.rawRead(MeetingApply.this,
						 * R.raw.a3), mmHandler);
						 */

						closePop();// 关闭Dialog
						ToastViewTools.initToast(MeetingApply.this, "出错了....");
					}
				});

	}

	/**
	 * 审批接口 METHOAD_TASKAUDITING
	 */
	private void getTaskAuditing(String loginName, String workID,
			String FK_Flow, String FID, String fkNode, String content,
			String intAuditType, String nextnode, String toemps) {
		mPopLoginDialog.showPoploginDialog(MeetingApply.this);
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("loginName", loginName);
		params.addBodyParameter("workID", workID);
		params.addBodyParameter("FK_Flow", FK_Flow);
		params.addBodyParameter("FID", FID);
		params.addBodyParameter("fkNode", fkNode);
		params.addBodyParameter("content", content);
		params.addBodyParameter("intAuditType", intAuditType);
		params.addBodyParameter("nextnode", nextnode);
		params.addBodyParameter("toemps", toemps);
		System.out.println("loginName:" + loginName);
		System.out.println("workID:" + workID);
		System.out.println("FK_Flow:" + FK_Flow);
		System.out.println("fkNode:" + fkNode);
		System.out.println("content:" + content);
		System.out.println("intAuditType:" + intAuditType);
		System.out.println("nextnode:" + nextnode);
		System.out.println("toemps:" + toemps);
		String url =  AppConfig.getIp(this)+AppConfig.METHOAD_TASKAUDITING_OA;
		LogUtils.e("审批接口METHOAD_TASKAUDITING:" + url);
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						closePop();
						btn_meetingapply_check.setClickable(true);
						try {
							JSONObject mJsonObject = new JSONObject(
									responseInfo.result);
							if (mJsonObject.getString("Status").equals("200")) {
								ToastViewTools.initToast(MeetingApply.this,
										mJsonObject.getString("Message"));
							} else if (mJsonObject.getString("Status").equals(
									"400")) {
								ToastViewTools.initToast(MeetingApply.this,
										mJsonObject.getString("Message"));
								closePop();
							} else {
								ToastViewTools.initToast(MeetingApply.this,
										mJsonObject.getString("Message"));
							}
							MeetingApply.this.finish();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						System.out.println("error:" + error + msg);
						closePop();

						ToastViewTools.initToast(MeetingApply.this, "出错了....");

						btn_meetingapply_check.setClickable(true);
					}
				});

	}

	/**
	 * 获取数据刷新页面
	 */
	Handler mmHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 111:
				mPopLoginDialog.dismisPop();
				mPopLoginDialog.showPoploginDialog(MeetingApply.this);
				break;
			case ShowRemind.HANDLER_STATUS:// 错误信息
				ToastViewTools.initToast(MeetingApply.this,
						ShowRemind.ErrorMessage);
				closePop();
				break;

			case ShowRemind.HANDLER_SUCCESS:// 获取信息成功
				mTask = new MyTask();
				mTask.execute();
				break;
			case 201:// 获取人员信息成功
				if (mBTaskInfo.isselect) {
					ToastViewTools.initToast(MeetingApply.this, "流程错误");
				} else {
					popWindowShow.showPopupWindowApply(MeetingApply.this,
							popClickListenershow);
				}

				closePop();
				break;

			case ShowRemind.HANDLER_NET_ERROE:// 网络超时
				ToastViewTools.initToast(MeetingApply.this,
						ShowRemind.NET_ERROE);
				break;
			case 10000:// 下载文件
				closePop();// 关闭Dialog
				ToastViewTools.initToast(MeetingApply.this, "下载完成");
				openFile(filePath, file_posi);

				break;
			case 10001:// 下载文件
				closePop();// 关闭Dialog
				ToastViewTools.initToast(MeetingApply.this, "下载失败");
				break;
			case 10003:// 下载文件
				setVistibility();
				closePop();// 关闭Dialog
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void openFile(String mpath, int i) {
		try {

			Uri path = Uri.fromFile(new File(filePath));
			Intent intent = new Intent("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			if (mBTaskInfo.attachment.get(i).get("FileExcute")// doc
					.equals("docx")
					|| mBTaskInfo.attachment.get(i).get("FileExcute")
							.equals("doc")) {
				intent.setDataAndType(path, "application/msword");
			} else if (mBTaskInfo.attachment.get(i).get("FileExcute")
					.equals("xls")
					|| mBTaskInfo.attachment.get(i).get("FileExcute")
							.equals("xlsx")) { // excel
				intent.setDataAndType(path, "application/vnd.ms-excel");
			} else if (mBTaskInfo.attachment.get(i).get("FileExcute")
					.equals("ppt")
					|| mBTaskInfo.attachment.get(i).get("FileExcute")
							.equals("pptx")) { // ppt
				intent.setDataAndType(path, "application/vnd.ms-powerpoint");
			} else if (mBTaskInfo.attachment.get(i).get("FileExcute")
					.equals("pdf")) {
				intent.setDataAndType(path, "application/pdf");

			} else {
				ToastViewTools.initToast(MeetingApply.this, "暂不支持此类型文件");
			}

			MeetingApply.this.startActivity(intent);
		} catch (Exception e) {
			// TODO: handle exception
			ToastViewTools.initToast(MeetingApply.this, "打开失败或未安装可打开程序");
		}
	}

	private class MyTask extends AsyncTask<String, Integer, String> {
		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			// mBTaskInfo.isselect wangzhen

			// 开始绘图
			initContentView();
			initlinDetailView();
			initLinAtttachment();
			initLinTask();
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected String doInBackground(String... params) {

			return null;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Integer... progresses) {
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(String result) {

			// 视图可见

			// closePop();// 关闭Dialog
			mmHandler.sendEmptyMessageDelayed(10003, 100);
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}

	/**
	 * 增加Content视图
	 */
	private void initContentView() {// 判断换行ColumnType
		LinearLayout lin_meetingcheck_content = (LinearLayout) findViewById(R.id.lin_meetingcheck_content);

		// 长度
		int lenth = mBTaskInfo.content.size();

		for (int i = 0; i < lenth; i++) {// 循环加载视图
			View mContentView = mInflater.inflate(
					R.layout.item_meetingapply_content, null);

			HashMap<String, Object> iter = mBTaskInfo.content.get(i);

			TextView tv_name = (TextView) mContentView
					.findViewById(R.id.tv_item_meetingapply_content_key);
			tv_name.setText(iter.get("ColumnName").toString().trim());
			TextView tv_value = (TextView) mContentView
					.findViewById(R.id.tv_item_meetingapply_content_value);
			tv_value.setText(iter.get("ColumnValue").toString().trim());

			if ((Boolean) iter.get("ColumnType")) {

				View mContentView_ver = mInflater.inflate(
						R.layout.item_meetingapply_content_ver, null);

				TextView tv_name1 = (TextView) mContentView_ver
						.findViewById(R.id.tv_item_meetingapply_content_key);
				tv_name1.setText(iter.get("ColumnName").toString().trim());
				TextView tv_value1 = (TextView) mContentView_ver
						.findViewById(R.id.tv_item_meetingapply_content_value);
				tv_value1.setText(iter.get("ColumnValue").toString().trim());
				lin_meetingcheck_content.addView(mContentView_ver);
			} else {
				lin_meetingcheck_content.addView(mContentView);
			}

		}

	}

	/**
	 * 附表信息
	 */
	@SuppressLint("ResourceAsColor")
	private void initlinDetailView() {
		wide_List = new ArrayList<Integer>();// 宽度
		LinearLayout lin_meetingcheck_detail = (LinearLayout) findViewById(R.id.lin_meetingcheck_detail);

		// 绘制表格
		int lenth = mBTaskInfo.detail.size();
		for (int i = 0; i < lenth; i++) {
			System.out.println("绘制表的个数:" + lenth);

			int title_col = mBTaskInfo.detail.get(i).columns.size();
			if (title_col > 0) {
				lin_meetingcheck_detail_.setVisibility(View.VISIBLE);
			}
			LinearLayout lin_hor = null;
			for (int ii = 0; ii < title_col; ii++) {// 为了绘制出一个title
				int title_lenth = mBTaskInfo.detail.get(i).columns.get(ii).length;
				// System.out.println("title_lenth:" + title_lenth);
				lin_hor = new LinearLayout(MeetingApply.this);

				for (int j = 0; j < title_lenth; j++) {

					// System.out.println("j:" + j);
					lin_hor.setOrientation(LinearLayout.HORIZONTAL);

					lin_hor.setLayoutParams(new LayoutParams(// 宽，高
							LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

					// 增加标题
					if (detile_title) {// 是否加载头
						LinearLayout lin_tit = new LinearLayout(
								MeetingApply.this);
						lin_tit.setOrientation(LinearLayout.HORIZONTAL);
						lin_tit.setLayoutParams(new LayoutParams(
								LayoutParams.FILL_PARENT,
								LayoutParams.WRAP_CONTENT));

						lin_tit.setBackgroundColor(Color.rgb(3, 160, 255));
						// lin_tit.setPadding(10, 5, 0, 5);
						int detail_lenth = mBTaskInfo.detail.get(i).title.length;
						for (int k = 0; k < detail_lenth; k++) {
							TextView tv_t = new TextView(MeetingApply.this);
							String rowstr = mBTaskInfo.detail.get(i).title[k]
									.trim();
							tv_t.setTextSize(15);
							tv_t.setText(rowstr);
							// tv_t.setPadding(0, 10, 5, 10);
							tv_t.setSingleLine();
							tv_t.setTextColor(Color.rgb(255, 255, 255));
							int textSize = (int) tv_t.getPaint().getTextSize()
									* ((rowstr.length() + 1)); // 获得当前textView的text
							tv_t.setLayoutParams(new LayoutParams(textSize,
									LinearLayout.LayoutParams.WRAP_CONTENT));
							lin_tit.addView(tv_t);
							wide_List.add(textSize);
							System.out.println("wide_List" + wide_List.get(k));
							detile_title = false;
						}
						lin_meetingcheck_detail.addView(lin_tit);
					} else {

					}
					TextView tv = new TextView(MeetingApply.this);
					// System.out.println("j:" + j
					// + mBTaskInfo.detail.get(i).title[j]);
					tv.setLayoutParams(new LayoutParams((Integer) wide_List
							.get(j), LinearLayout.LayoutParams.WRAP_CONTENT));
					// tv.setPadding(10, 5, 10, 5);
					tv.setText(mBTaskInfo.detail.get(i).columns.get(i)[j]);
					// tv.setLayoutParams( new LayoutParams(0,0
					// LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
					lin_hor.addView(tv);

					// hsv.addView(lin_hor);
				}

				lin_meetingcheck_detail.addView(lin_hor);

			}

		}

	}

	/**
	 * 附件
	 */
	private void initLinAtttachment() {
		LinearLayout lin_meetingcheck_attachment = (LinearLayout) findViewById(R.id.lin_meetingcheck_attachment);
		ListView lv_meetingapply_task_attachment = (ListView) findViewById(R.id.lv_meetingapply_task_attachment);
		AdapterMeetingApply mmAdapterMeetingApply = new AdapterMeetingApply(
				MeetingApply.this, mBTaskInfo.attachment, 1);
		lv_meetingapply_task_attachment.setAdapter(mmAdapterMeetingApply);

		lin_meetingcheck_attachment.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, dip2px(MeetingApply.this, 44)
						+ mmAdapterMeetingApply.getCount()
						* dip2px(MeetingApply.this, 70)));

	}

	/**
	 * 任务
	 */
	private void initLinTask() {
		int lv_hight = 0;
		ListView lv_meetingapply_task_listview = (ListView) findViewById(R.id.lv_meetingapply_task_listview);
		AdapterMeetingApply mAdapterMeetingApply = new AdapterMeetingApply(
				MeetingApply.this, mBTaskInfo.TaskSzs, 3);

		for (int i = 0, len = mAdapterMeetingApply.getCount(); i < len; i++) {
			View listItem = mAdapterMeetingApply.getView(i, null,
					lv_meetingapply_task_listview);
			listItem.measure(0, 0); // 计算子项View 的宽高
			int list_child_item_height = listItem.getMeasuredHeight()
					+ lv_meetingapply_task_listview.getDividerHeight();
			lv_hight += list_child_item_height; // 统计所有子项的总高度
		}

		lv_meetingapply_task_listview.setAdapter(mAdapterMeetingApply);
		/*
		 * lin_meetingcheck_task.setLayoutParams(new LayoutParams(
		 * LayoutParams.MATCH_PARENT, dip2px(MeetingApply.this, 50) +
		 * mAdapterMeetingApply.getCount() dip2px(MeetingApply.this, 122)));
		 */
		lin_meetingcheck_task.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, lv_hight
						+ dip2px(MeetingApply.this, 65) + 15));
		// 长度
		// int lenth = mBTaskInfo.content.size();
		if (mAdapterMeetingApply.getCount() < 1) {
			lin_meetingcheck_task.setVisibility(View.GONE);
		}
	}

	/**
	 * @author chang-zhiyuan 会议申请ListView适配器
	 */
	public class AdapterMeetingApply extends BaseAdapter {

		private LayoutInflater layoutInflater;
		private ArrayList<HashMap<String, Object>> TaskSzs;
		private int type;

		public AdapterMeetingApply() {

		}

		/**
		 * @param mContext
		 * @param mTaskSzs
		 * @param mtype
		 *            3加载审批 1 加载文件
		 */
		public AdapterMeetingApply(Context mContext,
				ArrayList<HashMap<String, Object>> mTaskSzs, int mtype) {
			this.layoutInflater = LayoutInflater.from(mContext);
			TaskSzs = mTaskSzs;
			type = mtype;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (type == 3) {
				return TaskSzs.size();
			} else {
				return mBTaskInfo.attachment.size();
			}

		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			if (type == 3) {
				return TaskSzs.get(arg0);
			} else {
				return mBTaskInfo.attachment.get(arg0);
			}
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolderSP viewHolder = null;
			ViewHolderattATT viewHolderatt = null;
			// if(type == 3){
			if (view == null) {
				if (type == 3) {
					view = layoutInflater.inflate(
							R.layout.item_meetingstate_listview, null);
					// hight = view.getHeight();
					viewHolder = new ViewHolderSP();
					viewHolder.tv_meetingcheck_item_name = (TextView) view
							.findViewById(R.id.tv_meetingcheck_item_name);

					viewHolder.tv_meetingcheck_item_job = (TextView) view
							.findViewById(R.id.tv_meetingcheck_item_job);
					viewHolder.tv_meetingcheck_item_time = (TextView) view
							.findViewById(R.id.tv_meetingcheck_item_time);

					viewHolder.tv_meetingcheck_item_content = (TextView) view
							.findViewById(R.id.tv_meetingcheck_item_content);

					viewHolder.iv_item_meetingapply_pic = (CircleImageView) view
							.findViewById(R.id.iv_item_meetingapply_pic);
					viewHolder.tv_meetingcheck_item_state = (TextView) view
							.findViewById(R.id.tv_meetingcheck_item_state);
					viewHolder.tv_usertime = (TextView) view
							.findViewById(R.id.tv_usertime);
					view.setTag(viewHolder);
				} else {
					view = layoutInflater.inflate(R.layout.item_meeting_file,
							null);
					// hight = view.getHeight();
					viewHolderatt = new ViewHolderattATT();
					viewHolderatt.tv_filesize = (TextView) view
							.findViewById(R.id.tv_filesize);

					viewHolderatt.tv_filename = (TextView) view
							.findViewById(R.id.tv_filename);

					viewHolderatt.iv_item_meeting_file = (ImageView) view
							.findViewById(R.id.iv_item_meeting_file);
					viewHolderatt.iv_download = (ImageView) view
							.findViewById(R.id.iv_download);
					view.setTag(viewHolderatt);
				}
			} else {
				if (type == 3) {
					viewHolder = (ViewHolderSP) view.getTag();
				} else {
					viewHolderatt = (ViewHolderattATT) view.getTag();
				}

			} // NodeCheckoer
			if (type == 3) {// 审批

				viewHolder.tv_meetingcheck_item_name.setText(TaskSzs
						.get(position).get("NodeCheckoer").toString());
				viewHolder.tv_meetingcheck_item_content.setText(TaskSzs
						.get(position).get("Msg").toString().trim());
				viewHolder.tv_meetingcheck_item_job.setText(TaskSzs
						.get(position).get("NodeName").toString());
				viewHolder.tv_meetingcheck_item_time.setText(TaskSzs
						.get(position).get("NodeCheckDatetime").toString());

				viewHolder.tv_usertime.setText("用时  "
						+ TaskSzs.get(position).get("UseTime").toString());
				if (TaskSzs.get(position).get("NodeCheckoerPhotoUrl")
						.equals("")) {

				} else {
					bitmapUtils.display(viewHolder.iv_item_meetingapply_pic,
							TaskSzs.get(position).get("NodeCheckoerPhotoUrl")
									.toString(), config);
				}

				if (TaskSzs.get(position).get("NodeState").equals("发起")) {// 判断状态
					viewHolder.tv_meetingcheck_item_state.setText(TaskSzs
							.get(position).get("NodeState").toString());
					viewHolder.tv_meetingcheck_item_state
							.setBackgroundColor(Color.rgb(4, 163, 255));
				} else if (TaskSzs.get(position).get("NodeState").equals("退回")) {// qianjin
					viewHolder.tv_meetingcheck_item_state.setText(TaskSzs
							.get(position).get("NodeState").toString());
					viewHolder.tv_meetingcheck_item_state
							.setBackgroundColor(Color.rgb(224, 72, 61));
				} else {// qianjin
					viewHolder.tv_meetingcheck_item_state.setText(TaskSzs
							.get(position).get("NodeState").toString());
					viewHolder.tv_meetingcheck_item_state
							.setBackgroundColor(Color.rgb(34, 191, 100));
				}
			} else {// attachment

				viewHolderatt.tv_filesize.setText((mBTaskInfo.attachment.get(
						position).get("FileSize").toString()).trim());

				if (mBTaskInfo.attachment.get(position).get("FileExcute")
						.equals("docx")
						|| mBTaskInfo.attachment.get(position)
								.get("FileExcute").equals("doc")) {

					viewHolderatt.iv_item_meeting_file
							.setImageDrawable(getResources().getDrawable(
									R.drawable.attached_icon_word));
				} else if (mBTaskInfo.attachment.get(position)
						.get("FileExcute").equals("xls")
						|| mBTaskInfo.attachment.get(position)
								.get("FileExcute").equals("xlsx")) {
					viewHolderatt.iv_item_meeting_file
							.setImageDrawable(getResources().getDrawable(
									R.drawable.attached_icon_excel));
				} else if (mBTaskInfo.attachment.get(position)
						.get("FileExcute").equals("ppt")
						|| mBTaskInfo.attachment.get(position)
								.get("FileExcute").equals("ppts")) {
					viewHolderatt.iv_item_meeting_file
							.setImageDrawable(getResources().getDrawable(
									R.drawable.attached_icon_ppt));
				} else if (mBTaskInfo.attachment.get(position)
						.get("FileExcute").equals("pdf")) {
					viewHolderatt.iv_item_meeting_file
							.setImageDrawable(getResources().getDrawable(
									R.drawable.attached_icon_pdf));
				}

				viewHolderatt.tv_filename.setText(mBTaskInfo.attachment.get(
						position).get("FileName")
						+ "");
				viewHolderatt.iv_download.setTag(position);

				viewHolderatt.iv_download
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View view) {
								// TODO Auto-generated method stub
								DownLoadFileTools mDownLoadFileTools = new DownLoadFileTools();
								try {
									file_posi = (Integer) view.getTag();
									filePath = SDCardTools.getFilePath()
											.toString()
											+ "/"
											+ mBTaskInfo.attachment.get(
													(Integer) view.getTag())
													.get("FileName");
									// 等待
									mPopLoginDialog
											.showPoploginDialog(MeetingApply.this);
									if (new File(filePath).exists()) {
										openFile(filePath,
												(Integer) view.getTag());
										closePop();
										openFile(filePath, file_posi);

									} else {// 如果不存在
										mDownLoadFileTools
												.downFile(
														http,
														mBTaskInfo.attachment
																.get((Integer) view
																		.getTag())
																.get("FileUrl")
																.toString(),
														SDCardTools
																.getFilePath()
																.toString()
																+ "/"
																+ mBTaskInfo.attachment
																		.get((Integer) view
																				.getTag())
																		.get("FileName"),
														mmHandler);
									}

								} catch (Exception e) {
									// TODO Auto-generated catch block
									// e.printStackTrace();
									closePop();
									ToastViewTools.initToast(MeetingApply.this,
											"文件打开失败");
								}

							}
						});
			}

			return view;
		}

		/**
		 * @author chang-zhiyuan 状态
		 */
		class ViewHolderSP {
			public TextView tv_meetingcheck_item_name;
			public TextView tv_meetingcheck_item_job;
			public TextView tv_meetingcheck_item_content;
			public TextView tv_meetingcheck_item_time;
			public CircleImageView iv_item_meetingapply_pic;
			public TextView tv_meetingcheck_item_state;
			public TextView tv_usertime;
		}

		/**
		 * @author chang-zhiyuan 文件
		 */
		class ViewHolderattATT {
			public TextView tv_filesize;
			public TextView tv_filename;
			public ImageView iv_item_meeting_file;
			public ImageView iv_download;

		}

	}// class end

	/**
	 * 像素转换
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (popWindowShow != null) {
				if (popWindowShow.isShowPopWindowd()) {
					popWindowShow.popWindowdismiss();
					// btn_meetingapply_check.setClickable(true);
				} else {
					MeetingApply.this.finish();
					// btn_meetingapply_check.setClickable(true);
				}
			} else {
				MeetingApply.this.finish();
			}

			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		popWindowShow = null;
		bitmapUtils = null;
	}

}
