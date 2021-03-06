/**
 * 
 */
package com.jnwat.swmobilegy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.jnwat.analysis.AGetUnEducationMessage;
import com.jnwat.analysis.ASearchSchedule;
import com.jnwat.analysis.ATaskInfo;
import com.jnwat.bean.BTaskInfo;
import com.jnwat.bean.BUserlogin;
import com.jnwat.bean.TrainingProgramBean;
import com.jnwat.bean.UnEducationBean;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.dialog.PopWindowShow;
import com.jnwat.swmobilegy.R;
import com.jnwat.tools.GetRawStringFile;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Administrator
 *
 */
public class UnEduProgramDetailActivity extends BaseActivity {
	
	private  static String TAG  = " UnEduProgramDetailActivity——2015120314091231";
	
//	private TextView title_tv; //标题
//	private TextView type_tv; //类型
//	private TextView start_tv; //开始时间
//	private TextView end_tv;  //结算时间
//	private TextView coast_tv; //花费时间
//	private TextView content_tv; //具体内容
	
	private List<HashMap<String, String>> list;
	private  String mTitle = "非教学工作详情";
	Gson gson = null;
	private UnEducationBean uneducation  = null;
	private String workId  =  "";
	private BTaskInfo mBTaskInfo;
	private  LayoutInflater mInflater =  null;
	private PopWindowShow popWindowShow;
	private MyTask mTask = null;
	
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch(msg.what){
			
			case 1:
				mPopLoginDialog.dismisPop();
				mPopLoginDialog.showPoploginDialog(UnEduProgramDetailActivity.this);				
				break;
			
			case ShowRemind.HANDLER_SUCCESS:
				mTask = new MyTask();
				mTask.execute();
			break;
			
			case 100:
				closePop();// 关闭Dialog
			break;
			
			}
		}
	};
	
	//创建一个视图
	
	private void initContentView() {
		// TODO Auto-generated method stub
		
		mInflater =  LayoutInflater.from(this);
		LinearLayout liearlayout_content = (LinearLayout) findViewById(R.id.linear_layout);
		
		int size  =  uneducation.list.size();
		
		// 长度
		for(int i = 0; i < size; i++){
			
			if(i==size-1){
				View mContentView_ver = mInflater.inflate(
						R.layout.item_unedued_list_1, null);
				
				TextView tv_name1 = (TextView) mContentView_ver
				.findViewById(R.id.tv_item_title);
	        	tv_name1.setText(uneducation.list.get(i).get("index").toString().trim());
		      TextView tv_value1 = (TextView) mContentView_ver
				.findViewById(R.id.item_type_tv);
		      tv_value1.setText(uneducation.list.get(i).get("value").toString().trim());
		      liearlayout_content.addView(mContentView_ver);
		      
			}else{
				View mContentView = mInflater.inflate(
						R.layout.item_unedute_list, null);
				
				TextView tv_name = (TextView) mContentView
						.findViewById(R.id.tv_item_meetingapply_content_key);
				tv_name.setText(uneducation.list.get(i).get("index").toString().trim());
				
				TextView tv_value = (TextView) mContentView
						.findViewById(R.id.tv_item_meetingapply_content_value);
				tv_value.setText(uneducation.list.get(i).get("value").toString().trim());
				
				liearlayout_content.addView(mContentView);
			}
		}
	}

	/**
	 * 
	 * @author zhaorl
	 * 绘制视图
	 * 
	 */
	
	private class MyTask extends AsyncTask<String, Integer, String> {
		// onPreExecute方法用于在执行后台任务前做一些UI操作
		@Override
		protected void onPreExecute() {
			initContentView();
		}

		// doInBackground方法内部执行后台任务,不可在此方法内修改UI
		@Override
		protected String doInBackground(String... params) {

			return null;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Integer... progresses) {
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(String result) {

			// 视图可见

			// closePop();// 关闭Dialog
			handler.sendEmptyMessageDelayed(100, 100);
		}

		// onCancelled方法用于在取消执行中的任务时更改UI
		@Override
		protected void onCancelled() {
		}
	}
	
	/* (non-Javadoc)
	 * @see com.jnwat.oamobilegy.BaseActivity#setListener()
	 */
	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		setBackListener(mTitle, false);
	}

	/* (non-Javadoc)
	 * @see com.jnwat.oamobilegy.BaseActivity#initData()
	 */
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		mPopLoginDialog = new PopLoginDialog();
		//handler.sendEmptyMessageDelayed(1, 150);
		if (popWindowShow == null) {
			popWindowShow = new PopWindowShow();
		}
		
		getDateByHttp(workId);
	}


	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.unedu_program_detail);
		
	//	title_tv = (TextView)findViewById(R.id.text);
		http = new HttpUtils();
		http.configTimeout(8000);
		http.configCurrentHttpCacheExpiry(1000); // 缓存时间
		workId  = getIntent().getStringExtra("workId");
		
		ModifySysTitle.ModifySysTitleStyle(R.color.title_blue,
				UnEduProgramDetailActivity.this);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}

	private void getDateByHttp(String workid) {
		// TODO Auto-generated method stub
		
		if(null == list){
			list = new ArrayList<HashMap<String, String>>();
		}
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("WorkID", workid);
		String url = AppConfig.getIp(UnEduProgramDetailActivity.this)
				+ AppConfig.METHOAD_UNEDUCATION_WORK_DETAIL;

		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
 
						AGetUnEducationMessage mUnEduMess = new AGetUnEducationMessage();
						uneducation = mUnEduMess.analysisUnEducated(
								responseInfo.result, handler);
						
					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						
//						//请求失败时操作、
//						ATaskInfo mATaskInfo = new ATaskInfo();
//						// GetRawStringFile.rawRead(MeetingApply.this, R.raw.a2)
//						mBTaskInfo = mATaskInfo.analysisTaskInfo(
//								GetRawStringFile.rawRead(UnEduProgramDetailActivity.this,
//										R.raw.a3), handler);

						closePop();// 关闭Dialog
						ToastViewTools.initToast(UnEduProgramDetailActivity.this,
								"网络超时....加载离线数据");
						
					}
				});
		
	}
	
	

}
