package com.jnwat.swmobilegy.mail;

import java.io.File;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jnwat.bean.Contacts;
import com.jnwat.bean.Contacts2;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.view.CircleImageView;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

;
/**
 * 显示 联系人的 adapter
 * 
 * @author Administrator
 * 
 */

public class ContactAdapter extends BaseAdapter implements SectionIndexer {
	private Context mContext;
	private List<Contacts2> UserInfos;// 好友信息
	private BitmapUtils bitmapUtils;
	BitmapDisplayConfig config;

	@SuppressWarnings("unchecked")
	public ContactAdapter(Context mContext, List<Contacts2> UserInfos) {
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
		return UserInfos.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder ViewHolder = null;
		Contacts2 user = UserInfos.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.contact_item, null);
			ViewHolder = new ViewHolder();

		} else {
			ViewHolder = (com.jnwat.swmobilegy.mail.ContactAdapter.ViewHolder) convertView
					.getTag();
		}
		ViewHolder.contactitem_catalog = (TextView) convertView
				.findViewById(R.id.contactitem_catalog);
		ViewHolder.contactitem_avatar_iv = (CircleImageView) convertView
				.findViewById(R.id.contactitem_avatar_iv);
		ViewHolder.contactitem_nick = (TextView) convertView
				.findViewById(R.id.contactitem_nick);
		ViewHolder.contactitem_username = (TextView) convertView
				.findViewById(R.id.contactitem_username);
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
		if (null  !=  user.getHeadurl()&& !"".equals(user.getHeadurl())) {
			bitmapUtils.display(ViewHolder.contactitem_avatar_iv,
					user.getHeadurl(),config);
		} else {
       
		}

		convertView.setTag(ViewHolder);
		return convertView;
	}

	class ViewHolder {
		TextView contactitem_catalog;
		CircleImageView contactitem_avatar_iv;
		TextView contactitem_username;
		TextView contactitem_nick;

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
}
