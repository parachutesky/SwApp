package com.jnwat.swmobilegy.workevent;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jnwat.analysis.AUserTasks;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BUserTasks;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterTaskInfo;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class OverProcessDetailActivity extends BaseActivity {
	private PullToRefreshListView list;
	private HttpUtils http;
	private List<BUserTasks> lisArray = new ArrayList<BUserTasks>();
	private AdapterTaskInfo adapter;
	private String flowId = "";
	private ImageView rebackBtn;
	private BitmapUtils bitmapUtils;
	private int page_nub = 1;
	private TextView showTip_txt;
	private AUserTasks mAUserTasks;
	
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		list = (PullToRefreshListView) findViewById(R.id.lv_overProcessDetail);
		showTip_txt = (TextView) findViewById(R.id.showTip_txt);
		list.setMode(Mode.BOTH);
		setBackListener("已办流程", false);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// 赋值
				BIntentObj.mBUserTasks = lisArray.get(arg2-1);
				BIntentObj.mBUserTasks.setFlowID(flowId);
				Intent intent = new Intent();
				intent.putExtra("From", "OverProcessDetailActivity");
				intent.setClass(OverProcessDetailActivity.this,
						MeetingApply.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		adapter = new AdapterTaskInfo(this, lisArray,bitmapUtils);
		list.setAdapter(adapter);

		list.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						OverProcessDetailActivity.this,
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (list.isHeaderShown()) {// 显示头部UI
					// 添加数据 网络请求
					if (lisArray.size() > 0) {
						lisArray.clear();
					}
					page_nub = 1;
					getWebData(BUserlogin.NO, flowId);
				} else if (list.isFooterShown()) {// 显示底部UI
					getWebData(BUserlogin.NO, flowId);
				} else {
					getWebData(BUserlogin.NO, flowId);
					LogUtils.d("初始化网络数据");
				}

			}
		});
		// 设置自动刷新
		list.setRefreshing(true);
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_over_process_detail);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark, this);
		Intent intent = this.getIntent();
		http = new HttpUtils();		
		bitmapUtils = new BitmapUtils(getApplicationContext());
		flowId = intent.getStringExtra("FLOWID");
		mAUserTasks = new AUserTasks();

	}

	public void getWebData(final String userNo, final String flowid) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("userNo", userNo.trim());
		params.addBodyParameter("flowid", flowid.trim());
		params.addBodyParameter("page", page_nub+"");

		String url =  AppConfig.getIp(this)+AppConfig.METHOD_DB_FLOWCOMPLETEDTL_OA;
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d("responseInfo.result:" + responseInfo.result);
/*						int status = AGetOverProcessDetail
								.analysisOverdProcess(responseInfo.result,
										lisArray);*/
						int status =  mAUserTasks.aAUserTasks(responseInfo.result,mmHandler, lisArray);
						if (status==1) {
							page_nub++;
						}
					/*	if (status == 0) {
							ToastViewTools.initToast(
									OverProcessDetailActivity.this, "获取已办详情异常");
						} else if (status == 2) {
							ToastViewTools.initToast(
									OverProcessDetailActivity.this, "没有获取到数据");
						}*/
						showInfo(status);

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						ToastViewTools.initToast(
								OverProcessDetailActivity.this, "获取数据失败");
						System.out.println("error:" + error + msg);

					}
				});

	}
	public Handler mmHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				ToastViewTools.initToast(OverProcessDetailActivity.this, "暂无数据");
				freshList();
				break;
			case ShowRemind.HANDLER_STATUS:
				ToastViewTools
						.initToast(OverProcessDetailActivity.this, ShowRemind.ErrorMessage);
						freshList();
				break;
			case ShowRemind.HANDLER_SUCCESS:
				freshList();
				break;
			}
		};
	};

	public void showInfo(int status) {
		int size = lisArray.size();
		if (status == 1) {
			showTip_txt.setVisibility(View.GONE);
		} else if (status == 2 && size == 0) { // 当 status 为 2时 说明 正常没有获取 到数据
			// 当没有数据的时候 为了 显示 提示 无数据 关闭 软键盘
			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(showTip_txt.getWindowToken(), 0);
			showTip_txt.setVisibility(View.VISIBLE);
		} else if (status == 0 && size == 0) {
			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(showTip_txt.getWindowToken(), 0);
			showTip_txt.setVisibility(View.VISIBLE);
			showTip_txt.setText("获取可发送流程信息息异常");
		} else if (status == -1 && size == 0) {
			InputMethodManager imm = (InputMethodManager) this
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(showTip_txt.getWindowToken(), 0);
			showTip_txt.setVisibility(View.VISIBLE);
			showTip_txt.setText("请检查是否登录或网络状况");
		}
	}
	public void freshList() {
		list.onRefreshComplete();
		adapter.notifyDataSetChanged();
	}
}
