package com.jnwat.swmobilegy.mainpager;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jnwat.analysis.ASearchSchedule;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.BaseActivity;
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
 * @author chang-zhiyuan 课表详情查询
 */
public class SearchScheduleDetailActivity extends BaseActivity {
	int type = 0;
	String projectid = "";
	String err;
	LinearLayout lin_sw_schedule_title;
	TextView tv_sw_schedule_name, tv_sw_schedule_name_dec;// title
	TextView tv_sw_schedule_class_nub, tv_sw_schedule_class_time,
			tv_sw_schedule_class_date, tv_sw_schedule_class_add,
			textView_sw_item_schedul_content;// CONTENT
	String str_tv_sw_schedule_name, str_tv_sw_schedule_name_dec;// title
	String str_tv_sw_schedule_class_nub, str_tv_sw_schedule_class_time,
			str_tv_sw_schedule_class_date, str_tv_sw_schedule_class_add,
			str_textView_sw_item_schedul_content;// CONTENT

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		setBackListener("课表详情", false);

		lin_sw_schedule_title = (LinearLayout) findViewById(R.id.lin_sw_schedule_title);
		tv_sw_schedule_name = (TextView) findViewById(R.id.tv_sw_schedule_name);
		tv_sw_schedule_name_dec = (TextView) findViewById(R.id.tv_sw_schedule_name_dec);
		tv_sw_schedule_class_nub = (TextView) findViewById(R.id.tv_sw_schedule_class_nub);
		tv_sw_schedule_class_time = (TextView) findViewById(R.id.tv_sw_schedule_class_time);
		tv_sw_schedule_class_date = (TextView) findViewById(R.id.tv_sw_schedule_class_date);
		tv_sw_schedule_class_add = (TextView) findViewById(R.id.tv_sw_schedule_class_add);
		textView_sw_item_schedul_content = (TextView) findViewById(R.id.textView_sw_item_schedul_content);

		Intent intent = getIntent();
		try {
			projectid = intent.getStringExtra("projectid");
		} catch (Exception e) {
			// TODO: handle exception
			projectid = "";
		}

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		getSearchScheduleDetail(projectid);

		lin_sw_schedule_title.setVisibility(View.INVISIBLE);
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		http = new HttpUtils();
		http.configTimeout(5000);
		http.configCurrentHttpCacheExpiry(1000); // 缓存时间
		setContentView(R.layout.activity_searchscheduledetail);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				SearchScheduleDetailActivity.this);
	}

	/**
	 * 得到课程详情信息
	 * 
	 * @param projectid
	 *            项目ID
	 * @param userid
	 */
	private void getSearchScheduleDetail(String projectid) {
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("projectid", projectid);
		params.addBodyParameter("userid",
				BUserlogin.userid);
		String url = AppConfig.getIp(SearchScheduleDetailActivity.this)
				+ AppConfig.METHOAD_SEARCHSCHEDULEDETAIL;
		LogUtils.d("获取课程详细:" + url + "projectid:" + projectid + "userid"
				+ BUserlogin.userid);

		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d("获取课程详细信息:" + responseInfo.result);
						JSONObject jsonobject;
						try {
							jsonobject = new JSONObject(responseInfo.result);

							err = jsonobject.get("Message").toString().trim();// 错误提示信息

							int Status = jsonobject.getInt("Status");
							// ShowRemind.HANDLER_STATUS = Status;
							if (0 != Status && Status == 200) {// 如果拿到的值是正确的继续解析
								JSONObject msonobject = jsonobject
										.getJSONObject("ReplyObject");
								str_textView_sw_item_schedul_content = msonobject
										.getString("Coursecontent");
								str_tv_sw_schedule_name = msonobject
										.getString("Course");
								str_tv_sw_schedule_name_dec = msonobject
										.getString("Projectname");
								str_tv_sw_schedule_class_nub = msonobject
										.getString("Code");
								
								str_tv_sw_schedule_class_date= msonobject
										.getString("Classdate");
								str_tv_sw_schedule_class_time = msonobject
										.getString("Sxw");
								str_tv_sw_schedule_class_add = msonobject
										.getString("Classroom");

								textView_sw_item_schedul_content
										.setText(str_textView_sw_item_schedul_content);
								tv_sw_schedule_name
										.setText(str_tv_sw_schedule_name);
								tv_sw_schedule_name_dec
										.setText(str_tv_sw_schedule_name_dec);
								tv_sw_schedule_class_nub
										.setText(str_tv_sw_schedule_class_nub);
								tv_sw_schedule_class_time
										.setText(str_tv_sw_schedule_class_time);
								tv_sw_schedule_class_date
										.setText(str_tv_sw_schedule_class_date);
								tv_sw_schedule_class_add
										.setText(str_tv_sw_schedule_class_add);

								lin_sw_schedule_title
										.setVisibility(View.VISIBLE);

							} else {// 解析错误编码，提示用户
								mHandler.sendEmptyMessage(ShowRemind.HANDLER_ANALYSIS_ERROE);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							mHandler.sendEmptyMessage(ShowRemind.HANDLER_ANALYSIS_ERROE);
							e.printStackTrace();
						}

					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						ToastViewTools.initToast(
								SearchScheduleDetailActivity.this, "暂无课程信息");

					}
				});

	}

	// 消息处理
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				ToastViewTools.initToast(SearchScheduleDetailActivity.this,
						"暂无数据");
				break;
			case ShowRemind.HANDLER_ANALYSIS_ERROE:
				ToastViewTools
						.initToast(SearchScheduleDetailActivity.this, err);
				break;
			case 9:

				break;
			case 10:// 自动加载数据
				break;
			case ShowRemind.HANDLER_STATUS:
				ToastViewTools.initToast(SearchScheduleDetailActivity.this,
						ShowRemind.ErrorMessage);
				break;
			}
		};
	};

}
