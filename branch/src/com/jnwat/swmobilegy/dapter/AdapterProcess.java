package com.jnwat.swmobilegy.dapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnwat.bean.OAProcess;
import com.jnwat.swmobilegy.R;

public class AdapterProcess extends BaseExpandableListAdapter {
	private Activity mContext;
	private List<String> groupArray;
	private List<List<OAProcess>> childArray;

	public List<List<OAProcess>> getChildArray() {
		return childArray;
	}

	public void setChildArray(List<List<OAProcess>> childArray) {
		this.childArray = childArray;
	}

	public AdapterProcess(Activity context, List<String> group,
			List<List<OAProcess>> child) {
		mContext = context;
		groupArray = group;
		childArray = child;
		// initData();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub

		return childArray.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		String str = childArray.get(groupPosition).get(childPosition).getName();
		return getChildView(str);

	}

	public View getChildView(String str) {

		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View v = mInflater.inflate(R.layout.process_child, null);
		TextView txt = (TextView) v.findViewById(R.id.child_txt);
		txt.setText(str);
		return v;

	}

	public View getGroupView(String str, boolean isExpanded, int position) {

		LayoutInflater mInflater = LayoutInflater.from(mContext);
		View v = mInflater.inflate(R.layout.process_group, null);
		TextView txt = (TextView) v.findViewById(R.id.group_txt);
		ImageView img = (ImageView) v
				.findViewById(R.id.process_group_indicator);
		if (isExpanded) {
			img.setImageResource(R.drawable.up_indicator);
		} else {
			img.setImageResource(R.drawable.down_indicator);
		}

		txt.setText(str);

		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return childArray.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupArray.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupArray.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		String string = groupArray.get(groupPosition);
		return getGroupView(string, isExpanded, groupPosition);

	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	public void initData() {
		/*
		 * String[] group =
		 * {"全部流程","公文管理","单位管理","费用报销","申请借款","对外付账","物资采购","合同评审"};
		 * 
		 * groupArray = new ArrayList<String>(); for(int i =0; i < group.length;
		 * i ++){ groupArray.add(group[i]); } childArray = new
		 * ArrayList<List<String>>();
		 * 
		 * List<String> tempList1 = new ArrayList<String>(); List<String>
		 * tempList2 = new ArrayList<String>(); List<String> tempList3 = new
		 * ArrayList<String>(); String[] str =
		 * {"部门费用报销审批流程","个人费用报销审批","招待申请(金额>1000元)"}; String[] copy =
		 * {"流程一(待补充)","流程二(待补充)","流程三(待补充)"}; int size = 3; for(int i =0; i <
		 * size ; i ++){ tempList1.add(copy[i]); } for(int i =0; i < size ; i
		 * ++){ tempList2.add(str[i]); } for(int i=0;i < group.length;i ++){
		 * if(i==0){ childArray.add(tempList3); } if(i==3){
		 * childArray.add(tempList2); }else{ childArray.add(tempList1); } }
		 */

	}
}
