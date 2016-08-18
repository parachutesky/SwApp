package com.jnwat.swmobilegy.mainpager.notify;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jnwat.bean.BGonggao;
import com.jnwat.bean.BUserlogin;
import com.jnwat.config.AppConfig;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.SimpleTreeAdapter;
import com.jnwat.swmobilegy.mainpager.deptmanager.bean.FileBean;
import com.jnwat.swmobilegy.mainpager.deptmanager.bean.Node;
import com.jnwat.swmobilegy.mainpager.deptmanager.bean.TreeListViewAdapter;
import com.jnwat.swmobilegy.mainpager.deptmanager.bean.TreeListViewAdapter.OnTreeNodeClickListener;
import com.jnwat.tools.SharedPrefsUtil;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 新增公告人员选择
 */
public class GongGaoReadActvity extends BaseActivity {
	ListView lsv_suoyouren, lsv_changyongzhu;
	private RadioGroup radioGroup;
	private RadioButton rb_suoyouren, rb_changyongzhu;
	private TreeListViewAdapter mAdapter;
	protected HttpUtils http = null;
	private List<FileBean> mDatas2 = new ArrayList<FileBean>();
	private TextView tv_edit, tv_gongao_creat;
	private String accountList_suoyouren = "";//
	private String accountList_suoyouren_show = "";//
	private LinearLayout lin_changyongzhu;
	private MyAdapter mMyAdapter; 
	DbUtils db;
	StringBuffer sb;// 分部门的姓名
	List<BGonggao> list = new ArrayList<BGonggao>();// 讨论组

	// private List<BGongGao> mDatas_cyr;// 常用人

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		db = DbUtils.create(this);// db还有其他的一些构造方法，比如含有更新表版本的监听器的
		tv_edit = (TextView) findViewById(R.id.tv_edit);
		tv_gongao_creat = (TextView) findViewById(R.id.tv_gongao_creat);
		radioGroup = (RadioGroup) findViewById(R.id.rdg_gonggao);
		rb_suoyouren = (RadioButton) findViewById(R.id.rb_suoyouren);
		rb_changyongzhu = (RadioButton) findViewById(R.id.rb_changyongzhu);
		lsv_suoyouren = (ListView) findViewById(R.id.lsv_suoyouren);
		lsv_changyongzhu = (ListView) findViewById(R.id.lsv_changyongzhu);
		lin_changyongzhu = (LinearLayout) findViewById(R.id.lin_changyongzhu);
		lsv_suoyouren.setVisibility(View.VISIBLE);
		lin_changyongzhu.setVisibility(View.INVISIBLE);
		showProgressDialog();

		try {
			list = db.findAll(Selector.from(BGonggao.class)
					.where("userName", "=", BUserlogin.Username)
					.orderBy("ID", true));
			// 加载讨论组的数据
			mMyAdapter = new MyAdapter(GongGaoReadActvity.this, list);
			lsv_changyongzhu.setAdapter(mMyAdapter);
			lsv_changyongzhu.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(GongGaoReadActvity.this,
							TaoLunZhuManActivity.class);
					intent.putExtra("ID", list.get(arg2).getID());
					startActivityForResult(intent, 1000);
				}
			});

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.rb_suoyouren:
							lsv_suoyouren.setVisibility(View.VISIBLE);
							lin_changyongzhu.setVisibility(View.INVISIBLE);
							break;
						case R.id.rb_changyongzhu:
							lsv_suoyouren.setVisibility(View.INVISIBLE);
							lin_changyongzhu.setVisibility(View.VISIBLE);
							break;
						}
					}
				});
		tv_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				// 设置选中的数据
				if (lsv_suoyouren.isShown()) {
					if (null != mAdapter) {
						System.out.println("showl----" + showMessgaePeople()
								+ "\n.." + accountList_suoyouren_show);

						Intent mIntent = new Intent(); //
						mIntent.putExtra("data_account", showMessgaePeople());
						mIntent.putExtra("data_name", sb.toString());

						// 设置结果，并进行传
						setResult(100, mIntent);
						GongGaoReadActvity.this.finish();
					} else {
						ToastViewTools.initToast(GongGaoReadActvity.this,
								"您暂未选择有效信息!");
					}

				} else {// 常用组
					if (null != list && list.size() > 0) {
						int lenth = list.size();
						StringBuffer sb_cyz = new StringBuffer();
						StringBuffer sb_name = new StringBuffer();
						for (int i = 0; i < lenth; i++) {
							if (list.get(i).isCheckPeople()) {// 如果选择这个常用组的人员
								sb_cyz.append(String.valueOf(list.get(i)
										.getAccount() + ","));
								sb_name.append(String.valueOf(list.get(i)
										.getName() + ","));
							}

						}
						// 返回给AddNotifyActivity
						sb_cyz.replace(sb_cyz.length() - 1, sb_cyz.length(), "");
						sb_name.replace(sb_name.length() - 1, sb_name.length(),
								"");
						Intent mIntent = new Intent(); //
						mIntent.putExtra("data_account", sb_cyz.toString());
						mIntent.putExtra("data_name", sb_name.toString());

						// 设置结果，并进行传
						setResult(100, mIntent);
						GongGaoReadActvity.this.finish();
					} else {
						ToastViewTools.initToast(GongGaoReadActvity.this,
								"请选择有效信息");
					}

				}
			}
		});
		tv_gongao_creat.setOnClickListener(new OnClickListener() {// 创建讨论组

					@Override
					public void onClick(View view) {
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(GongGaoReadActvity.this,
								SeclectPeopleActivity.class);
						startActivityForResult(intent, 1000);
					}
				});
	}

	/**
	 * 显示人员信息
	 */
	private String showMessgaePeople() {
		sb = new StringBuffer();
		StringBuffer accountList_sb = new StringBuffer();
		int lenth = mAdapter.getData().size();

		if (null != mAdapter.getData() && lenth > 0) {

			for (int i = 0; i < lenth; i++) {
				if (((Node) mAdapter.getData().get(i)).isLeaf()
						&& ((Node) mAdapter.getData().get(i)).isHadSeclect()) {
					sb.append(String.valueOf(((Node) mAdapter.getData().get(i))
							.getName() + ","));
					accountList_sb.append(((Node) mAdapter.getData().get(i))
							.getAccount() + ",");

				}

			}
			sb.replace(sb.length() - 1, sb.length(), "");
			accountList_sb.replace(accountList_sb.length() - 1,
					accountList_sb.length(), "");

		} else {

		}
		if (!mAdapter.isselectAll) {
			this.accountList_suoyouren = accountList_sb.toString();
		} else {
			this.accountList_suoyouren = "0";
		}

		this.accountList_suoyouren_show = sb.toString();

		return accountList_suoyouren;

	}

	// 回调方法，从第二个页面回来的时候会执行这个方法
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 根据上面发送过去的请求吗来区别
		switch (requestCode) {
		case 999:
			break;

		default:// 刷新ListView 的视图
			try {
				list = db.findAll(Selector.from(BGonggao.class)
						.where("userName", "=", BUserlogin.Username)
						.orderBy("ID", true));
				System.out.println("list--" + list.size());

				// 加载讨论组的数据
				mMyAdapter = new MyAdapter(GongGaoReadActvity.this, list);
				lsv_changyongzhu.setAdapter(mMyAdapter);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		}
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		getDateByHttp();
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_gonggaoread);
		setBackListener("审阅人", false);

	}

	private void getDateByHttp() {
		// TODO Auto-generated method stub

		http = new HttpUtils();
		http.configTimeout(8000);
		http.configCurrentHttpCacheExpiry(1000); // 缓存时间
		RequestParams params = new RequestParams(); // 参数

		String url = SharedPrefsUtil.getSETTINGIP(GongGaoReadActvity.this)
				+ AppConfig.METHOAD_ADDRESSBYDEP;
		;
		http.send(HttpRequest.HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						try {
							LogUtils.d("responseInfo..." + responseInfo.result);

							JSONObject jsonObj = new JSONObject(
									responseInfo.result);

							JSONArray jsonArray = jsonObj
									.getJSONArray("ReplyObject");
							int lenth = jsonArray.length();
							if (lenth > 0) {
								for (int i = 0; i < lenth; i++) {

									JSONObject jb_bean = jsonArray
											.getJSONObject(i);
									mDatas2.add(new FileBean(jb_bean
											.getInt("NodeId"), jb_bean
											.getInt("Pid"), jb_bean
											.getString("Name"), jb_bean
											.getString("Account")));
								}

							}
							myHandler.sendEmptyMessage(200);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					@Override
					public void onStart() {
					}

					@Override
					public void onFailure(HttpException error, String msg) {

						LogUtils.e("error code===" + msg.toString());
					}
				});
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 200:
				try {
					dismissProgressDialog();
					mAdapter = new SimpleTreeAdapter<FileBean>(lsv_suoyouren,
							GongGaoReadActvity.this, mDatas2, 10);
					mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
						@Override
						public void onClick(Node node, int position) {
							if (node.isLeaf()) {

								Toast.makeText(
										getApplicationContext(),
										node.getName()
												+ node.getChildren().size(),
										Toast.LENGTH_SHORT).show();
							}
						}

					});

				} catch (Exception e) {
					e.printStackTrace();
				}
				lsv_suoyouren.setAdapter(mAdapter);
				break;
			}
			super.handleMessage(msg);
		}
	};

	// 设置讨论组的LISTVIEW
	public class MyAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private Context mContext;
		private List<BGonggao> mDatas;

		public MyAdapter(Context context, List<BGonggao> mDatass) {
			mInflater = LayoutInflater.from(context);
			this.mContext = context;
			this.mDatas = mDatass;

		}

		@Override
		public int getCount() {
			if (null != mDatas) {
				return mDatas.size();
			}
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item, parent,
						false);
				viewHolder = new ViewHolder();
				viewHolder.icon = (ImageView) convertView
						.findViewById(R.id.id_treenode_icon);
				viewHolder.label = (TextView) convertView
						.findViewById(R.id.id_treenode_label);
				viewHolder.checkbox = (CheckBox) convertView
						.findViewById(R.id.checkbox);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			/*
			 * if (node.isHadSeclect()) { node.setHadSeclect(false); } else {
			 * node.setHadSeclect(true); }
			 */
			// 设置选择状态
			viewHolder.checkbox
					.setChecked(mDatas.get(position).isCheckPeople());
			viewHolder.checkbox.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					mDatas.get(position).setCheckPeople(
							!mDatas.get(position).isCheckPeople());
				}
			});
			viewHolder.label.setText(mDatas.get(position).getTitle() + "  ("
					+ mDatas.get(position).getNmb() + "人" + ")");
			viewHolder.icon.setVisibility(View.GONE);
			return convertView;
		}

		private final class ViewHolder {
			private ImageView icon;
			private TextView label;
			private CheckBox checkbox;
		}

	}

}
