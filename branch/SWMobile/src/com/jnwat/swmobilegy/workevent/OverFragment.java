package com.jnwat.swmobilegy.workevent;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jnwat.analysis.AGetOverProcess;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BUserlogin;
import com.jnwat.bean.OverTask;
import com.jnwat.config.AppConfig;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterOverProcessSort;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 办结
 */
public class OverFragment extends Fragment {
	private View view;
	private Activity mContext;
	private PullToRefreshListView list;
	private List<OverTask> lisArray = new ArrayList<OverTask>();
	private HttpUtils http;
	private AdapterOverProcessSort adapter;
	private TextView showTip_txt;
	private boolean allowFresh = false;

	static OverFragment newInstance(String s) {
		OverFragment newFragment = new OverFragment();
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
		list.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				getWebData();
			}

		});
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				OverTask task = (OverTask) adapter.getItem(position - 1);
				Intent intent = new Intent();
				intent.putExtra("FLOWID", task.getNO());
				intent.setClass(mContext, OverProcessDetailActivity.class);
				mContext.startActivity(intent);

			}

		});
	}

	public void intView() {
		list = (PullToRefreshListView) view
				.findViewById(R.id.lv_workevent_over_progcess);
		showTip_txt = (TextView) view.findViewById(R.id.showTip_txt);
	}

	public void initData() {
		adapter = new AdapterOverProcessSort(mContext, lisArray);
		list.setAdapter(adapter);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
		params.addBodyParameter("userNo", name.trim());
		String url = AppConfig.getIp(mContext)+AppConfig.METHOAD_FLOWCOMPLETEGROUP_OA;
		allowFresh = true;
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d("responseInfo.result:" + responseInfo.result);
						AGetOverProcess AGetOverProcess = new AGetOverProcess();
						int status = AGetOverProcess.analysisOverdProcess(
								responseInfo.result, lisArray);
						/*
						 * if (status == 0) { Toast.makeText(mContext,
						 * "获取已办流程信息异常", Toast.LENGTH_SHORT).show(); } else if
						 * (status == 2) { // 正常情况下无数据 Toast.makeText(mContext,
						 * "暂无已办数据", Toast.LENGTH_SHORT).show(); }
						 */
						showInfo(status);
						freshList();
					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						showInfo(-1);
						System.out.println("error:" + error + msg);

						freshList();

					}
				});

	}

	public void showInfo(int status) {
		int size = lisArray.size();
		if (status == 1) {
			showTip_txt.setVisibility(View.GONE);
		} else if (status == 2 && size == 0) { // 当 status 为 2时 说明 正常没有获取 到数据
			// 当没有数据的时候 为了 显示 提示 无数据 关闭 软键盘
			InputMethodManager imm = (InputMethodManager) mContext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(showTip_txt.getWindowToken(), 0);
			showTip_txt.setVisibility(View.VISIBLE);
			showTip_txt.setText("暂无数据");
		} else if (status == 0 && size == 0) {
			InputMethodManager imm = (InputMethodManager) mContext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(showTip_txt.getWindowToken(), 0);
			showTip_txt.setVisibility(View.VISIBLE);
			showTip_txt.setText("获取办结流程信息息异常");
		} else if (status == -1 && size == 0) {
			InputMethodManager imm = (InputMethodManager) mContext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (showTip_txt != null) {
				imm.hideSoftInputFromWindow(showTip_txt.getWindowToken(), 0);
				showTip_txt.setVisibility(View.VISIBLE);
				showTip_txt.setText("请检查是否登录或网络状况");
			}

		}
	}

	/**
	 * 清空列表数据
	 */
	public void clearData() {
		lisArray.clear();
		freshList();
	}

	/**
	 * 刷新数据列表
	 */
	public void freshList() {
		BIntentObj.EndProcess = false;
		adapter.notifyDataSetChanged();
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
		if (BUserlogin.loginStatus == 1 && BIntentObj.EndProcess == true
				&& view != null && allowFresh == true) {
			getWebData();
		}

	}

}
