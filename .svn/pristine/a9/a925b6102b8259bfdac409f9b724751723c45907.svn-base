/**
 * 
 */
package com.jnwat.swmobilegy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.jnwat.analysis.ASalaryQuery;
import com.jnwat.analysis.ASearchSchedule;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterSearchSchedule;
import com.jnwat.swmobilegy.dapter.SalaryQueryListAdapter;
import com.jnwat.swmobilegy.dapter.UnEducationListAdapter;
import com.jnwat.swmobilegy.mainpager.SearchScheduleActivity;
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
 * @author zhrol
 * 
 * @category 薪资查询
 * 
 */
public class SalaryQueryActivity extends BaseActivity {

	private String mTitle = "薪资查询";
	private TextView mTitle_tv;
	private PullToRefreshListView salary_lv;;
	private ImageView iv_back;
	private List<Map<String, Object>> list;
	private SalaryQueryListAdapter mAdapter = null;
	private RelativeLayout rel_show;
	private TextView textView_show;
	private int cur_page = 0;
	private int type = 0;
	int list_size = 0;
	Calendar calendar = null;
	int year = 0;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			case -1:
				// 获取列表数据后
				Toast.makeText(getApplicationContext(), "您还未登录，请登录后操作", 0)
						.show();
				salary_lv.onRefreshComplete();
				if (list.size() < 1) {
					rel_show.setVisibility(View.VISIBLE);
					textView_show.setText("您还未登录，请登录后操作!");
				} else {
					rel_show.setVisibility(View.GONE);
				}
				break;
			case 0:
				Toast.makeText(getApplicationContext(), "网络链接失败", 0).show();
				salary_lv.onRefreshComplete();
				if (list.size() < 1) {
					rel_show.setVisibility(View.VISIBLE);
					textView_show.setText("网络链接失败!");
				} else {
					rel_show.setVisibility(View.GONE);
				}
				break;

			case 10:
				// 获取列表数据后
				getSalaryListData(cur_page, true);

				break;

			case 100:
				// 更多数据
				getSalaryListData(cur_page, false);
				// if (type == 0) {
				// System.out.println("刷新数据");
				// mAdapter.notifyDataSetChanged();
				// salary_lv.onRefreshComplete();
				// }
				// if (type == 3) {
				// salary_lv.onRefreshComplete();
				// }

				break;

			case 9:
				list_size = list.size();
				if (type == 0) {
					System.out.println("刷新数据");

					// Call onRefreshComplete when the list has been refreshed.
					salary_lv.onRefreshComplete();
				}
				if (type == 3) {
					salary_lv.onRefreshComplete();
					System.out.println("刷新数据成功");
					if (list.size() < 1) {
						rel_show.setVisibility(View.VISIBLE);
						textView_show.setText("暂无您的薪资数据!");
					} else {
						rel_show.setVisibility(View.GONE);
					}
				}
				if (list_size == 0) {
					ToastViewTools.initToast(SalaryQueryActivity.this,
							"未找到"+year+"年的数据,请您尝试上拉加载");
				}
				if (null != mPopLoginDialog)
					mPopLoginDialog.dismisPop();

				mAdapter.notifyDataSetChanged();
				
				break;
			}
		}

	};

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		setBackListener("薪资查询", false);

		mAdapter = new SalaryQueryListAdapter(SalaryQueryActivity.this, list);
		salary_lv.setAdapter(mAdapter);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		setBackListener(mTitle, false);

		salary_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(SalaryQueryActivity.this,
						SalaryDetailActivity.class);
				mIntent.putExtra("name", list.get(position - 1).get("name")
						.toString());
				mIntent.putExtra("date", list.get(position - 1).get("date")
						.toString());
				mIntent.putExtra("salary", list.get(position - 1).get("gz")
						.toString());
				startActivity(mIntent);

			}
		});
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jnwat.oamobilegy.BaseActivity#setView()
	 */
	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.salary_query_layout);

		calendar = Calendar.getInstance();
		year = (calendar.get(Calendar.YEAR));
		http = new HttpUtils();
		http.configTimeout(5000);
		http.configCurrentHttpCacheExpiry(1000); // 缓存时间
		if (null == list) {
			list = new ArrayList<Map<String, Object>>();
		}
		salary_lv = (PullToRefreshListView) findViewById(R.id.lv_salary);
		rel_show = (RelativeLayout) findViewById(R.id.rel_show);
		textView_show = (TextView) findViewById(R.id.textView_show);
		ModifySysTitle.ModifySysTitleStyle(R.color.title_blue,
				SalaryQueryActivity.this);

		// salary_lv.getRefreshableView().setDivider(null);
		salary_lv.getRefreshableView().setSelector(android.R.color.transparent);
		salary_lv.setMode(Mode.BOTH);
		salary_lv.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if (salary_lv.isHeaderShown()) {// 显示头部UI
					// 添加数据 网络请求
					list.clear();
					cur_page = 1;
					year = (calendar.get(Calendar.YEAR));
					mHandler.sendEmptyMessage(10);
				} else if (salary_lv.isFooterShown()) {// 显示底部UI
					// 加载更多
					cur_page++;
					year--;
					Log.e("yyyyyyyyyy", "year==" + year);
					mHandler.sendEmptyMessage(100);
				} else {
					// 网络请求
					LogUtils.d("初始化网络数据");
					list.clear();
					cur_page = 1;
					year = (calendar.get(Calendar.YEAR));
					mHandler.sendEmptyMessage(10);
				}

			}
		});
		// 设置自动刷新
		salary_lv.setRefreshing(true);

	}

	private void getSalaryListData(final int page, final boolean isClear) {
		// TODO Auto-generated method stub

		if (!BUserlogin.Username.equals("")) {

			RequestParams params = new RequestParams(); // 参数
			params.addBodyParameter("username", BUserlogin.Username);
			params.addBodyParameter("userflag", BUserlogin.Flag);
			params.addBodyParameter("yearly", year + "");

			String url = AppConfig.getIp(SalaryQueryActivity.this)
					+ AppConfig.METHOAD_SALARY_LIST;
			Log.e("地址===", "param==>>" + params + "    url===" + url
					+ "       year==" + year + "      BUserlogin.Flag"
					+ BUserlogin.Flag);
			http.send(HttpRequest.HttpMethod.POST, url, params,
					new RequestCallBack<String>() {
						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
						}

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							ASalaryQuery mASalaryList = new ASalaryQuery();
							LogUtils.d("薪资:" + responseInfo.result);
							type = mASalaryList.analySalaryList(
									responseInfo.result, list, isClear);
							LogUtils.d("共：" + list.size() + "项");
							int new_list_size = list.size();
							if (new_list_size > list_size) {// 如果有获取的数据
								cur_page++;
							}
							list_size = new_list_size;
							mHandler.sendEmptyMessageDelayed(9, 500);
						}

						@Override
						public void onStart() {

						}

						@Override
						public void onFailure(HttpException error, String msg) {
							LogUtils.e("错误信息:" + msg);
							mHandler.sendEmptyMessage(0);
						}
					});
		} else {

			mHandler.sendEmptyMessage(-1);
		}

	}

}
