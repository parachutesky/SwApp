package com.jnwat.swmobilegy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jnwat.analysis.AGetUserInfo;
import com.jnwat.bean.BAppVersion;
import com.jnwat.bean.BUserlogin;
import com.jnwat.bean.UserInfo;
import com.jnwat.config.AppConfig;
import com.jnwat.dialog.PopWindowShow;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.mail.Fragment_Friends;
import com.jnwat.swmobilegy.mainpager.MainPageFragment;
import com.jnwat.swmobilegy.set.SetFragment;
import com.jnwat.swmobilegy.workevent.WorkeventFragment;
import com.jnwat.tools.GetNewAppVersion;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.SharedPrefsUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * Created by Parachute on 14/12/12. 控制程序的主要界面
 */
@SuppressLint({ "InlinedApi", "ResourceAsColor" })
public class MainActivity extends FragmentActivity {
	private GetNewAppVersion mGetNewAppVersion;
	private BAppVersion bAppVersion;
	private boolean isLogin = false;
	// 定义FragmentTabHost对象
	private FragmentTabHost mTabHost;
	private PopWindowShow popWindowShow;
	// 定义一个布局
	private LayoutInflater layoutInflater;
	private int Type = 0;// 0老师 1学生
	private String url = "";

	
	private Class fragmentArray[] = {MainPageFragment.class, WorkeventFragment.class
			, Fragment_Friends.class, SetFragment.class };
	
	// Tab选项卡的文字
	private String mTextviewArray[] = { "首页","工作流程", "通讯录", "设置" };
	private HttpUtils http;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ModifySysTitle.ModifySysTitleStyle(R.color.title_blue,
				MainActivity.this);
		
		http = new HttpUtils();
		http.configTimeout(1500);
		if (popWindowShow == null) {
			popWindowShow = new PopWindowShow();
		}
		initView();
		if (BUserlogin.loginStatus == 0) {
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			this.startActivity(intent);
		}
		
		//
		/*SharedPreferences s =PreferenceManager.getDefaultSharedPreferences(this);
		Boolean str =s.getBoolean("ck_message_set",false);
		if(str){
			Toast.makeText(this,"true", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this,"false", Toast.LENGTH_LONG).show();
		}*/
		
		
		//版本升级
		mGetNewAppVersion = new GetNewAppVersion(bAppVersion, MainActivity.this, popWindowShow);
		mGetNewAppVersion.getAppVersionInfo(http);
		
		/*
		 * UserInfo user =SharedPrefsUtil.getUserLogin(this);
		 * if(!"".equals(user.getName())){ BUserlogin.NO = user.getName();
		 * 
		 * BUserlogin.PASS = user.getPasswd(); if(BUserlogin.PASS.equals("")){
		 * BUserlogin.PASS ="123456"; } login(); }else{ Intent intent = new
		 * Intent(); intent.setClass(this,LoginActivity.class);
		 * this.startActivity(intent); }
		 */
		/*
		 * if(!SharedPrefsUtil.getLogin(this)){ Intent intent = new Intent();
		 * intent.setClass(this,LoginActivity.class);
		 * this.startActivity(intent); }else{ login(); }
		 */
		
		//版本升级的方法
		
		System.out.println(">>>>>>Mainactivity--Oncreate");

	}

	// 调用登录方法登录
	public void login() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				// BUserlogin userinfo
				// =SharedPrefsUtil.getUserLogin(MainActivity.this);
				if (Type == 0) {// 判断类型 0 老师
					url = AppConfig.getIp(MainActivity.this)+AppConfig.METHOAD_GETUSERINFO_STUDENT;
				} else {
					url = AppConfig.getIp(MainActivity.this)+AppConfig.METHOAD_GETUSERINFO_TEACHER;
				}
				String NO = BUserlogin.NO;
				String paw = BUserlogin.PASS;
				RequestParams params = new RequestParams(); // 参数
				// params.addHeader("Content-Type",
				// "application/x-www-form-urlencoded");
				params.addBodyParameter("userName", NO.trim());
				params.addBodyParameter("pwd", paw.trim());

				http.send(HttpRequest.HttpMethod.POST,
						url, params,
						new RequestCallBack<String>() {
							@Override
							public void onLoading(long total, long current,
									boolean isUploading) {

							}

							@Override
							public void onSuccess(
									ResponseInfo<String> responseInfo) {
								LogUtils.d("responseInfo.result:"
										+ responseInfo.result);
								AGetUserInfo mAGetUserInfo = new AGetUserInfo();
								mAGetUserInfo.analysisUserLogin(
										responseInfo.result, new Handler());
								if (BUserlogin.loginStatus == 1) {
									UserInfo user = new UserInfo();
									user.setName(BUserlogin.NO);
									user.setName(BUserlogin.PASS);
									SharedPrefsUtil.putUserLogin(
											MainActivity.this, user);
								}

							}

							@Override
							public void onStart() {
							}
 
							@Override
							public void onFailure(HttpException error,
									String msg) {
								noteUserInfo(new BUserlogin());
								Intent intent = new Intent();
								intent.setClass(MainActivity.this,
										LoginActivity.class);
								startActivity(intent);

							}
						});
			}

		}).start();

	}

	// 定义数组来存放按钮图片
	private int mImageViewArray[] = { R.drawable.tab_workevent,
			R.drawable.tab_news, R.drawable.tab_addresslist, R.drawable.tab_set };

	/*
	 * 初始化组件
	 */
	private void initView() {
		// 实例化布局对象
		layoutInflater = LayoutInflater.from(this);

		// 实例化TabHost对象，得到TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		// 得到fragment的个数
		int count = fragmentArray.length;
		for (int i = 0; i < count; i++) {
			// 为每一个Tab按钮设置图标、文字和内容
			TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTextviewArray[i])
					.setIndicator(getTabItemView(i));
			// 将Tab按钮添加进Tab选项卡中
			mTabHost.addTab(tabSpec, fragmentArray[i], null);
			// 设置Tab按钮的背景
			// mTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
		}

	}

	/**
	 * 给Tab按钮设置图标和文字
	 */
	private View getTabItemView(int index) {
		View view = layoutInflater.inflate(R.layout.tab_item_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview_tab);
		imageView.setImageResource(mImageViewArray[index]);
		TextView textView = (TextView) view.findViewById(R.id.textview_tab);
		textView.setText(mTextviewArray[index]);
		return view;
	}

	/**
	 * 将用户信息填写入 sharedPreference 进行记录
	 */
	public void noteUserInfo(Object user) {
		// SharedPrefsUtil.putUserLogin(this,user);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println(">>>>>>MainActivity--Onresume");
	}

	/*
	 * @Override public void onBackPressed() { // TODO Auto-generated method
	 * stub super.onBackPressed(); BIntentObj.IsGetOverProcess = false;
	 * BIntentObj.IsGetProcess = false; BIntentObj.IsGetSendProcess = false; }
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			if (popWindowShow != null) {
				if (popWindowShow.isShowPopWindowd()) {
					popWindowShow.popWindowdismiss();
					return false;
				} 
				finish();
				System.exit(0);
				return false;
			}
			
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}