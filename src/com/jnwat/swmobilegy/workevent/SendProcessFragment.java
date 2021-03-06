package com.jnwat.swmobilegy.workevent;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.jnwat.analysis.AGetSendProcessInfo;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BUserlogin;
import com.jnwat.bean.OAProcess;
import com.jnwat.config.AppConfig;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterProcess;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 发起流程
 */
public class SendProcessFragment extends Fragment {

	private View view;
	private List<OAProcess> lisArray = new ArrayList<OAProcess>();
	private Activity mContext;
	private PullToRefreshExpandableListView eList;
	private ExpandableListView explist;
	private HttpUtils http = new HttpUtils();
	private List<String> group = new ArrayList<String>();
	private List<List<OAProcess>> child = new ArrayList<List<OAProcess>>();
	AdapterProcess adapter;
	// PopLoginDialog mPopLoginDialog;
	private TextView showTip_txt; // 显示 获取数据情况

	static SendProcessFragment newInstance(String s) {
		SendProcessFragment newFragment = new SendProcessFragment();
		Bundle bundle = new Bundle();
		newFragment.setArguments(bundle);
		return newFragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_sendprocess, null);
			intView();
			// initData();
			initListen();
			if (BUserlogin.loginStatus == 1
					&& BIntentObj.IsGetSendProcess == false) {
				// mPopLoginDialog.showPoploginDialog(mContext);
			}
		}

		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}

		return view;

	}

	public void intView() {
		eList = (PullToRefreshExpandableListView) view
				.findViewById(R.id.sendprocess_elist);
		explist = eList.getRefreshableView();
		explist.setGroupIndicator(null);
		eList.setShowIndicator(false);
		showTip_txt = (TextView) view.findViewById(R.id.showTip_txt);
		// mPopLoginDialog = new PopLoginDialog();
	}

	public void initData() {
		if (adapter == null) {
			adapter = new AdapterProcess(mContext, group, child);
		}

		explist.setAdapter(adapter);
		if (BUserlogin.loginStatus == 1)
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					getWebData();
				}

			}).start();
	}

	public void initListen() {

		explist.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int arg2, int arg3, long arg4) {
				// TODO Auto-generated method stub
				ToastViewTools.initToast(mContext, "此功能正在研发中，敬请期待");
				return false;
			}
		});

		eList.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh(PullToRefreshBase refreshView) {
				// TODO Auto-generated method stub
				new refresh().execute();
			}
		});

	}

	//
	public void showInfo(int status) {
		int size = group.size();
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
			showTip_txt.setText("获取可发送流程信息息异常");
		} else if (status == -1 && size == 0) {
			InputMethodManager imm = (InputMethodManager) mContext
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(showTip_txt.getWindowToken(), 0);
			showTip_txt.setVisibility(View.VISIBLE);
			showTip_txt.setText("请检查是否登录或网络状况");
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (mContext == null) {
			mContext = this.getActivity();
		}
	}

	public class refresh extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub

			if (BUserlogin.loginStatus == 1) {
				getWebData();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			eList.onRefreshComplete();
		}

	}

	// 获取接口数据
	public void getWebData() {
		RequestParams params = new RequestParams(); // 参数
		String name = BUserlogin.NO;// 暂时将 登录人写成固定的userNo
		params.addBodyParameter("loginName", name.trim());
		String url = AppConfig.getIp(getActivity())+AppConfig.METHOAD_GETSENDPROCESS_OA;
		LogUtils.e("发起流程:" + url);
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d("responseInfo.result:" + responseInfo.result);
						AGetSendProcessInfo AGetContactsInfo = new AGetSendProcessInfo();
						int status = AGetContactsInfo.analysisSendProcess(
								responseInfo.result, group, child);
						if (status == 0) {
							ToastViewTools.initToast(mContext, "获取可发送列表信息异常");
						} else if (status == 2) {
							ToastViewTools.initToast(mContext, "暂无数据");
						}
						/*
						 * if(BIntentObj.IsGetSendProcess == true){
						 * if(group.size()==0){
						 * ToastViewTools.initToast(mContext, "没有更多了"); }
						 * }else{//获取数据问题 ToastViewTools.initToast(mContext,
						 * "数据更新失败");
						 * 
						 * }
						 */
						noticeUpdateData();
						showInfo(status);
					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {// 未连接上服务器
						System.out.println("error:" + error + msg);
						showInfo(-1);
						ToastViewTools.initToast(mContext, "数据连接失败");
						Toast.makeText(mContext, "加载失败", Toast.LENGTH_SHORT)
								.show();
						noticeUpdateData();

					}
				});
	}

	//

	Handler mainHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int x = msg.what;
			switch (x) {
			case 101:
				// mPopLoginDialog.dismisPop();
				adapter.notifyDataSetChanged();
				break;
			}
		}

	};

	public void noticeUpdateData() {
		Message msg = mainHandler.obtainMessage();
		msg.what = 101;
		mainHandler.sendMessage(msg);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// ***********************顺序不能乱********************************
		// 当 重新 处于非登录状态的话 将数据清空
		if (BUserlogin.loginStatus == 0) {
			group.clear();
			child.clear();
			if (null != adapter) {
				adapter.notifyDataSetChanged();
			}

		}
		if (BIntentObj.IsGetSendProcess == false && BUserlogin.loginStatus == 1) {
			initData();
		} else if (BUserlogin.loginStatus == 0) {
			showInfo(-1);
		}

	}

}
