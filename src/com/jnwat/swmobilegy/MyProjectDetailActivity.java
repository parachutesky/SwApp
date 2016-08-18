package com.jnwat.swmobilegy;

import java.util.List;

import com.jnwat.analysis.AGetProjectDetail;
import com.jnwat.bean.AccomdationInfo;
import com.jnwat.bean.BIntentObj;
import com.jnwat.bean.BProjectDetail;
import com.jnwat.bean.TempClassBean;
import com.jnwat.config.AppConfig;
import com.jnwat.dialog.PopLoginDialog;
import com.jnwat.swmobilegy.R;
import com.jnwat.swmobilegy.dapter.TempClassListAdapter;
import com.jnwat.swmobilegy.mainpager.SearchScheduleActivity;
import com.jnwat.swmobilegy.mainpager.SearchScheduleDetailActivity;
import com.jnwat.personDetail.PersonalInfoActivity;
import com.jnwat.tools.ModifySysTitle;
import com.jnwat.tools.ToastViewTools;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class MyProjectDetailActivity extends BaseActivity {
	private TextView title_bar_name;
	private ImageView iv_message;
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
			tv_houseNo3_people_num,
			tv_sw_trainingCourse_remark,
			tv_sw_trainingCourse_remark1; // 备注
	private String projectId;
	private RelativeLayout layout_1, layout_2, layout_3;
	private View layout_btn, layout_1_line, layout_2_line;
	private final int MYPROJECT = 1;
	private final int ALLPROJECT = 2;
	private int type;
	private HttpUtils http;
	private BProjectDetail projectDetail;
	private Button btn_course, btn_participants;

	private ListView listInfo = null; // 内容视图
	private LinearLayout mLinearlayout = null; // 搭班车住布局

	private TempClassListAdapter mTempAdapter = null;
	private TextView dbc_label = null;

	// protected PopLoginDialog mPopLoginDialog;

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

		layout_btn = findViewById(R.id.layout_btnShow);
		btn_participants = (Button) findViewById(R.id.btn_participants);

		if (type == MYPROJECT) {
			layout_btn.setVisibility(View.VISIBLE);
			// layout_btn.setVisibility(View.VISIBLE);
		} else if (type == ALLPROJECT) {
			layout_btn.setVisibility(View.VISIBLE);
			btn_participants.setVisibility(View.GONE);
			// layout_btn.setVisibility(View.GONE);
		}

		mLinearlayout = (LinearLayout) findViewById(R.id.dbc_info_layout);
		dbc_label = (TextView) findViewById(R.id.dbc_label);
		listInfo = (ListView) findViewById(R.id.list_temp_info);

		tv_sw_trainingCourse_name = (TextView) findViewById(R.id.tv_sw_trainingCourse_name);
		tv_sw_trainingCourse_periodNum = (TextView) findViewById(R.id.tv_sw_trainingCourse_periodNum);

		tv_sw_trainingCourse_trainType = (TextView) findViewById(R.id.tv_sw_trainingCourse_trainType);
		tv_sw_trainingCourse_startTime = (TextView) findViewById(R.id.tv_sw_trainingCourse_startTime);
		tv_sw_trainingCourse_endTime = (TextView) findViewById(R.id.tv_sw_trainingCourse_endTime);
		tv_sw_trainingCourse_trainPeopNum = (TextView) findViewById(R.id.tv_sw_trainingCourse_trainPeopNum);
		tv_sw_trainingCourse_trainingDays = (TextView) findViewById(R.id.tv_sw_trainingCourse_trainingDays);
		tv_sw_trainingCourse_respPeop = (TextView) findViewById(R.id.tv_sw_trainingCourse_respPeop);
		tv_sw_trainingCourse_classRoom = (TextView) findViewById(R.id.tv_sw_trainingCourse_classRoom);
		rv_accomdation_label = (TextView) findViewById(R.id.tv_accomdation_label);
		tv_house_No1 = (TextView) findViewById(R.id.tv_house_No1);
		tv_houseNo1_people_num = (TextView) findViewById(R.id.tv_houseNo1_people_num);
		tv_house_No2 = (TextView) findViewById(R.id.tv_house_No2);
		tv_houseNo2_people_num = (TextView) findViewById(R.id.tv_houseNo2_people_num);
		tv_house_No3 = (TextView) findViewById(R.id.tv_house_No3);
		tv_houseNo3_people_num = (TextView) findViewById(R.id.tv_houseNo3_people_num);
		tv_sw_trainingCourse_remark = (TextView) findViewById(R.id.tv_sw_trainingCourse_remark);
		tv_sw_trainingCourse_remark1 = (TextView) findViewById(R.id.tv_sw_trainingCourse_remark1);
		layout_1 = (RelativeLayout) findViewById(R.id.layout_1);
		layout_2 = (RelativeLayout) findViewById(R.id.layout_2);
		layout_3 = (RelativeLayout) findViewById(R.id.layout_3);
		layout_1_line = findViewById(R.id.layout_1_line);
		layout_2_line = findViewById(R.id.layout_2_line);
		iv_message = (ImageView) findViewById(R.id.iv_message);
		iv_message.setVisibility(View.GONE);
		btn_course = (Button) findViewById(R.id.btn_course);
		btn_participants = (Button) findViewById(R.id.btn_participants);
	}

	public void setData() {

		if (projectDetail != null) {
			tv_sw_trainingCourse_name.setText(projectDetail.getProjectName());
			tv_sw_trainingCourse_periodNum.setText(projectDetail.getCode());
			tv_sw_trainingCourse_trainType.setText(projectDetail
					.getProjecttype());
			tv_sw_trainingCourse_startTime
					.setText(projectDetail.getStartdate());
			tv_sw_trainingCourse_endTime.setText(projectDetail.getEnddate());
			tv_sw_trainingCourse_trainPeopNum
					.setText(projectDetail.getPronum());
			tv_sw_trainingCourse_trainingDays.setText(projectDetail.getDays());

			if (projectDetail.getClient().equals("null")) {
				tv_sw_trainingCourse_respPeop.setText("无");
				tv_sw_trainingCourse_respPeop
						.setTextColor(android.graphics.Color.RED);
			} else {
				tv_sw_trainingCourse_respPeop
						.setText(projectDetail.getClient());
			}

			tv_sw_trainingCourse_classRoom
					.setText(projectDetail.getClassroom());
			if (projectDetail.getRemark().equals("null")) {
				tv_sw_trainingCourse_remark.setText("");
				tv_sw_trainingCourse_remark.setVisibility(View.GONE);
				tv_sw_trainingCourse_remark1.setVisibility(View.GONE);

			} else {
				tv_sw_trainingCourse_remark.setText(projectDetail.getRemark());

			}
			// tv_sw_trainingCourse_remark.setText(projectDetail.getRemark());
			List<AccomdationInfo> accomdationArray = projectDetail
					.getAccommodation();
			int size = 0;
			if (accomdationArray != null) {
				size = accomdationArray.size();
			}
			if (size > 0) {
				rv_accomdation_label.setVisibility(View.VISIBLE);
			}
			for (int i = 0; i < size; i++) {
				if (i == 0) {
					AccomdationInfo accomdationInfo = accomdationArray.get(i);
					tv_house_No1.setText(accomdationInfo.getBuilding() + "号楼");
					layout_1.setVisibility(View.VISIBLE);
					if (size > 1) {
						layout_1_line.setVisibility(View.VISIBLE);
					}
					tv_houseNo1_people_num
							.setText(accomdationInfo.getPersons());
				}
				if (i == 1) {
					AccomdationInfo accomdationInfo = accomdationArray.get(i);
					tv_house_No2.setText(accomdationInfo.getBuilding() + "号楼");
					tv_houseNo2_people_num
							.setText(accomdationInfo.getPersons());
					if (size == 3) {
						layout_2_line.setVisibility(View.VISIBLE);
					}
					layout_2.setVisibility(View.VISIBLE);
				}
				if (i == 2) {
					AccomdationInfo accomdationInfo = accomdationArray.get(i);
					tv_house_No3.setText(accomdationInfo.getBuilding() + "号楼");
					tv_houseNo3_people_num
							.setText(accomdationInfo.getPersons());
					layout_3.setVisibility(View.VISIBLE);
				}
			}

			List<TempClassBean> tempList = projectDetail.getTempClassList();
			if (null != tempList) {
				{
					mLinearlayout.setVisibility(View.VISIBLE);
					mTempAdapter = new TempClassListAdapter(this, tempList);
					listInfo.setAdapter(mTempAdapter);
				}
			} else {
				// dbc_label.setVisibility(View.GONE);
				mLinearlayout.setVisibility(View.GONE);
				// listInfo.setVisibility(View.GONE);
			}

		}
		// mPopLoginDialog.dismisPop();
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		this.setBackListener("详情", true);
		btn_course.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				BIntentObj.isGetClass = 1;
				intent.setClass(MyProjectDetailActivity.this,
						SearchScheduleActivity.class);
				intent.putExtra("projectid", projectId);

				startActivity(intent);
			}

		});
		btn_participants.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MyProjectDetailActivity.this,
						ParticipantsActivity.class);
				intent.putExtra("Projectid", projectId);
				LogUtils.i("MyProject....Projectid is " + projectId);
				startActivity(intent);
			}

		});
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		getWebData();
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void setView() {
		// TODO Auto-generated method stub

		setContentView(R.layout.activity_sw_training_course);
		ModifySysTitle.ModifySysTitleStyle(R.color.black_dark,
				MyProjectDetailActivity.this);
		/*
		 * title_bar_name = (TextView) findViewById(R.id.title_bar_name);
		 * title_bar_name.setText("详情");
		 */
		Intent intent = getIntent();
		type = intent.getIntExtra("from", 0);
		projectId = intent.getStringExtra("Projectid");

		// mPopLoginDialog = new PopLoginDialog();
	}

	public void getWebData() {
		// String projectid ="ec5456a8-e6c6-492e-95b3-e3c7fd0bc0b6";
		// METHOAD_TRAININGCOURSEDETAIL
		// mPopLoginDialog = new PopLoginDialog();
		// mPopLoginDialog.showPoploginDialog(MyProjectDetailActivity.this);
		RequestParams params = new RequestParams(); // 参数
		params.addBodyParameter("projectid", projectId);
		http = new HttpUtils();
		http.configTimeout(3000);

		http.send(HttpRequest.HttpMethod.POST,
				AppConfig.getIp(MyProjectDetailActivity.this)
						+ AppConfig.METHOAD_TRAININGCOURSEDETAIL, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						LogUtils.d("responseInfo.result:" + "连接失败");
						tv_sw_trainingCourse_remark.setLines(2);
						tv_sw_trainingCourse_remark.setVisibility(View.GONE);
						tv_sw_trainingCourse_remark1.setVisibility(View.GONE);
						// ToastViewTools.initToast(MyProjectDetailActivity.this,
						// "连接失败");
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						LogUtils.d("responseInfo.result:" + responseInfo.result);
						// ToastViewTools.initToast(MyProjectDetailActivity.this,
						// responseInfo.result);
						AGetProjectDetail GetProjectDetail = new AGetProjectDetail();
						projectDetail = GetProjectDetail
								.analysisPersonalDeta(responseInfo.result);
						if (projectDetail.getRemark() == null
								|| "".equals(projectDetail.getRemark())) {
							tv_sw_trainingCourse_remark.setLines(2);
						}
						setData();
					}

				});

	}

}
