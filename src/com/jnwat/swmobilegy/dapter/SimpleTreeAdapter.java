package com.jnwat.swmobilegy.dapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.mainpager.deptmanager.bean.Node;
import com.jnwat.swmobilegy.mainpager.deptmanager.bean.TreeListViewAdapter;

public class SimpleTreeAdapter<T> extends TreeListViewAdapter<T> {

	public SimpleTreeAdapter(ListView mTree, Context context, List<T> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException {
		super(mTree, context, datas, defaultExpandLevel);
	}

	@Override
	public View getConvertView(final Node node, int position, View convertView,
			ViewGroup parent) {

		ViewHolder viewHolder = null;
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.icon = (ImageView) convertView
					.findViewById(R.id.id_treenode_icon);
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.id_treenode_label);
			viewHolder.checkbox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (node.getIcon() == -1) {
			viewHolder.icon.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.icon.setVisibility(View.VISIBLE);
			viewHolder.icon.setImageResource(node.getIcon());

		}
		// 设置选择状态
		viewHolder.checkbox.setChecked(node.isHadSeclect());
		viewHolder.checkbox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				int allLenth = mAllNodes.size();

				// 等于0 表示是根节点 表示全部
				if (node.getLevel() == 0) {
					 isselectAll = (!node.isHadSeclect());
					if (node.isHadSeclect()) {
						for (int i = 0; i < allLenth; i++) {
							mAllNodes.get(i).setHadSeclect(false);
						}
					} else {
						for (int i = 0; i < allLenth; i++) {
							mAllNodes.get(i).setHadSeclect(true);
						}

					}

					/*
					 * for (int i = 0; i < allLenth; i++) { if
					 * (mAllNodes.get(i).isHadSeclect()) {
					 * mAllNodes.get(i).setHadSeclect(false); } else {
					 * mAllNodes.get(i).setHadSeclect(true); } }
					 */
				}

				// 等于1 表示是次根节点 表示部门
				if (node.getLevel() == 1) {

					int child_lenth = node.getChildren().size();
					if (node.isHadSeclect()) {

						node.setHadSeclect(!node.isHadSeclect());
						for (int i = 0; i < child_lenth; i++) { // mAllNodes.get(i).
							node.getChildren().get(i).setHadSeclect(false);
						}
					} else {
						node.setHadSeclect(!node.isHadSeclect());
						for (int i = 0; i < child_lenth; i++) {
							node.getChildren().get(i).setHadSeclect(true);
						}
					}

				}
				// 单个点击
				if (node.getLevel() == 2) {

					if (node.isHadSeclect()) {
						node.setHadSeclect(false);
					} else {
						node.setHadSeclect(true);
					}

				}
				/*
				 * for (int i = 0; i < allLenth; i++) {
				 * 
				 * System.out.println(mAllNodes.get(i).getName() + ":" +
				 * mAllNodes.get(i).isHadSeclect()); }
				 */

				// mNodes = TreeHelper.filterVisibleNode(mAllNodes);
				RefreshView();
			}
		});
		viewHolder.label.setText(node.getName());

		return convertView;
	}

	private class ViewHolder {
		private ImageView icon;
		private TextView label;
		private CheckBox checkbox;
	}

	/**
	 * 刷新视图
	 */
	public void refreshView() {
		SimpleTreeAdapter.this.notifyDataSetChanged();
	}

}
