package com.jnwat.tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jnwat.analysis.AAppVersion;
import com.jnwat.bean.BAppVersion;
import com.jnwat.bean.IntentObject;
import com.jnwat.config.AppConfig;
import com.jnwat.dialog.PopClickListening;
import com.jnwat.dialog.PopWindowShow;
import com.jnwat.service.UpdateAppService;
import com.jnwat.swmobilegy.UnEduProgramDetailActivity;
import com.jnwat.swmobilegy.WelcomeActivity;
import com.jnwat.tools.UpAppVersion;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class GetNewAppVersion {
	private BAppVersion bAppVersion;
	private Activity context;
	private PopWindowShow popWindowShow;
	String url  = "";
	public GetNewAppVersion() {

	}

	public GetNewAppVersion(BAppVersion mbAppVersion, Activity mContext,
			PopWindowShow mpopWindowShow) {
		bAppVersion = mbAppVersion;
		context = mContext;
		popWindowShow = mpopWindowShow;
		
	    url = AppConfig.getIp(mContext)
				+ AppConfig.METHOAD_GETAPPVERSONINFO;
	}

	/**
	 * 获取升级版本信息
	 */
	public void getAppVersionInfo(HttpUtils http) { // METHOAD_GETAPPVERSONINFO
		http.send(HttpRequest.HttpMethod.POST,url,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						LogUtils.d(responseInfo.result);
						bAppVersion = new AAppVersion()
								.Login_SetData(responseInfo.result);
						IntentObject.aPPDownLoad = bAppVersion.getFILENAME();
						System.out.println("是否升级" + bAppVersion.getVERSION());
						if (UpAppVersion.isUpData(context, bAppVersion)) {// 升级
							final String data = "更新版本：\n"
									+ bAppVersion.getVERSION()
									+ "\n更新内容：\n" + bAppVersion.getINFO();
							popWindowShow.showPopupWindowDialog(context,
									"发现新版本", data, popClickListenershow);
						} else {
							
						}
						
						Log.d("版本信息","result："+responseInfo.result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {

						Log.d("版本信息","URL="+AppConfig.METHOAD_GETAPPVERSONINFO+"错误信息："+msg);
					}
					
					
				});

		
	}

	final PopClickListening popClickListenershow = new PopClickListening() {
		@Override
		public void PopPositiveLister() {
			Intent updateIntent = new Intent(context, UpdateAppService.class);

			context.startService(updateIntent);

			popWindowShow.popWindowdismiss();
		}

		@Override
		public void PopNegativeLister() {
			popWindowShow.popWindowdismiss();

		}

		@Override
		public void EditPopPositiveLister(EditText et) {
			// TODO Auto-generated method stub

		}

		@Override
		public void EditPopNegativeLister(EditText et) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setListViewForName(ListView lv, LinearLayout lin) {
			// TODO Auto-generated method stub
			
		}
	};

}
