package com.jnwat.swmobilegy;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.config.ShowRemind;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.swmobilegy.R;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;

/**
 * @author chang-zhiyuan
 * 
 */
public abstract class BaseActivity extends Activity {
	private ImageView iv_back = null;
	private ImageView iv_message = null;
	private TextView title_bar_name = null;
	protected HttpUtils http = null;
	protected PopLoginDialog mPopLoginDialog;

	/*
	 * private int HANDLER_NET_ERROE = 0;// 网络错误 private int
	 * HANDLER_ANALYSIS_ERROE = 1;// 解析错误 private int HANDLER_DELER_ERROE = 2;//
	 * 超时 private int HANDLER_LOGIN_SUCCESS = 3; private int HANDLER_GET_SUCCESS
	 * = 4; private int HANDLER_SHOW_DIALOG = 5; private int
	 * HANDLER_DISMISS_DIALOG = 6;
	 */
	/**
	 * 关闭Pop
	 */
	protected void closePop() {
		mPopLoginDialog.dismisPop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView();
		initView();
		initData();
		setListener();

	}

	/**
	 * 初始化
	 */
	protected abstract void initView();

	/**
	 * 设置监听器
	 */
	protected abstract void setListener();

	/**
	 * 初始化数据
	 */
	protected abstract void initData();

	/**
	 * 设置视图
	 */
	protected abstract void setView();

	// public void doBack(View view) {
	// super.onBackPressed();
	// }

	/**
	 * 设置返回Title的返回监听
	 * 
	 * @param view
	 */
	public void setBackListener(String title, boolean isshow) {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title_bar_name = (TextView) findViewById(R.id.title_bar_name);
		title_bar_name.setText(title);

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ShowRemind.HANDLER_NET_ERROE:
				ToastViewTools.initToast(BaseActivity.this,
						ShowRemind.LOGIN_NETERROR);
				break;

			case ShowRemind.HANDLER_ANALYSIS_ERROE:
				ToastViewTools.initToast(BaseActivity.this,
						ShowRemind.ANALYSIS_ERROE);
				break;

			case ShowRemind.HANDLER_DELER_ERROE:
				break;

			case ShowRemind.HANDLER_LOGIN_SUCCESS:
				break;
			case ShowRemind.HANDLER_GET_SUCCESS:
				break;

			case ShowRemind.HANDLER_SHOW_DIALOG:
				break;
			case ShowRemind.HANDLER_DISMISS_DIALOG:
				break;
			case ShowRemind.HANDLER_DB_ERROR:
				ToastViewTools
						.initToast(BaseActivity.this, ShowRemind.DB_ERROR);
				break;
			case ShowRemind.HANDLER_STATUS: // 网络的错误代码 400

				ToastViewTools.initToast(BaseActivity.this,
						ShowRemind.ErrorMessage);
				break;
			case ShowRemind.HANDLER_NET_TYPE:// 网络类型
				ToastViewTools
						.initToast(BaseActivity.this, ShowRemind.NET_TYPE);
				break;
			case 10000:// 登录失败
				ToastViewTools
				.initToast(BaseActivity.this, ShowRemind.LOGIN_ERROR);
				break;
			case 10001:// 登录失败
				ToastViewTools
				.initToast(BaseActivity.this, ShowRemind.LOGIN_SUCCESS);
				break;

			}
			super.handleMessage(msg);
		}
	};

}
