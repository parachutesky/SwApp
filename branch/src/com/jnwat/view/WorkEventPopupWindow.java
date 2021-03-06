package com.jnwat.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jnwat.analysis.ATrainingCourse;
import com.jnwat.config.AppConfig;
import com.jnwat.config.ShowRemind;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.swmobilegy.MyProjectDetailActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterAllProject;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

@SuppressLint("HandlerLeak")
public class WorkEventPopupWindow extends PopupWindow implements
		OnClickListener {
	private Activity mContext;
	private List<HashMap<String, String>> showList;
	private View mView;
	private EditText searchEdit;
     private ListView  listView;
     private AdapterAllProject mAdapterAllProject = null;
     private String inputText  = "";
     private EditChangedListener editTextListener = null;
   //  private ProgressDialog progressDialog= null;
     private String  TAG  = " 搜索";
 	 private CharSequence temp;//监听前的文本
     private final int charMaxNum = 30;
     public static boolean isFinish  = false;
    // private Context mContext =  null;
     private int currentLen=  0;
     private int type;// 判断是否刷新的类型
 	private int nub = 1;// 页码
 	private int list_size = 0;// list的长度
	HttpUtils http ;
     
 	protected PopLoginDialog mPopLoginDialog;
 	
	public WorkEventPopupWindow(Activity Context) {
		this.mContext = Context;
/*		LayoutInflater mInflater = (LayoutInflater) Context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.mView = mInflater.inflate(R.layout., null);
		this.setContentView(mView);*/
		// 设置弹窗的布局界面
		setContentView(LayoutInflater.from(mContext).inflate(
				R.layout.workevent_popup, null));
		mView = getContentView();
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		ColorDrawable cd = new ColorDrawable(0x000000);
		this.setBackgroundDrawable(cd);
		WindowManager.LayoutParams lp = Context.getWindow().getAttributes();
		lp.alpha = 0.7f;
		Window c = Context.getWindow();
		c.setAttributes(lp);
		this.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				WindowManager.LayoutParams lp = ((Activity) mContext)
						.getWindow().getAttributes();
				lp.alpha = 1f;
				((Activity) mContext).getWindow().setAttributes(lp);
			}

		});
		System.out.println("pop _ refresh");
		initView();
		initListen();
		
	}

	public void initView() {
		mPopLoginDialog = new PopLoginDialog();
		
		if(showList==null){
			showList = new ArrayList<HashMap<String, String>>();
		}
		listView =  (ListView) mView.findViewById(R.id.mailist_list);
		searchEdit = (EditText) mView.findViewById(R.id.search_edt);
		/*
		listView.setMode(Mode.PULL_FROM_END);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						String label = DateUtils.formatDateTime(mContext,
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						// Do work to refresh the list here.
						if (listView.isHeaderShown()) {// 显示头部UI
							// 添加数据 网络请求
							mHandler.sendEmptyMessage(10);
						} else if (listView.isFooterShown()) {// 显示底部UI
							// 加载更多
							mHandler.sendEmptyMessage(100);
						} else {
							// 网络请求
							LogUtils.d("初始化网络数据");
						}
					}
				});
<<<<<<< .mine
	
		mAdapterAllProject = new AdapterAllProject(mContext, showList);
		listView.setAdapter(mAdapterAllProject);
=======
		*/


	}

	public void initListen() {
		
		editTextListener  = new EditChangedListener();
		mView.setOnClickListener(this);// searchEdit
		searchEdit.setOnKeyListener(new EditText.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent keyEvent) {
				// TODO Auto-generated method stub
				if (keyCode == keyEvent.KEYCODE_BACK && showList.size() == 0) {
					dismiss();
					return false;
				}
				return false;
			}
		});
		
		searchEdit.addTextChangedListener(editTextListener);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
				Map<String,String> map =showList.get(position);
				String projectid =map.get("Projectid");
				Intent intent = new Intent();
				intent.setClass(mContext,MyProjectDetailActivity.class);
				intent.putExtra("from", 1);
				intent.putExtra("Projectid",projectid);
				mContext.startActivity(intent);
			}
			
		});
		mAdapterAllProject = new AdapterAllProject(mContext, showList);
		listView.setAdapter(mAdapterAllProject);
	}

	// 消息处理
		public Handler mHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0:
					ToastViewTools.initToast(mContext, "暂无数据");
				//	listView.onRefreshComplete();
					break;
				case 1:
					ToastViewTools.initToast(mContext, "数据出错了");
				//	listView.onRefreshComplete();
					break;
				case 9:
					// 0代表成功 1 代表失败 2有新数据刷新 3 不刷新
					
					
					list_size = showList.size();
	
					
					System.out.println("刷新数据case 9");
					if (type == 0) {
						System.out.println("刷新数据");
						// Call onRefreshComplete when the list has been refreshed.
				//	listView.onRefreshComplete();
						mAdapterAllProject.notifyDataSetChanged();
					}
					if (type == 3) {
			//			listView.onRefreshComplete();
						mAdapterAllProject.notifyDataSetChanged();
						System.out.println("刷新数据成功");
					}
					if(list_size==0){
						ToastViewTools.initToast(mContext, "没有符合条件的数据");
					}
				if(null!=mPopLoginDialog)
						mPopLoginDialog.dismisPop();
					
					
					break;
				case 10:// 自动加载数据
					nub = 1; 
					initData(nub, true);
					break;
				case 100:// 加载更多数据
					initData(nub, false);
					break;
				case ShowRemind.HANDLER_STATUS:
					
					String message  = (String) msg.obj;
					ToastViewTools
							.initToast(mContext,message);
					if(null!=mPopLoginDialog)
						mPopLoginDialog.dismisPop();
					break;
				case 101:
					ToastViewTools
					.initToast(mContext,"没有输入查询条件");
					break;
				}
			};
		};
	
	public void initData(final int currPage,final boolean isClear) {

	final String text  =  searchEdit.getText().toString();
		//查询条件不为空时
		if(!text.equals("")){
			mPopLoginDialog.showPoploginDialog(mContext);
			/*new Thread(new Runnable() {
				@Override
				public void run() {
				    	Log.d("获取查询的列表","条件为："+text);
				*/
		
						if(null == http ){
							http = new HttpUtils();
						}
		
						http.configTimeout(5500);
						http.configCurrentHttpCacheExpiry(10);
						String url = AppConfig.getIp(mContext)+AppConfig.METHOAD_TRAININGCOURSE;
						RequestParams params = new RequestParams(); // 参数
						params.addBodyParameter("projecttype", "0");
						params.addBodyParameter("proname", text);
						params.addBodyParameter("page", String.valueOf(currPage));
						LogUtils.i("地址信息："+url + "proname:"+text+" /page"+String.valueOf(currPage));
						http.send(HttpRequest.HttpMethod.POST,url,
								params,
								new RequestCallBack<String>() {
									@Override
									public void onLoading(long total, long current,
											boolean isUploading) {
									}
									
									@Override
									public void onSuccess(
											ResponseInfo<String> responseInfo) {
		                        		ATrainingCourse mATrainingCourse = new ATrainingCourse();
										LogUtils.d("课程:" + responseInfo.result);
										type = mATrainingCourse.analyTrainingCourse(
												responseInfo.result, showList, isClear);
										LogUtils.d("共：" + showList.size() + "项");
										
										int new_list_size = showList.size();
										if (new_list_size > list_size) {// 如果有获取的数据
											nub++;
										}
										
										list_size = new_list_size;
			
										mHandler.sendEmptyMessageDelayed(9, 200);
									}

									@Override
									public void onStart() {
										
									}
									@Override
									public void onFailure(HttpException error,
											String msg) {
										System.out.println("msg:"+msg);
										Message message  = Message.obtain(mHandler);
										message.obj  = msg;
										message.what = ShowRemind.HANDLER_STATUS;
										mHandler.sendMessage(message);
									}
								});
					
				}

		/*	}).start();
		}else{
			
			//条件为空时不用查询
			
		}*/
		
		
	}

	public class EditChangedListener implements TextWatcher {
	    
	    @Override
	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	            Log.i(TAG, "输入文本之前的状态");
	            //temp = s;
	    }

	    @Override
	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	            Log.i(TAG, "输入文字长度"+currentLen);
	            temp = s;
	    }

	    @Override  
	    public void afterTextChanged(Editable s) {
	            Log.i(TAG, "输入文字后的状态");
	            currentLen = temp.length();
	            int size  = charMaxNum - currentLen;
	            if(currentLen>0){
	            	if(size<=0){
	                	Toast.makeText(mContext, "输入的文本已经超过了限制", Toast.LENGTH_LONG).show();
	                	isFinish = false;
	                }else{
	                	nub = 1;
	                	list_size = 0;
	                	mHandler.sendEmptyMessageDelayed(10, 800);
	                	//initData(nub, true);
	                }
	        	}else{
	        		  isFinish  = false;
	        	}
	    }
	    
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.search_edt:
			break;
		default:
			if (showList.size() == 0
					&& searchEdit.getText().toString().isEmpty()) {
				this.dismiss();
			}
			break;
		}

		
	}
	
	
	

}
