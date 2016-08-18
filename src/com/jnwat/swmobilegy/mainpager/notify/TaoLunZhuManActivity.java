package com.jnwat.swmobilegy.mainpager.notify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

import com.google.gson.Gson;
import com.jnwat.bean.BGonggao;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.Contacts2;
import com.jnwat.swmobilegy.BaseActivity;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.mail.MailDetalActivity;
import com.jnwat.swmobilegy.mail.PinyinComparator;
import com.jnwat.swmobilegy.mainpager.SelectSideBar;
import com.jnwat.swmobilegy.mainpager.TLZSelectSideBar;
import com.jnwat.swmobilegy.mainpager.notify.SeclectPeopleActivity.listItemOnClick;
import com.jnwat.swmobilegy.view.CircleImageView;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

/**
 * @author chang-zhiyuan 讨论组管理Activity
 */
public class TaoLunZhuManActivity extends BaseActivity {

	private ListView lvContact;
	private TextView tv_edit;
	private int ID;
	DbUtils db;
	private TextView mDialogText;
	private TLZSelectSideBar indexBar;
	private BGonggao bGonggao = new BGonggao();;
	NotifyContactAdapterTlz mNotifyContactAdapter;
	List<Contacts2> mlist;
	private WindowManager mWindowManager;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		lvContact = (ListView) findViewById(R.id.lvContact);
		tv_edit = (TextView) findViewById(R.id.tv_edit);
		indexBar = (TLZSelectSideBar) findViewById(R.id.sideBar);
		indexBar.setListView(lvContact);
		lvContact.setAdapter(mNotifyContactAdapter);
		mDialogText = (TextView) LayoutInflater.from(TaoLunZhuManActivity.this)
				.inflate(R.layout.list_position, null);
		mDialogText.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
				PixelFormat.TRANSLUCENT);
		mWindowManager.addView(mDialogText, lp);
		indexBar.setTextView(mDialogText);
		indexBar.setTextView(mDialogText);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		lvContact.setOnItemClickListener(new listItemOnClick());

		tv_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					db.delete(db.findFirst(Selector.from(BGonggao.class).where(
							"ID", "=", ID)));
					ToastViewTools.initToast(TaoLunZhuManActivity.this, "删除成功");

					Intent mIntent = new Intent();

					// 设置结果，并进行传
					setResult(100, mIntent);
					TaoLunZhuManActivity.this.finish();
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ToastViewTools.initToast(TaoLunZhuManActivity.this, "删除失败");
				}

			}
		});
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
			Contacts2 user = (Contacts2) mlist.get(position);
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putString("name", user.getName());

			bundle.putString("department", user.getDepartment());
			bundle.putString("offphone", user.getOffphone());
			bundle.putString("mobphone", user.getMobphone());
			bundle.putString("email", user.getEmail());
			bundle.putString("headurl", user.getHeadurl());
			intent.putExtras(bundle);
			intent.setClass(TaoLunZhuManActivity.this, MailDetalActivity.class);
			TaoLunZhuManActivity.this.startActivity(intent);

		}

	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
		db = DbUtils.create(this);// db还有其他的一些构造方法，比如含有更新表版本的监听器的
		setContentView(R.layout.taolunzhuactivity);
		setBackListener("成员信息列表", false);
		Intent mIntent = getIntent();
		ID = mIntent.getIntExtra("ID", 1000);

		try {
			bGonggao = db.findFirst(Selector.from(BGonggao.class).where("ID",
					"=", ID));
			Gson gson = new Gson();
			// System.out.println(bGonggao.getContentData());
			mlist = new ArrayList<Contacts2>();
			JSONArray jsonarray = new JSONArray(bGonggao.getContentData());
			int lenth = jsonarray.length();
			for (int i = 0; i < lenth; i++) {
				mlist.add(gson.fromJson(jsonarray.getJSONObject(i).toString(),
						Contacts2.class));
			}
			mNotifyContactAdapter = new NotifyContactAdapterTlz(
					TaoLunZhuManActivity.this, mlist);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	public class NotifyContactAdapterTlz extends BaseAdapter implements
			SectionIndexer {
		private Context mContext;
		private List<Contacts2> UserInfos;// 好友信息
		private BitmapUtils bitmapUtils;
		BitmapDisplayConfig config;

		@SuppressWarnings("unchecked")
		public NotifyContactAdapterTlz(Context mContext, List<Contacts2> UserInfos) {
			this.mContext = mContext;
			this.UserInfos = UserInfos;

			config = new BitmapDisplayConfig();
			config.setLoadingDrawable(mContext.getResources().getDrawable(
					R.drawable.detail_icon_user_default));
			config.setLoadFailedDrawable(mContext.getResources().getDrawable(
					R.drawable.detail_icon_user_default));
			bitmapUtils = new BitmapUtils(mContext);
			// 排序(实现了中英文混排)
			Collections.sort(UserInfos, new PinyinComparator());
		}

		public void updateListView(List<Contacts2> list) {
			this.UserInfos = list;
			Collections.sort(UserInfos, new PinyinComparator());
			this.notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			if (null != UserInfos) {
				return UserInfos.size();
			}
			return 0;
		}

		@Override
		public int getPositionForSection(int section) {
			for (int i = 0; i < UserInfos.size(); i++) {
				Contacts2 user = UserInfos.get(i);
				char firstChar = user.getFirstLetter().charAt(0);
				if (firstChar == section) {
					return i;
				}
			}
			return 0;
		}

		@Override
		public int getSectionForPosition(int position) {
			return 0;
		}

		@Override
		public Object[] getSections() {
			return null;
		}

		@Override
		public Object getItem(int position) {
			return UserInfos.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder ViewHolder = null;
			Contacts2 user = UserInfos.get(position);
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.notify_contact_item, null);
				ViewHolder = new ViewHolder();
				ViewHolder.contactitem_catalog = (TextView) convertView
						.findViewById(R.id.contactitem_catalog);
				ViewHolder.contactitem_avatar_iv = (CircleImageView) convertView
						.findViewById(R.id.contactitem_avatar_iv);
				ViewHolder.contactitem_nick = (TextView) convertView
						.findViewById(R.id.contactitem_nick);
				ViewHolder.contactitem_username = (TextView) convertView
						.findViewById(R.id.contactitem_username);
				ViewHolder.checkbox = (CheckBox) convertView
						.findViewById(R.id.checkbox);
				convertView.setTag(ViewHolder);
			} else {
				ViewHolder = (NotifyContactAdapterTlz.ViewHolder) convertView
						.getTag();
			}

			ViewHolder.checkbox.setVisibility(View.GONE);

			// String firstString =
			// PingYinUtil.converterToFirstSpell(user.getUserName());

			String firstString = user.getFirstLetter();

			if (firstString.length() > 0) {
				firstString = firstString.substring(0, 1);
			}
			if (position == 0) {
				ViewHolder.contactitem_catalog.setText(firstString);
				ViewHolder.contactitem_catalog.setVisibility(View.VISIBLE);
			} else {
				Contacts2 LastUser = UserInfos.get(position - 1);
				// String lastFirstString =
				// PingYinUtil.converterToFirstSpell(LastUser.getUserName());
				String lastFirstString = LastUser.getFirstLetter();
				if (lastFirstString.length() > 0) {
					lastFirstString = lastFirstString.substring(0, 1);
				}
				if (!firstString.equals(lastFirstString)) {
					ViewHolder.contactitem_catalog.setText(firstString);
					ViewHolder.contactitem_catalog.setVisibility(View.VISIBLE);
				} else {
					ViewHolder.contactitem_catalog.setVisibility(View.GONE);
				}
			}

			ViewHolder.contactitem_username.setText(user.getName());
			if (null != user.getHeadurl() && !"".equals(user.getHeadurl())) {
				bitmapUtils.display(ViewHolder.contactitem_avatar_iv,
						user.getHeadurl(), config);
			} else {

			}

			return convertView;
		}

		class ViewHolder {
			TextView contactitem_catalog;
			CircleImageView contactitem_avatar_iv;
			TextView contactitem_username;
			TextView contactitem_nick;
			CheckBox checkbox;

		}

		/**
		 * 得到 选中的人员信息
		 */
		public void putDataPeople() {
			List<Contacts2> UserInfos_temp = new ArrayList<Contacts2>();// 好友信息

			int size = UserInfos.size();
			for (int i = 0; i < size; i++) {
				if (UserInfos.get(i).isHadSeclect()) {
					UserInfos_temp.add(UserInfos.get(i));
				}

				BIntentObj.UserInfos = UserInfos_temp;
			}

		}

	}

}
