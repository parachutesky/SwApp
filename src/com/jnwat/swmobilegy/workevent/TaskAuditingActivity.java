package com.jnwat.swmobilegy.workevent;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BMeetingApplyLeade;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterAuditingDept;
import com.jnwat.swmobilegy.dapter.AdapterAuditingName;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 审核页面
 */
public class TaskAuditingActivity extends BaseActivity {
	private ListView lv_auditing_name, lv_auditing_dept;
	AdapterAuditingName adapterAuditingName;
	AdapterAuditingDept adapterAuditingDept;
	EditText et_pop__message;
	int ord;
	int isagree;
	// 赋值
	// BIntentObj.list_getnode
	private List<BMeetingApplyLeade> mmlist_getnode_child;
	AdapterGridAuditing mAdapterGridAuditing;
	Button pop_positiveButton;
	/**
	 * 选中的临时数据
	 */
	private List<BMeetingApplyLeade> temp_name;
	private GridView gd_taskaudting;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		adapterAuditingDept = new AdapterAuditingDept(
				TaskAuditingActivity.this, false, isagree);
		adapterAuditingDept.selecItem(0);
		lv_auditing_dept.setAdapter(adapterAuditingDept);
		mmHandler.sendEmptyMessage(10);

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		lv_auditing_dept.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view,
					int i, long l) {
				// TODO Auto-generated method stub
				BIntentObj.index = i;
				adapterAuditingDept.selecItem(i);
				adapterAuditingDept.notifyDataSetChanged();

				if (isagree == 1) {
					if (i != ord) {
						String str = BIntentObj.list_getnode.get(ord)
								.getNodeid();
						int length = BIntentObj.list_getnode_child.get(str)
								.size();
						for (int ii = 0; ii < length; ii++) {
							BIntentObj.list_getnode_child.get(str).get(ii)
									.setIdididi("");

						}
					}
				} else {
					if (i != ord) {
						String str = BIntentObj.list_getreturnnode.get(ord)
								.getNodeid();
						int length = BIntentObj.list_getreturnnode_child.get(
								str).size();
						for (int ii = 0; ii < length; ii++) {
							BIntentObj.list_getreturnnode_child.get(str)
									.get(ii).setIdididi("");

						}
					}
				}

				if (null != temp_name) {
					temp_name.clear();
				}
				ord = i;
				mmHandler.sendEmptyMessage(10);

			}
		});

		lv_auditing_name.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterview, View view,
					int i, long l) {
				// TODO Auto-generated method stub
				// 设置点击事件
				getSel(BIntentObj.index, i, BIntentObj.ismul);
				adapterAuditingName.notifyDataSetChanged();
				if (null == mAdapterGridAuditing) {
					mAdapterGridAuditing = new AdapterGridAuditing();
					gd_taskaudting.setAdapter(mAdapterGridAuditing);
				}

				mAdapterGridAuditing.notifyDataSetChanged();
			}
		});
		pop_positiveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				if (isagree == 1) { // 等于1 同意
					if (BIntentObj.issub) {// 子线程
						// 提交网络
						// getTaskAuditing

						mPopLoginDialog
								.showPoploginDialog(TaskAuditingActivity.this);
						BIntentObj.IsDoProcess = true; // 为了处理完成后刷新待办流程列表
						BIntentObj.IsHadDidProcess = true;
						getTaskAuditing(BUserlogin.NO,
								BIntentObj.mBUserTasks.getTaskId(),
								BIntentObj.mBUserTasks.getFlowID(),
								BIntentObj.mBUserTasks.getFID(),
								BIntentObj.mBUserTasks.getCurrNodeID(),
								et_pop__message.getText().toString().trim(),
								isagree + "", BIntentObj.issub_str_ID,
								BIntentObj.issub_str);

					} else {

						String str = getName();
						if (str.equals("")) {
							ToastViewTools.initToast(TaskAuditingActivity.this,
									"请选择下一节点");
							System.out.println("getName:" + getName());
						} else {
							// 提交网络
							// getTaskAuditing
							mPopLoginDialog
									.showPoploginDialog(TaskAuditingActivity.this);
							BIntentObj.IsDoProcess = true; // 为了处理完成后刷新待办流程列表
							BIntentObj.IsHadDidProcess = true;
							getTaskAuditing(
									BUserlogin.NO,
									BIntentObj.mBUserTasks.getTaskId(),
									BIntentObj.mBUserTasks.getFlowID(),
									BIntentObj.mBUserTasks.getFID(),
									BIntentObj.mBUserTasks.getCurrNodeID(),
									et_pop__message.getText().toString().trim(),
									isagree + "", temp_name.get(0).getNodeid(),
									str);
						}
					}

				} else {
					String str = getName();
					if (str.equals("")) {
						ToastViewTools.initToast(TaskAuditingActivity.this,
								"请选择下一节点");
						System.out.println("getName:" + getName());
					} else {
						// 提交网络
						// getTaskAuditing
						mPopLoginDialog
								.showPoploginDialog(TaskAuditingActivity.this);
						BIntentObj.IsDoProcess = true; // 为了处理完成后刷新待办流程列表
						BIntentObj.IsHadDidProcess = true;
						getTaskAuditing(BUserlogin.NO,
								BIntentObj.mBUserTasks.getTaskId(),
								BIntentObj.mBUserTasks.getFlowID(),
								BIntentObj.mBUserTasks.getFID(),
								BIntentObj.mBUserTasks.getCurrNodeID(),
								et_pop__message.getText().toString().trim(),
								isagree + "", temp_name.get(0).getNodeid(), str);
					}

				}

			}
		});
	}

	/**
	 * 选择
	 * 
	 * @param mountSel
	 *            是否多选
	 */
	private void getSel(int parent, int click, boolean mountSel) {
		System.out.println("parent.." + parent + "click.." + click);

		if (isagree == 1) {
			String str = BIntentObj.list_getnode.get(parent).getNodeid();
			int length = BIntentObj.list_getnode_child.get(str).size();
			System.out.println("是否为多选:" + mountSel);

			// fasle 单选
			if (!mountSel) {
				for (int ii = 0; ii < length; ii++) {// 如果为多选
					BIntentObj.list_getnode_child.get(str).get(ii)
							.setIdididi("");
				}
				temp_name.clear();
				BIntentObj.list_getnode_child.get(str).get(click)
						.setIdididi("click");

				if (!temp_name.contains(BIntentObj.list_getnode_child.get(str)
						.get(click))) {
					temp_name.add(BIntentObj.list_getnode_child.get(str).get(
							click));
				}

			} else {

				if (BIntentObj.list_getnode_child.get(str).get(click)
						.getIdididi().equals("")) {// 没有被点击过
					BIntentObj.list_getnode_child.get(str).get(click)
							.setIdididi("click");
					temp_name.add(BIntentObj.list_getnode_child.get(str).get(
							click));
				} else {
					BIntentObj.list_getnode_child.get(str).get(click)
							.setIdididi("");
					temp_name.remove(BIntentObj.list_getnode_child.get(str)
							.get(click));
				}

			}
		} else {
			String str = BIntentObj.list_getreturnnode.get(parent).getNodeid();
			int length = BIntentObj.list_getreturnnode_child.get(str).size();
			if (!mountSel) {
				for (int ii = 0; ii < length; ii++) {// 如果为单选
					BIntentObj.list_getreturnnode_child.get(str).get(ii)
							.setIdididi("");
				}
				temp_name.clear();
				BIntentObj.list_getreturnnode_child.get(str).get(click)
						.setIdididi("click");

				if (!temp_name.contains(BIntentObj.list_getreturnnode_child
						.get(str).get(click))) {
					temp_name.add(BIntentObj.list_getreturnnode_child.get(str)
							.get(click));
				}

			} else {
				if (BIntentObj.list_getreturnnode_child.get(str).get(click)
						.getIdididi().equals("")) {
					BIntentObj.list_getreturnnode_child.get(str).get(click)
							.setIdididi("click");
					temp_name.add(BIntentObj.list_getreturnnode_child.get(str)
							.get(click));
				} else {
					BIntentObj.list_getreturnnode_child.get(str).get(click)
							.setIdididi("");
					temp_name.remove(BIntentObj.list_getreturnnode_child.get(
							str).get(click));
				}

			}

		}

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		lv_auditing_dept.setSelector(R.color.white);

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_taskauditing);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				TaskAuditingActivity.this);
		setBackListener("提交审核信息", false);
		lv_auditing_name = (ListView) findViewById(R.id.lv_auditing_name);

		lv_auditing_dept = (ListView) findViewById(R.id.lv_auditing_dept);
		pop_positiveButton = (Button) findViewById(R.id.pop_positiveButton);
		gd_taskaudting = (GridView) findViewById(R.id.gd_taskaudting);
		et_pop__message = (EditText) findViewById(R.id.et_pop__message);
		mmlist_getnode_child = new ArrayList<BMeetingApplyLeade>();
		temp_name = new ArrayList<BMeetingApplyLeade>();

		Intent intent = getIntent();
		isagree = intent.getIntExtra("isagree", 3);
		if (isagree == 1) { // 等于1

		} else {
			pop_positiveButton.setText("驳 回");
			pop_positiveButton.setBackgroundResource(R.color.redbohui);
		}
		http = new HttpUtils();
		http.configTimeout(8000);// 最多等待6秒
		http.configCurrentHttpCacheExpiry(500);
		mPopLoginDialog = new PopLoginDialog();

	}

	/**
	 * 得到提交人员信息
	 * 
	 * @return
	 */
	private String getName() {
		int lenth = temp_name.size();
		String str = "";
		if (lenth > 1) {
			for (int i = 0; i < lenth - 1; i++) {
				str = temp_name.get(i).getReceNo() + "," + str;

			}
		} else if (lenth > 0) {
			str = temp_name.get(lenth - 1).getReceNo();
		}

		return str;
	}

	/**
	 * 审批接口 METHOAD_TASKAUDITING
	 * 
	 * @param loginName
	 * @param workID
	 * @param FK_Flow
	 * @param FID
	 * @param fkNode
	 * @param content
	 * @param intAuditType
	 * @param nextnode
	 * @param toemps
	 */
	private void getTaskAuditing(String loginName, String workID,
			String FK_Flow, String FID, String fkNode, String content,
			String intAuditType, String nextnode, String toemps) {
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
		String url = AppConfig.getIp(TaskAuditingActivity.this)
				+ AppConfig.METHOAD_TASKAUDITING_OA;
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
						try {
							JSONObject mJsonObject = new JSONObject(
									responseInfo.result);
							if (mJsonObject.getString("Status").equals("200")) {
								ToastViewTools.initToast(
										TaskAuditingActivity.this,
										mJsonObject.getString("Message"));
								BIntentObj.END_MEETINGAPPLY = true;
								TaskAuditingActivity.this.finish();
							} else if (mJsonObject.getString("Status").equals(
									"400")) {
								ToastViewTools.initToast(
										TaskAuditingActivity.this,
										mJsonObject.getString("Message"));
								closePop();
							} else {
								ToastViewTools.initToast(
										TaskAuditingActivity.this,
										mJsonObject.getString("Message"));
							}
							BIntentObj.END_MEETINGAPPLY = true;
							TaskAuditingActivity.this.finish();
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
					}
				});

	}

	Handler mmHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 10:
				try {
					if (isagree == 1) {
						mmlist_getnode_child = BIntentObj.list_getnode_child
								.get(BIntentObj.list_getnode.get(
										BIntentObj.index).getNodeid());
						adapterAuditingName = new AdapterAuditingName(
								TaskAuditingActivity.this, true,
								BIntentObj.index, mmlist_getnode_child);
						lv_auditing_name.setAdapter(adapterAuditingName);

					} else {
						mmlist_getnode_child = BIntentObj.list_getreturnnode_child
								.get(BIntentObj.list_getreturnnode.get(
										BIntentObj.index).getNodeid());
						adapterAuditingName = new AdapterAuditingName(
								TaskAuditingActivity.this, true,
								BIntentObj.index, mmlist_getnode_child);
						lv_auditing_name.setAdapter(adapterAuditingName);

					}

				} catch (Exception e) {
					// TODO: handle exception
				}
				break;

			case 100:
				mmlist_getnode_child = BIntentObj.list_getnode_child
						.get(BIntentObj.list_getnode.get(BIntentObj.index)
								.getNodeid());
				adapterAuditingName.notifyDataSetChanged();
				break;

			}
			super.handleMessage(msg);
		}
	};

	public class AdapterGridAuditing extends BaseAdapter {

		LayoutInflater mInflater;

		public AdapterGridAuditing() {
			mInflater = LayoutInflater.from(TaskAuditingActivity.this);

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return temp_name.size();
		}

		@Override
		public Object getItem(int i) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int i) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolderz viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.item_grid_name, null,
						false);
				viewHolder = new ViewHolderz();
				viewHolder.textView_gridview_name = (TextView) convertView
						.findViewById(R.id.textView_gridview_name);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolderz) convertView.getTag();
			}
			viewHolder.textView_gridview_name.setText(temp_name.get(position)
					.getRecname());
			return convertView;
		}

		/**
		 * ViewHolder
		 * 
		 * @author mrsimple
		 */
		class ViewHolderz {
			TextView textView_gridview_name;
		}

	}

}
