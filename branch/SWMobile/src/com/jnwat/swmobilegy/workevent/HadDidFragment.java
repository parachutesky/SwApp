package com.jnwat.swmobilegy.workevent;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterTaskInfo;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 已办流程
 */
public class HadDidFragment extends Fragment {
	private View view;
	private Activity mContext;
	private PullToRefreshListView list;
	private ArrayList<BUserTasks> mArrayListUserTasks;
	private HttpUtils http;
	private TextView showTip_txt;
	private AdapterTaskInfo mAdapterTaskInfo;
	private BitmapUtils bitmapUtils;
	private AUserTasks mAUserTasks;
	private boolean allowFresh = false;
	
	static HadDidFragment newInstance(String s) {
		HadDidFragment newFragment = new HadDidFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
		return newFragment;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_listview_overprocess,
					null);
			intView();
			initData();
			initListen();
		}

		ViewGroup parent = (ViewGroup) view.getParent();
		/*
		 * if(BUserlogin.loginStatus==1&&BIntentObj.IsGetOverProcess==false){
		 * getWebData(); } if(BUserlogin.loginStatus==0){ clearData(); }
		 */
		if (parent != null) {
			parent.removeView(view);
		}
		return view;

	}

	public void initListen() {
		list.setOnScrollListener(new PauseOnScrollListener(bitmapUtils, false,
				true));
		list.setMode(Mode.PULL_FROM_START);
		list.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(),
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (list.isHeaderShown()) {// 显示头部UI
					// 添加数据 网络请求
					if (mArrayListUserTasks.size() > 0) {
						mArrayListUserTasks.clear();
					}
					if (BUserlogin.loginStatus == 1) {
						mHandler.sendEmptyMessage(10);
					} else {
						mHandler.sendEmptyMessageDelayed(0, 100);
					}
				
				} else if (list.isFooterShown()) {// 显示底部UI
					// 加载更多
					mHandler.sendEmptyMessage(100);
				} else {
					// 网络请求
					LogUtils.d("初始化网络数据");
					allowFresh = true;
					if (BUserlogin.loginStatus == 1) {
						mHandler.sendEmptyMessage(10);
					} else {
						mHandler.sendEmptyMessageDelayed(0, 100);
					}
		
				}

			}
		});
		// 设置自动刷新
		list.setRefreshing(true);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				// 赋值
				BIntentObj.mBUserTasks = mArrayListUserTasks.get(position - 1);
				Intent intent = new Intent();
				// intent.putExtra("result", "100");
				intent.setClass(getActivity(), MeetingApply.class);
				startActivity(intent);

			}

		});

		mAdapterTaskInfo = new AdapterTaskInfo(getActivity(),
				mArrayListUserTasks, bitmapUtils);
		list.setAdapter(mAdapterTaskInfo);
	}

	public void intView() {
		list = (PullToRefreshListView) view
				.findViewById(R.id.lv_workevent_over_progcess);
		showTip_txt = (TextView) view.findViewById(R.id.showTip_txt);
	}

	public void initData() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this.getActivity();
		bitmapUtils = new BitmapUtils(getActivity().getApplicationContext());
		http = new HttpUtils();
		http.configTimeout(5000);
		http.configCurrentHttpCacheExpiry(4000); // 缓存时间
		mAUserTasks = new AUserTasks();
		mArrayListUserTasks = new ArrayList<BUserTasks>();
		mContext = this.getActivity();
	}

	/**
	 * 获取 已办流程列表数据
	 */
	public void getWebData() {
		// TODO Auto-generated method stub
		http = new HttpUtils();
		RequestParams params = new RequestParams(); // 参数

		String name = BUserlogin.NO;// 暂时将 登录人写成固定的userNo
		params.addBodyParameter("loginName", name.trim());

		String url = AppConfig.getIp(mContext)+
				AppConfig.METHOAD_GETUSERYIBAN_OA;
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d("已办responseInfo.result:"
								+ responseInfo.result);
						int status = mAUserTasks.aAUserTasks(
								responseInfo.result, mHandler,
								mArrayListUserTasks);
						showInfo(status);

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						showInfo(-1);
						System.out.println("error:" + error + msg);
						Toast.makeText(mContext, "已办数据加载失败", Toast.LENGTH_SHORT)
								.show();
						if (BUserlogin.loginStatus == 1) {// 新用户登录成功 但是
															// 在获取数据上失败
															// clear是为了将
															// 上一用户的数据清理掉
							mArrayListUserTasks.clear();
						}
						freshList();

					}
				});

	}

	public void showInfo(int status) {
		int size = mArrayListUserTasks.size();
		if (status == 1) {
			showTip_txt.setVisibility(View.GONE);
		} else if (status == 2 && size == 0) { // 当 status 为 2时 说明 正常没有获取 到数据
			// 当没有数据的时候 为了 显示 提示 无数据 关闭 软键盘
			InputMethodManager imm = (InputMethodManager) mContext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(showTip_txt.getWindowToken(), 0);
			showTip_txt.setVisibility(View.VISIBLE);
		} else if (status == 0 && size == 0) {
			InputMethodManager imm = (InputMethodManager) mContext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(showTip_txt.getWindowToken(), 0);
			showTip_txt.setVisibility(View.VISIBLE);
			showTip_txt.setText("获取已办流程信息息异常");
		} else if (status == -1 && size == 0) {
			InputMethodManager imm = (InputMethodManager) mContext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(showTip_txt.getWindowToken(), 0);
			showTip_txt.setVisibility(View.VISIBLE);
			showTip_txt.setText("请检查是否登录或网络状况");
		}
	}

	/**
	 * 清空列表数据
	 */
	public void clearData() {
		mArrayListUserTasks.clear();
		freshList();
	}

	/**
	 * 刷新数据列表
	 */
	public void freshList() {

		mAdapterTaskInfo.notifyDataSetChanged();
		list.onRefreshComplete();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		// ****************************顺序不能乱*****************************************
		super.onResume();
		if (BUserlogin.loginStatus == 0) {
			clearData();
			showInfo(-1);
		}
		if (BUserlogin.loginStatus == 1 && BIntentObj.IsGetOverProcess == true	&& view != null && allowFresh == true) {
			getWebData();
		}

	}

	// 消息处理
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
			//	ToastViewTools.initToast(getActivity(), "数据出错了");
				list.onRefreshComplete();
				break;
			case 1:
				ToastViewTools.initToast(getActivity(), "数据出错了");
				list.onRefreshComplete();
				break;
			case 200:
				BIntentObj.IsGetOverProcess = false;
				freshList();
				break;
			/*
			 * case 9: // 0代表成功 1 代表失败 2有新数据刷新 3 不刷新 list_size =
			 * mArrayListUserTasks.size(); if (type == 0) {
			 * System.out.println("刷新数据");
			 * mAdapterSearchSchedule.notifyDataSetChanged(); // Call
			 * onRefreshComplete when the list has been refreshed.
			 * sw_lv_searchschedule.onRefreshComplete(); } if (type == 3) {
			 * sw_lv_searchschedule.onRefreshComplete(); }
			 * 
			 * break; case 10:// 自动加载数据 getSearchSchedule(1,true); break;
			 */
			case 10:// 自动加载数据
				if (BUserlogin.loginStatus == 1) {
					getWebData();
				}
				break;
			case 100:// 加载更多数据

				// getSearchSchedule(nub,false);
				break;
			case ShowRemind.HANDLER_STATUS:
				ToastViewTools
						.initToast(getActivity(), ShowRemind.ErrorMessage);
				break;
			}
		};
	};

}
