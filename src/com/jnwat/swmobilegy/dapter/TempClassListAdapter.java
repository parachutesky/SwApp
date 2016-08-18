/**
 * 
 */
package com.jnwat.swmobilegy.dapter;

import java.util.ArrayList;
import java.util.List;

import com.jnwat.bean.AccomdationInfo;
import com.jnwat.bean.Message;
import com.jnwat.bean.TempClassBean;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.AdapterMessage.ViewHolder;
import com.jnwat.swmobilegy.view.CircleImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author zhaorl
 * 
 *         搭班列表适配器
 * 
 */
public class TempClassListAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private List<TempClassBean> data;

	public TempClassListAdapter(Context mcontext, List<TempClassBean> data) {
		if (null != data) {
			this.data = data;
		} else {
			data = new ArrayList<TempClassBean>();
		}
		this.data = data;
		this.layoutInflater = LayoutInflater.from(mcontext);
	}

	@Override
	public int getCount() {
		Log.d("TempClassListAdapter", "tempList size ==>>" + data.size());
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
					R.layout.temp_class_layout_items, null);
			viewHolder = new ViewHolder();

			viewHolder.tv_sw_trainingCourse_name = (TextView) convertView
					.findViewById(R.id.tv_sw_trainingCourse_name);

			viewHolder.tv_sw_trainingCourse_periodNum = (TextView) convertView
					.findViewById(R.id.tv_sw_trainingCourse_periodNum);

			viewHolder.tv_sw_trainingCourse_trainType = (TextView) convertView
					.findViewById(R.id.tv_sw_trainingCourse_trainType);

			viewHolder.tv_sw_trainingCourse_startTime = (TextView) convertView
					.findViewById(R.id.tv_sw_trainingCourse_startTime);

			viewHolder.tv_sw_trainingCourse_endTime = (TextView) convertView
					.findViewById(R.id.tv_sw_trainingCourse_endTime);

			viewHolder.tv_sw_trainingCourse_trainPeopNum = (TextView) convertView
					.findViewById(R.id.tv_sw_trainingCourse_trainPeopNum);

			viewHolder.tv_sw_trainingCourse_trainingDays = (TextView) convertView
					.findViewById(R.id.tv_sw_trainingCourse_trainingDays);

			viewHolder.tv_sw_trainingCourse_respPeop = (TextView) convertView
					.findViewById(R.id.tv_sw_trainingCourse_respPeop);

			viewHolder.tv_sw_trainingCourse_classRoom = (TextView) convertView
					.findViewById(R.id.tv_sw_trainingCourse_classRoom);

			viewHolder.rv_accomdation_label = (TextView) convertView
					.findViewById(R.id.tv_accomdation_label);

			viewHolder.accom_layout = (LinearLayout) convertView
					.findViewById(R.id.accomdation_layout);

			viewHolder.layout_1 = (RelativeLayout) convertView
					.findViewById(R.id.layout_1);
			viewHolder.layout_2 = (RelativeLayout) convertView
					.findViewById(R.id.layout_2);
			viewHolder.layout_3 = (RelativeLayout) convertView
					.findViewById(R.id.layout_3);

			viewHolder.tv_house_No1 = (TextView) convertView
					.findViewById(R.id.tv_house_No1);
			viewHolder.tv_houseNo1_people_num = (TextView) convertView
					.findViewById(R.id.tv_houseNo1_people_num);
			viewHolder.tv_house_No2 = (TextView) convertView
					.findViewById(R.id.tv_house_No2);
			viewHolder.tv_houseNo2_people_num = (TextView) convertView
					.findViewById(R.id.tv_houseNo2_people_num);
			viewHolder.tv_house_No3 = (TextView) convertView
					.findViewById(R.id.tv_house_No3);
			viewHolder.tv_houseNo3_people_num = (TextView) convertView
					.findViewById(R.id.tv_houseNo3_people_num);
			// 备注
			viewHolder.tv_sw_trainingCourse_remark = (TextView) convertView
					.findViewById(R.id.tv_sw_trainingCourse_remark);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		TempClassBean bean = data.get(position);

		viewHolder.tv_sw_trainingCourse_name.setText(bean.getProjectname());
		viewHolder.tv_sw_trainingCourse_periodNum.setText(bean.getCode());
		viewHolder.tv_sw_trainingCourse_trainType
				.setText(bean.getProjecttype());
		viewHolder.tv_sw_trainingCourse_startTime.setText(bean.getStartdate());
		viewHolder.tv_sw_trainingCourse_endTime.setText(bean.getEnddate());
		viewHolder.tv_sw_trainingCourse_trainPeopNum.setText(bean.getPronum());
		viewHolder.tv_sw_trainingCourse_trainingDays.setText(bean.getDays());
			if(bean.getClient().equals("null")){
				viewHolder.tv_sw_trainingCourse_respPeop.setText("无");
				viewHolder.tv_sw_trainingCourse_respPeop.setTextColor(android.graphics.Color.RED);
			}else{
				viewHolder.tv_sw_trainingCourse_respPeop.setText(bean.getClient());
			}

		viewHolder.tv_sw_trainingCourse_classRoom.setText(bean.getClassroom());
		if (bean.getRemark().equals("null")) {
			viewHolder.tv_sw_trainingCourse_remark.setText("");
		} else {
			viewHolder.tv_sw_trainingCourse_remark.setText(bean.getRemark());
		}

		List<AccomdationInfo> list = bean.getAccommodation();
		if (list.size() > 0) {

			int size = list.size();
			viewHolder.rv_accomdation_label.setVisibility(View.VISIBLE);
			viewHolder.accom_layout.setVisibility(View.VISIBLE);

			if (size == 1) {

				viewHolder.tv_house_No1.setText(bean.getAccommodation().get(0)
						.getBuilding()
						+ "号楼");
				viewHolder.tv_houseNo1_people_num.setText(bean
						.getAccommodation().get(0).getPersons());
				viewHolder.layout_2.setVisibility(View.GONE);
				viewHolder.layout_3.setVisibility(View.GONE);
			} else if (size == 2) {
				viewHolder.tv_house_No1.setText(bean.getAccommodation().get(0)
						.getBuilding()
						+ "号楼");
				viewHolder.tv_houseNo1_people_num.setText(bean
						.getAccommodation().get(0).getPersons());

				viewHolder.tv_house_No2.setText(bean.getAccommodation().get(1)
						.getBuilding()
						+ "号楼");
				viewHolder.tv_houseNo2_people_num.setText(bean
						.getAccommodation().get(1).getPersons());
				viewHolder.layout_3.setVisibility(View.GONE);
			} else if (size == 3) {

				viewHolder.tv_house_No1.setText(bean.getAccommodation().get(0)
						.getBuilding()
						+ "号楼");
				viewHolder.tv_houseNo1_people_num.setText(bean
						.getAccommodation().get(0).getPersons());
				viewHolder.tv_house_No2.setText(bean.getAccommodation().get(1)
						.getBuilding()
						+ "号楼");
				viewHolder.tv_houseNo2_people_num.setText(bean
						.getAccommodation().get(1).getPersons());
				viewHolder.tv_house_No3.setText(bean.getAccommodation().get(2)
						.getBuilding()
						+ "号楼");
				viewHolder.tv_houseNo3_people_num.setText(bean
						.getAccommodation().get(2).getPersons());
			}

		} else {
			viewHolder.rv_accomdation_label.setVisibility(View.GONE);
			viewHolder.accom_layout.setVisibility(View.GONE);
		}

		convertView.setTag(viewHolder);
		return convertView;
	}

	class ViewHolder {

		private TextView tv_sw_trainingCourse_name, // 项目名
				tv_sw_trainingCourse_periodNum, // 期数
				tv_sw_trainingCourse_trainType,// 类型
				tv_sw_trainingCourse_startTime,
				tv_sw_trainingCourse_endTime,
				tv_sw_trainingCourse_trainPeopNum,// 人数
				tv_sw_trainingCourse_trainingDays,
				tv_sw_trainingCourse_respPeop,// 联系人
				tv_sw_trainingCourse_classRoom,
				rv_accomdation_label,
				tv_house_No1,// 第一个楼号
				tv_houseNo1_people_num, // 第一个楼的人数
				tv_house_No2, tv_houseNo2_people_num,
				tv_house_No3,
				tv_houseNo3_people_num, tv_sw_trainingCourse_remark; // 备注

		private LinearLayout accom_layout;

		private RelativeLayout layout_1;
		private RelativeLayout layout_2;
		private RelativeLayout layout_3;

	}

}
