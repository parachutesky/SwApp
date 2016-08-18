package com.jnwat.swmobilegy;

import java.util.Random;

import org.androidpn.client.Constants;
import org.androidpn.client.ServiceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.TextView;

import com.jnwat.analysis.AGetUserInfo;
import com.jnwat.bean.BAppVersion;
import com.jnwat.bean.BUserlogin;
import com.jnwat.bean.UserInfo;
import com.jnwat.config.AppConfig;
import com.jnwat.dialog.PopWindowShow;
import com.jnwat.swmobilegy.R;
import com.jnwat.service.UpdateAppService;
import com.jnwat.tools.IMEIiUtil;
import com.jnwat.tools.SharedPrefsUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class WelcomeActivity extends BaseActivity {
	private int DELAY_TIME_MILLISECOND = 1300;
	private BAppVersion bAppVersion;
	private PopWindowShow popWindowShow;
	public static ServiceManager serviceManager;
	private int Type = 0;// 0老师 1学生
	private String url = "";
	private TextView textView1;

	/**
	 * 默认登陆
	 */
	public void Prelogin(Context context) {
		UserInfo user = SharedPrefsUtil.getUserLogin(context);

		if (!user.getName().equals("") && !user.getPasswd().equals("")) {
			BUserlogin.NO = user.getName();
			BUserlogin.PASS = user.getPasswd();
			login();
		} else {
			// initSkip();
			if (!popWindowShow.isShowPopWindowd()) {
				// initSkip();
			}

		}
	}

	// 根据用户信息启动推送服务

	private void startPushService(Context mcontext) {
		UserInfo user = SharedPrefsUtil.getUserLogin(mcontext);
		if (!user.getName().equals("") || !user.getPasswd().equals("")) {
			// 不启动服务或者临时推送
			serviceManager = new ServiceManager(this, user.getName(),
					IMEIiUtil.getDeviceIMEI(mcontext));
			serviceManager.setNotificationIcon(R.drawable.icon);
			System.out.println("------启动 推送登录...-----");
			Constants.serviceManager = serviceManager;
			if (isNotificationEnabled()) {
				serviceManager.startService();
			}
		}

	}

	// if (isNotificationEnabled()) {
	private boolean isNotificationEnabled() {

		SharedPreferences sharedPrefs = getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		return sharedPrefs.getBoolean(Constants.SETTINGS_NOTIFICATION_ENABLED,
				true);
	}

	public void login() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				LogUtils.e("静默登录");
				// TODO Auto-generated method stub
				// BUserlogin userinfo
				// =SharedPrefsUtil.getUserLogin(MainActivity.this);
				http = new HttpUtils();
				http.configTimeout(1450);
				String NO = BUserlogin.NO;
				String paw = BUserlogin.PASS;
				RequestParams params = new RequestParams(); // 参数
				params.addBodyParameter("LoginName", NO.trim());
				params.addBodyParameter("Password", paw.trim());
				if (Type == 0) {// 判断类型 0 老师
					url = AppConfig.getIp(WelcomeActivity.this)
							+ AppConfig.METHOAD_GETUSERINFO_TEACHER;

				} else {
					url = AppConfig.getIp(WelcomeActivity.this)
							+ AppConfig.METHOAD_GETUSERINFO_STUDENT;
				}

				http.send(HttpRequest.HttpMethod.POST, url, params,
						new RequestCallBack<String>() {
							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {

							}

							@Override
							public void onSuccess(
									ResponseInfo<String> responseInfo) {
								LogUtils.d("LOGINresponseInfo.result:"
										+ responseInfo.result);
								AGetUserInfo mAGetUserInfo = new AGetUserInfo();
								mAGetUserInfo.analysisUserLogin(
										responseInfo.result, new Handler());

							}

							@Override
							public void onStart() {
							}

							@Override
							public void onFailure(HttpException error,
									String msg) {
								// if (!popWindowShow.isShowPopWindowd()) {
								// }

							}
						});
			}

		}).start();
	}

	/**
	 * 延迟操作
	 */
	public void initSkip() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,
						MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);// 跳转动画
				finish();
			}
		}, DELAY_TIME_MILLISECOND);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		try {
			String result = data.getExtras().getString("result");// 得到新Activity
			// 关闭后返回的数据
			if (result.equals("1")) {
				setContentView(R.layout.activity_welcome);
				if (!popWindowShow.isShowPopWindowd()) {
					initSkip();
				}

			} else {
				finish();
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		/*
		 * if (!SharedPrefsUtil.getGurder_Once(this)) {// 第一次打开程序
		 * startActivityForResult(new Intent(WelcomeActivity.this,
		 * GuiderActivity.class), 1); } else {// 第二次及以后
		 * setContentView(R.layout.activity_welcome); Prelogin(this);
		 * 
		 * }
		 */
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		// APP版本升级
		startPushService(WelcomeActivity.this);
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		http = new HttpUtils();
		if (popWindowShow == null) {
			popWindowShow = new PopWindowShow();
		} else {
		}
		if (!SharedPrefsUtil.getGurder_Once(this)) {// 第一次打开程序
			startActivityForResult(new Intent(WelcomeActivity.this,
					GuiderActivity.class), 1);
		} else {// 第二次及以后
			setContentView(R.layout.activity_welcome);
			textView1 = (TextView) findViewById(R.id.textView1);
			try {
				// ---get the package info---
				PackageManager pm = WelcomeActivity.this.getPackageManager();
				PackageInfo pi = pm.getPackageInfo(
						WelcomeActivity.this.getPackageName(), 0);
				String versionName = pi.versionName;
				textView1.setText("当前版本:  " + versionName);
			} catch (Exception e) {
			}

			// getAppVersionInfo();
			Prelogin(WelcomeActivity.this);
			initSkip();

		}
		// 设置IP
		String ip = SharedPrefsUtil.getSETTINGIP(WelcomeActivity.this);
		System.out.println("===" + AppConfig.WEB_IP_PROT);
	}

	/**
	 * handler处理消息机制
	 */
	protected Handler uiHandler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 104:// APP下载更新
				// 网络类型判断，询问是否使用流量下载,通知文件大小

				Intent updateIntent = new Intent(WelcomeActivity.this,
						UpdateAppService.class);

				startService(updateIntent);

				break;
			case 110:
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		WelcomeActivity.this.finish();
		return super.onKeyDown(keyCode, event);
	}

}
