package com.jnwat.swmobilegy.dapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.bean.Message;
import com.jnwat.bean.OAProcess;
import com.jnwat.bean.Participatants;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterMessage.ViewHolder;
import com.jnwat.swmobilegy.view.CircleImageView;

public class AdapterParticipants extends BaseAdapter {
	private Activity mContext;
	private List<Participatants> mData;
	public AdapterParticipants(Activity context, List<Participatants> data) {
		mContext = context;
		mData = data;
		// initData();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mData!=null){
			return mData.size();
		}
		return 0;
	}
	public void updateData(){
		if(mData!=null){
			this.notifyDataSetChanged();
		}
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView =LayoutInflater.from(mContext).inflate(R.layout.item_participants, null);
			viewHolder = new ViewHolder();
		    viewHolder.tv_username = (TextView) convertView
				.findViewById(R.id.tv_username);
			
			viewHolder.iv_head =  (ImageView) convertView
					.findViewById(R.id.iv_head);
			viewHolder.tv_tel  = (TextView) convertView.findViewById(R.id.tv_tel);
			
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Participatants participatants= mData.get(position);
		viewHolder.tv_username.setText(participatants.getName());
		viewHolder.tv_tel.setText(participatants.getMobilePhone());
	
		convertView.setTag(viewHolder);
		return  convertView;
	
}

	 class ViewHolder {
		public TextView tv_username;
		public ImageView  iv_head;
		public TextView tv_tel;

	}

	
}
