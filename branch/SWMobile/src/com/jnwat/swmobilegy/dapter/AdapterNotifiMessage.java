package com.jnwat.swmobilegy.dapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.bean.BPushMessageInfo;
import com.jnwat.swmobilegy.R;
import com.lidroid.xutils.BitmapUtils;


public class AdapterNotifiMessage extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<BPushMessageInfo> data;
	private  Context context;
	public AdapterNotifiMessage(Context mcontext, List<BPushMessageInfo> data) {
	    if (null != data) {
	    	this.data= data;
	        } else {
	        	data= new ArrayList<BPushMessageInfo>();
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
			
			convertView = layoutInflater.inflate(
					R.layout.message_listview, null);
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
		String sendTime = message.getMsgSendTime();
		sendTime = sendTime.substring(5);
		int index = sendTime.lastIndexOf(":");
		sendTime = sendTime.substring(0,index);
		viewHolder.tv_messageTime.setText(sendTime);
		viewHolder.tv_messageContent.setText(message.getMsgContent());
		 viewHolder.tv_messageType.setText(message.getTitle());
		 
		if(message.getMsgType()==1){//流程审批
			if(message.getIsRead()==1){//已读
				viewHolder.iv_circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.message_icon_process_read));
			}else{
				viewHolder.iv_circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.message_icon_process_unread));
			}
			
		}else if(message.getMsgType()==2){
			if(message.getIsRead()==1){//已读
				viewHolder.iv_circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.message_icon_notice_read));
			}else{
				viewHolder.iv_circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.message_icon_notice_unread));
			}
			
		}else if(message.getMsgType()==4){
			if(message.getIsRead()==1){//已读
				viewHolder.iv_circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.message_icon_announcement_read));
			}else{
				viewHolder.iv_circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.message_icon_announcement_unread));
			}

		}else if(message.getMsgType()==8){
			if(message.getIsRead()==1){//已读
				viewHolder.iv_circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.message_icon_tips_read));
			}else{
				viewHolder.iv_circleImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.message_icon_tips_unread));

			}
		}
		convertView.setTag(viewHolder);
		return  convertView;
	
}

	 class ViewHolder {
		public TextView tv_messageType;
		public TextView tv_messageTime;
		public TextView tv_messageContent;
		public ImageView iv_circleImageView;

	}

}
