package com.jnwat.swmobilegy.mainpager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jnwat.analysis.AClassNotifiMessage;
import com.jnwat.bean.BKaiBanTongZhi;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.NoticeSortMessageActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterKaibanTongz;
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
 * @author chang-zhiyuan 开班通知
 */
public class ClassNotificationActivity extends BaseActivity {
	private Activity mContext;
	private PullToRefreshListView mPullToRefreshListView;
	private int type;// 判断是否刷新的类型
	private int nub = 1;// 页码
	private int list_size = 0;// list的长度
	private List<BKaiBanTongZhi> list;
	private AdapterKaibanTongz mAdapterKaibanTongz;
	private HttpUtils http;
	private RelativeLayout rel_show;
	private TextView textView_show;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		initView1();
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		setBackListener("办班通知", false);

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			mAdapterKaibanTongz.notifyDataSetChanged();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		http = new HttpUtils();
		http.configTimeout(5000);
		http.configCurrentHttpCacheExpiry(1000); // 缓存时间
		if (null == list) {
			list = new ArrayList<BKaiBanTongZhi>();
		}

		setContentView(R.layout.activity_classnotification);
		setBackListener("办班通知", false);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				ClassNotificationActivity.this);
	}

	private void initView1() {
		mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.lv_workevent_over_progcess);
		rel_show = (RelativeLayout) findViewById(R.id.rel_show);
		textView_show = (TextView) findViewById(R.id.textView_show);

		mPullToRefreshListView.getRefreshableView().setDivider(null);
		mPullToRefreshListView.getRefreshableView().setSelector(
				android.R.color.transparent);
		mPullToRefreshListView.setMode(Mode.BOTH);
		mPullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(
								ClassNotificationActivity.this,
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						// Do work to refresh the list here.
						if (mPullToRefreshListView.isHeaderShown()) {// 显示头部UI
							if (null != list || list.size() > 0) {
								list.clear();
							}
							// 添加数据 网络请求
							mHandler.sendEmptyMessage(10);
						} else if (mPullToRefreshListView.isFooterShown()) {// 显示底部UI
							// 加载更多
							nub++;
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
		mPullToRefreshListView.setRefreshing(true);
		mPullToRefreshListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						// TODO Auto-generated method stub
						position = position - 1;
						Intent intent4 = new Intent();
						intent4.setClass(ClassNotificationActivity.this,
								NoticeSortMessageActivity.class);
						intent4.putExtra("iskkaike", true);
						intent4.putExtra("time", list.get(position)
								.getMsgSendTime());
						intent4.putExtra("content", list.get(position)
								.getMsgContent());
						intent4.putExtra("title", list.get(position).getTitle());
						list.get(position).setIsRead("11");
						startActivity(intent4);

					}
				});

		mAdapterKaibanTongz = new AdapterKaibanTongz(
				ClassNotificationActivity.this, list);
		mPullToRefreshListView.setAdapter(mAdapterKaibanTongz);

	}

	// 消息处理
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				ToastViewTools
						.initToast(ClassNotificationActivity.this, "暂无数据");
				mPullToRefreshListView.onRefreshComplete();

				if (list_size < 1) {
					rel_show.setVisibility(View.VISIBLE);
					textView_show.setText("暂时没有您的办班通知！");
				} else {
					rel_show.setVisibility(View.GONE);
				}
				break;
			case 1:
				ToastViewTools.initToast(ClassNotificationActivity.this,
						"数据出错了");
				mPullToRefreshListView.onRefreshComplete();

				if (list_size < 1) {
					rel_show.setVisibility(View.VISIBLE);
					textView_show.setText("暂时没有您的办班通知！");
				} else {
					rel_show.setVisibility(View.GONE);
				}
				break;
			case 9:
				// 0代表成功 1 代表失败 2有新数据刷新 3 不刷新
				System.out.println("Type:" + type);

				mAdapterKaibanTongz.notifyDataSetChanged();
				mPullToRefreshListView.onRefreshComplete();

				if (list_size < 1) {
					rel_show.setVisibility(View.VISIBLE);
					textView_show.setText("暂时没有您的办班通知！");
				} else {
					rel_show.setVisibility(View.GONE);
				}
				break;
			case 10:// 自动加载数据
				nub = 1;
				getClassInFrom(nub, true);
				break;
			case 100:// 加载更多数据
				getClassInFrom(nub, false);
				break;
			case ShowRemind.HANDLER_STATUS:
				ToastViewTools.initToast(ClassNotificationActivity.this,
						ShowRemind.ErrorMessage);
				break;
			}
		};
	};

	/**
	 * 所有项目 TrainingCourse
	 */
	private void getClassInFrom(final int mnub, final boolean isClear) {
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("Userid", BUserlogin.userid);
		// 页码
		params.addBodyParameter("page", mnub + "");

		String url = AppConfig.getIp(ClassNotificationActivity.this)
				+ AppConfig.METHOAD_CLASSINFROM;
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						AClassNotifiMessage mAClassNotifiMessage = new AClassNotifiMessage();
						LogUtils.d("开办通知:" + responseInfo.result);

						try {
							list.addAll(mAClassNotifiMessage
									.APushMessage1(responseInfo.result));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						LogUtils.d("共：" + list.size() + "项");
						int new_list_size = list.size();
						// if (new_list_size > list_size) {// 如果有获取的数据
						// nub++;
						// }

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
}
