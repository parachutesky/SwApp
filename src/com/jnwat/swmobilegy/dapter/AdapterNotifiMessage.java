package com.jnwat.swmobilegy.dapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.bean.BPushMessageInfo;
import com.jnwat.swmobilegy.R;

@SuppressLint("SimpleDateFormat")
public class AdapterNotifiMessage extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<BPushMessageInfo> data;
	private Context context;
	long five_time = 1000 * 60 * 5;
	long ten_time = 1000 * 60 * 10;
	long thir_time = 1000 * 60 * 30; // 30分钟
	long five_hour = 1000 * 60 * 300;
	long tf_hour = 1000 * 60 * 60 * 24;

	public AdapterNotifiMessage(Context mcontext, List<BPushMessageInfo> data) {
		if (null != data) {
			this.data = data;
		} else {
			data = new ArrayList<BPushMessageInfo>();
		}
		this.data = data;
		this.layoutInflater = LayoutInflater.from(mcontext);
		context = mcontext;
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

			convertView = layoutInflater.inflate(R.layout.message_listview,
					null);
			viewHolder = new ViewHolder();
			viewHolder.tv_messageType = (TextView) convertView
					.findViewById(R.id.message_tv_Type);

			viewHolder.tv_messageTime = (TextView) convertView
					.findViewById(R.id.message_tv_time);

			viewHolder.tv_messageContent = (TextView) convertView
					.findViewById(R.id.message_tv_content);

			viewHolder.iv_circleImageView = (ImageView) convertView
					.findViewById(R.id.message_iv_circleImageView);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		BPushMessageInfo message = data.get(position);
		Long sendTime = Long.parseLong(message.getMsgSendTime()) * 1000;
		viewHolder.tv_messageTime.setText(getCruentTime(sendTime));
		viewHolder.tv_messageContent.setText(message.getMsgContent());
		viewHolder.tv_messageType.setText(message.getTitle());

		if (message.getMsgType() == 1) {// 流程审批
			if (message.getIsRead() == 1) {// 已读
				viewHolder.iv_circleImageView.setImageDrawable(context
						.getResources().getDrawable(
								R.drawable.message_icon_process_read));
			} else {
				viewHolder.iv_circleImageView.setImageDrawable(context
						.getResources().getDrawable(
								R.drawable.message_icon_process_unread));
			}

		} else if (message.getMsgType() == 2) {
			if (message.getIsRead() == 1) {// 已读
				viewHolder.iv_circleImageView.setImageDrawable(context
						.getResources().getDrawable(
								R.drawable.message_icon_notice_read));
			} else {
				viewHolder.iv_circleImageView.setImageDrawable(context
						.getResources().getDrawable(
								R.drawable.message_icon_notice_unread));
			}

		} else if (message.getMsgType() == 4) {
			if (message.getIsRead() == 1) {// 已读
				viewHolder.iv_circleImageView.setImageDrawable(context
						.getResources().getDrawable(
								R.drawable.message_icon_announcement_read));
			} else {
				viewHolder.iv_circleImageView.setImageDrawable(context
						.getResources().getDrawable(
								R.drawable.message_icon_announcement_unread));
			}

		} else if (message.getMsgType() == 8) {
			if (message.getIsRead() == 1) {// 已读
				viewHolder.iv_circleImageView.setImageDrawable(context
						.getResources().getDrawable(
								R.drawable.message_icon_tips_read));
			} else {
				viewHolder.iv_circleImageView.setImageDrawable(context
						.getResources().getDrawable(
								R.drawable.message_icon_tips_unread));

			}
		}
		convertView.setTag(viewHolder);
		return convertView;

	}

	class ViewHolder {
		public TextView tv_messageType;
		public TextView tv_messageTime;
		public TextView tv_messageContent;
		public ImageView iv_circleImageView;

	}

	/**
	 * @return 得到当前时间
	 * 
	 *         long five_time = 1000*60*5; long ten_time = 1000*60*10; long
	 *         thir_time = 1000*60*30; //30分钟 long five_hour = 1000*60*300; long
	 *         tf_hour = 1000*60*60*24;
	 */
	public String getCruentTime(long time) {
		System.out.println(time);

		System.out.println(timestampToDate(time));
		Long cruTime = System.currentTimeMillis();
		System.out.println("当前时间" + cruTime);
		long start_end = cruTime - time;

		System.out.println("start_end:" + start_end);
		if (start_end < five_time) {// 小于五分钟
			return "刚刚";

		}
		if (start_end < ten_time) {// 小于10分钟
			return "10分钟前";

		}
		if (start_end < thir_time) {// 小于30分钟
			return "30分钟前";

		}
		if (start_end < five_hour) {// 五小时分钟
			return "五小时前";

		}
		if (start_end < tf_hour) {// 24小时分钟
			return "昨天";
		}
		return timestampToDate(time);

	}

	public String timestampToDate(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sd = sdf.format(new Date(time));
		System.out.println("sd:" + sd);
		String sendTime = sd.substring(5);
		int index = sendTime.lastIndexOf(":");
		sendTime = sendTime.substring(0, index);

		return sendTime;
	}
}
