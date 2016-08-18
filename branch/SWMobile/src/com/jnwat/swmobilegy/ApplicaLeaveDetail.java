package com.jnwat.swmobilegy;

import com.jnwat.swmobilegy.R;

import android.widget.TextView;

public class ApplicaLeaveDetail extends BaseActivity{
    private TextView  title_bar_name;
	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setView() {
		// TODO Auto-generated method stub
    	setContentView(R.layout.activity_applica_leave_detail);
    	title_bar_name = (TextView) findViewById(R.id.title_bar_name);
    	title_bar_name.setText("请假申请");
	}

}
