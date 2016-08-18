package com.jnwat.swmobilegy.mainpager;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jnwat.analysis.ANoticeMessage;
import com.jnwat.bean.BNoticeMessage;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterNotifyAndMessage;
import com.jnwat.swmobilegy.mainpager.notify.AddNotiftActivity;
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
 * @author chang-zhiyuan 通知公告 2016年4月21日 14:03:52
 */
public class NotifyAndMessageActivity extends BaseActivity {
	private TextView mTitle_tv;
	private TextView tv_edit;
	private PullToRefreshListView mProgram_lv;
	private BNoticeMessage mBNoticeMessage;
	private AdapterNotifyAndMessage adapter;
	private ImageView iv_back;
	private List<BNoticeMessage> list;
	private int page = 1;
	private int type = 0;
	Gson gson = null;
	private RelativeLayout rel_show;
	private TextView textView_show;
	ANoticeMessage mANoticeMessage;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			adapter.notifyDataSetChanged();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		// 新增通知
		tv_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setClass(NotifyAndMessageActivity.this,
						AddNotiftActivity.class);
				startActivity(intent);
			}
		});
		mProgram_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				list.get(arg2 - 1).setREADSTATUS("已读");
				intent.putExtra("intentstr", gson.toJson(list.get(arg2 - 1)));
				intent.setClass(NotifyAndMessageActivity.this,
						NotifyShowDetilsActivity.class);
				startActivity(intent);

			}
		});

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		mANoticeMessage = new ANoticeMessage();
		gson = new Gson();
		setContentView(R.layout.activity_notifyandmessage);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		mTitle_tv = (TextView) findViewById(R.id.title_bar_name);
		tv_edit = (TextView) findViewById(R.id.tv_edit);
		rel_show = (RelativeLayout) findViewById(R.id.rel_show);
		textView_show = (TextView) findViewById(R.id.textView_show);
		mProgram_lv = (PullToRefreshListView) findViewById(R.id.lv_notifymessage);
		mProgram_lv.setMode(Mode.BOTH);
		ModifySysTitle.ModifySysTitleStyle(R.color.title_blue,
				NotifyAndMessageActivity.this);
		setBackListener("通知公告", false);

		if (null == list) {
			list = new ArrayList<BNoticeMessage>();
		}
		adapter = new AdapterNotifyAndMessage(NotifyAndMessageActivity.this,
				list);
		mProgram_lv.setAdapter(adapter);
		mProgram_lv.getRefreshableView().setDivider(null);
		mProgram_lv.getRefreshableView().setSelector(
				android.R.color.transparent);
		mProgram_lv.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				/*
				 * System.out.println("top:" + mProgram_lv.isHeaderShown() +
				 * "footer:" + mProgram_lv.isFooterShown());
				 */
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (mProgram_lv.isHeaderShown()) {// 显示头部UI
					page = 1;
					// 添加数据 网络请求
					list.clear();
					getDateByHttp();
				} else if (mProgram_lv.isFooterShown()) {// 显示底部UI
					// 网络请求
					getDateByHttp();
					// mHandler.sendEmptyMessageDelayed(1000, 1500);
				} else {
					// 网络请求
					page = 1;
					list.clear();
					mHandler.sendEmptyMessageDelayed(0, 1500);
				}
			}
		});

		// 设置自动刷新
		mProgram_lv.setRefreshing(true);
		getDateByHttp();

	}

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			System.out.println("msg..." + msg.what);
			switch (msg.what) {

			case ShowRemind.HANDLER_SUCCESS:
				// 刷新列表项
				adapter.notifyDataSetChanged();
				mProgram_lv.onRefreshComplete();
				// mProgram_lv.setMode(Mode.DISABLED);
				break;

			case 100:
				mProgram_lv.onRefreshComplete();
				// closePop();// 关闭Dialog
				break;
			case 101:
				adapter.notifyDataSetChanged();
				mProgram_lv.onRefreshComplete();
				// closePop();// 关闭Dialog
				break;
			case 1000: // 加载更多
				adapter.notifyDataSetChanged();
				mProgram_lv.onRefreshComplete();
				break;

			case 10:
				ToastViewTools
						.initToast(NotifyAndMessageActivity.this, "暂无新信息");
				adapter.notifyDataSetChanged();
				mProgram_lv.onRefreshComplete();
				// closePop();// 关闭Dialog
			case -1:
				adapter.notifyDataSetChanged();
				mProgram_lv.onRefreshComplete();
				// closePop();// 关闭Dialog
				break;
			}
		}
	};

	private void getDateByHttp() {
		// TODO Auto-generated method stub

		http = new HttpUtils();
		http.configTimeout(8000);
		http.configCurrentHttpCacheExpiry(1000); // 缓存时间
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("userid", BUserlogin.userid);
		params.addBodyParameter("page", page + "");

		String url = AppConfig.getIp(NotifyAndMessageActivity.this)
				+ AppConfig.METHOAD_NOTICE;
		System.out.println("page..." + page);
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						try {
							LogUtils.d("responseInfo..." + responseInfo.result);
							List<BNoticeMessage> list_t = mANoticeMessage
									.AnaNotice(responseInfo.result, gson,
											mHandler);
							// 加载更多的数据
							if (null != list_t && list_t.size() > 0) {
								list.addAll(list_t);
								page++;
								if (null != list) {
									mHandler.sendEmptyMessage(101);
								}
							} else {
								// 提示暂无数据
								if (list.size() < 1) {
									rel_show.setVisibility(View.VISIBLE);
									textView_show.setText("暂时没有通知公告信息！");
								} else {
									rel_show.setVisibility(View.GONE);
									textView_show.setVisibility(View.GONE);
								}

							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {

						LogUtils.e("error code===" + msg.toString());
					}
				});
	}

}
