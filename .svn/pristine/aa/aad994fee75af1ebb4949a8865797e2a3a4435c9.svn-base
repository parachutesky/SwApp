package com.jnwat.swmobilegy.dapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.bean.ApplicaInfo;
import com.jnwat.swmobilegy.R;


public class AdapterLeaveManage extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<ApplicaInfo> mData;
    private Activity mContext;
	public AdapterLeaveManage(Activity context, List<ApplicaInfo> data) {
		mContext = context;
		mData    = 	data;	
	}

	@Override
	public int getCount() {
		if (null != mData) {
			return mData.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	public void upate(){
		if(mData!=null){
			this.notifyDataSetChanged();
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView =LayoutInflater.from(mContext).inflate(R.layout.item_leave_manage,null);
			
			viewHolder = new ViewHolder();
		    viewHolder.tv_event = (TextView) convertView
				.findViewById(R.id.tv_event);
			
			viewHolder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			
			viewHolder.iv_circleImageView = (ImageView) convertView
					.findViewById(R.id.iv_head);
			
			viewHolder.tv_username = (TextView) convertView
					.findViewById(R.id.tv_username);
			viewHolder.tv_adutor = (TextView) convertView
					.findViewById(R.id.tv_auditor);
			viewHolder.tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ApplicaInfo applicaInfo = mData.get(position);
		viewHolder.tv_event.setText(applicaInfo.getContent());
		viewHolder.tv_time.setText(applicaInfo.getTime());
		viewHolder.tv_username.setText(applicaInfo.getProposer());
		viewHolder.tv_adutor.setText(applicaInfo.getAdutior());
		viewHolder.tv_status.setText(applicaInfo.getStatus());
		viewHolder.tv_event.setText(applicaInfo.getContent());
		viewHolder.tv_event.setText(applicaInfo.getContent());
		
		convertView.setTag(viewHolder);
		return  convertView;
	
}

	 class ViewHolder {
		public TextView tv_event;
		public TextView tv_time;
		public TextView tv_username;
		public TextView tv_adutor;   //处理人 或  待处理人
		public TextView tv_status; //状态
		public ImageView iv_circleImageView;

	}

}
