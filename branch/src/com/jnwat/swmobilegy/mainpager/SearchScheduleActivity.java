package com.jnwat.swmobilegy.mainpager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.Camera.Size;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jnwat.analysis.ASearchSchedule;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.LoginActivity;
import com.jnwat.swmobilegy.dapter.AdapterSearchSchedule;
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
 * @author chang-zhiyuan 课表查询
 */
@SuppressLint("HandlerLeak")
public class SearchScheduleActivity extends BaseActivity {
	private PullToRefreshListView sw_lv_searchschedule;
	private List<HashMap<String, String>> list;
	private AdapterSearchSchedule mAdapterSearchSchedule;
	private int type;// 判断是否刷新的类型
	public static ImageView iv_message;
	private int nub = 1;// 页码
	private int list_size = 0;// list的长度

	@Override
	protected void initView() {

		setBackListener("课程表", false);

		mAdapterSearchSchedule = new AdapterSearchSchedule(
				SearchScheduleActivity.this, list);
		sw_lv_searchschedule.setAdapter(mAdapterSearchSchedule);

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		sw_lv_searchschedule.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (list.get(arg2 - 1).get("TYPE").equals("2")) {//
					Intent intent = new Intent();
					intent.putExtra("projectid", list.get(arg2 - 1).get("Id"));
					intent.setClass(SearchScheduleActivity.this,
							SearchScheduleDetailActivity.class);
					startActivity(intent);
				}

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
		http = new HttpUtils();
		http.configTimeout(5000);
		http.configCurrentHttpCacheExpiry(1000); // 缓存时间
		if (null == list) {
			list = new ArrayList<HashMap<String, String>>();
		}
		setContentView(R.layout.activity_searchschedule);
		ModifySysTitle.ModifySysTitleStyle(R.color.title_blue,
				SearchScheduleActivity.this);
		sw_lv_searchschedule = (PullToRefreshListView) findViewById(R.id.sw_lv_searchschedule);
		sw_lv_searchschedule.getRefreshableView().setDivider(null);
		sw_lv_searchschedule.getRefreshableView().setSelector(
				android.R.color.transparent);
		sw_lv_searchschedule.setMode(Mode.BOTH);
		sw_lv_searchschedule
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								SearchScheduleActivity.this,
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						// Do work to refresh the list here.
						if (sw_lv_searchschedule.isHeaderShown()) {// 显示头部UI
							// 添加数据 网络请求
							mHandler.sendEmptyMessage(10);
						} else if (sw_lv_searchschedule.isFooterShown()) {// 显示底部UI
							// 加载更多
							mHandler.sendEmptyMessage(100);
						} else {
							// 网络请求
							LogUtils.d("初始化网络数据");
							list_size = 0;
							mHandler.sendEmptyMessage(10);
						}

					}
				});
		// 设置自动刷新
		sw_lv_searchschedule.setRefreshing(true);

	}

	/**
	 * 得到SearchSchedule
	 */
	private void getSearchSchedule(final int mnub, final boolean isClear) {
		RequestParams params = new RequestParams(); // 参数
		
		if(	BIntentObj.isGetClass==0){
			params.addBodyParameter("userid", BUserlogin.userid);
		}else{
			
		}
		String url = "";

		if (BIntentObj.isGetClass == 0) {

			params.addBodyParameter("userid", "");
			// params.addBodyParameter("userid", BUserlogin.userid);
			url = AppConfig.getIp(SearchScheduleActivity.this)
					+ AppConfig.METHOAD_SEARCHSCHEDULE;
		} else {
			Intent intent = getIntent();
			String 		projectid = intent.getStringExtra("projectid");
			params.addBodyParameter("projectid", projectid);
			url = AppConfig.getIp(SearchScheduleActivity.this)
					+ AppConfig.METHOAD_SEARCHSCHEDULEBYPROJECT;
		}

		// 页码
		params.addBodyParameter("page", mnub + "");

		LogUtils.d("课程查询:" + url + "当前页：" + mnub);
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override  
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						ASearchSchedule mASearchSchedule = new ASearchSchedule();
						LogUtils.d("课程:" + responseInfo.result);
						type = mASearchSchedule.analySearchSchedule(
								responseInfo.result, list, isClear);
						LogUtils.d("共：" + list.size() + "项");
						int new_list_size = list.size();
						if (new_list_size > list_size) {// 如果有获取的数据
							nub++;
						}
						list_size = new_list_size;
						mHandler.sendEmptyMessageDelayed(9, 500);

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						mHandler.sendEmptyMessage(0);

					}
				});

	}

	// 消息处理
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				ToastViewTools.initToast(SearchScheduleActivity.this, "暂无数据");
				sw_lv_searchschedule.onRefreshComplete();
				break;
			case 1:
				ToastViewTools.initToast(SearchScheduleActivity.this, "数据出错了");
				sw_lv_searchschedule.onRefreshComplete();
				break;
			case 9:
				// 0代表成功 1 代表失败 2有新数据刷新 3 不刷新
				System.out.println("Type:" + type);
				list_size = list.size();
				if (type == 0) {
					System.out.println("刷新数据");
					mAdapterSearchSchedule.notifyDataSetChanged();
					// Call onRefreshComplete when the list has been refreshed.
					sw_lv_searchschedule.onRefreshComplete();
				}
				if (type == 3) {
					sw_lv_searchschedule.onRefreshComplete();
				}

				break;
			case 10:// 自动加载数据
				getSearchSchedule(1, true);
				break;
			case 100:// 加载更多数据
				getSearchSchedule(nub, false);
				break;
			case ShowRemind.HANDLER_STATUS:
				ToastViewTools.initToast(SearchScheduleActivity.this,
						ShowRemind.ErrorMessage);
				break;
			}
		};
	};

}
