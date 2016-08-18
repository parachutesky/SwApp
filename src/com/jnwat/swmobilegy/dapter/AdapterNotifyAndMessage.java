package com.jnwat.swmobilegy.dapter;

import java.util.List;

import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BNoticeMessage;
import com.jnwat.swmobilegy.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author chang-zhiyuan 2016年4月21日 14:41:26 消息公告的适配器
 */
public class AdapterNotifyAndMessage extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<BNoticeMessage> list;

	public AdapterNotifyAndMessage() {

	}

	public AdapterNotifyAndMessage(Context mcontext,
			List<BNoticeMessage> list_temp) {
		this.layoutInflater = LayoutInflater.from(mcontext);
		this.list = list_temp;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int lenth = 0;
		try {
			if (null != list) {
				lenth = list.size();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return lenth;
	}

	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderNotice mviewHolder = null;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.item_noticemessage,
					parent, false);
			mviewHolder = new ViewHolderNotice();
			mviewHolder.tv_noticemessage_title = (TextView) convertView
					.findViewById(R.id.tv_noticemessage_title);
			/*
			 * mviewHolder.tv_noticemessage_content = (TextView) convertView
			 * .findViewById(R.id.tv_noticemessage_content);
			 */
			mviewHolder.tv_noticemessage_time = (TextView) convertView
					.findViewById(R.id.tv_noticemessage_time);
			mviewHolder.iv_point = (ImageView) convertView
					.findViewById(R.id.iv_point);
			convertView.setTag(mviewHolder);
		} else {

			mviewHolder = (ViewHolderNotice) convertView.getTag();
		}
		// mviewHolder.mLayout.setBackgroundResource(R.color.eerji2f2f2f2);
		if (list.get(position).getREADSTATUS().equals("已读")) {
			mviewHolder.tv_noticemessage_title.setText(list.get(position)
					.getTITLE() + "(已读)");
			mviewHolder.iv_point.setVisibility(View.GONE);
/*			mviewHolder.tv_noticemessage_title
					.setTextColor(android.graphics.Color.RED);*/
		} else {
			mviewHolder.tv_noticemessage_title.setText(list.get(position)
					.getTITLE());
			mviewHolder.iv_point.setVisibility(View.VISIBLE);
/*			mviewHolder.tv_noticemessage_title
			.setTextColor(android.graphics.Color.BLACK);*/
		}
		mviewHolder.tv_noticemessage_time.setText(list.get(position)
				.getSENDDATE());
		// mviewHolder.tv_noticemessage_content.setText(Html.fromHtml(list.get(position).getCONTENT()));
		// mviewHolder.tv_noticemessage_user.setText(list.get(position).getSENDUSER());


		return convertView;
	}

	class ViewHolderNotice {
		public TextView tv_noticemessage_title;
		// public TextView tv_noticemessage_content;
		// public TextView tv_noticemessage_user;
		public TextView tv_noticemessage_time;
		public ImageView iv_point;
		
	}

}
