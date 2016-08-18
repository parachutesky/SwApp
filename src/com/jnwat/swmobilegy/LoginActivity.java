package com.jnwat.swmobilegy;

import org.androidpn.client.Constants;
import org.androidpn.client.ServiceManager;
import org.androidpn.client.XmppManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.analysis.AGetUserInfo;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BUserlogin;
import com.jnwat.bean.UserInfo;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.dialog.Popup_LoginType;
import com.jnwat.dialog.Popup_LoginType.OnItemOnClickListener;
import com.jnwat.swmobilegy.view.ActionItem;
import com.jnwat.tools.IMEIiUtil;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.SharedPrefsUtil;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 登录
 * 
 */
public class LoginActivity extends BaseActivity {
	String str_loginname;
	String str_loginpwd;
	EditText et_loginname;
	EditText et_passwd;
	TextView tv_forget_pwd, EditText_type;
	Button btn_login;
	private int Type = 0;// 0老师 1学生
	private String url = "";
	private Popup_LoginType mPopup_LoginType;
	public ServiceManager serviceManager;
	private TextView tv_modifyip;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		serviceManager = Constants.serviceManager;
		et_loginname = (EditText) findViewById(R.id.et_loginname);
		et_passwd = (EditText) findViewById(R.id.et_passwd);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_forget_pwd = (TextView) findViewById(R.id.tv_forget_pwd);
		EditText_type = (TextView) findViewById(R.id.EditText_type);
		tv_modifyip = (TextView) findViewById(R.id.tv_modifyip);

		UserInfo user = SharedPrefsUtil.getUserLogin(LoginActivity.this);

		if (!user.getName().equals("")) {
			et_loginname.setText(user.getName());

		}
		http.configTimeout(2500);
		if (SharedPrefsUtil.getLogin(LoginActivity.this)) {
			et_loginname.setText(SharedPrefsUtil
					.getUserNumInfo(LoginActivity.this).NO);
		}

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		//修改IP
		tv_modifyip.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showIpModifyDialog();
			}
		});
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (null != Constants.notificationManager) {
					Constants.notificationManager.cancel(0);
					if(null!=BIntentObj.list_PushMessage)
						BIntentObj.list_PushMessage.clear();
				}
				BIntentObj.recover();
				// TODO Auto-generated method stub
				/*
				 * if (null != serviceManager) { serviceManager.stopService(); }
				 */
				if (Type == 1) {// 判断类型 0 老师
					url = AppConfig.getIp(LoginActivity.this)
							+ AppConfig.METHOAD_GETUSERINFO_STUDENT;
				} else {
					url = AppConfig.getIp(LoginActivity.this)
							+ AppConfig.METHOAD_GETUSERINFO_TEACHER;
					
					System.out.println(url);
				}

				if (isCanLogin()) {
					mPopLoginDialog.showPoploginDialog(LoginActivity.this);

					if (null != Constants.xmppManager
							&& BUserlogin.set_intent_login) {
						Constants.xmppManager.disconnect();
					}

					RequestParams params = new RequestParams(); // 参数
					// params.addHeader("Content-Type",
					// "application/x-www-form-urlencoded");
					str_loginname = et_loginname.getText().toString().trim();
					str_loginpwd = et_passwd.getText().toString().trim();
					params.addBodyParameter("LoginName", str_loginname);
					params.addBodyParameter("Password", str_loginpwd);
					System.out.println("登录地址:" + url);
					http.send(HttpRequest.HttpMethod.POST, url, params,
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
											responseInfo.result, mHandler);
									closePop();
									BUserlogin.NO = str_loginname;
									BUserlogin.PASS = str_loginpwd;

									if (BUserlogin.loginStatus == 1) {
										UserInfo user = new UserInfo();
										user.setName(BUserlogin.NO);
										user.setPasswd(BUserlogin.PASS);
										System.out.println("保存用户信息");
										// 保存用户名和密码
										SharedPrefsUtil.putUserLogin(
												LoginActivity.this, user);
										BIntentObj.recover();
										startPushService(LoginActivity.this,
												BUserlogin.Account);
										BIntentObj.IsModifyUi = true;
										finish();
									} else {
										ToastViewTools.initToast(
												LoginActivity.this,
												"用户密码不正确或者网络错误!");
									}

								}

								@Override
								public void onStart() {
								}

								@Override
								public void onFailure(HttpException error,
										String msg) {
									ToastViewTools.initToast(
											LoginActivity.this,
											ShowRemind.LOGIN_ERROR);

									BUserlogin.loginStatus = 0;
									// mApp.recover();
									// finish();
									closePop();

								}
							});

				} else {
					ToastViewTools.initToast(LoginActivity.this,
							ShowRemind.LOGIN_FORMERROR);
				}
			}

		});

		tv_forget_pwd.setOnClickListener(new OnClickListener() {// 忘记密码

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ToastViewTools.initToast(LoginActivity.this, "请咨询管理人员");
					}
				});

		EditText_type.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPopup_LoginType.show(v);
			}
		});
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mPopLoginDialog = new PopLoginDialog();
	}

	// 根据用户信息启动推送服务

	private void startPushService(Context mcontext, String user) { //
		// 不启动服务或者临时推送
		if (null == serviceManager) {
			serviceManager = new ServiceManager(LoginActivity.this,
					BUserlogin.NO, IMEIiUtil.getDeviceIMEI(LoginActivity.this));
			serviceManager.setNotificationIcon(R.drawable.icon);
			Constants.serviceManager = serviceManager;
			if (isNotificationEnabled()) {
				serviceManager.startService();
			}
		} else {
			if (BUserlogin.set_intent_login) {
				System.out.println("重连");
				Constants.xmppManager.setUsername(BUserlogin.NO);
				Constants.xmppManager.setPassword(IMEIiUtil
						.getDeviceIMEI(LoginActivity.this));
				if (isNotificationEnabled()) {
					Constants.xmppManager.connect();
				}
				BUserlogin.set_intent_login = false;
			}

		}
	}

	private OnItemOnClickListener onitemClick = new OnItemOnClickListener() {

		@Override
		public void onItemClick(ActionItem item, int position) {
			// mLoadingDialog.show();
			switch (position) {
			case 0:// 老师
				Type = 0;
				SharedPrefsUtil.putType(LoginActivity.this, Type);
				EditText_type.setText("老师");
				break;
			case 1:// 学生
				Type = 1;
				SharedPrefsUtil.putType(LoginActivity.this, Type);
				EditText_type.setText("学生");
				break;
			default:
				break;
			}
		}
	};

	private void initPopWindow() {
		// 实例化标题栏弹窗
		mPopup_LoginType = new Popup_LoginType(LoginActivity.this,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		mPopup_LoginType.setItemOnClickListener(onitemClick);
		// 给标题栏弹窗添加子类
		mPopup_LoginType.addAction(new ActionItem(LoginActivity.this, "老师",
				R.drawable.login_icon_teacher));
		mPopup_LoginType.addAction(new ActionItem(LoginActivity.this, "学生",
				R.drawable.login_icon_student));
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				LoginActivity.this);
		setContentView(R.layout.activity_login);
		initPopWindow();
		setTitleListener("登录", "");
		http = new HttpUtils();
		http.configTimeout(2500);
		http.configCurrentHttpCacheExpiry(1500); // 缓存时间

	}

	/**
	 * 判断是否能登录
	 * 
	 * @return
	 */
	private boolean isCanLogin() {

		if (et_loginname.getText().toString().trim().equals("")
				|| et_passwd.getText().toString().trim().equals("")) {
			ToastViewTools.initToast(LoginActivity.this, "用户名密码不能为空");
			return false;
		}
		return true;

	}

	/**
	 * 将用户信息填写入 sharedPreference 进行记录
	 */
	public void noteUserInfo(Object obj) {
		// SharedPrefsUtil.putUserLogin(this,obj);
	}

	/**
	 * 设置返回Title的返回监听
	 * 
	 * @param view
	 */
	public void setTitleListener(String title, String register) {
		TextView title_bar_register = (TextView) findViewById(R.id.title_bar_register);
		ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title_bar_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				ToastViewTools.initToast(LoginActivity.this, "注册");
			}
		});
		TextView title_bar_name = (TextView) findViewById(R.id.title_bar_name);
		title_bar_name.setText(title);
		title_bar_register.setText(register);
	}

	// if (isNotificationEnabled()) {
	private boolean isNotificationEnabled() {

		SharedPreferences sharedPrefs = getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		return sharedPrefs.getBoolean(Constants.SETTINGS_NOTIFICATION_ENABLED,
				true);
	}
	
	public void showIpModifyDialog(){
		LayoutInflater factory = LayoutInflater.from(LoginActivity.this);// 提示框
		final View view = factory.inflate(
				R.layout.dialogip_editbox_layout, null);// 这里必须是final的
		final EditText edit = (EditText) view
				.findViewById(R.id.editText);// 获得输入框对象

		AlertDialog.Builder builder;
		if (android.os.Build.VERSION.SDK_INT > 11) {
			builder = new AlertDialog.Builder(LoginActivity.this,
					AlertDialog.THEME_HOLO_LIGHT); // 先得到构造器
		} else {
			builder = new AlertDialog.Builder(LoginActivity.this); // 先得到构造器
		}

		builder.setTitle("当前IP:" + AppConfig.getIp(LoginActivity.this))
				// 提示框标题
				.setView(view)
				.setPositiveButton(
						"修改",// 提示框的两个按钮
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 事件
								String ip = edit.getText().toString()
										.trim();
								if (ip.equals("")) {
									com.jnwat.tools.ToastViewTools
											.initToast(LoginActivity.this,
													"请输入IP");
								} else {
									com.jnwat.tools.SharedPrefsUtil
											.putSETTINGIP(
													LoginActivity.this,
													"http://" + ip
															+ ":");
									AppConfig.WEB_IP_PROT = "http://"
											+ ip + ":"
											+ AppConfig.WEBPROT + "/"
											+ AppConfig.WEBSERVICE_SW;
									com.jnwat.tools.ToastViewTools
											.initToast(LoginActivity.this,
													"修改完成");
									System.out
											.println("  AppConfig.WEB_IP_PROT--"
													+ AppConfig.WEB_IP_PROT);
								}

							}
						}).setNegativeButton("取消", null).create()

				.show();
	}

}
