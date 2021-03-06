/**
 * 
 */
package com.jnwat.swmobilegy.mainpager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.jnwat.config.ShowRemind;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.FeedBackListAdapter;
import com.jnwat.swmobilegy.dapter.MyAnswerListAdapter;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.util.LogUtils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author zhaorl
 * 
 * 
 */
public class FeedBackFragment extends Fragment {

	private View view;
	private Activity mContext;
	private PullToRefreshListView mPullToRefreshListView;
	private FeedBackListAdapter mAdapterAllProject = null;
	int size = 0;
	int page = 0;
	int type = 0;
	private HttpUtils http;
	List<Map<String, Object>> list = null;

	static ProjectBackFragment newInstance(String s) {
		ProjectBackFragment newFragment = new ProjectBackFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
		return newFragment;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		http = new HttpUtils();
		http.configTimeout(5000);
		http.configCurrentHttpCacheExpiry(1000); // 缓存时间
		if (null == list) {
			list = new ArrayList<Map<String, Object>>();
		}
		mContext = this.getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_listview_overprocess,
					null);

			initView(view);
			// initData();
			// initListen();
		}

		ViewGroup parent = (ViewGroup) view.getParent();

		if (parent != null) {
			parent.removeView(view);
		}
		return view;

	}

	private void initView(View view) {
		mPullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.lv_workevent_over_progcess);
		// mPullToRefreshListView.getRefreshableView().setDivider(null);
		mPullToRefreshListView.getRefreshableView().setSelector(
				android.R.color.transparent);
		// mPullToRefreshListView.setMode(Mode.BOTH);
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(getActivity(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						// Do work to refresh the list here.
						if (mPullToRefreshListView.isHeaderShown()) {// 显示头部UI
							// 添加数据 网络请求
							list.clear();
							mHandler.sendEmptyMessage(10);
						} else if (mPullToRefreshListView.isFooterShown()) {// 显示底部UI
							// 加载更多
							// mHandler.sendEmptyMessage(100);
						} else {
							// 网络请求
							LogUtils.d("初始化网络数据");
							size = 0;
							list.clear();
							mHandler.sendEmptyMessage(10);
						}

					}
				});
		// 设置自动刷新
		mPullToRefreshListView.setRefreshing(true);
		mAdapterAllProject = new FeedBackListAdapter(getActivity(), list);
		mPullToRefreshListView.setAdapter(mAdapterAllProject);

		mPullToRefreshListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub

						Toast.makeText(mContext, "开发中尽请期待", 0).show();
					}

				});
	}

	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				ToastViewTools.initToast(getActivity(), "暂无数据");
				mPullToRefreshListView.onRefreshComplete();
				break;
			case 1:
				ToastViewTools.initToast(getActivity(), "数据出错了");
				mPullToRefreshListView.onRefreshComplete();
				break;
			case 9:
				// 0代表成功 1 代表失败 2有新数据刷新 3 不刷新
				System.out.println("Type:" + type);
				size = list.size();
				if (type == 0) {
					System.out.println("刷新数据");
					mAdapterAllProject.notifyDataSetChanged();
					// Call onRefreshComplete when the list has been refreshed.
					mPullToRefreshListView.onRefreshComplete();
				}
				if (type == 3) {
					mPullToRefreshListView.onRefreshComplete();
				}

				break;
			case 10:// 自动加载数据
				page = 1;
				getTrainingCourse(page, true);
				break;
			case 100:// 加载更多数据
				// getTrainingCourse(page, false);
				break;
			case ShowRemind.HANDLER_STATUS:
				ToastViewTools
						.initToast(getActivity(), ShowRemind.ErrorMessage);
				break;
			}
		}

	};

	private void getTrainingCourse(int page, boolean b) {
		// TODO Auto-generated method stub

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "每天都要早起上操，还不能能好好睡了");
		map.put("date", "20分钟前");
		map.put("name", "李思思");
		map.put("num", 3);

		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("title", "又到开学的时间，童鞋们有什么收获假期里？");
		map1.put("date", "2分钟前");
		map1.put("name", "张永利");
		map1.put("num", 5);

		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("title", "学校要举办文艺表演，希望大家踊跃报名参赛");
		map2.put("date", "4小时前");
		map2.put("name", "晓明");
		map2.put("num", 1);

		list.add(map);
		list.add(map1);
		list.add(map2);

		mHandler.sendEmptyMessageDelayed(9, 200);

	}
}
