/**
 * 
 */
package com.jnwat.swmobilegy.dapter;

import java.util.ArrayList;
import java.util.List;

import com.jnwat.bean.BUserTasks;
import com.jnwat.bean.UnEducationBean;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterTaskInfo.ViewHolder;
import com.jnwat.swmobilegy.view.CircleImageView;
import com.lidroid.xutils.BitmapUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author 非培训适配器
 * 
 */
public class UnEducationListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<UnEducationBean> data;

	public UnEducationListAdapter(Context mcontext, List<UnEducationBean> data) {
		if (null != data) {
			this.data = data;
		} else {
			data = new ArrayList<UnEducationBean>();
		}
		this.data = data;
		this.layoutInflater = LayoutInflater.from(mcontext);
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

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder;
		if (view == null) {

			view = layoutInflater.inflate(R.layout.item_uneduted_list, null);
			viewHolder = new ViewHolder();
			viewHolder.tv_date = (TextView) view
					.findViewById(R.id.tv_item_date);
			viewHolder.tv_title = (TextView) view
					.findViewById(R.id.tv_item_title);
			viewHolder.tv_type = (TextView) view
					.findViewById(R.id.item_type_tv);

			view.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) view.getTag();
		}

		viewHolder.tv_date.setText("时间: " + data.get(position).getStartTime()
				+ "  至  " + data.get(position).getEndTime());

		viewHolder.tv_title.setText(data.get(position).getTitle());
		viewHolder.tv_type.setText("类别: " + data.get(position).getType());
		view.setTag(viewHolder);
		return view;
	}

	class ViewHolder {
		public TextView tv_date;
		public TextView tv_title;
		public TextView tv_type;

	}

}
