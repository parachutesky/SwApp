/**
 * 
 */
package com.jnwat.swmobilegy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.jnwat.analysis.ATrainingProgramList;
import com.jnwat.bean.TrainingProgramBean;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.TrainingProgramListAdapter;
import com.jnwat.tools.ModifySysTitle;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author zhaorl
 * 
 * 培训项目统计
 *
 */
public class TrainingProgramActivity extends BaseActivity {

	private  static String TAG  = " TrainingProgramListActivity_20151202140120";
	private  String mTitle = "培训项目统计列表";
	private  TextView  mTitle_tv;
	private  PullToRefreshListView mProgram_lv;
	private ImageView iv_back;
	private List<Map<String, Object>> list;
	Gson gson = null;
	private TrainingProgramBean programBean  = null;
	private TrainingProgramListAdapter adapter = null;
	Handler mHandler = new Handler() {

		@Override 
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			switch(msg.what){
			case 1:
				mPopLoginDialog.dismisPop();
				mPopLoginDialog.showPoploginDialog(TrainingProgramActivity.this);				
				break;
			case ShowRemind.HANDLER_SUCCESS:
				//刷新列表项
				adapter.notifyDataSetChanged();
				mProgram_lv.onRefreshComplete();
			break;
			
			case 100:
				adapter = new TrainingProgramListAdapter(TrainingProgramActivity.this,list);
				mProgram_lv.setAdapter(adapter);
				mProgram_lv.onRefreshComplete();
				//closePop();// 关闭Dialog
			break;
			
			case 101:
				adapter.notifyDataSetChanged();
				mProgram_lv.onRefreshComplete();
				//closePop();// 关闭Dialog
				break;
			}
		}
	};
	
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		setBackListener(mTitle, false);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see com.jnwat.oamobilegy.BaseActivity#initData()
	 */
	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see com.jnwat.oamobilegy.BaseActivity#setView()
	 */
	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.activity_uneducation_list);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		mTitle_tv = (TextView) findViewById(R.id.title_bar_name);
		mProgram_lv  =  (PullToRefreshListView) findViewById(R.id.lv_trainpro);
		ModifySysTitle.ModifySysTitleStyle(R.color.title_blue, TrainingProgramActivity.this);
		 mProgram_lv.getRefreshableView().setDivider(null);
		 mProgram_lv.getRefreshableView().setSelector(
		 android.R.color.transparent);
		 mProgram_lv
			.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(
						PullToRefreshBase<ListView> refreshView) {
					String label = DateUtils.formatDateTime(
							getApplicationContext(),
							System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME
									| DateUtils.FORMAT_SHOW_DATE
									| DateUtils.FORMAT_ABBREV_ALL);
					System.out.println("top:"
							+ mProgram_lv.isHeaderShown()
							+ "footer:"
							+ mProgram_lv.isFooterShown());
					// Update the LastUpdatedLabel
					refreshView.getLoadingLayoutProxy()
							.setLastUpdatedLabel(label);
					// Do work to refresh the list here.
					if (mProgram_lv.isHeaderShown()) {// 显示头部UI
						// 添加数据	网络请求
						getDateByHttp(1);
					} else if (mProgram_lv.isFooterShown()) {// 显示底部UI
						//网络请求
						mHandler.sendEmptyMessageDelayed(0, 1500);
					} else {
						//网络请求
						mHandler.sendEmptyMessageDelayed(0, 1500);
					}
				}
			});
			
			// 设置自动刷新
			mProgram_lv.setRefreshing(true);
		    getDateByHttp(0);
	}

	private void getDateByHttp(final int type) {
		// TODO Auto-generated method stub
		
		if(null == list){
			
			list = new ArrayList<Map<String, Object>>();
		}
		
		http = new HttpUtils();
		http.configTimeout(8000);
		http.configCurrentHttpCacheExpiry(1000); // 缓存时间
//		RequestParams params = new RequestParams(); // 参数
//		params.addBodyParameter("projecttype", "1");
		String url = AppConfig.getIp(TrainingProgramActivity.this)
				+ AppConfig.METHOAD_TRAINING_PROGRAM_STATICS_LIST;

		http.send(HttpRequest.HttpMethod.POST, url, null,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.e(">>>>>>httprequest>>>>>>","result=="+responseInfo.result);
						try {
							ATrainingProgramList mUnEduMessage  = new ATrainingProgramList();
							list  = mUnEduMessage.getTrainingList(responseInfo.result);
							
							if(type>0){
								 if(null!=list){
									 mHandler.sendEmptyMessage(101);
								 }else{
									 mHandler.sendEmptyMessage(-1);
								 }
							 }else{
								 if(null!=list){
									 mHandler.sendEmptyMessage(100);
								 }else{
									 mHandler.sendEmptyMessage(-1);
								 }
						}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						
						LogUtils.e("error code==="+msg.toString());
					}
				});
	}

}
