package com.jnwat.view;

import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.widget.ZoomButtonsController;

@SuppressLint("NewApi")
public class SupportScrollEventWebView extends WebView {
	private ZoomButtonsController zoomController = null;

	Context cx;
	Handler uihandler;
	private boolean issucceed = true;

	public SupportScrollEventWebView(Context context) {
		super(context);
		this.cx = context;
		disableZoomController();
	}

	public void setWebChromeClient(final ProgressBarDetermininate mProgressBar) {
		// TODO Auto-generated method stub
		super.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView wview, String url, String message,
					JsResult result) {
				if (message.toString().equals("错误: error")) {
					Toast.makeText(cx, "请检查网络连接", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(cx, message, Toast.LENGTH_SHORT).show();
				}
				result.confirm();
				return true;
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {

				if (newProgress == 100) {
					mProgressBar.setVisibility(View.INVISIBLE);
					if (issucceed) {
						uihandler.sendEmptyMessage(203);
						uihandler.sendEmptyMessage(202);
					} else {
						uihandler.sendEmptyMessage(201);
					}
				} else {
					view.setVisibility(View.INVISIBLE);
					if (View.INVISIBLE == mProgressBar.getVisibility()) {
						mProgressBar.setVisibility(View.VISIBLE);
					}
					mProgressBar.setProgress(newProgress);
				}
			}
		});
	}

	public void setWebViewClient(Context context, Handler handler) {

		// TODO Auto-generated method stub
		this.cx = context;
		this.uihandler = handler;
		super.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				view.stopLoading();
				uihandler.sendEmptyMessage(201);
				setIssucceed(false);
			}

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				try {
					URL url1 = new URL(url);
					view.loadUrl(url1.toString());
					return true;
				} catch (MalformedURLException e) {
					// Uri uri = Uri.parse(url);
					// Intent intent = new Intent(Intent.ACTION_DIAL, uri);
					// cx.startActivity(intent);
				}
				return true;

			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				uihandler.sendEmptyMessage(301);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				uihandler.sendEmptyMessage(302);
			}
		});

	}

	public SupportScrollEventWebView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		disableZoomController();
	}

	public SupportScrollEventWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		disableZoomController();
	}

	// 使得控制按钮不可用
	private void disableZoomController() {
		// API version 大于11的时候，SDK提供了屏蔽缩放按钮的方法
		// if (android.os.Build.VERSION.SDK_INT >= 11) {
		this.getSettings().setBuiltInZoomControls(true);
		this.getSettings().setDisplayZoomControls(false);
		// } else {
		// getControlls();
		// }
	}

	// private void getControlls() {
	// try {
	// Class webview = Class.forName("android.webkit.WebView");
	// Method method = webview.getMethod("getZoomButtonsController");
	// zoomController = (ZoomButtonsController) method.invoke(this,null);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		super.onTouchEvent(ev);
		if (zoomController != null) {
			zoomController.setVisible(false);
		}
		return true;
	}

	public void setIssucceed(boolean issucceed) {
		this.issucceed = issucceed;
	}

}
