package com.jnwat.swmobilegy.dapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.bean.BPushMessageInfo;
import com.jnwat.bean.BUserlogin;
import com.jnwat.swmobilegy.R;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

public class AdapterMessageSort extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private DbUtils db;
	private List<String> data = new ArrayList<String>();

	public AdapterMessageSort(Context mcontext, DbUtils mdb) {
		init();
		this.layoutInflater = LayoutInflater.from(mcontext);
		db = mdb;
	}

	@Override
	public int getCount() {
		if (null != data) {
			return data.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		if (convertView == null) {

			convertView = layoutInflater.inflate(
					R.layout.item_message_sort_listview, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_messageType = (TextView) convertView
					.findViewById(R.id.message_tv_Type);

			viewHolder.tv_messageTime = (TextView) convertView
					.findViewById(R.id.message_tv_count);

			viewHolder.iv_circleImageView = (ImageView) convertView
					.findViewById(R.id.message_iv_circleImageView);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String messageSort = data.get(position);
		viewHolder.tv_messageType.setText(messageSort);
		try {
			if (position == 0) {// isRead
				int process = db.findAll(
						Selector.from(BPushMessageInfo.class)
								.where("MsgType", "=", "1")
								.and("isRead", "=", 0)
								.and("LoginName", "=", BUserlogin.NO)).size();
				if (process > 0) {

					viewHolder.iv_circleImageView
							.setImageResource(R.drawable.message_icon_process_unread);
				} else {

					viewHolder.iv_circleImageView
							.setImageResource(R.drawable.message_icon_process_read);
				}

				viewHolder.tv_messageTime.setText(process + "条未读消息");

			} else if (position == 1) {
				int message = db.findAll(
						Selector.from(BPushMessageInfo.class)
								.where("MsgType", "=", "2")
								.and("isRead", "=", 0)
								.and("LoginName", "=", BUserlogin.NO)).size();
				if (message > 0) {
					viewHolder.iv_circleImageView
							.setImageResource(R.drawable.message_icon_notice_unread);
				} else {
					viewHolder.iv_circleImageView
							.setImageResource(R.drawable.message_icon_notice_read);
				}

				viewHolder.tv_messageTime.setText(message + "条未读消息");

			} else if (position == 2) {
				int annou = db.findAll(
						Selector.from(BPushMessageInfo.class)
								.where("MsgType", "=", "4")
								.and("isRead", "=", 0)
								.and("LoginName", "=", BUserlogin.NO)).size();
				if (annou > 0) {
					viewHolder.iv_circleImageView
							.setImageResource(R.drawable.message_icon_announcement_unread);
				} else {
					viewHolder.iv_circleImageView
							.setImageResource(R.drawable.message_icon_announcement_read);
				}

				viewHolder.tv_messageTime.setText(annou + "条未读消息");
			} else if (position == 3) {
				int tips = db.findAll(
						Selector.from(BPushMessageInfo.class)
								.where("MsgType", "=", "8")
								.and("isRead", "=", 0)
								.and("LoginName", "=", BUserlogin.NO)).size();
				if (tips > 0) {
					viewHolder.iv_circleImageView
							.setImageResource(R.drawable.message_icon_tips_unread);
				} else {
					viewHolder.iv_circleImageView
							.setImageResource(R.drawable.message_icon_tips_read);
				}

				viewHolder.tv_messageTime.setText(tips + "条未读消息");
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
	//		e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if (position == 0) {// isRead
				viewHolder.iv_circleImageView
						.setImageResource(R.drawable.message_icon_process_read);
			} else if (position == 1) {
				viewHolder.iv_circleImageView
						.setImageResource(R.drawable.message_icon_notice_read);

			} else if (position == 2) {
				viewHolder.iv_circleImageView
						.setImageResource(R.drawable.message_icon_announcement_read);
			} else if (position == 3) {
				viewHolder.iv_circleImageView
						.setImageResource(R.drawable.message_icon_tips_read);

			}
		}

		convertView.setTag(viewHolder);
		return convertView;

	}

	class ViewHolder {
		public TextView tv_messageType;
		public TextView tv_messageTime;

		public ImageView iv_circleImageView;

	}

	public void init() {
		String[] str = { "流程的审批", "新闻", "短消息", "通知" };

		for (int i = 0; i < str.length; i++) {
			data.add(str[i]);
		}

	}

}
