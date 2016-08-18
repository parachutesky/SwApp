/**
 * 
 */
package com.jnwat.swmobilegy.dapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.FeedBackListAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author 赵荣林
 * 
 * @category 我的回答  Adapter 
 *
 *
 */
public class MyAnswerListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<Map<String , Object>> data;

	public MyAnswerListAdapter(Context mcontext, List<Map<String , Object>> data) {
	    if (null != data) {
	    	this.data= data;
	        } else {
	        	data= new ArrayList<Map<String , Object>>();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		if (convertView == null) {
			
			convertView = layoutInflater.inflate(
					R.layout.answers_list_items, null);
			viewHolder = new ViewHolder();
		    viewHolder.tv_title = (TextView) convertView
				.findViewById(R.id.title_tv);
			
//			viewHolder.tv_name = (TextView) convertView
//					.findViewById(R.id.name_tv);
			
			viewHolder.tv_time = (TextView) convertView
					.findViewById(R.id.showtime_tv);
			
//			viewHolder.tv_num = (TextView) convertView
//					.findViewById(R.id.info_num_tv);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_title.setText(data.get(position).get("title").toString());
		//viewHolder.tv_name.setText(data.get(position).get("name").toString());
		viewHolder.tv_time.setText(data.get(position).get("date").toString());
		//viewHolder.tv_num.setText(data.get(position).get("num").toString());
		
		convertView.setTag(viewHolder);
		return  convertView;
	
}

	 class ViewHolder {
		public TextView tv_title;
		//public TextView tv_name;
		public TextView tv_time;
		//public TextView tv_num;

	}

}
