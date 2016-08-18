package com.jnwat.swmobilegy.dapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jnwat.bean.BMeetingApplyLeade;
import com.jnwat.swmobilegy.R;

/**
 * @author chang-zhiyuan 审核选择部门
 */
public class AdapterAuditingName extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private boolean isShowNub;
	private int index;
	private int clickItemt;
	private List<BMeetingApplyLeade> mlist_getnode;

	public AdapterAuditingName(Context mcontext, boolean ShowNub, int mindex,
			List<BMeetingApplyLeade> list_getnode) {
		this.layoutInflater = LayoutInflater.from(mcontext);
		isShowNub = ShowNub;
		index = mindex;
		this.mlist_getnode = list_getnode;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist_getnode.size();
	}

	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	public void selecItem(int mint) {
		this.clickItemt = mint;

	}

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;

		if (convertView == null) {

			convertView = layoutInflater.inflate(
					R.layout.item_meetingapply_pop_lv, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tv_item_meetingapply_content_key = (TextView) convertView
					.findViewById(R.id.tv_item_meetingapply_content_key);

			viewHolder.tv_item_meetingapply_content_value = (TextView) convertView // 职位
					.findViewById(R.id.tv_item_meetingapply_content_value);

			viewHolder.iv_pop_sec = (ImageView) convertView
					.findViewById(R.id.iv_pop_sec);
			viewHolder.lin_auth = (LinearLayout) convertView
					.findViewById(R.id.lin_auth);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_item_meetingapply_content_key.setText(mlist_getnode.get(
				position).getRecname());

		viewHolder.lin_auth.setBackgroundResource(R.color.erji1e4e4e4);
		// viewHolder.lin_auth.getBackground().setAlpha(80);

		if (mlist_getnode.get(position).getIdididi().equals("")) {
			viewHolder.iv_pop_sec.setVisibility(View.INVISIBLE);
			// nodeid 节点
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
		public LinearLayout lin_auth;
	}

}