package com.jnwat.swmobilegy.dapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jnwat.bean.BUserTasks;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.view.CircleImageView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;

@SuppressLint("InflateParams")
public class AdapterTaskInfo extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<BUserTasks> data;
	private BitmapUtils bitmapUtils;
	private BitmapDisplayConfig config;

	public AdapterTaskInfo(Context mcontext, List<BUserTasks> data,
			BitmapUtils mbitmapUtils) {
		config = new BitmapDisplayConfig();
		config.setLoadingDrawable(mcontext.getResources().getDrawable(
				R.drawable.detail_icon_user_default));
		config.setLoadFailedDrawable(mcontext.getResources().getDrawable(
				R.drawable.detail_icon_user_default));
		if (null != data) {
			this.data = data;
		} else {
			data = new ArrayList<BUserTasks>();
		}
		this.data = data;
		this.layoutInflater = LayoutInflater.from(mcontext);
		this.bitmapUtils = mbitmapUtils;
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
			view = layoutInflater.inflate(R.layout.item_workevent_listview,
					null);
			viewHolder = new ViewHolder();
			viewHolder.tv_workevent_name = (TextView) view
					.findViewById(R.id.tv_workevent_name);

			viewHolder.tv_workevent_description = (TextView) view
					.findViewById(R.id.tv_workevent_description);

			viewHolder.tv_workevent_time = (TextView) view
					.findViewById(R.id.tv_workevent_time);

			viewHolder.iv_circleImageView = (CircleImageView) view
					.findViewById(R.id.iv_circleImageView);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tv_workevent_name.setText(data.get(position).getFlowName());
		viewHolder.tv_workevent_description.setText(data.get(position)
				.getTaskName());
		viewHolder.tv_workevent_time.setText(data.get(position).getStartDate());
		String state = "";
		state = data.get(position).getState();
		if (null!=state&&state.equals("")) {
			viewHolder.tv_workevent_time.setTextColor(Color.GRAY);// 灰色
		} else if (null!=state&&state.equals("逾期")) {
			viewHolder.tv_workevent_time.setTextColor(Color
					.parseColor("#cc0000"));// 红色
		}

		// 加载网络图片
		if (data.get(position).getSender_Photo().equals("")) {
		/*	bitmapUtils.display(viewHolder.iv_circleImageView,
					"http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg", config);*/
		} else {
			bitmapUtils.display(viewHolder.iv_circleImageView,
					data.get(position).getSender_Photo(),config);
		}
		view.setTag(viewHolder);
		return view;
	}

	class ViewHolder {
		public TextView tv_workevent_name;
		public TextView tv_workevent_description;
		public TextView tv_workevent_time;
		public TextView tv_workevent_state;
		public CircleImageView iv_circleImageView;

	}

}
