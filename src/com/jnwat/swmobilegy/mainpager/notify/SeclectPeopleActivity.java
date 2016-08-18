package com.jnwat.swmobilegy.mainpager.notify;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jnwat.bean.BGonggao;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BPushMessageInfo;
import com.jnwat.bean.BUserlogin;
import com.jnwat.bean.Contacts;
import com.jnwat.bean.Contacts2;
import com.jnwat.config.AppConfig;
import com.jnwat.swmobilegy.App;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.mail.AGetContactsInfo;
import com.jnwat.swmobilegy.mail.CharacterParser;
import com.jnwat.swmobilegy.mail.MailDetalActivity;
import com.jnwat.swmobilegy.mainpager.SelectSideBar;
import com.jnwat.swmobilegy.mainpager.deptmanager.bean.Node;
import com.jnwat.tools.GetRawStringFile;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.SharedPrefsUtil;
import com.jnwat.tools.ToastViewTools;
import com.jnwat.tools.WriteRawStringFile;
import com.jnwat.view.mailListPopupWindow;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

public class SeclectPeopleActivity extends BaseActivity implements
		OnClickListener {
	private final static String CONTACTS_NAME = "contacts.txt";
	private ListView lvContact;
	private SelectSideBar indexBar;
	private RelativeLayout rel_notify_title;
	private TextView mDialogText;
	private WindowManager mWindowManager;
	private Contacts contacts = new Contacts(); //
	List<Contacts2> lisArray = new ArrayList<Contacts2>();
	TextView search_edt;// 搜索功能
	TextView tv_edit;// 搜索功能
	NotifyContactAdapter contactAdapter;
	mailListPopupWindow mPopup;
	HttpUtils http = new HttpUtils();
	int jud = 0;

	DbUtils mdbUtils;

	// PopLoginDialog mPopLoginDialog;
	/**
	 * 随便给出的数据 用于测试
	 */
	public void initDt() {
		// mPopLoginDialog.showPoploginDialog(SeclectPeopleActivity.this);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				getData();
			}

		}).start();

	}

	public void getData() {

		final File file = new File(
				((App) SeclectPeopleActivity.this.getApplication())
						.getCONTACTS_PATH());

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!SharedPrefsUtil.getExistContacts(SeclectPeopleActivity.this)) {
			http.send(HttpRequest.HttpMethod.POST,
					AppConfig.getIp(SeclectPeopleActivity.this)
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
							if (null != lisArray && lisArray.size() > 0) {
								SharedPrefsUtil.putExistContacts(
										SeclectPeopleActivity.this, true);
								noticeUpdateData();
							}

							if (status == 0) {
								ToastViewTools.initToast(
										SeclectPeopleActivity.this, "获取数据异常");
								// mPopLoginDialog.dismisPop();
							} else if (status == 2) {
								ToastViewTools.initToast(
										SeclectPeopleActivity.this, "没有获取到数据");
								// mPopLoginDialog.dismisPop();
							}

						}

						@Override
						public void onStart() {
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							ToastViewTools.initToast(
									SeclectPeopleActivity.this, "连接数据失败");
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
		initData1();
	}

	private void initData1() {
		contactAdapter = new NotifyContactAdapter(SeclectPeopleActivity.this,
				lisArray);
		lvContact.setAdapter(contactAdapter);

	}

	private void setOnListener() {
		System.out.println("setOnListener");
		search_edt.setOnClickListener(new searOnClickListener());
		lvContact.setOnItemClickListener(new listItemOnClick());
		tv_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				contactAdapter.putDataPeople();
				if (BIntentObj.UserInfos.size() > 0) {// 提示输入讨论组名称
					showNameModifyDialog();
				} else {
					ToastViewTools.initToast(SeclectPeopleActivity.this,
							"您未选择审阅人");
				}

				/*
				 * // 设置选中的数据 contactAdapter.putDataPeople(); Intent mIntent =
				 * new Intent(); // 设置结果，并进行传送 setResult(1000, mIntent);
				 * finish();// 结束当前的activity的生命周期
				 */

			}
		});
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

	private void initViews() {
		mdbUtils = DbUtils.create(SeclectPeopleActivity.this);
		rel_notify_title = (RelativeLayout) findViewById(R.id.rel_notify_title);
		search_edt = (TextView) findViewById(R.id.search_edt);
		tv_edit = (TextView) findViewById(R.id.tv_edit);
		lvContact = (ListView) findViewById(R.id.lvContact);

		mDialogText = (TextView) LayoutInflater
				.from(SeclectPeopleActivity.this).inflate(
						R.layout.list_position, null);
		mDialogText.setVisibility(View.INVISIBLE);
		indexBar = (SelectSideBar) findViewById(R.id.sideBar);
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
		// mPopLoginDialog = new PopLoginDialog();

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
			intent.setClass(SeclectPeopleActivity.this, MailDetalActivity.class);
			SeclectPeopleActivity.this.startActivity(intent);

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

			mPopup = new mailListPopupWindow(SeclectPeopleActivity.this,
					lisArray, rel_notify_title);
			mPopup.showAsDropDown(rel_notify_title);
			InputMethodManager imm = (InputMethodManager) SeclectPeopleActivity.this
					.getSystemService(SeclectPeopleActivity.this.INPUT_METHOD_SERVICE);
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
				// mPopLoginDialog.dismisPop();
				break;
			}
		}

	};

	public void noticeUpdateData() {
		Message msg = mainHandler.obtainMessage();
		msg.what = 101;
		mainHandler.sendMessage(msg);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				SeclectPeopleActivity.this);
		setContentView(R.layout.activity_notify_select);

		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		initViews();
		setBackListener("选择审阅人", false);
		initData1();
		initDt();// 获取数据

		setOnListener();
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	public void showNameModifyDialog() {
		LayoutInflater factory = LayoutInflater
				.from(SeclectPeopleActivity.this);// 提示框
		final View view = factory.inflate(R.layout.dialogip_editbox_layout,
				null);// 这里必须是final的
		final EditText edit = (EditText) view.findViewById(R.id.editText);// 获得输入框对象
		edit.setHint("请输入");
		AlertDialog.Builder builder;
		if (android.os.Build.VERSION.SDK_INT > 11) {
			builder = new AlertDialog.Builder(SeclectPeopleActivity.this,
					AlertDialog.THEME_HOLO_LIGHT); // 先得到构造器
		} else {
			builder = new AlertDialog.Builder(SeclectPeopleActivity.this); // 先得到构造器
		}

		builder.setTitle("请输入讨论组名称")
		// 提示框标题
				.setView(view).setPositiveButton("确定",// 提示框的两个按钮
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								BGonggao mBGongGao = new BGonggao();
								if (edit.getText().toString().equals("")) {
									ToastViewTools.initToast(
											SeclectPeopleActivity.this,
											"请输入讨论组名称");
								} else {

									mBGongGao.setTitle(edit.getText()
											.toString().trim());
									mBGongGao.setNmb(BIntentObj.UserInfos
											.size() + "");
									mBGongGao.setUserName(BUserlogin.Username);
									int lenth = BIntentObj.UserInfos.size();
									StringBuffer account = new StringBuffer();
									StringBuffer name = new StringBuffer();
									for (int i = 0; i < lenth; i++) {
										name.append(BIntentObj.UserInfos.get(i)
												.getName() + ",");

										account.append(BIntentObj.UserInfos
												.get(i).getAccount() + ",");
									}
									name.replace(name.length() - 1,
											name.length(), "");
									account.replace(account.length() - 1,
											account.length(), "");
									mBGongGao.setName(name.toString());
									mBGongGao.setAccount(account.toString());
									mBGongGao.setTime(System
											.currentTimeMillis() + "");
									mBGongGao.setCheckPeople(false);

									Gson mgson = new Gson();

									mBGongGao.setContentData(mgson
											.toJson(BIntentObj.UserInfos));
									System.out.println("存储的人员信息："
											+ mBGongGao.getContentData());

									try {
										// BPushMessageInfo mBPushMessageInfo =
										// new BPushMessageInfo();
										mdbUtils.save(mBGongGao);

										Intent mIntent = new Intent(); //
										// 设置结果，并进行传
										setResult(1000, mIntent);
										finish();// 结束当前的activity的生命周期
										ToastViewTools.initToast(
												SeclectPeopleActivity.this,
												"创建成功");
										System.out.println("共----"
												+ mdbUtils.findAll(
														BGonggao.class).size());
									} catch (DbException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
										ToastViewTools.initToast(
												SeclectPeopleActivity.this,
												"创建讨论组失败");
									}

								}

							}
						}).setNegativeButton("取消", null).create()

				.show();
	}
}
