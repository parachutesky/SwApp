package com.jnwat.swmobilegy.mainpager.notify;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.dialog.DateTimePickDialogUtil;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
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
 * @author chang-zhiyuan 编辑通知公告
 */
public class AddNotiftActivity extends BaseActivity {
	private TextView tv_readmember, tv_notify_type, tv_databegin, tv_dataend;
	private EditText et_notifytitle, et_notifycontent;
	private Button btn_notify_send;
	private String accountList = "";//

	@Override
	protected void initView() {
		mPopLoginDialog = new PopLoginDialog();

		// TODO Auto-generated method stub
		tv_readmember = (TextView) findViewById(R.id.tv_readmember);
		tv_notify_type = (TextView) findViewById(R.id.tv_notify_type);
		tv_databegin = (TextView) findViewById(R.id.tv_databegin);
		tv_dataend = (TextView) findViewById(R.id.tv_dataend);
		et_notifytitle = (EditText) findViewById(R.id.et_notifytitle);
		et_notifycontent = (EditText) findViewById(R.id.et_notifycontent);
		btn_notify_send = (Button) findViewById(R.id.btn_notify_send);
		tv_databegin.setText(getCrrentTime());
		tv_dataend.setText(getCrrentTime());

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

		// 提交
		btn_notify_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				verInfo();
			}
		});
		// 类型
		tv_readmember.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * Intent intent = new Intent();
				 * intent.setClass(AddNotiftActivity.this,
				 * SeclectPeopleActivity.class); startActivityForResult(intent,
				 * 1000);
				 */
				Intent intent = new Intent();
				intent.setClass(AddNotiftActivity.this,
						GongGaoReadActvity.class);
				startActivityForResult(intent, 100);

			}
		});
		tv_databegin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
						AddNotiftActivity.this, getCrrentTime());
				dateTimePicKDialog.dateTimePicKDialog(tv_databegin);

			}
		});
		// 结束时间
		tv_dataend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
						AddNotiftActivity.this, getCrrentTime());
				dateTimePicKDialog.dateTimePicKDialog(tv_dataend);

			}
		});

	}

	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 根据上面发送过去的请求吗来区别
		System.out.println("resultCode" + resultCode);
		switch (resultCode) {
		case 100:
			accountList = data.getStringExtra("data_account");
			String data_name = data.getStringExtra("data_name");
			;
			System.out.println("data_account.." + accountList);
			System.out.println("data_name.." + data_name);

			tv_readmember.setText(data_name);

			break;

		default:
			/*
			 * if (BIntentObj.UserInfos.size() == 1) {
			 * tv_readmember.setText(String.valueOf(BIntentObj.UserInfos
			 * .get(0).getName())); StringBuffer accountList_sb = new
			 * StringBuffer();
			 * accountList_sb.append(String.valueOf(BIntentObj.UserInfos
			 * .get(0).getAccount())); accountList = accountList_sb.toString();
			 * } else { tv_readmember.setText(showMessgaePeople());
			 * 
			 * }
			 */
			break;
		}
	}

	/**
	 * 显示人员信息
	 */
	private String showMessgaePeople() {
		StringBuffer sb = new StringBuffer();
		StringBuffer accountList_sb = new StringBuffer();

		if (null != BIntentObj.UserInfos && BIntentObj.UserInfos.size() > 0) {
			int lenth = BIntentObj.UserInfos.size();
			for (int i = 0; i < lenth - 1; i++) {
				sb.append(String.valueOf(BIntentObj.UserInfos.get(i).getName()
						+ ","));
				accountList_sb.append(String.valueOf(BIntentObj.UserInfos
						.get(i).getAccount() + ","));
			}
			sb.append(String.valueOf(BIntentObj.UserInfos.get(lenth - 1)
					.getName()));
			accountList_sb.append(String.valueOf(BIntentObj.UserInfos.get(
					lenth - 1).getAccount()));
		} else {

		}

		this.accountList = accountList_sb.toString();
		return sb.toString();

	}

	/**
	 * 得到当前的时间
	 */
	private String getCrrentTime() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				AddNotiftActivity.this);
		setContentView(R.layout.activity_addnotify);

		setBackListener("新增公告", false);

	}

	/**
	 * 发送人
	 */
	private void sendNotifyMessage() {
		mPopLoginDialog.showPoploginDialog(AddNotiftActivity.this);
		// TODO Auto-generated method stub
		http = new HttpUtils();
		http.configTimeout(5000);
		http.configCurrentHttpCacheExpiry(1000); // 缓存时间
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("title", et_notifytitle.getText().toString()
				.trim());
		params.addBodyParameter("Userid", BUserlogin.userid);
		params.addBodyParameter("touser", accountList);

		params.addBodyParameter("st", "1");
		params.addBodyParameter("bdate", tv_databegin.getText().toString()
				.trim());
		params.addBodyParameter("edate", tv_dataend.getText().toString().trim());
		params.addBodyParameter("content", et_notifycontent.getText()
				.toString().trim());

		System.out
				.println("..." + et_notifycontent.getText().toString().trim());
		// params.addBodyParameter("monthly", date);

		String url = AppConfig.getIp(AddNotiftActivity.this)
				+ AppConfig.METHOAD_TRAINING_SAVENOTICE;
		Log.e("地址===", "param==>>" + params + "url===" + url);
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						mPopLoginDialog.dismisPop();
						System.out.println(responseInfo.result);
						try {
							JSONObject jbo = new JSONObject(responseInfo.result);

							String Status = jbo.getString("Status");
							if (Status.equals("200")) {
								ToastViewTools.initToast(
										AddNotiftActivity.this,
										jbo.getString("Message"));
							} else {
								ToastViewTools.initToast(
										AddNotiftActivity.this,
										jbo.getString("Message"));
							}

							finish();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							ToastViewTools.initToast(AddNotiftActivity.this,
									"网络访问异常");
						}
					}

					@Override
					public void onStart() {

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						LogUtils.e("错误信息:" + msg);
						mPopLoginDialog.dismisPop();
					}
				});

	}

	/**
	 * 验证数据是否正确
	 */
	private void verInfo() {
		System.out.println("accountList.." + accountList);
		/*
		 * private TextView tv_readmember, tv_notify_type, tv_databegin,
		 * tv_dataend; private EditText et_notifytitle, et_notifycontent;
		 */
		if (tv_readmember.getText().toString().trim().length() < 1) {
			ToastViewTools.initToast(AddNotiftActivity.this, "请选择阅读人员");
			return;
		}
		if (et_notifytitle.getText().toString().trim().length() < 1) {
			ToastViewTools.initToast(AddNotiftActivity.this, "请输入标题");
			return;
		}

		if (tv_databegin.getText().toString().trim().length() < 1) {
			ToastViewTools.initToast(AddNotiftActivity.this, "请选择有效开始时间");
			return;
		}

		if (tv_dataend.getText().toString().trim().length() < 1) {
			ToastViewTools.initToast(AddNotiftActivity.this, "请选择有效结束时间");
			return;
		}
		if (et_notifycontent.getText().toString().trim().length() < 1) {
			ToastViewTools.initToast(AddNotiftActivity.this, "请输入内容信息");
			return;
		}

		String s1 = tv_databegin.getText().toString().trim();
		String s2 = tv_dataend.getText().toString().trim();
		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm");
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		java.util.Calendar c2 = java.util.Calendar.getInstance();
		try {
			c1.setTime(df.parse(s1));
			c2.setTime(df.parse(s2));
		} catch (java.text.ParseException e) {
			System.err.println("格式不正确");
		}
		int result = c1.compareTo(c2);
		if (result == 0)
			System.out.println("c1相等c2");
		else if (result < 0)
			System.out.println("c1小于c2");
		else {
			ToastViewTools.initToast(AddNotiftActivity.this, "有效结束时间选择不正确");
			return;
		}

		// 进行网络访问
		sendNotifyMessage();
	}

}
