package com.jnwat.swmobilegy.dapter;

import java.util.ArrayList;
import java.util.List;

import com.jnwat.bean.BKaiBanTongZhi;
import com.jnwat.swmobilegy.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author chang-zhiyuan
 * 开班通知
 */
public class AdapterKaibanTongz extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<BKaiBanTongZhi> data;
	private Context context;

	public AdapterKaibanTongz(Context mcontext, List<BKaiBanTongZhi> data) {
		if (null != data) {
			this.data = data;
		} else {
			data = new ArrayList<BKaiBanTongZhi>();
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

			convertView = layoutInflater.inflate(R.layout.item_listview_kaiban,
					null);
			viewHolder = new ViewHolder();
/*			viewHolder.tv_messageType = (TextView) convertView
					.findViewById(R.id.message_tv_Type);*/

			viewHolder.tv_noticemessage_time = (TextView) convertView
					.findViewById(R.id.tv_noticemessage_time);

			viewHolder.tv_noticemessage_title = (TextView) convertView
					.findViewById(R.id.tv_noticemessage_title);

			/*
			 * viewHolder.iv_circleImageView = (ImageView) convertView
			 * .findViewById(R.id.message_iv_circleImageView);
			 */

			viewHolder.iv_point = (ImageView) convertView
					.findViewById(R.id.iv_point);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		BKaiBanTongZhi message = data.get(position);
		viewHolder.tv_noticemessage_time.setText(message.getMsgSendTime());
		viewHolder.tv_noticemessage_title.setText(message.getMsgContent());
		// viewHolder.tv_messageType.setText(message.getTitle());

		if (!message.getIsRead() .equals("0") ) {
			viewHolder.iv_point.setVisibility(View.GONE);
			/*
			 * viewHolder.iv_circleImageView.setImageDrawable(context
			 * .getResources().getDrawable(
			 * R.drawable.message_icon_notice_read)); } else {
			 * viewHolder.iv_circleImageView.setImageDrawable(context
			 * .getResources().getDrawable(
			 * R.drawable.message_icon_notice_unread)); }
			 */

		} else {

			viewHolder.iv_point.setVisibility(View.VISIBLE);
		}
		convertView.setTag(viewHolder);
		return convertView;

	}

	class ViewHolder {
		public TextView tv_noticemessage_time;
		public TextView tv_noticemessage_title;
		// public ImageView iv_circleImageView;
		public ImageView iv_point;

	}

}
