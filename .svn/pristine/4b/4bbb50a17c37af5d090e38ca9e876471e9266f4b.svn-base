package com.jnwat.swmobilegy.news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.view.ProgressBarDetermininate;
import com.jnwat.view.SupportScrollEventWebView;

@SuppressLint("SetJavaScriptEnabled")
public class NewsDetailActivity extends BaseActivity {
	protected SupportScrollEventWebView webviewId;
	private RelativeLayout rlay_web404;
	private Button btn_reload;
	private ProgressBarDetermininate progressbar_webview;

	public void setHtml(String html) {

		progressbar_webview = (ProgressBarDetermininate) findViewById(R.id.progressbar_webview);
		webviewId = (SupportScrollEventWebView) findViewById(R.id.webview_recommend);
		rlay_web404 = (RelativeLayout) findViewById(R.id.rlay_web404);
		btn_reload = (Button) findViewById(R.id.btn_reload);
		rlay_web404.setVisibility(View.INVISIBLE);
		btn_reload.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				webviewId.setIssucceed(true);
				// basehandler.sendEmptyMessage(202);
				webviewId.reload();
			}
		});
		webviewId.setHorizontalScrollBarEnabled(false);// 滚动条水平不显示
		webviewId.setVerticalScrollBarEnabled(false); // 滚动条垂直不显示
		webviewId.getSettings().setJavaScriptEnabled(true);
		webviewId.getSettings().setDefaultFontSize(10);
		webviewId.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);// 默认不使用缓存
		// webviewId.addJavascriptInterface(new AndroidGetUserInfoForJs(
		// WebBaseActivity.this) {}, "JavaScriptInterface");

		webviewId.setWebViewClient(NewsDetailActivity.this, basehandler);

		webviewId.setWebChromeClient(progressbar_webview);

		webviewId.loadUrl(html);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * handler处理消息机制
	 */
	protected Handler basehandler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case 201:// 显示网络连接失败的布局
				webviewId.setVisibility(View.INVISIBLE);
				rlay_web404.setVisibility(View.VISIBLE);
				break;
			case 202:// 关闭网络连接失败的布局
				rlay_web404.setVisibility(View.INVISIBLE);
				break;
			case 203:// 显示webview的布局
				webviewId.setVisibility(View.VISIBLE);
				break;
			case 301:// 显示加载状态等待
				// showLoadingDialog();
				break;
			case 302:// 关闭加载状态等待
				// dismissLoadingDialog();
				break;
			}
		}
	};

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.webview_view);

		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				NewsDetailActivity.this);
		setBackListener("新闻详情", true);
		Intent intent = this.getIntent();
		String url =intent.getStringExtra("URL");
		setHtml(url);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}

}
