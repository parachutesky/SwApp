package com.jnwat.swmobilegy.dapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jnwat.swmobilegy.R;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author chang-zhiyuan 课表查询
 */
public class AdapterSearchSchedule extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<HashMap<String, String>> data;

	public AdapterSearchSchedule(Context mcontext,
			List<HashMap<String, String>> mdata) {
		if (null != mdata) {
		} else {
			data = new ArrayList<HashMap<String, String>>();
		}
		data = mdata;
		this.layoutInflater = LayoutInflater.from(mcontext);
		LogUtils.d("初始化课程表适配器");
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 每个convert view都会调用此方法，获得当前所需要的view样式
	@Override
	public int getItemViewType(int position) {
		if (data.get(position).get("TYPE").equals("1")) {

			return 0;
		} else {
			return 1;
			// TODO Auto-generated method stub

		}

	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		// 因为有两种视图，所以返回2
		return 2;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		viewHolder mviewHolder = null;
		viewHolderTitle mViewHolderTitle = null;
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
			case 0:
				convertView = layoutInflater.inflate(
						R.layout.sw_item_title_date, parent, false);
				mViewHolderTitle = new viewHolderTitle();
				mViewHolderTitle.tv_sw_title_date = (TextView) convertView
						.findViewById(R.id.tv_sw_title_date);
				convertView.setTag(mViewHolderTitle);
				break;

			case 1:
				convertView = layoutInflater
						.inflate(R.layout.sw_item_searchschudule_listview,
								parent, false);
				mviewHolder = new viewHolder();
				mviewHolder.textView_sw_item_schedul_time = (TextView) convertView
						.findViewById(R.id.textView_sw_item_schedul_time);

				mviewHolder.textView_sw_item_schedul_course = (TextView) convertView
						.findViewById(R.id.textView_sw_item_schedul_course);

				mviewHolder.textView_sw_item_schedul_positon = (TextView) convertView
						.findViewById(R.id.textView_sw_item_schedul_positon);
				convertView.setTag(mviewHolder);
				break;
			}
		} else {
			switch (type) {
			case 0:
				mViewHolderTitle = (viewHolderTitle) convertView.getTag();
				break;
			case 1:
				mviewHolder = new viewHolder();
				mviewHolder = (viewHolder) convertView.getTag();
				break;
			}

		}
		switch (type) {
		case 0:
			mViewHolderTitle.tv_sw_title_date.setText(data.get(position).get(
					"classdate"));
			break;
		case 1:
			mviewHolder.textView_sw_item_schedul_time.setText(data
					.get(position).get("Sxw"));
			mviewHolder.textView_sw_item_schedul_course.setText(data.get(
					position).get("COURSE"));
			mviewHolder.textView_sw_item_schedul_positon.setText(data.get(
					position).get("Positon"));
			break;

		}

		return convertView;
	}
}

class viewHolder {
	public TextView textView_sw_item_schedul_time;
	public TextView textView_sw_item_schedul_course;
	public TextView textView_sw_item_schedul_positon;

}

class viewHolderTitle {
	public TextView tv_sw_title_date;

}
