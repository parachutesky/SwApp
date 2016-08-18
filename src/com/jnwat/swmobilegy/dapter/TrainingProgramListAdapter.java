/**
 * 
 */
package com.jnwat.swmobilegy.dapter;

import java.util.List;
import java.util.Map;

import com.jnwat.bean.UnEducationBean;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.UnEducationListAdapter.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author zhaorl
 * 
 * 
 * 
 */
public class TrainingProgramListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<Map<String, Object>> data;
	private Context context;

	public TrainingProgramListAdapter(Context mcontext,
			List<Map<String, Object>> data) {
		context = mcontext;
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

			view = layoutInflater.inflate(R.layout.item_training_count, null);
			viewHolder = new ViewHolder();

			viewHolder.tv_title = (TextView) view
					.findViewById(R.id.tv_item_meetingapply_content_key);
			viewHolder.tv_value = (TextView) view
					.findViewById(R.id.tv_item_meetingapply_content_value);

			view.setTag(viewHolder);
		} else {

			viewHolder = (ViewHolder) view.getTag();
		}

		/*
		 * viewHolder.tv_title.setText(data.get(position).get("index").toString()
		 * );
		 * viewHolder.tv_value.setText(data.get(position).get("value").toString
		 * ());
		 */

		if (data.get(position).get("index").toString().equals("办班情况统计图表")) {
			viewHolder.tv_title.setText(data.get(position).get("index")
					.toString());
			Drawable drawable = context.getResources().getDrawable(
					R.drawable.right);
			drawable.setBounds(0, 0, drawable.getMinimumWidth(),
					drawable.getMinimumHeight()); // 设置边界
			viewHolder.tv_value
					.setCompoundDrawables(null, null, drawable, null);// 画在右边
			viewHolder.tv_value.setText("");
		} else {
			System.out.println("position>>" + position);
			viewHolder.tv_title.setText(data.get(position).get("index")
					.toString());
			viewHolder.tv_value.setText(data.get(position).get("value")
					.toString());
		}
		return view;
	}

	class ViewHolder {
		public TextView tv_title;
		public TextView tv_value;

	}

}
