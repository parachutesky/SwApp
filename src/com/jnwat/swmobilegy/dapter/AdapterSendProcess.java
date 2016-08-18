package com.jnwat.swmobilegy.dapter;

import java.util.List;

import com.jnwat.swmobilegy.R;
import com.jnwat.bean.OAProcess;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.jnwat.bean.OAProcess;

public class AdapterSendProcess extends BaseAdapter {
	private List<OAProcess> lisArray;
	private Activity mContext;

	public AdapterSendProcess(Activity Context, List<OAProcess> list) {
		lisArray = list;
		mContext = Context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lisArray.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lisArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View Convertview, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (Convertview == null) {
			viewHolder = new ViewHolder();
			Convertview = LayoutInflater.from(mContext).inflate(
					R.layout.item_sendprocess_listview, null);
			viewHolder.promoterName = (TextView) Convertview
					.findViewById(R.id.tv_promoter_name);
			viewHolder.sendProcessTime = (TextView) Convertview
					.findViewById(R.id.tv_promoter_time);
			viewHolder.sendProcessContent = (TextView) Convertview
					.findViewById(R.id.tv_sendProcess_description);
			viewHolder.promoterImage = (ImageView) Convertview
					.findViewById(R.id.iv_circleImageView_promoter);
		} else {
			viewHolder = (ViewHolder) Convertview.getTag();
		}
		OAProcess process = lisArray.get(position);
		viewHolder.promoterName.setText(process.getPromoter());
		viewHolder.sendProcessTime.setText(process.getDate());
		viewHolder.sendProcessContent.setText(process.getDepartment());
		Convertview.setTag(viewHolder);
		return Convertview;
	}

	public class ViewHolder {
		TextView promoterName, sendProcessTime, sendProcessContent;
		ImageView promoterImage;

	}
}
