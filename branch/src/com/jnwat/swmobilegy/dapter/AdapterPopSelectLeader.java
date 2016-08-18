package com.jnwat.swmobilegy.dapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.bean.BMeetingApplyLeade;
import com.jnwat.swmobilegy.R;
import com.lidroid.xutils.util.LogUtils;

public class AdapterPopSelectLeader extends BaseAdapter {

	private ArrayList<BMeetingApplyLeade> list_getnode;
	private LayoutInflater layoutInflater;


	public AdapterPopSelectLeader(
			ArrayList<BMeetingApplyLeade> mlist_getnode, Context mContext) {
		this.list_getnode = mlist_getnode;
		this.layoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != list_getnode) {
			return list_getnode.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list_getnode.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;

		if (convertView == null) {

			convertView = layoutInflater.inflate(
					R.layout.item_meetingapply_pop_lv, parent,false);
			viewHolder = new ViewHolder();
			viewHolder.tv_item_meetingapply_content_key = (TextView) convertView
					.findViewById(R.id.tv_item_meetingapply_content_key);

			viewHolder.tv_item_meetingapply_content_value = (TextView) convertView // 职位
					.findViewById(R.id.tv_item_meetingapply_content_value);

			viewHolder.iv_pop_sec = (ImageView) convertView
					.findViewById(R.id.iv_pop_sec);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_item_meetingapply_content_key.setText(list_getnode.get(
				position).getRecname());
		viewHolder.tv_item_meetingapply_content_value.setText(list_getnode.get(
				position).getNodename());
		
		if (list_getnode.get(position).getIdididi().equals("")) {
			viewHolder.iv_pop_sec.setVisibility(View.INVISIBLE);
			// nodeid  节点
		} else {
			viewHolder.iv_pop_sec.setVisibility(View.VISIBLE);
		}
		convertView.setTag(viewHolder);
		return convertView;
	}

	class ViewHolder {
		public TextView tv_item_meetingapply_content_key;
		public TextView tv_item_meetingapply_content_value;
		public ImageView iv_pop_sec;

	}
}
