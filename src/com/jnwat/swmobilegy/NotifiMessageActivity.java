package com.jnwat.swmobilegy;

import java.util.List;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BPushMessageInfo;
import com.jnwat.bean.BUserTasks;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.swmobilegy.dapter.AdapterNotifiMessage;
import com.jnwat.swmobilegy.mainpager.MainPageFragment;
import com.jnwat.swmobilegy.news.NewsDetailActivity;
import com.jnwat.swmobilegy.workevent.MeetingApply;
import com.jnwat.tools.ModifySysTitle;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * @author chang-zhiyuan
 * 
 */
public class NotifiMessageActivity extends BaseActivity {
	private PullToRefreshListView lv_notifi_message;
	private AdapterNotifiMessage mAdapter;
	private DbUtils db;
	private Intent intent;
	private int type;
	private List<BPushMessageInfo> list_PushMessage;
	private boolean isFresh;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		lv_notifi_message = (PullToRefreshListView) findViewById(R.id.lv_notifi_message);

		// intent.putExtra("selectObj", false);//等于fasle查看推送的列表
		boolean selectObj = intent.getBooleanExtra("selectObj", true);
		if (selectObj) {// true 根据类型判断
			type = intent.getIntExtra("type", 1);
			try {
				list_PushMessage = db.findAll(Selector
						.from(BPushMessageInfo.class)
						.where("MsgType", "=", type)
						.and("LoginName", "=", BUserlogin.NO).orderBy("ID"));
				mAdapter = new AdapterNotifiMessage(NotifiMessageActivity.this,
						list_PushMessage);
				lv_notifi_message.setAdapter(mAdapter);
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			list_PushMessage = BIntentObj.list_PushMessage;
			mAdapter = new AdapterNotifiMessage(NotifiMessageActivity.this,
					list_PushMessage);
			lv_notifi_message.setAdapter(mAdapter);
		}
		// 设置消息已读
		MainPageFragment mMainPageFragment = MainPageFragment.newInstance("");
		BIntentObj.IsGetNewPushMessage = false;
		mMainPageFragment.setMessageStage();

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		lv_notifi_message.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				// TODO Auto-generated method stub

				int position = index - 1;
				BPushMessageInfo mBPushMessageInfo = list_PushMessage
						.get(position);
				mBPushMessageInfo.setIsRead(1);
				readMessqage(mBPushMessageInfo.getIDid());
				// 标记消息为已读
				setMessageIsRead(mBPushMessageInfo);
				if (list_PushMessage.get(position).getMsgType() == 1) {// 流程审批
					Intent intent1 = new Intent();
					if (list_PushMessage.get(position).getIsDaiban()
							.equals("true")) {// 传递 参数是false
					} else {
						intent1.putExtra("From", true + "");// 是不是推送过来的
					}

					BUserTasks mBUserTasks = new BUserTasks();
					mBUserTasks.setFlowID(list_PushMessage.get(position)
							.getFlowID());
					mBUserTasks.setTaskId(list_PushMessage.get(position)
							.getWorkID());
					mBUserTasks.setFID(list_PushMessage.get(position).getFid());
					mBUserTasks.setIsDaiBan(list_PushMessage.get(position)
							.getIsDaiban());
					BIntentObj.mBUserTasks = mBUserTasks;
					intent1.setClass(NotifiMessageActivity.this,
							MeetingApply.class);
					startActivity(intent1);

				}

				if (list_PushMessage.get(position).getMsgType() == 2) {// 新闻
					Intent intent2 = new Intent();
					intent2.setClass(NotifiMessageActivity.this,
							NewsDetailActivity.class);
					intent2.putExtra("URL", list_PushMessage.get(position)
							.getNewsUrl());

					startActivity(intent2);

				}

				if (list_PushMessage.get(position).getMsgType() == 4) {// 通知
					Intent intent4 = new Intent();
					intent4.setClass(NotifiMessageActivity.this,
							NoticeSortMessageActivity.class);
					intent4.putExtra("time", list_PushMessage.get(position)
							.getMsgSendTime());
					intent4.putExtra("content", list_PushMessage.get(position)
							.getMsgContent());
					intent4.putExtra("title", list_PushMessage.get(position)
							.getTitle());
					intent4.putExtra("iskkaike", false);
					startActivity(intent4);

				}

				if (list_PushMessage.get(position).getMsgType() == 8) {// 短消息
					Intent intent8 = new Intent();
					intent8.setClass(NotifiMessageActivity.this,
							NoticeSortMessageActivity.class);
					intent8.putExtra("time", list_PushMessage.get(position)
							.getMsgSendTime());
					intent8.putExtra("content", list_PushMessage.get(position)
							.getMsgContent());
					intent8.putExtra("title", list_PushMessage.get(position)
							.getTitle());
					intent8.putExtra("iskkaike", false);
					startActivity(intent8);

				}

			}
		});

		isFresh = true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isFresh) {
			mAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		http = new HttpUtils();
		http.configCurrentHttpCacheExpiry(10);
		http.configTimeout(4000);
		http.configSoTimeout(4000);
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_notifi_message);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark, this);
		setBackListener("消息通知", false);
		if (null == db) {
			db = DbUtils.create(NotifiMessageActivity.this);
		}
		intent = getIntent();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 设置消息已读 本地
	 */
	private void setMessageIsRead(BPushMessageInfo mBPushMessageInfo) {
		try {
			db.update(mBPushMessageInfo);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// -标识详细已读

	private void readMessqage(String messageid) {

		String url = AppConfig.getIp(NotifiMessageActivity.this)
				+ AppConfig.METHOAD_READMESSAGE;
		System.out.println(url);
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("id", messageid);
		params.addBodyParameter("username", BUserlogin.Username);
		System.out.println("messageid:" + messageid);

		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						System.out.println("responseInfo是否已读::"
								+ responseInfo.result);

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}
}
