package com.jnwat.swmobilegy.dapter;

import com.jnwat.bean.BIntentObj;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterAllProject.ViewHolder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author chang-zhiyuan 审核选择部门
 */
public class AdapterAuditingDept extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private boolean isShowNub;
	private int clickItemt = -1;
	private int lenth;
	private int type;

	public AdapterAuditingDept(Context mcontext, boolean ShowNub,int mtype) {
		this.layoutInflater = LayoutInflater.from(mcontext);
		isShowNub = ShowNub;
		type = mtype;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		try {
			if(type == 1){
				 lenth=  BIntentObj.list_getnode.size();
			}else{
				 lenth=  BIntentObj.list_getreturnnode.size();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return lenth;
	}

	@Override
	public Object getItem(int i) {
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public long getItemId(int i) {
		// TODO Auto-generated method stub
		return i;
	}


	public void selecItem(int mint){
		this.clickItemt = mint;
		
	}


	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder11 mviewHolder = null;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.item_auditing_dept,
					parent, false);
			mviewHolder = new ViewHolder11();
			mviewHolder.textView_auditing_dept = (TextView) convertView
					.findViewById(R.id.textView_auditing_dept);
			mviewHolder.textView_auditing_nub = (TextView) convertView
					.findViewById(R.id.textView_auditing_nub);
			mviewHolder.mLayout = (LinearLayout) convertView
					.findViewById(R.id.mLayout);
			convertView.setTag(mviewHolder);
		} else {

			mviewHolder = (ViewHolder11) convertView.getTag();
		}
		if(clickItemt==position){

			mviewHolder.mLayout.setBackgroundResource(R.color.erji1e4e4e4);
		//mviewHolder.mLayout.getBackground().setAlpha(80);
			
		//	mviewHolder.textView_auditing_nub.setVisibility(View.VISIBLE);
		}else{
			mviewHolder.mLayout.setBackgroundResource(R.color.eerji2f2f2f2);
		}
/*		mviewHolder.textView_auditing_nub.setText(BIntentObj.list_getnode.get(position).getReceNo().split(",").length
				+ "");*/
if(type == 1){
	 mviewHolder.textView_auditing_dept.setText(BIntentObj.list_getnode.get(position).getNodename());
}else{
	 mviewHolder.textView_auditing_dept.setText(BIntentObj.list_getreturnnode.get(position).getNodename());
}


		return convertView;
	}

}

class ViewHolder11 {
	public TextView textView_auditing_dept;
	public TextView textView_auditing_nub;
	public LinearLayout mLayout  ;

}
