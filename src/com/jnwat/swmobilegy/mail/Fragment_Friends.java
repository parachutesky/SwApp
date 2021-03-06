package com.jnwat.swmobilegy.mail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.jnwat.bean.Contacts;
import com.jnwat.bean.Contacts2;
import com.jnwat.config.AppConfig;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.App;
import com.jnwat.tools.GetRawStringFile;
import com.jnwat.tools.SharedPrefsUtil;
import com.jnwat.tools.ToastViewTools;
import com.jnwat.tools.WriteRawStringFile;
import com.jnwat.view.mailListPopupWindow;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

//通讯录

public class Fragment_Friends extends Fragment implements OnClickListener {
	private Activity ctx;
	private View layout, layout_title;
	private ListView lvContact;
	private SideBar indexBar;
	private TextView mDialogText;
	private WindowManager mWindowManager;
	private Contacts contacts = new Contacts(); //
	List<Contacts2> lisArray = new ArrayList<Contacts2>();
	TextView search_edt;// 搜索功能
	ContactAdapter contactAdapter;
	mailListPopupWindow mPopup;
	HttpUtils http = new HttpUtils();
	int jud = 0;
	App mApp;
	PopLoginDialog mPopLoginDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Log.d("tongxunlu", "kaishi");
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_friends,
					null);
			mWindowManager = (WindowManager) ctx
					.getSystemService(Context.WINDOW_SERVICE);
			initViews();
			initData();
			initDt();// 获取数据

		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		setOnListener();
		return layout;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		mApp = (App) this.getActivity().getApplication();
	}

	private void initViews() {
		layout_title = layout.findViewById(R.id.layout_bar);
		search_edt = (TextView) layout.findViewById(R.id.search_edt);
		lvContact = (ListView) layout.findViewById(R.id.lvContact);

		mDialogText = (TextView) LayoutInflater.from(getActivity()).inflate(
				R.layout.list_position, null);
		mDialogText.setVisibility(View.INVISIBLE);
		indexBar = (SideBar) layout.findViewById(R.id.sideBar);
		indexBar.setListView(lvContact);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		mWindowManager.addView(mDialogText, lp);
		indexBar.setTextView(mDialogText);

		// 等待 dialog
		mPopLoginDialog = new PopLoginDialog();

	}

	@Override
	public void onDestroy() {
		mWindowManager.removeView(mDialogText);
		super.onDestroy();
	}

	/**
	 * 随便给出的数据 用于测试
	 */
	public void initDt() {
		mPopLoginDialog.showPoploginDialog(ctx);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				getData();
			}

		}).start();

	}

	// 给 获得的 联系人信息 补充他的 按第一个字符分类的标识 如果没有 拼音 补充平音 如果没有 简拼 补充简拼
	public List<Contacts2> fillData(List<Contacts2> list) {
		int size = list.size();
		CharacterParser chaParse = new CharacterParser();
		for (int i = 0; i < list.size(); i++) {
			Contacts2 user = list.get(i);
			String name = user.getName();
			String firstLetter = "";
			if (null != user.getPinyin1() && !"".equals(user.getPinyin1())) {
				firstLetter = user.getPinyin1().substring(0, 1).toUpperCase();
			} else {
				firstLetter = chaParse.getSelling(name).substring(0, 1)
						.toUpperCase();
				user.setPinyin1(chaParse.getSelling(user.getName())); // 全拼拼音
			}
			if (user.getPinyin2() == null || "".equals(user.getPinyin2())) {// 简拼
																			// 拼音
				user.setPinyin2(chaParse.getSimpleLetter(user.getName()));
			}
			if (firstLetter.matches("[A-Z]")) {
				list.get(i).setFirstLetter(firstLetter);
			} else {
				list.get(i).setFirstLetter("#");
			}
		}
		return list;

	}

	public void getData() {

		final File file = new File(mApp.getCONTACTS_PATH());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!SharedPrefsUtil.getExistContacts(ctx)) {
			http.send(HttpRequest.HttpMethod.POST, AppConfig.getIp(ctx)
					+ AppConfig.METHOAD_GETTEACHERCONTACTS,
					new RequestCallBack<String>() {
						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {

						}

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
							LogUtils.d("responseInfo.result:"
									+ responseInfo.result);
							WriteRawStringFile.write(file, responseInfo.result);
							AGetContactsInfo AGetContactsInfo = new AGetContactsInfo();
							int status = AGetContactsInfo.analysisMaillist(
									responseInfo.result, mainHandler, contacts);
							lisArray = contacts.getReplyObject();
							if (null!=lisArray&&lisArray.size() > 0) {
								SharedPrefsUtil.putExistContacts(ctx, true);
								noticeUpdateData();
							}

							if (status == 0) {
								ToastViewTools.initToast(getActivity(),
										"获取数据异常");
								mPopLoginDialog.dismisPop();
							} else if (status == 2) {
								ToastViewTools.initToast(getActivity(),
										"没有获取到数据");
								mPopLoginDialog.dismisPop();
							}

						}

						@Override
						public void onStart() {
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							ToastViewTools.initToast(getActivity(), "连接数据失败");
							System.out.println("error:" + error + msg);
							// localData();
							// noticeUpdateData();

						}
					});
		} else {
			String resilt = GetRawStringFile.read(file);
			AGetContactsInfo AGetContactsInfo = new AGetContactsInfo();
			int status = AGetContactsInfo.analysisMaillist(resilt, mainHandler,
					contacts);
			lisArray = contacts.getReplyObject();
			noticeUpdateData();
		}
	}

	/**
	 * 刷新页面
	 */
	public void refresh() {
		initData();
	}

	private void initData() {
		contactAdapter = new ContactAdapter(getActivity(), lisArray);
		lvContact.setAdapter(contactAdapter);

	}

	private void setOnListener() {
		System.out.println("setOnListener");
		search_edt.setOnClickListener(new searOnClickListener());
		lvContact.setOnItemClickListener(new listItemOnClick());

	}

	/**
	 * listView的监听事件
	 */
	class listItemOnClick implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long arg3) {
			// TODO Auto-generated method stub
			/*
			 * // FragmentManager mFragment = android.app.FragmentManager m =
			 * ctx.getFragmentManager(); android.app.FragmentTransaction
			 * fragmentTransaction = m.beginTransaction();
			 * fragmentTransaction.replace(0,fg);
			 */
			Contacts2 user = (Contacts2) contactAdapter.getItem(position);
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("name", user.getName());

			bundle.putString("department", user.getDepartment());
			bundle.putString("offphone", user.getOffphone());
			bundle.putString("mobphone", user.getMobphone());
			bundle.putString("email", user.getEmail());
			bundle.putString("headurl", user.getHeadurl());
			intent.putExtras(bundle);
			intent.setClass(ctx, MailDetalActivity.class);
			ctx.startActivity(intent);

		}

	}

	/**
	 * 搜索 txt的 点击监听事件
	 * 
	 * @author Administrator
	 * 
	 */
	class searOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			mPopup = new mailListPopupWindow(ctx, lisArray, layout_title);
			mPopup.showAsDropDown(layout_title);
			InputMethodManager imm = (InputMethodManager) ctx
					.getSystemService(ctx.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}

	}

	/**
	 * 获取信息通讯录
	 */

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		default:
			break;
		}
	}

	public Handler mainHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int x = msg.what;
			switch (x) {
			case 101:
				lisArray = fillData(lisArray);
				contactAdapter.updateListView(lisArray);
				mPopLoginDialog.dismisPop();
				break;
			}
		}

	};

	public void noticeUpdateData() {
		Message msg = mainHandler.obtainMessage();
		msg.what = 101;
		mainHandler.sendMessage(msg);
	}
	/*
	 * public void localData(){ for(int i =0 ; i < 3;i ++){ Contacts2 user = new
	 * Contacts2(); user.setName("张三");
	 * 
	 * user.setOffphone("053158786944"); user.setMobphone("15066658823");
	 * lisArray.add(user); } for(int i =0;i < 3;i ++){ Contacts2 user = new
	 * Contacts2(); user.setName("李四");
	 * 
	 * user.setOffphone("053158786944"); user.setMobphone("15066658823");
	 * lisArray.add(user); }
	 * 
	 * for(int i =0;i < 3;i ++){ Contacts2 user = new Contacts2();
	 * user.setName("王五");
	 * 
	 * user.setOffphone("053158786944"); user.setMobphone("15066658823");
	 * lisArray.add(user); } for(int i =0;i < 3;i ++){ Contacts2 user = new
	 * Contacts2(); user.setName("赵华");
	 * 
	 * user.setOffphone("053158786944"); user.setMobphone("15066658823");
	 * lisArray.add(user); } for(int i =0;i < 3;i ++){ Contacts2 user = new
	 * Contacts2(); user.setName("//**");
	 * 
	 * user.setOffphone("053158786944"); user.setMobphone("15066658823");
	 * lisArray.add(user); } }
	 */

}
