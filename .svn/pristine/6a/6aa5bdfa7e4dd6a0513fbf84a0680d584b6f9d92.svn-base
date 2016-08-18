package com.jnwat.swmobilegy.dapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jnwat.bean.Message;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.view.CircleImageView;


public class AdapterMessage extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<Message> data;

	public AdapterMessage(Context mcontext, List<Message> data) {
	    if (null != data) {
	    	this.data= data;
	        } else {
	        	data= new ArrayList<Message>();
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
					R.layout.message_listview, null);
			viewHolder = new ViewHolder();
		    viewHolder.tv_messageType = (TextView) convertView
				.findViewById(R.id.message_tv_Type);
			
			viewHolder.tv_messageTime = (TextView) convertView
					.findViewById(R.id.message_tv_time);
			
			viewHolder.tv_messageContent = (TextView) convertView
					.findViewById(R.id.message_tv_content);
			
			viewHolder.iv_circleImageView = (CircleImageView) convertView
					.findViewById(R.id.message_iv_circleImageView);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Message message = data.get(position);
		viewHolder.tv_messageType.setText(message.getType());
		viewHolder.tv_messageTime.setText(message.getTimeLong());
		viewHolder.tv_messageContent.setText(message.getContent());
		if(message.getType().equals(message.NOTICE_MESSAGE)){
			
		}else if(message.getType().equals(message.ANNOUNCEMENT_MESSAGE)){
			
		}else if(message.getType().equals(message.PROCESS_SCHEDULE)){
			
		}else if(message.getType().equals(message.REMINDER)){
			
		}
		convertView.setTag(viewHolder);
		return  convertView;
	
}

	 class ViewHolder {
		public TextView tv_messageType;
		public TextView tv_messageTime;
		public TextView tv_messageContent;
		public CircleImageView iv_circleImageView;

	}

}
