package com.jnwat.swmobilegy.mainpager;

import org.json.JSONObject;

import android.content.Intent;
import android.text.Html;
import android.text.method.ArrowKeyMovementMethod;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jnwat.bean.BNoticeMessage;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.tools.ModifySysTitle;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class NotifyShowDetilsActivity extends BaseActivity {
	private TextView tv_notice_title, tv_notice_content, tv_notice_time,
			title_bar_name;
	private String intentstr;
	private boolean iskkaike;
	private BNoticeMessage mBNoticeMessage;
	Gson gson = null;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		tv_notice_title = (TextView) findViewById(R.id.tv_notice_title);
		title_bar_name = (TextView) findViewById(R.id.title_bar_name);
		tv_notice_content = (TextView) findViewById(R.id.tv_notice_content);
		tv_notice_time = (TextView) findViewById(R.id.tv_notice_time);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		tv_notice_title.setText(mBNoticeMessage.getTITLE());
		if (null == mBNoticeMessage.getCONTENT()) {
			tv_notice_content.setText("\u3000\u3000"+"暂无消息");
		} else {
			tv_notice_content.setText("\u3000\u3000"+Html.fromHtml(mBNoticeMessage
					.getCONTENT()));
		}

		tv_notice_time.setText(mBNoticeMessage.getSENDDATE() + "     " + " "
				+ mBNoticeMessage.getNOTICETYPE());
		// 设置消息为已读
		getDateByHttp();

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		gson = new Gson();
		setContentView(R.layout.activity_notifyshowdetils);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark, this);

		Intent intent = this.getIntent();
		try {
			intentstr = intent.getStringExtra("intentstr");

			mBNoticeMessage = gson.fromJson(intentstr, BNoticeMessage.class);
			setBackListener("通知公告详情", false);
			/*
			 * WorkeventFragment mWorkeventFragment
			 * =WorkeventFragment.newInstance("");
			 * BIntentObj.IsGetNewPushMessage = false;
			 * mWorkeventFragment.setMessageStage();
			 */

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private void getDateByHttp() {
		// TODO Auto-generated method stub

		http = new HttpUtils();
		http.configTimeout(8000);
		http.configCurrentHttpCacheExpiry(100); // 缓存时间
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("noticeid", mBNoticeMessage.getID());
		params.addBodyParameter("userid", BUserlogin.userid);
		String url = AppConfig.getIp(NotifyShowDetilsActivity.this)
				+ AppConfig.METHOAD_UPDATENOTICE;
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						try {
							String ReplyObject = new JSONObject(
									responseInfo.result)
									.getString("ReplyObject");
							System.out.println("ReplyObject.." + ReplyObject);

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
